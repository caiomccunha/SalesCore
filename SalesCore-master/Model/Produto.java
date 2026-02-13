package Model;

public class Produto {
    private String nome, marca, codigo;
    private Double preco;
    private Integer id;

    public Produto(String nome, String marca, Double preco,Integer id, String codigo) {
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.codigo = codigo;
        this.id = id;
    }

    public Produto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
