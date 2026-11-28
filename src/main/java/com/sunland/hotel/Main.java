package com.sunland.hotel;

import com.sunland.hotel.enums.TipoCama;
import com.sunland.hotel.enums.TipoSuite;
import com.sunland.hotel.exceptions.ConflitoReservaException;
import com.sunland.hotel.exceptions.DadosInexistentesException;
import com.sunland.hotel.exceptions.DadosInvalidosException;
import com.sunland.hotel.exceptions.DadosNaoInformadosException;
import com.sunland.hotel.models.Hospede;
import com.sunland.hotel.models.Hotel;
import com.sunland.hotel.models.Quarto;
import com.sunland.hotel.models.QuartoDuplo;
import com.sunland.hotel.models.QuartoSimples;
import com.sunland.hotel.models.Reserva;
import com.sunland.hotel.models.Suite;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Scanner;

/**
 * Interface de linha de comando (CLI) do Sunland Hotel Manager.
 */
public class Main {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = new Hotel();
        int opcaoPrincipal;

        do {
            exibirMenuPrincipal();
            opcaoPrincipal = lerInteiro(scanner, "Escolha uma opção: ");

            switch (opcaoPrincipal) {
                case 1 -> exibirMenuHospedes(scanner, hotel);
                case 2 -> exibirMenuQuartos(scanner, hotel);
                case 3 -> exibirMenuReservas(scanner, hotel);
                case 0 -> System.out.println("\nEncerrando o sistema de gestão hoteleira. Até mais!");
                default -> System.out.println("\nOpção inválida! Tente novamente.");
            }
        } while (opcaoPrincipal != 0);

        scanner.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n=============================================");
        System.out.println("          SUNLAND HOTEL MANAGER              ");
        System.out.println("=============================================");
        System.out.println("1 ----- Gerenciar Hóspedes");
        System.out.println("2 ----- Gerenciar Quartos");
        System.out.println("3 ----- Gerenciar Reservas");
        System.out.println("0 ----- Sair");
        System.out.println("=============================================");
    }

    private static void exibirMenuHospedes(Scanner scanner, Hotel hotel) {
        int opcao;
        do {
            System.out.println("\n---------------------------------------------");
            System.out.println("          SUBMENU: GERENCIAR HÓSPEDES        ");
            System.out.println("---------------------------------------------");
            System.out.println("1. Cadastrar Hóspede");
            System.out.println("2. Listar Hóspedes");
            System.out.println("3. Deletar Hóspede");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("---------------------------------------------");

            opcao = lerInteiro(scanner, "Escolha uma opção: ");

            switch (opcao) {
                case 1 -> cadastrarHospede(scanner, hotel);
                case 2 -> listarHospedes(hotel);
                case 3 -> deletarHospede(scanner, hotel);
                case 0 -> { }
                default -> System.out.println("\nOpção inválida!");
            }
        } while (opcao != 0);
    }

    private static void cadastrarHospede(Scanner scanner, Hotel hotel) {
        System.out.println("\n[CADASTRO DE HÓSPEDE]");
        System.out.print("Digite o nome completo: ");
        String nome = scanner.nextLine().trim();

        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine().trim();

        System.out.print("Digite o email: ");
        String email = scanner.nextLine().trim();

        try {
            Hospede hospede = new Hospede(nome, cpf, email);
            hotel.adicionarHospede(hospede);
            System.out.println("\n[HÓSPEDE CADASTRADO COM SUCESSO!]");
        } catch (DadosInvalidosException | DadosNaoInformadosException | ConflitoReservaException e) {
            System.out.println("\n[ERRO AO CADASTRAR HÓSPEDE!] " + e.getMessage());
        }
    }

    private static void listarHospedes(Hotel hotel) {
        System.out.println("\n[LISTAGEM DE HÓSPEDES]");
        if (hotel.getHospedes().isEmpty()) {
            System.out.println("Nenhum hóspede cadastrado.");
            return;
        }
        hotel.getHospedes().forEach(System.out::println);
    }

    private static void deletarHospede(Scanner scanner, Hotel hotel) {
        System.out.println("\n[EXCLUSÃO DE HÓSPEDE]");
        if (hotel.getHospedes().isEmpty()) {
            System.out.println("Nenhum hóspede cadastrado.");
            return;
        }
        System.out.print("Digite o CPF do hóspede a ser deletado: ");
        String cpfDeletar = scanner.nextLine().trim();
        try {
            hotel.excluirHospede(hotel.buscarHospedePorCpf(cpfDeletar));
            System.out.println("\n[HÓSPEDE DELETADO COM SUCESSO!]");
        } catch (DadosInexistentesException | DadosNaoInformadosException | IllegalStateException e) {
            System.out.println("\n[ERRO AO DELETAR HÓSPEDE!] " + e.getMessage());
        }
    }

    private static void exibirMenuQuartos(Scanner scanner, Hotel hotel) {
        int opcao;
        do {
            System.out.println("\n---------------------------------------------");
            System.out.println("          SUBMENU: GERENCIAR QUARTOS         ");
            System.out.println("---------------------------------------------");
            System.out.println("1. Cadastrar Quarto");
            System.out.println("2. Listar Todos os Quartos");
            System.out.println("3. Consultar Quartos Disponíveis por Período");
            System.out.println("4. Deletar Quarto");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("---------------------------------------------");

            opcao = lerInteiro(scanner, "Escolha uma opção: ");

            switch (opcao) {
                case 1 -> cadastrarQuarto(scanner, hotel);
                case 2 -> listarQuartos(hotel);
                case 3 -> consultarQuartosDisponiveis(scanner, hotel);
                case 4 -> deletarQuarto(scanner, hotel);
                case 0 -> { }
                default -> System.out.println("\nOpção inválida!");
            }
        } while (opcao != 0);
    }

    private static void cadastrarQuarto(Scanner scanner, Hotel hotel) {
        System.out.println("\n[CADASTRO DE QUARTO]");
        System.out.println("1. Simples | 2. Duplo | 3. Suíte");
        int tipo = lerInteiro(scanner, "Tipo: ");
        if (tipo < 1 || tipo > 3) {
            System.out.println("Tipo de quarto inválido. Cadastro cancelado.");
            return;
        }

        int numero = lerInteiro(scanner, "Digite o número do quarto: ");
        BigDecimal precoBase = lerBigDecimal(scanner, "Digite o preço base da diária: ");
        int capacidade = lerInteiro(scanner, "Digite a capacidade do quarto: ");

        try {
            Quarto quarto;
            if (tipo == 1) {
                boolean possuiAr = lerBoolean(scanner, "O quarto possui ar-condicionado? (S/N): ");
                quarto = new QuartoSimples(numero, precoBase, capacidade, possuiAr);
            } else if (tipo == 2) {
                System.out.print("Digite o tipo de cama (Casal ou Solteiro): ");
                TipoCama tipoCama = TipoCama.fromString(scanner.nextLine());
                boolean possuiVaranda = lerBoolean(scanner, "O quarto possui varanda? (S/N): ");
                quarto = new QuartoDuplo(numero, precoBase, capacidade, tipoCama, possuiVaranda);
            } else {
                System.out.print("Digite o tipo de suíte (Executiva ou Presidencial): ");
                TipoSuite tipoSuite = TipoSuite.fromString(scanner.nextLine());
                quarto = new Suite(numero, precoBase, capacidade, tipoSuite);
            }
            hotel.adicionarQuarto(quarto);
            System.out.println("\n[QUARTO CADASTRADO COM SUCESSO!]");
        } catch (DadosInvalidosException | DadosNaoInformadosException | ConflitoReservaException e) {
            System.out.println("\n[ERRO AO CADASTRAR QUARTO!] " + e.getMessage());
        }
    }

    private static void listarQuartos(Hotel hotel) {
        System.out.println("\n[LISTAGEM DE QUARTOS]");
        if (hotel.getQuartos().isEmpty()) {
            System.out.println("Nenhum quarto cadastrado.");
            return;
        }
        hotel.getQuartos().forEach(q -> System.out.println(q.exibirDescricao()));
    }

    private static void consultarQuartosDisponiveis(Scanner scanner, Hotel hotel) {
        System.out.println("\n[QUARTOS DISPONÍVEIS POR PERÍODO]");
        LocalDate checkIn = lerData(scanner, "Data de check-in (dd/MM/yyyy): ");
        LocalDate checkOut = lerData(scanner, "Data de check-out (dd/MM/yyyy): ");
        try {
            List<Quarto> disponiveis = hotel.listarQuartosDisponiveis(checkIn, checkOut);
            if (disponiveis.isEmpty()) {
                System.out.println("Nenhum quarto disponível para o período informado.");
            } else {
                disponiveis.forEach(q -> System.out.println(q.exibirDescricao()));
            }
        } catch (DadosInvalidosException | DadosNaoInformadosException e) {
            System.out.println("\n[ERRO NA CONSULTA!] " + e.getMessage());
        }
    }

    private static void deletarQuarto(Scanner scanner, Hotel hotel) {
        System.out.println("\n[EXCLUSÃO DE QUARTO]");
        if (hotel.getQuartos().isEmpty()) {
            System.out.println("Nenhum quarto cadastrado.");
            return;
        }
        int numeroDeletar = lerInteiro(scanner, "Digite o número do quarto a ser deletado: ");
        try {
            hotel.excluirQuarto(hotel.buscarQuartoPorNumero(numeroDeletar));
            System.out.println("\n[QUARTO DELETADO COM SUCESSO!]");
        } catch (DadosInexistentesException | DadosNaoInformadosException | IllegalStateException e) {
            System.out.println("\n[ERRO AO DELETAR QUARTO!] " + e.getMessage());
        }
    }

    private static void exibirMenuReservas(Scanner scanner, Hotel hotel) {
        int opcao;
        do {
            System.out.println("\n---------------------------------------------");
            System.out.println("          SUBMENU: GERENCIAR RESERVAS        ");
            System.out.println("---------------------------------------------");
            System.out.println("1. Criar Reserva");
            System.out.println("2. Listar Reservas");
            System.out.println("3. Confirmar Reserva");
            System.out.println("4. Realizar Check-In");
            System.out.println("5. Realizar Check-Out");
            System.out.println("6. Cancelar Reserva");
            System.out.println("7. Deletar Reserva");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("---------------------------------------------");

            opcao = lerInteiro(scanner, "Escolha uma opção: ");

            switch (opcao) {
                case 1 -> criarReserva(scanner, hotel);
                case 2 -> listarReservas(hotel);
                case 3 -> confirmarReserva(scanner, hotel);
                case 4 -> realizarCheckIn(scanner, hotel);
                case 5 -> realizarCheckOut(scanner, hotel);
                case 6 -> cancelarReserva(scanner, hotel);
                case 7 -> deletarReserva(scanner, hotel);
                case 0 -> { }
                default -> System.out.println("\nOpção inválida!");
            }
        } while (opcao != 0);
    }

    private static void criarReserva(Scanner scanner, Hotel hotel) {
        System.out.println("\n[CRIAÇÃO DE RESERVA]");
        if (hotel.getHospedes().isEmpty()) {
            System.out.println("Nenhum hóspede cadastrado. Não é possível criar reserva.");
            return;
        }
        if (hotel.getQuartos().isEmpty()) {
            System.out.println("Nenhum quarto cadastrado. Não é possível criar reserva.");
            return;
        }

        try {
            System.out.print("Digite o CPF do hóspede: ");
            String cpfHospede = scanner.nextLine().trim();
            Hospede hospede = hotel.buscarHospedePorCpf(cpfHospede);

            int numeroQuarto = lerInteiro(scanner, "Digite o número do quarto: ");
            Quarto quarto = hotel.buscarQuartoPorNumero(numeroQuarto);

            LocalDate checkIn = lerData(scanner, "Digite a data de check-in (dd/MM/yyyy): ");
            LocalDate checkOut = lerData(scanner, "Digite a data de check-out (dd/MM/yyyy): ");

            Reserva reserva = hotel.criarReserva(hospede, quarto, checkIn, checkOut);
            System.out.println("\n[RESERVA CRIADA COM SUCESSO! Código: " + reserva.getCodigoCurto() + "]");
            System.out.println(reserva.exibirDescricao());
        } catch (DadosInexistentesException | DadosInvalidosException
                | DadosNaoInformadosException | ConflitoReservaException e) {
            System.out.println("\n[ERRO AO CRIAR RESERVA!] " + e.getMessage());
        }
    }

    private static void listarReservas(Hotel hotel) {
        System.out.println("\n[LISTAGEM DE RESERVAS]");
        if (hotel.getReservas().isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada.");
            return;
        }
        hotel.getReservas().forEach(r -> System.out.println(r.exibirDescricao()));
    }

    private static Reserva localizarReserva(Scanner scanner, Hotel hotel) {
        System.out.print("Digite o código da reserva (ou CPF do hóspede): ");
        String entrada = scanner.nextLine().trim();

        String apenasDigitos = Hospede.normalizarCpf(entrada);
        if (apenasDigitos.length() == 11) {
            List<Reserva> doHospede = hotel.buscarReservasPorCpf(entrada);
            if (doHospede.isEmpty()) {
                throw new DadosInexistentesException("Nenhuma reserva encontrada para o CPF informado.");
            }
            if (doHospede.size() == 1) {
                return doHospede.get(0);
            }
            System.out.println("\nMúltiplas reservas encontradas para este CPF:");
            doHospede.forEach(r -> System.out.println(r.exibirDescricao()));
            System.out.print("Informe o código da reserva desejada: ");
            return hotel.buscarReservaPorCodigoCurto(scanner.nextLine());
        }

        return hotel.buscarReservaPorCodigoCurto(entrada);
    }

    private static void confirmarReserva(Scanner scanner, Hotel hotel) {
        System.out.println("\n[CONFIRMAÇÃO DE RESERVA]");
        if (hotel.getReservas().isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada.");
            return;
        }
        try {
            Reserva reserva = localizarReserva(scanner, hotel);
            System.out.println("\n[RESERVA ENCONTRADA!]");
            System.out.println(reserva.exibirDescricao());
            if (!lerBoolean(scanner, "Deseja confirmar esta reserva? (S/N): ")) {
                System.out.println("\n[OPERAÇÃO CANCELADA PELO USUÁRIO!]");
                return;
            }
            hotel.confirmarReserva(reserva.getId());
            System.out.println("\n[RESERVA CONFIRMADA COM SUCESSO!]");
        } catch (DadosInexistentesException | DadosNaoInformadosException
                | ConflitoReservaException | IllegalStateException e) {
            System.out.println("\n[ERRO AO CONFIRMAR RESERVA!] " + e.getMessage());
        }
    }

    private static void realizarCheckIn(Scanner scanner, Hotel hotel) {
        System.out.println("\n[CHECK-IN]");
        if (hotel.getReservas().isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada.");
            return;
        }
        try {
            Reserva reserva = localizarReserva(scanner, hotel);
            hotel.realizarCheckIn(reserva.getId());
            System.out.println("\n[CHECK-IN REALIZADO COM SUCESSO!]");
        } catch (DadosInexistentesException | DadosNaoInformadosException | IllegalStateException e) {
            System.out.println("\n[ERRO AO REALIZAR CHECK-IN!] " + e.getMessage());
        }
    }

    private static void realizarCheckOut(Scanner scanner, Hotel hotel) {
        System.out.println("\n[CHECK-OUT]");
        if (hotel.getReservas().isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada.");
            return;
        }
        try {
            Reserva reserva = localizarReserva(scanner, hotel);
            hotel.realizarCheckOut(reserva.getId());
            System.out.println("\n[CHECK-OUT REALIZADO COM SUCESSO!] Total: R$ " + reserva.calcularValorTotal());
        } catch (DadosInexistentesException | DadosNaoInformadosException | IllegalStateException e) {
            System.out.println("\n[ERRO AO REALIZAR CHECK-OUT!] " + e.getMessage());
        }
    }

    private static void cancelarReserva(Scanner scanner, Hotel hotel) {
        System.out.println("\n[CANCELAMENTO DE RESERVA]");
        if (hotel.getReservas().isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada.");
            return;
        }
        try {
            Reserva reserva = localizarReserva(scanner, hotel);
            hotel.cancelarReserva(reserva.getId());
            System.out.println("\n[RESERVA CANCELADA COM SUCESSO!]");
        } catch (DadosInexistentesException | DadosNaoInformadosException | IllegalStateException e) {
            System.out.println("\n[ERRO AO CANCELAR RESERVA!] " + e.getMessage());
        }
    }

    private static void deletarReserva(Scanner scanner, Hotel hotel) {
        System.out.println("\n[EXCLUSÃO DE RESERVA]");
        if (hotel.getReservas().isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada.");
            return;
        }
        try {
            Reserva reserva = localizarReserva(scanner, hotel);
            hotel.excluirReserva(reserva);
            System.out.println("\n[RESERVA DELETADA COM SUCESSO!]");
        } catch (DadosInexistentesException | DadosNaoInformadosException | IllegalStateException e) {
            System.out.println("\n[ERRO AO DELETAR RESERVA!] " + e.getMessage());
        }
    }

    public static int lerInteiro(Scanner scanner, String msg) {
        while (true) {
            System.out.print(msg);
            String linha = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Por favor, digite um número inteiro.");
            }
        }
    }

    public static BigDecimal lerBigDecimal(Scanner scanner, String msg) {
        while (true) {
            System.out.print(msg);
            String linha = scanner.nextLine().trim().replace(",", ".");
            try {
                return new BigDecimal(linha);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Por favor, digite um valor numérico.");
            }
        }
    }

    public static LocalDate lerData(Scanner scanner, String msg) {
        while (true) {
            System.out.print(msg);
            String linha = scanner.nextLine().trim();
            try {
                return LocalDate.parse(linha, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida! Use o formato dd/MM/yyyy (ex.: 25/12/2026).");
            }
        }
    }

    public static boolean lerBoolean(Scanner scanner, String msg) {
        while (true) {
            System.out.print(msg);
            String entrada = scanner.nextLine().trim().toUpperCase();
            if ("S".equals(entrada)) {
                return true;
            }
            if ("N".equals(entrada)) {
                return false;
            }
            System.out.println("Entrada inválida! Por favor, digite [S] ou [N].");
        }
    }
}
