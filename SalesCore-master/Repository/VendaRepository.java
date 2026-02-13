package Repository;

import Model.Venda;

import java.util.ArrayList;
import java.util.List;

public class VendaRepository {
    private final List<Venda> vendas = new ArrayList<>();

    public void adicionarVenda(Venda venda) {
        vendas.add(venda);
    }

    public List<Venda> listarVendas() {
        return vendas;
    }

    public Venda buscarVendaPorId(Integer id) {
        for (Venda v : vendas) {
            if (v.getId() == id) {
                return v;
            }
        }
        throw new RuntimeException("Venda não encontrada");
    }

    public List<Venda> buscarVendasPorCPF(String cpf) {
        List<Venda> vendaCliente = new ArrayList<>();
        for (Venda v : vendas) {
            if (v.getCliente().getCpf().equals(cpf)) {
                vendaCliente.add(v);
            }
        }
        return vendaCliente;
    }

    public void removerVenda(Venda venda) {
        vendas.remove(venda);
    }
}

