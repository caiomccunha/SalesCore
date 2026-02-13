package Service;

import Model.Cliente;
import Model.ItemVenda;
import Model.Produto;
import Model.Venda;
import Repository.ClienteRepository;
import Repository.VendaRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class VendaService {
    private final VendaRepository vendaRepo;
    private final ClienteRepository clienteRepo;
    private final EstoqueService estoqueService;
    private Integer proximoIdVenda = 1;

    public VendaService(VendaRepository vendaRepo, ClienteRepository clienteRepo, EstoqueService estoqueService) {
        this.vendaRepo = vendaRepo;
        this.clienteRepo = clienteRepo;
        this.estoqueService = estoqueService;
    }

    public Venda realizarVenda(String cpfCliente, Integer idProduto, Integer quantidade) {
        // Validações básicas
        if (cpfCliente == null || cpfCliente.isEmpty()) {
            throw new RuntimeException("CPF do cliente inválido");
        }
        if (idProduto == null || idProduto <= 0) {
            throw new RuntimeException("ID do produto inválido");
        }
        if (quantidade == null || quantidade <= 0) {
            throw new RuntimeException("Quantidade deve ser maior que zero");
        }

        // Busca o cliente
        Cliente cliente = clienteRepo.buscarPorCPF(cpfCliente);
        if (cliente == null) {
            throw new RuntimeException("Cliente com CPF " + cpfCliente + " não encontrado");
        }

        // Verifica disponibilidade no estoque
        Integer quantidadeDisponivel = estoqueService.obterQuantidadeDisponivel(idProduto);
        if (quantidadeDisponivel < quantidade) {
            throw new RuntimeException("Quantidade insuficiente em estoque. Disponível: " + quantidadeDisponivel);
        }

        // Obtém o produto do estoque
        Produto produto = estoqueService.obterProdutoDoEstoque(idProduto);

        // Cria a venda
        Venda venda = new Venda(proximoIdVenda++, cliente);

        // Cria o item da venda
        ItemVenda item = new ItemVenda(produto, quantidade, produto.getPreco());
        venda.adicionarItem(item);

        // Remove a quantidade do estoque
        estoqueService.removerQuantidade(idProduto, quantidade);

        // Salva a venda
        vendaRepo.adicionarVenda(venda);

        return venda;
    }

    public void listarVendas() {
        List<Venda> vendas = vendaRepo.listarVendas();
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda realizada!");
            return;
        }

        System.out.println("\n========== HISTÓRICO DE VENDAS ==========");
        for (Venda venda : vendas) {
            exibirDetalhesVenda(venda);
        }
        System.out.println("========================================\n");
    }

    public void listarVendasPorCliente(String cpf) {
        List<Venda> vendas = vendaRepo.buscarVendasPorCPF(cpf);
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda encontrada para o cliente com CPF: " + cpf);
            return;
        }

        System.out.println("\n========== VENDAS DO CLIENTE ==========");
        for (Venda venda : vendas) {
            exibirDetalhesVenda(venda);
        }
        System.out.println("=====================================\n");
    }

    private void exibirDetalhesVenda(Venda venda) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        System.out.println("--- Venda ID: " + venda.getId() + " ---");
        System.out.println("Cliente: " + venda.getCliente().getNome());
        System.out.println("CPF: " + venda.getCliente().getCpf());
        System.out.println("Data: " + venda.getDataVenda().format(formatter));
        System.out.println("Itens comprados:");
        for (ItemVenda item : venda.getItens()) {
            System.out.println("  - " + item.getProduto().getNome() +
                    " | Quantidade: " + item.getQuantidade() +
                    " | Preço unitário: R$ " + String.format("%.2f", item.getPrecoUnitario()) +
                    " | Subtotal: R$ " + String.format("%.2f", item.getSubtotal()));
        }
        System.out.println("Total da venda: R$ " + String.format("%.2f", venda.getTotal()));
        System.out.println("");
    }

    public void exibirResumoVenda(Venda venda) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        System.out.println("\n========== COMPRA REALIZADA COM SUCESSO ==========");
        System.out.println("Cliente: " + venda.getCliente().getNome());
        System.out.println("CPF: " + venda.getCliente().getCpf());
        System.out.println("Data: " + venda.getDataVenda().format(formatter));
        System.out.println("\nItens comprados:");
        for (ItemVenda item : venda.getItens()) {
            System.out.println("  - " + item.getProduto().getNome() +
                    " | Quantidade: " + item.getQuantidade() +
                    " | Preço unitário: R$ " + String.format("%.2f", item.getPrecoUnitario()));
        }
        System.out.println("\n>>> TOTAL: R$ " + String.format("%.2f", venda.getTotal()) + " <<<");
        System.out.println("==================================================\n");
    }
}

