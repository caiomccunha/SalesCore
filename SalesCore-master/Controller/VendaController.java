package Controller;

import Model.Venda;
import Service.VendaService;

import java.util.Scanner;

public class VendaController {
    private final VendaService vendaService;
    private final Scanner sc;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
        this.sc = new Scanner(System.in);
    }

    public void realizarVenda() {
        try {
            System.out.print("Digite o CPF do cliente: ");
            String cpfCliente = sc.nextLine();

            System.out.print("Digite o ID do produto: ");
            Integer idProduto = sc.nextInt();

            System.out.print("Digite a quantidade desejada: ");
            Integer quantidade = sc.nextInt();
            sc.nextLine();

            Venda venda = vendaService.realizarVenda(cpfCliente, idProduto, quantidade);
            vendaService.exibirResumoVenda(venda);

        } catch (RuntimeException e) {
            System.out.println("Erro na venda: " + e.getMessage());
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Erro ao realizar venda: " + e.getMessage());
            sc.nextLine();
        }
    }

    public void listarVendas() {
        vendaService.listarVendas();
    }

    public void listarVendasPorCliente() {
        try {
            System.out.print("Digite o CPF do cliente: ");
            String cpf = sc.nextLine();
            vendaService.listarVendasPorCliente(cpf);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}

