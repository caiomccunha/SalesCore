package Service;

import Model.Produto;
import Repository.ProdutoRepository;

public class ProdutoService {
    private final ProdutoRepository repo;
    private EstoqueService estoqueService;

    public ProdutoService(ProdutoRepository repo){
        this.repo = repo;
    }

    public void setEstoqueService(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    public Produto cadastrarProdutos (String nome, String marca, Double preco, Integer id, String codigo){
        if(nome.isEmpty()){
            throw new RuntimeException("Nome de produto inválido");
        }
        if (marca.isEmpty()){
            throw new RuntimeException("Marca inválida");
        }
        if(preco == null || preco <= 0){
            throw new RuntimeException("O campo de preço não pode estar vazio ou com um valor negativo");
        }
        if((id < 1) || (id == null)){
            throw new RuntimeException("ID inválido.");
        }
        if (codigo == null || codigo.length() < 13){
            throw new RuntimeException("Não reconhecido. Tente com um valor válido");
        }
        Produto produto = new Produto(nome, marca, preco, id, codigo);
        repo.adicionarProduto(produto);

        // Adiciona automaticamente ao estoque com quantidade 0
        if (estoqueService != null) {
            estoqueService.adicionarProdutoAoEstoque(produto, 0);
        }

        System.out.println(nome + " cadastrado com sucesso!!");
        return produto;
    }

    public Produto editarProdutos(String nomeNovo, String marcaNova, Double precoNovo, Integer id) {
        Produto produto = repo.buscarProdutosId(id);

        if (produto == null) {
            throw new RuntimeException("Produto não encontrado.");
        }

        // Só altera se o usuário digitou algo (não está vazio)
        if (nomeNovo != null && !nomeNovo.trim().isBlank()) {
            produto.setNome(nomeNovo);
        }

        if (marcaNova != null && !marcaNova.trim().isBlank()) {
            produto.setMarca(marcaNova);
        }

        // Só altera o preço se ele não for nulo
        if (precoNovo != null) {
            produto.setPreco(precoNovo);
        }

        System.out.println(nomeNovo + " teve os dados alterados com sucesso !!!");
        System.out.println("--- Novos Dados ---");
        System.out.println("Nome : " + produto.getNome());
        System.out.println("Marca: " + produto.getMarca());
        System.out.println("Preço: " + produto.getPreco());

        return produto;
    }

    public Produto excluirProduto(Integer id){
        Produto produto = repo.buscarProdutosId(id);
        System.out.println("ID do produto: ");
        if(produto == null){
            throw new RuntimeException("Produto não encontrado");
        }
        repo.excluirProduto(produto);
        System.out.println(produto + " excluido com sucesso");
        return produto;
    }
}
