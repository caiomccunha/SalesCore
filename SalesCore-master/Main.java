import Controller.ClienteController;
import Controller.EstoqueController;
import Controller.ProdutoController;
import Controller.VendaController;
import Menus.MenuClientes;
import Menus.MenuEstoque;
import Menus.MenuProdutos;
import Menus.MenuVendas;
import Repository.ClienteRepository;
import Repository.EstoqueRepository;
import Repository.ProdutoRepository;
import Repository.VendaRepository;
import Service.ClienteService;
import Service.EstoqueService;
import Service.ProdutoService;
import Service.VendaService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Inicializa repositórios
        ClienteRepository clienteRepo = new ClienteRepository();
        ProdutoRepository produtoRepo = new ProdutoRepository();
        EstoqueRepository estoqueRepo = new EstoqueRepository();
        VendaRepository vendaRepo = new VendaRepository();

        // Inicializa serviços
        ClienteService clienteService = new ClienteService(clienteRepo);
        EstoqueService estoqueService = new EstoqueService(estoqueRepo);
        ProdutoService produtoService = new ProdutoService(produtoRepo);
        produtoService.setEstoqueService(estoqueService);
        VendaService vendaService = new VendaService(vendaRepo, clienteRepo, estoqueService);

        // Inicializa controllers
        ProdutoController produtoController = new ProdutoController(produtoService);
        ClienteController clienteController = new ClienteController(clienteService);
        EstoqueController estoqueController = new EstoqueController(estoqueService, produtoService);
        VendaController vendaController = new VendaController(vendaService);

        // Inicializa menus com as mesmas instâncias de serviços
        MenuClientes clientes = new MenuClientes(clienteController, clienteRepo);
        MenuProdutos produtos = new MenuProdutos(produtoController, produtoRepo);
        MenuEstoque estoque = new MenuEstoque(estoqueController);
        MenuVendas vendas = new MenuVendas(vendaController);

        int escolha;

        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1 - Clientes");
            System.out.println("2 - Produtos");
            System.out.println("3 - Vendas");
            System.out.println("4 - Estoque");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            escolha = sc.nextInt();
            sc.nextLine(); // limpa buffer

            switch (escolha) {
                case 1:
                    clientes.exibirMenuClientes();
                    break;
                case 2:
                    produtos.exibirMenuProdutos();
                    break;
                case 3:
                    vendas.exibirMenu();
                    break;
                case 4:
                    estoque.exibirMenu();
                    break;
                case 0:
                    System.out.println("Encerrando sistema...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }

        } while (escolha != 0);
    }
}
