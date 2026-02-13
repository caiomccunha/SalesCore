package Controller;

import Service.ClienteService;

public class ClienteController {
    private final ClienteService service;
    public ClienteController (ClienteService service){
        this.service = service;
    }

    public void cadastrarClientes (String nome, String cpf, String telefone, int idade, String endereco){
        service.cadastarClientes(nome, cpf, telefone, idade, endereco);
    }

    public void editarClientes (String cpf, String novoTelefone, String novoEndereco){
        service.editarDadosCliente(cpf ,novoTelefone, novoEndereco);
    }

    public  void excluir (String cpf){
        service.excluirCliente(cpf);
        System.out.println("Cliente excluido com sucesso");
    }

    public void listarClientes(){
        service.listarCliente();
    }


}
