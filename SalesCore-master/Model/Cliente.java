package Model;

public class Cliente extends Pessoa {
    String endereco;
    Pessoa cliente = new Pessoa();

    public Cliente(String nome, String cpf, String telefone, int idade, String endereco) {
        super(nome, cpf, telefone, idade);
        this.endereco = endereco;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
