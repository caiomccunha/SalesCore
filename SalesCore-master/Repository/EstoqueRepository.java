package Repository;

import Model.Estoque;
import Model.Produto;

import java.util.ArrayList;
import java.util.List;

public class EstoqueRepository {
    private final List<Estoque> estoque = new ArrayList<>();

    public void adicionarAoEstoque(Estoque item) {
        estoque.add(item);
    }

    public List<Estoque> listarEstoque() {
        return estoque;
    }

    public Estoque buscarPorIdProduto(Integer idProduto) {
        for (Estoque e : estoque) {
            if (e.getProduto().getId() == idProduto) {
                return e;
            }
        }
        throw new RuntimeException("Produto não encontrado no estoque");
    }

    public void removerDoEstoque(Estoque item) {
        estoque.remove(item);
    }

    public boolean produtoExisteNoEstoque(Integer idProduto) {
        for (Estoque e : estoque) {
            if (e.getProduto().getId() == idProduto) {
                return true;
            }
        }
        return false;
    }
}

