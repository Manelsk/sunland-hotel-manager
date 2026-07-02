package Main;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
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
                        Hospede hospede1 = new Hospede(nome, cpf, email);
                        hotel.adicionarHospede(hospede1);
                        System.out.println("\n[HÓSPEDE CADASTRADO COM SUCESSO!]");
                        
                        } catch (DadosInvalidosException | DadosNaoInformadosException e) {
                        System.out.println("\n[ERRO AO CADASTRAR HÓSPEDE!]");
                        System.out.println(e.getMessage());
                        }

                        break;
                    case 2:
                        System.out.println("\n[LISTAGEM DE HÓSPEDES]");
                        
                        if(hotel.getHospedes().isEmpty()) {
                            System.out.println("Nenhum hóspede cadastrado.");
                        } else {
                            hotel.getHospedes().forEach(hospede -> {
                                System.out.println(hospede.toString());
                            });
                        }
                        
                        break;
                    case 3:
                        System.out.println("\n[EXCLUSÃO DE HÓSPEDE]");
                        if(hotel.getHospedes().isEmpty()) {
                            System.out.println("Nenhum hóspede cadastrado.");
                        } else {
                            System.out.print("Digite o CPF do hóspede a ser deletado: ");
                            String cpfDeletar = scanner.nextLine().trim();
                            try {
                                hotel.excluirHospede(hotel.buscarHospedePorCpf(cpfDeletar));
                                System.out.println("\n[HÓSPEDE DELETADO COM SUCESSO!]");
                            } catch (DadosInexistentesException e) {
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
            System.out.println("3. Atualizar Quarto");
            System.out.println("4. Deletar Quarto");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("---------------------------------------------");
            
            opcao = lerInteiro(scanner, "Escolha uma opção: ");

                switch (opcao) {
                    case 1:
                        System.out.println("\n[CADASTRO DE QUARTO]");
                        System.out.println("Selecione o tipo de quarto:");
                        System.out.println("1. Simples | 2. Duplo | 3. Suíte");
                        int tipo = lerInteiro(scanner, "Tipo: ");
                        
                        break;
                    case 2:
                        System.out.println("\n[LISTAGEM DE QUARTOS]");
                        if(hotel.getQuartos().isEmpty()) {
                            System.out.println("Nenhum quarto cadastrado.");
                        } else {
                            hotel.getQuartos().forEach(quarto -> {
                                System.out.println(quarto.toString());
                            });
                        }
                        break;
                    case 3:
                        System.out.println("\n[ATUALIZAÇÃO DE QUARTO]");
                        
                        break;
                    case 4:
                        System.out.println("\n[EXCLUSÃO DE QUARTO]");
                        
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
                        
                        break;
                    case 2:
                        System.out.println("\n[LISTAGEM DE RESERVAS]");
                        if(hotel.getReservas().isEmpty()) {
                            System.out.println("Nenhuma reserva cadastrada.");
                        } else {
                            hotel.getReservas().forEach(reserva -> {
                                System.out.println(reserva.toString());
                            });
                        }
                        break;
                    case 3:
                        System.out.println("\n[CONFIRMAÇÃO DE RESERVA]");
                        
                        break;
                    case 4:
                        System.out.println("\n[CHECK-IN]");
                        
                        break;
                    case 5:
                        System.out.println("\n[CHECK-OUT]");
                        
                    case 6:
                        System.out.println("\n[CANCELAMENTO DE RESERVA]");
                        
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("\n Opção inválida!");
                }
        } while (opcao != 0);
    }

    public static int lerInteiro(Scanner scanner, String msg) {
        int valor = 1;
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
}