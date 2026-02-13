package Service;

import Model.Cliente;
import Repository.ClienteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClienteService {
    Scanner sc = new Scanner(System.in);
    private final ClienteRepository repo;
        public ClienteService (ClienteRepository repo){
            this.repo = repo;
        }
    public void cadastarClientes (String nome, String cpf, String telefone, int idade, String endereco){
        if(nome == null || !nome.matches("[A-Za-zÀ-ÿ ]+")) {
            throw new RuntimeException("Nome inválido");
        }
        if (cpf.length() < 11 || cpf == null){
            throw new RuntimeException("CPF inválido");
        }
        if (telefone == null || telefone.length() < 9){
            throw new RuntimeException("Telefone inválido");
        }
        if (idade < 0){
            throw new RuntimeException("Insira uma idade válida.");
        }
        if (endereco == null){
            throw new RuntimeException("Endereço inválido");
        }
        Cliente cliente = new Cliente(nome, cpf.trim(), telefone, idade, endereco);
        repo.salvar(cliente);
    }

    public void excluirCliente (String cpf){
        Cliente cliente = repo.buscarPorCPF(cpf);
        if(cliente == null){
            throw new RuntimeException("Cliente não encontrado");
        }
        repo.excluir(cliente);
    }

    public void editarDadosCliente (String cpf, String novoTelefone, String novoEndereco){
        Cliente cliente = repo.buscarPorCPF(cpf);

        if (cliente == null){
            throw new RuntimeException("Cliente não encontrado");
        }

        cliente.setTelefone(novoTelefone);
        cliente.setEndereco(novoEndereco);
        repo.salvar(cliente);

        System.out.println("Dados editados com sucesso");
    }

    public void listarCliente (){
        repo.listar();
    }




}
