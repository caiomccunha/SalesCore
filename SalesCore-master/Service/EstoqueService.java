package Service;

import Model.Estoque;
import Model.Produto;
import Repository.EstoqueRepository;

import java.util.List;

public class EstoqueService {
    private final EstoqueRepository repo;

    public EstoqueService(EstoqueRepository repo) {
        this.repo = repo;
    }

    public void adicionarProdutoAoEstoque(Produto produto, Integer quantidade) {
        if (produto == null) {
            throw new RuntimeException("Produto inválido");
        }
        if (quantidade == null || quantidade <= 0) {
            throw new RuntimeException("Quantidade deve ser maior que zero");
        }

        // Verifica se o produto já existe no estoque
        if (repo.produtoExisteNoEstoque(produto.getId())) {
            Estoque itemExistente = repo.buscarPorIdProduto(produto.getId());
            itemExistente.adicionarQuantidade(quantidade);
            System.out.println(quantidade + " unidade(ns) de " + produto.getNome() + " adicionada(s) ao estoque!");
        } else {
            Estoque novoItem = new Estoque(produto, quantidade);
            repo.adicionarAoEstoque(novoItem);
            System.out.println(produto.getNome() + " adicionado ao estoque com " + quantidade + " unidade(ns)!");
        }
    }

    public void adicionarQuantidade(Integer idProduto, Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new RuntimeException("Quantidade deve ser maior que zero");
        }

        Estoque item = repo.buscarPorIdProduto(idProduto);
        item.adicionarQuantidade(quantidade);
        System.out.println(quantidade + " unidade(ns) adicionada(s) ao produto " + item.getProduto().getNome());
    }

    public void removerQuantidade(Integer idProduto, Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new RuntimeException("Quantidade deve ser maior que zero");
        }

        Estoque item = repo.buscarPorIdProduto(idProduto);
        item.removerQuantidade(quantidade);
        System.out.println(quantidade + " unidade(ns) removida(s) do produto " + item.getProduto().getNome());
    }

    public void listarEstoque() {
        List<Estoque> itens = repo.listarEstoque();
        if (itens.isEmpty()) {
            System.out.println("Estoque vazio!");
            return;
        }
        System.out.println("\n========== ESTOQUE ==========");
        for (Estoque item : itens) {
            System.out.println("ID: " + item.getProduto().getId() +
                    " | Produto: " + item.getProduto().getNome() +
                    " | Marca: " + item.getProduto().getMarca() +
                    " | Preço: R$ " + item.getProduto().getPreco() +
                    " | Quantidade: " + item.getQuantidade());
        }
        System.out.println("==============================\n");
    }

    public Integer obterQuantidadeDisponivel(Integer idProduto) {
        Estoque item = repo.buscarPorIdProduto(idProduto);
        return item.getQuantidade();
    }

    public Produto obterProdutoDoEstoque(Integer idProduto) {
        Estoque item = repo.buscarPorIdProduto(idProduto);
        return item.getProduto();
    }
}

