package Repository;

import Model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {
    private final List <Produto> produtos = new ArrayList<>();

    public void adicionarProduto (Produto produto){
        produtos.add(produto);
    }
    public List<Produto> listarProdutos(){
        return produtos;
    }

    public Produto buscarProdutosId (int id){
        for (Produto p : produtos){
            if (p.getId() == id){
            return p;
            }
        }
        throw new RuntimeException("Produto não encontrado.");
    }
    public void excluirProduto (Produto produto){
        produtos.remove(produto);
    }
}
