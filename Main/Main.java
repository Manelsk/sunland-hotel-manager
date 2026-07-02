package Main;

import java.util.Scanner;
import models.*;
import exceptions.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = new Hotel();
        int opcaoPrincipal;

        do {
            exibirMenuPrincipal();
            opcaoPrincipal = lerInteiro(scanner, "Escolha uma opção: ");

            switch (opcaoPrincipal) {
                case 1:
                    exibirMenuHospedes(scanner, hotel);
                    break;
                case 2:
                    exibirMenuQuartos(scanner, hotel);
                    break;
                case 3:
                    exibirMenuReservas(scanner, hotel);
                    break;
                case 0:
                    System.out.println("\n Encerrando o sistema de gestão hoteleira. Até mais!");
                    break;
                default:
                    System.out.println("\n Opção inválida! Tente novamente.");
            }
        } while (opcaoPrincipal != 0);

        scanner.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n=============================================");
        System.out.println("          SISTEMA DE GESTÃO HOTEL            ");
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
            System.out.println("          SUBMENU: GERENCIAR HÓSPEDES         ");
            System.out.println("---------------------------------------------");
            System.out.println("1. Cadastrar Hóspede");
            System.out.println("2. Listar Hóspedes");
            System.out.println("3. Deletar Hóspede");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("---------------------------------------------");

            opcao = lerInteiro(scanner, "Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    System.out.println("\n[CADASTRO DE HÓSPEDE]");

                    System.out.print("Digite o nome completo: ");
                    String nome = scanner.nextLine().trim();

                    System.out.print("Digite o CPF (apenas números): ");
                    String cpf = scanner.nextLine().trim();

                    System.out.print("Digite o email: ");
                    String email = scanner.nextLine().trim();

                    try {
                        Hospede hospede = new Hospede(nome, cpf, email);
                        hotel.adicionarHospede(hospede);
                        System.out.println("\n[HÓSPEDE CADASTRADO COM SUCESSO!]");

                    } catch (DadosInvalidosException | DadosNaoInformadosException | DadosInexistentesException
                            | IllegalArgumentException e) {
                        System.out.println("\n[ERRO AO CADASTRAR HÓSPEDE!]");
                        System.out.println(e.getMessage());
                    }

                    break;
                case 2:
                    System.out.println("\n[LISTAGEM DE HÓSPEDES]");

                    if (hotel.getHospedes().isEmpty()) {
                        System.out.println("Nenhum hóspede cadastrado.");
                    } else {
                        hotel.getHospedes().forEach(hospede -> {
                            System.out.println(hospede.toString());
                        });
                    }

                    break;
                case 3:
                    System.out.println("\n[EXCLUSÃO DE HÓSPEDE]");

                    if (hotel.getHospedes().isEmpty()) {
                        System.out.println("Nenhum hóspede cadastrado.");
                    } else {
                        System.out.print("Digite o CPF do hóspede a ser deletado: ");
                        String cpfDeletar = scanner.nextLine().trim();
                        try {
                            hotel.excluirHospede(hotel.buscarHospedePorCpf(cpfDeletar));
                            System.out.println("\n[HÓSPEDE DELETADO COM SUCESSO!]");
                        } catch (DadosInexistentesException | IllegalStateException | IllegalArgumentException e) {
                            System.out.println("\n[ERRO AO DELETAR HÓSPEDE!]");
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("\n Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void exibirMenuQuartos(Scanner scanner, Hotel hotel) {
        int opcao;
        do {
            System.out.println("\n---------------------------------------------");
            System.out.println("          SUBMENU: GERENCIAR QUARTOS         ");
            System.out.println("---------------------------------------------");
            System.out.println("1. Cadastrar Quarto");
            System.out.println("2. Listar Quartos");
            System.out.println("3. Deletar Quarto");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("---------------------------------------------");

            opcao = lerInteiro(scanner, "Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    System.out.println("\n[CADASTRO DE QUARTO]");
                    System.out.println("Selecione o tipo de quarto:");
                    System.out.println("1. Simples | 2. Duplo | 3. Suíte");
                    int tipo = lerInteiro(scanner, "Tipo: ");
                    if (tipo < 1 || tipo > 3) {
                        System.out.println("Tipo de quarto inválido. Cadastro cancelado.");
                        break;
                    }
                    if (tipo == 1) {
                        System.out.println("Tipo de quarto selecionado: Simples");

                        System.out.print("Digite o número do quarto: ");
                        int numero = lerInteiro(scanner, "");

                        System.out.print("Digite o preço base do quarto: ");
                        double precoBase = lerDouble(scanner);

                        System.out.print("Digite a capacidade do quarto: ");
                        int capacidade = lerInteiro(scanner, "");

                        System.out.print("O quarto possui ar-condicionado? (S/N): ");
                        boolean possuiArCondicionado = lerBoolean(scanner);

                        try {
                            QuartoSimples quartoSimples = new QuartoSimples(numero, precoBase, capacidade,
                                    possuiArCondicionado);
                            hotel.adicionarQuarto(quartoSimples);
                            System.out.println("\n[QUARTO SIMPLES CADASTRADO COM SUCESSO!]");
                        } catch (DadosNaoInformadosException | DadosInexistentesException | DadosInvalidosException
                                | IllegalArgumentException e) {
                            System.out.println("\n[ERRO AO CADASTRAR QUARTO!]");
                            System.out.println(e.getMessage());
                        }

                    } else if (tipo == 2) {
                        System.out.println("Tipo de quarto selecionado: Duplo");

                        System.out.print("Digite o número do quarto: ");
                        int numero = lerInteiro(scanner, "");

                        System.out.print("Digite o preço base do quarto: ");
                        double precoBase = lerDouble(scanner);

                        System.out.print("Digite a capacidade do quarto: ");
                        int capacidade = lerInteiro(scanner, "");

                        System.out.print("Digite o tipo de cama (Casal ou Solteiro): ");
                        String tipoCama = scanner.next();

                        System.out.print("O quarto possui varanda? (S/N): ");
                        boolean possuiVaranda = lerBoolean(scanner);

                        try {
                            QuartoDuplo quartoDuplo = new QuartoDuplo(numero, precoBase, capacidade, tipoCama,
                                    possuiVaranda);
                            hotel.adicionarQuarto(quartoDuplo);
                            System.out.println("\n[QUARTO DUPLO CADASTRADO COM SUCESSO!]");
                        } catch (DadosNaoInformadosException | DadosInexistentesException | DadosInvalidosException
                                | IllegalArgumentException e) {
                            System.out.println("\n[ERRO AO CADASTRAR QUARTO!]");
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Tipo de quarto selecionado: Suíte");

                        System.out.print("Digite o número do quarto: ");
                        int numero = lerInteiro(scanner, "");

                        System.out.print("Digite o preço base do quarto: ");
                        double precoBase = lerDouble(scanner);

                        System.out.print("Digite a capacidade do quarto: ");
                        int capacidade = lerInteiro(scanner, "");

                        System.out.print("Digite o tipo de suíte (Presidencial/Executiva): ");
                        String tipoSuite = scanner.next().trim();

                        try {
                            Suite Suite = new Suite(numero, precoBase, capacidade, tipoSuite);
                            hotel.adicionarQuarto(Suite);
                            System.out.println("\n[QUARTO SUÍTE CADASTRADO COM SUCESSO!]");
                        } catch (DadosNaoInformadosException | DadosInexistentesException | DadosInvalidosException
                                | IllegalArgumentException e) {
                            System.out.println("\n[ERRO AO CADASTRAR QUARTO!]");
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                case 2:
                    System.out.println("\n[LISTAGEM DE QUARTOS]");
                    if (hotel.getQuartos().isEmpty()) {
                        System.out.println("Nenhum quarto cadastrado.");
                    } else {
                        hotel.getQuartos().forEach(quarto -> {
                            System.out.println(quarto.exibirDescricao());
                        });
                    }
                    break;
                case 3:
                    System.out.println("\n[EXCLUSÃO DE QUARTO]");

                    if (hotel.getQuartos().isEmpty()) {
                        System.out.println("Nenhum quarto cadastrado.");
                    } else {
                        System.out.print("Digite o número do quarto a ser deletado: ");
                        int numeroDeletar = lerInteiro(scanner, "");
                        try {
                            hotel.excluirQuarto(hotel.buscarQuartoporNumero(numeroDeletar));
                            System.out.println("\n[QUARTO DELETADO COM SUCESSO!]");
                        } catch (DadosInexistentesException | IllegalStateException | IllegalArgumentException e) {
                            System.out.println("\n[ERRO AO DELETAR QUARTO!]");
                            System.out.println(e.getMessage());
                        }
                    }

                    break;
                case 0:
                    break;
                default:
                    System.out.println("\n Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void exibirMenuReservas(Scanner scanner, Hotel hotel) {
        int opcao;
        do {
            System.out.println("\n---------------------------------------------");
            System.out.println("          SUBMENU: GERENCIAR RESERVAS         ");
            System.out.println("---------------------------------------------");
            System.out.println("1. Criar Reserva");
            System.out.println("2. Listar Reservas");
            System.out.println("3. Confirmar Reserva");
            System.out.println("4. Realizar Check-In");
            System.out.println("5. Realizar Check-Out");
            System.out.println("6. Cancelar/Deletar Reserva");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("---------------------------------------------");

            opcao = lerInteiro(scanner, "Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    System.out.println("\n[CRIAÇÃO DE RESERVA]");
                        if(hotel.getHospedes().isEmpty()){
                            System.out.println("Nenhum hóspede cadastrado. Não é possível criar reserva.");
                            break;
                        }
                        if(hotel.getQuartos().isEmpty()){
                            System.out.println("Nenhum quarto cadastrado. Não é possível criar reserva.");
                            break;
                        }
                       
                    try {
                        System.out.print("Digite o CPF do hóspede: ");
                        String cpfHospede = scanner.nextLine().trim();
                        Hospede hospede = hotel.buscarHospedePorCpf(cpfHospede);
                        
                        System.out.print("Digite o número do quarto: ");
                        int numeroQuarto = lerInteiro(scanner, "");
                        Quarto quarto = hotel.buscarQuartoporNumero(numeroQuarto);

                        System.out.print("Digite a quantidade de diárias: ");
                        int qtdeDiarias = lerInteiro(scanner, "");  

                        hotel.criarReserva(hospede, quarto, qtdeDiarias);
                        System.out.println("\n[RESERVA CRIADA COM SUCESSO!]");
                    
                    } catch (DadosInexistentesException | IllegalArgumentException e) {
                        System.out.println("\n[ERRO AO CRIAR RESERVA!]");
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("\n[LISTAGEM DE RESERVAS]");
                    if (hotel.getReservas().isEmpty()) {
                        System.out.println("Nenhuma reserva cadastrada.");
                    } else {
                        hotel.getReservas().forEach(reserva -> {
                            System.out.println(reserva.toString());
                        });
                    }
                    break;
                case 3:
                    System.out.println("\n[CONFIRMAÇÃO DE RESERVA]");
                        if(hotel.getReservas().isEmpty()){
                            System.out.println("Nenhuma reserva cadastrada. Não é possível confirmar reserva.");
                            break;
                        }
                    
                    System.out.print("Digite o CPF do hóspede para confirmar a reserva: ");
                        String cpfConfirmar = scanner.nextLine().trim();
                   
                    try {
                        Reserva reserva = hotel.buscarReservaPorCpf(cpfConfirmar);
                        System.out.println("\n[RESERVA ENCONTRADA!]");
                        System.out.println(reserva.exibirDescricao());
                        System.out.print("Deseja confirmar esta reserva? (S/N): ");
                        boolean confirmar = lerBoolean(scanner);
                        if(!confirmar){
                            System.out.println("\n[RESERVA CANCELADA PELO USUÁRIO!]");
                            break;
                        }
                        reserva.confirmaReserva();
                        System.out.println("\n[RESERVA CONFIRMADA COM SUCESSO!]");
                    } catch (DadosInexistentesException | IllegalStateException e) {
                        System.out.println("\n[ERRO AO CONFIRMAR RESERVA!]");
                        System.out.println(e.getMessage());
                    }

                    break;
                case 4:
                    System.out.println("\n[CHECK-IN]");
                        if(hotel.getReservas().isEmpty()){
                            System.out.println("Nenhuma reserva cadastrada. Não é possível realizar check-in.");
                            break;
                        }
                    
                        System.out.print("Digite o CPF do hóspede para realizar o check-in: ");
                        String cpfCheckIn = scanner.nextLine().trim(); 
                        

                    break;
                case 5:
                    System.out.println("\n[CHECK-OUT]");

                case 6:
                    System.out.println("\n[EXCLUSÃO DE RESERVA]");

                    break;
                case 0:
                    break;
                default:
                    System.out.println("\n Opção inválida!");
            }
        } while (opcao != 0);
    }

    public static int lerInteiro(Scanner scanner, String msg) {
        int valor = -1;
        boolean valido = false;
        do {
            System.out.print(msg);
            if (scanner.hasNextInt()) {
                valor = scanner.nextInt();
                valido = true;
            } else {
                System.out.println("Entrada inválida! Por favor, digite um número inteiro.");
            }
            scanner.nextLine();
        } while (!valido);
        return valor;
    }

    public static double lerDouble(Scanner scanner) {
        double valor = -1;
        boolean valido = false;
        do {
            if (scanner.hasNextDouble()) {
                valor = scanner.nextDouble();
                valido = true;
            } else {
                System.out.println("Entrada inválida! Por favor, digite um número decimal.");
            }
            scanner.nextLine();
        } while (!valido);
        return valor;
    }

    public static boolean lerBoolean(Scanner scanner) {
    String entrada;
    boolean valido = false;
    boolean resultado = false;

    do {
        entrada = scanner.nextLine().trim().toUpperCase();

        if (entrada.equals("S")) {
            resultado = true;
            valido = true;
        } else if (entrada.equals("N")) {
            resultado = false;
            valido = true;
        } else {
            System.out.println("Entrada inválida! Por favor, digite [S] ou [N]");
        }
    } while (!valido);

    return resultado;
}

}