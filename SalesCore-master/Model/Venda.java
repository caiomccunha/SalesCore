package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Venda {
    private Integer id;
    private Cliente cliente;
    private List<ItemVenda> itens;
    private LocalDateTime dataVenda;
    private Double total;

    public Venda(Integer id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.dataVenda = LocalDateTime.now();
        this.total = 0.0;
    }

    public Venda() {
        this.itens = new ArrayList<>();
        this.dataVenda = LocalDateTime.now();
        this.total = 0.0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void adicionarItem(ItemVenda item) {
        this.itens.add(item);
        this.total += item.getSubtotal();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return "Venda{" +
                "id=" + id +
                ", cliente='" + cliente.getNome() + '\'' +
                ", data=" + dataVenda.format(formatter) +
                ", total=R$ " + String.format("%.2f", total) +
                '}';
    }
}

