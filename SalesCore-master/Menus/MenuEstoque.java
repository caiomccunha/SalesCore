package Menus;

import Controller.EstoqueController;
import java.util.Scanner;

public class MenuEstoque {
    private final EstoqueController estoqueController;
    private final Scanner sc;

    public MenuEstoque(EstoqueController estoqueController) {
        this.estoqueController = estoqueController;
        this.sc = new Scanner(System.in);
    }

    public void exibirMenu() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n========== MENU ESTOQUE ==========");
            System.out.println("1. Listar Estoque");
            System.out.println("2. Adicionar Quantidade");
            System.out.println("3. Remover Quantidade");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        estoqueController.listarEstoque();
                        break;
                    case 2:
                        estoqueController.adicionarQuantidade();
                        break;
                    case 3:
                        estoqueController.removerQuantidade();
                        break;
                    case 0:
                        sair = true;
                        System.out.println("Voltando ao menu principal...");
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Erro na entrada: " + e.getMessage());
                sc.nextLine();
            }
        }
    }
}

