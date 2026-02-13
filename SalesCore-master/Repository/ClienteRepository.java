package Repository;

import Model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {
    private final List <Cliente> clientes = new ArrayList<>();

    public void salvar (Cliente cliente){
        clientes.add(cliente);
    }

    public List<Cliente> listar(){
        return clientes;
    }

    public void excluir (Cliente cliente){
        clientes.remove(cliente);
    }

    public Cliente buscarPorCPF(String CPF){
        for(Cliente c : clientes){
            if (c.getCpf().equals(CPF)){
                return c;
            }
        }
        throw new RuntimeException("Cliente com o cpf " + CPF + " não encontrado");
    }
}
