package Menus;

import Controller.ClienteController;
import Repository.ClienteRepository;

import java.util.Scanner;

public class MenuClientes {
    Scanner sc = new Scanner(System.in);
    ClienteRepository repo;
    ClienteController controller;
    String nome, cpf, telefone, endereco;
    int idade;

    public MenuClientes(ClienteController controller, ClienteRepository repo) {
        this.controller = controller;
        this.repo = repo;
    }

    public void exibirMenuClientes(){

    System.out.println("---------------- Bem Vindo ao Menu de Clientes ----------------------");

    boolean noMenuClientes = true;

    do {
        System.out.println("\nO que deseja fazer?");
        System.out.println("a - Cadastrar Clientes");
        System.out.println("b - Editar Clientes");
        System.out.println("c - Excluir Clientes");
        System.out.println("d - Listar Clientes");
        System.out.println("e - Voltar");

        String opcao = sc.nextLine().toLowerCase();

        switch (opcao) {

            case "a":
                System.out.println("=== Cadastrar Clientes ===");

                System.out.print("Nome: ");
                nome = sc.nextLine();

                System.out.print("CPF: ");
                cpf = sc.nextLine();

                System.out.print("Telefone: ");
                telefone = sc.nextLine();

                System.out.print("Idade: ");
                idade = sc.nextInt();
                sc.nextLine();

                System.out.print("Endereço: ");
                endereco = sc.nextLine();

                try {
                    controller.cadastrarClientes(nome, cpf, telefone, idade, endereco);
                    System.out.println("Cliente cadastrado com sucesso!");
                } catch (RuntimeException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
                break;

            case "b":
                System.out.println("=== Editar Cliente ===");

                System.out.print("CPF do cliente: ");
                cpf = sc.nextLine();

                System.out.print("Novo telefone: ");
                String novoTelefone = sc.nextLine();

                System.out.print("Novo endereço: ");
                String novoEndereco = sc.nextLine();

                try {
                    controller.editarClientes(cpf, novoTelefone, novoEndereco);
                } catch (RuntimeException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
                break;

            case "c":
                System.out.println("=== Excluir Cliente ===");

                System.out.print("CPF do cliente: ");
                cpf = sc.nextLine();

                try {
                    controller.excluir(cpf);
                    System.out.println("Cliente excluído com sucesso!");
                } catch (RuntimeException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
                break;

            case "d":
                System.out.println(repo.listar());
                break;

            case "e":
                noMenuClientes = false; // 🔙 volta ao menu principal
                break;

            default:
                System.out.println("Opção inválida");
        }

    } while (noMenuClientes);

    }

}
