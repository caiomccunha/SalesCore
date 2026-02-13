package Menus;

import Controller.ProdutoController;
import Repository.ProdutoRepository;

import java.util.Scanner;

public class MenuProdutos {
        Scanner sc = new Scanner(System.in);
        ProdutoRepository repo;
        ProdutoController controller;
        String nome, marca, codigo;
        Double preco;
        Integer id;

        public MenuProdutos(ProdutoController controller, ProdutoRepository repo) {
            this.controller = controller;
            this.repo = repo;
        }

    public void exibirMenuProdutos() {

        System.out.println(" ---------------- Bem Vindo ao Menu de Produtos ---------------------- ");

        boolean noMenuProdutos = true;
        do {

        System.out.println("a - Cadastar Produtos");
        System.out.println("b - Editar Produtos");
        System.out.println("c - Excluir Produtos");
        System.out.println("d - Listar Produtos");
        System.out.println("e - Voltar");

        String escolha = sc.next();

            switch (escolha){
                case "a" :
                    System.out.println("=== Cadastrar Produtos ===");
                    sc.nextLine();
                    System.out.println("Nome do produto: ");
                    nome = sc.nextLine();
                    System.out.println("Marca: ");
                    marca = sc.nextLine();
                    System.out.println("Preço: ");
                    preco = sc.nextDouble();
                    System.out.println("ID: ");
                    id = sc.nextInt();
                    System.out.println("Código: ");
                    codigo = sc.next();

                    try{
                        controller.cadastrarProdutos(nome, marca, preco, id, codigo);
                    }catch (RuntimeException e){
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "b":
                    System.out.println("=== Editar Produtos ===");
                    System.out.println("Insira o ID do produto: ");
                    id = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Nome Novo: ");
                    String nomeNovo = sc.nextLine();
                    System.out.println("Marca: ");
                    String marcaNova = sc.nextLine();
                    System.out.println("Preço: ");
                    String precoInput = sc.nextLine();

                    Double precoNovo = precoInput.isEmpty() ? null : Double.parseDouble(precoInput);
                    try {
                        controller.editarProdutos(nomeNovo, marcaNova, precoNovo, id);
                    }catch (RuntimeException e){
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "c":
                    System.out.println("=== Excluir Produtos ===");
                    System.out.println("Insira o ID do produto : ");
                    id = sc.nextInt();
                    sc.nextLine();
                    try {
                        controller.excluirProdutos(id);
                        System.out.println("Produto excluído com sucesso !!!");
                    }catch (RuntimeException e){
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "d":
                    System.out.println("=== Listar Produtos ===");
                    System.out.println(repo.listarProdutos());
                    break;
                case "e":
                    noMenuProdutos = false;
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }while (noMenuProdutos);
    }
}
