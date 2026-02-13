package Menus;

import Controller.VendaController;
import java.util.Scanner;

public class MenuVendas {
    private final VendaController vendaController;
    private final Scanner sc;

    public MenuVendas(VendaController vendaController) {
        this.vendaController = vendaController;
        this.sc = new Scanner(System.in);
    }

    public void exibirMenu() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n========== MENU VENDAS ==========");
            System.out.println("1. Realizar Venda");
            System.out.println("2. Listar Todas as Vendas");
            System.out.println("3. Listar Vendas por Cliente");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        vendaController.realizarVenda();
                        break;
                    case 2:
                        vendaController.listarVendas();
                        break;
                    case 3:
                        vendaController.listarVendasPorCliente();
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

