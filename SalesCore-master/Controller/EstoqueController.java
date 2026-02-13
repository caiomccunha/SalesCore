package Controller;

import Model.Estoque;
import Model.Produto;
import Service.EstoqueService;
import Service.ProdutoService;

import java.util.Scanner;

public class EstoqueController {
    private final EstoqueService estoqueService;
    private final ProdutoService produtoService;
    private final Scanner sc;

    public EstoqueController(EstoqueService estoqueService, ProdutoService produtoService) {
        this.estoqueService = estoqueService;
        this.produtoService = produtoService;
        this.sc = new Scanner(System.in);
    }

    public void adicionarProdutoAoEstoque(Produto produto, Integer quantidade) {
        try {
            estoqueService.adicionarProdutoAoEstoque(produto, quantidade);
        } catch (RuntimeException e) {
            System.out.println("Erro ao adicionar ao estoque: " + e.getMessage());
        }
    }

    public void adicionarQuantidade() {
        try {
            System.out.print("Digite o ID do produto: ");
            Integer idProduto = sc.nextInt();
            sc.nextLine();

            System.out.print("Quantidade a adicionar: ");
            Integer quantidade = sc.nextInt();
            sc.nextLine();

            estoqueService.adicionarQuantidade(idProduto, quantidade);
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao adicionar quantidade: " + e.getMessage());
            sc.nextLine();
        }
    }

    public void removerQuantidade() {
        try {
            System.out.print("Digite o ID do produto: ");
            Integer idProduto = sc.nextInt();
            sc.nextLine();

            System.out.print("Quantidade a remover: ");
            Integer quantidade = sc.nextInt();
            sc.nextLine();

            estoqueService.removerQuantidade(idProduto, quantidade);
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao remover quantidade: " + e.getMessage());
            sc.nextLine();
        }
    }

    public void listarEstoque() {
        estoqueService.listarEstoque();
    }
}

