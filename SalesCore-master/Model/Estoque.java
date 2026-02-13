package Model;

public class Estoque {
    private Produto produto;
    private Integer quantidade;

    public Estoque(Produto produto, Integer quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Estoque() {
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void adicionarQuantidade(Integer qtd) {
        if (qtd <= 0) {
            throw new RuntimeException("A quantidade a adicionar deve ser maior que zero");
        }
        this.quantidade += qtd;
    }

    public void removerQuantidade(Integer qtd) {
        if (qtd <= 0) {
            throw new RuntimeException("A quantidade a remover deve ser maior que zero");
        }
        if (this.quantidade < qtd) {
            throw new RuntimeException("Quantidade insuficiente em estoque. Disponível: " + this.quantidade);
        }
        this.quantidade -= qtd;
    }

    @Override
    public String toString() {
        return "Estoque{" +
                "produto=" + produto.getNome() +
                ", quantidade=" + quantidade +
                '}';
    }
}

