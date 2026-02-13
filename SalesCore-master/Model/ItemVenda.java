package Model;

public class ItemVenda {
    private Produto produto;
    private Integer quantidade;
    private Double precoUnitario;
    private Double subtotal;

    public ItemVenda(Produto produto, Integer quantidade, Double precoUnitario) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal = quantidade * precoUnitario;
    }

    public ItemVenda() {
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
        this.subtotal = quantidade * precoUnitario;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
        this.subtotal = quantidade * precoUnitario;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    @Override
    public String toString() {
        return "ItemVenda{" +
                "produto='" + produto.getNome() + '\'' +
                ", quantidade=" + quantidade +
                ", precoUnitario=R$ " + String.format("%.2f", precoUnitario) +
                ", subtotal=R$ " + String.format("%.2f", subtotal) +
                '}';
    }
}

