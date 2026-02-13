package Controller;

import Service.ProdutoService;

public class ProdutoController {
    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }
    public void cadastrarProdutos (String nome, String marca, Double preco, Integer id, String codigo){
        service.cadastrarProdutos(nome, marca, preco, id, codigo);
    }
    public void editarProdutos (String nomeNovo, String marcaNova, Double precoNovo, Integer id){
        service.editarProdutos(nomeNovo, marcaNova, precoNovo, id);
    }

    public void excluirProdutos(Integer id){
        service.excluirProduto(id);
    }

}
