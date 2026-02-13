# ARQUITETURA DO SISTEMA DE VENDAS

## 🏗️ Estrutura em Camadas

```
┌─────────────────────────────────────────────────────────────────┐
│                       CAMADA DE APRESENTAÇÃO (UI)              │
│  ┌──────────────┬──────────────┬──────────────┬──────────────┐ │
│  │ MenuClientes │ MenuProdutos │ MenuEstoque  │  MenuVendas  │ │
│  └──────────────┴──────────────┴──────────────┴──────────────┘ │
└───────────────┬────────────────────────────────────────────────┘
                │
┌───────────────▼────────────────────────────────────────────────┐
│                       CAMADA DE CONTROLADOR                    │
│  ┌──────────────┬──────────────┬──────────────┬──────────────┐ │
│  │ClienteCtrl   │ProdutoCtrl   │EstoqueCtrl   │VendaCtrl     │ │
│  └──────────────┴──────────────┴──────────────┴──────────────┘ │
└───────────────┬────────────────────────────────────────────────┘
                │
┌───────────────▼────────────────────────────────────────────────┐
│                     CAMADA DE SERVIÇO (Service)                │
│  ┌──────────────┬──────────────┬──────────────┬──────────────┐ │
│  │ClienteService│ProdutoService│EstoqueService│VendaService  │ │
│  │              │              │              │              │ │
│  │ • Validação  │ • Validação  │ • Adicionar  │ • Validação  │ │
│  │ • Regras neg │ • Regras neg │   quantidade │ • Calculo    │ │
│  └──────────────┴──────────────┴──────────────┴──────────────┘ │
└───────────────┬────────────────────────────────────────────────┘
                │
┌───────────────▼────────────────────────────────────────────────┐
│                   CAMADA DE REPOSITÓRIO (DAO)                  │
│  ┌──────────────┬──────────────┬──────────────┬──────────────┐ │
│  │ClienteRepo   │ProdutoRepo   │EstoqueRepo   │VendaRepo     │ │
│  │              │              │              │              │ │
│  │ • ArrayList  │ • ArrayList  │ • ArrayList  │ • ArrayList  │ │
│  │ • CRUD ops   │ • CRUD ops   │ • CRUD ops   │ • CRUD ops   │ │
│  └──────────────┴──────────────┴──────────────┴──────────────┘ │
└───────────────┬────────────────────────────────────────────────┘
                │
┌───────────────▼────────────────────────────────────────────────┐
│                   CAMADA DE MODELO (Domain)                    │
│  ┌──────────────┬──────────────┬──────────────┬──────────────┐ │
│  │   Cliente    │   Produto    │   Estoque    │    Venda     │ │
│  │              │              │              │              │ │
│  │ + nome       │ + nome       │ + produto    │ + id         │ │
│  │ + cpf        │ + marca      │ + quantidade │ + cliente    │ │
│  │ + telefone   │ + preco      │              │ + itens[]    │ │
│  │ + idade      │ + id         │              │ + dataVenda  │ │
│  │ + endereco   │ + codigo     │              │ + total      │ │
│  └──────────────┴──────────────┴──────────────┴──────────────┘ │
│                                                                 │
│  └──────────────────────────────────────────────────────────┘  │
│                       ItemVenda                                 │
│                                                                 │
│                   + produto                                     │
│                   + quantidade                                  │
│                   + precoUnitario                               │
│                   + subtotal                                    │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## 🔀 Fluxo de Dados

### 1️⃣ Cadastro de Produto

```
Main.java (inicializa)
    ↓
MenuProdutos.exibirMenuProdutos()
    ↓
Usuario escolhe "a" (Cadastrar)
    ↓
MenuProdutos coleta dados: nome, marca, preco, id, codigo
    ↓
ProdutoController.cadastrarProdutos(nome, marca, preco, id, codigo)
    ↓
ProdutoService.cadastrarProdutos()
    ├─ Validações
    ├─ Cria novo Produto
    ├─ repo.adicionarProduto() → ProdutoRepository
    └─ estoqueService.adicionarProdutoAoEstoque() ⭐
       ↓
       EstoqueService.adicionarProdutoAoEstoque()
       ├─ Verifica se produto existe no estoque
       └─ Se não existe: EstoqueRepository.adicionarAoEstoque()
          └─ Cria new Estoque(produto, 0)
```

### 2️⃣ Adicionar Quantidade ao Estoque

```
MenuEstoque.exibirMenu()
    ↓
Usuario escolhe "2" (Adicionar Quantidade)
    ↓
MenuEstoque coleta: idProduto, quantidade
    ↓
EstoqueController.adicionarQuantidade(idProduto, quantidade)
    ↓
EstoqueService.adicionarQuantidade()
    ├─ Validações
    ├─ repo.buscarPorIdProduto(idProduto)
    └─ item.adicionarQuantidade(quantidade)
```

### 3️⃣ Realizar Venda ⭐ (PRINCIPAL)

```
MenuVendas.exibirMenu()
    ↓
Usuario escolhe "1" (Realizar Venda)
    ↓
MenuVendas coleta: cpfCliente, idProduto, quantidade
    ↓
VendaController.realizarVenda()
    ↓
VendaService.realizarVenda(cpfCliente, idProduto, quantidade)
    ├─ Validações:
    │  ├─ CPF válido?
    │  ├─ ID Produto válido?
    │  └─ Quantidade > 0?
    ├─ clienteRepo.buscarPorCPF(cpfCliente)
    │  └─ Verifica se cliente existe
    ├─ estoqueService.obterQuantidadeDisponivel(idProduto)
    │  └─ Verifica se tem quantidade suficiente
    ├─ estoqueService.obterProdutoDoEstoque(idProduto)
    │  └─ Obtém o Produto
    ├─ Cria new Venda(id, cliente)
    ├─ Cria new ItemVenda(produto, quantidade, preco)
    ├─ venda.adicionarItem(itemVenda)
    ├─ estoqueService.removerQuantidade() ⭐ (ATUALIZA ESTOQUE)
    ├─ vendaRepo.adicionarVenda(venda)
    └─ vendaService.exibirResumoVenda()
       └─ Exibe comprovante detalhado
```

## 📊 Diagrama de Relacionamento

```
┌─────────────┐
│   CLIENTE   │
│─────────────│
│ - nome      │
│ - cpf       │
│ - telefone  │
│ - idade     │
│ - endereco  │
└──────┬──────┘
       │
       │ 1
       │
       │ * faz
       │
       ├──────────────────┐
       │                  │
       ▼                  ▼
    ┌──────────┐    ┌───────────┐
    │  VENDA   │    │ ESTOQUE   │
    │──────────│    │───────────│
    │ - id     │    │ - qtd     │
    │ - data   │    └─────┬─────┘
    │ - total  │          │
    └────┬─────┘          │ 1
         │                │
         │ * contém       │ * refere
         │                │
         ▼                ▼
    ┌──────────────┐  ┌─────────────┐
    │  ITEMVENDA   │  │   PRODUTO   │
    │──────────────│  │─────────────│
    │ - qtd        │  │ - id        │
    │ - preco_unit │  │ - nome      │
    │ - subtotal   │  │ - marca     │
    └──────┬───────┘  │ - preco     │
           │          │ - codigo    │
           │          └─────────────┘
           │
           │ * refere
           │
           ▼
```

## 🔗 Dependências Entre Classes

```
Main.java (PONTO DE ENTRADA)
├── Cria instâncias de Repositórios
├── Cria instâncias de Services
├── Cria instâncias de Controllers
└── Passa para os Menus

MenuClientes ←── ClienteController ←── ClienteService ←── ClienteRepository
MenuProdutos ←── ProdutoController ←── ProdutoService ←── ProdutoRepository
MenuEstoque  ←── EstoqueController ←── EstoqueService ←── EstoqueRepository
MenuVendas   ←── VendaController   ←── VendaService   ←── VendaRepository

ProdutoService ──injeta──→ EstoqueService
VendaService ──injeta──→ EstoqueService
VendaService ──injeta──→ ClienteRepository
```

## 💾 Fluxo de Armazenamento em Memória

```
┌─────────────────────────────────────────────┐
│          MEMÓRIA (Tempo de Execução)        │
├─────────────────────────────────────────────┤
│                                             │
│  ClienteRepository                          │
│  ├── ArrayList<Cliente> clientes            │
│  │   ├── Cliente(João, 123, ...)           │
│  │   └── Cliente(Maria, 456, ...)          │
│  │                                          │
│  ProdutoRepository                          │
│  ├── ArrayList<Produto> produtos            │
│  │   ├── Produto(Notebook, Dell, ...)      │
│  │   └── Produto(Mouse, Logitech, ...)     │
│  │                                          │
│  EstoqueRepository                          │
│  ├── ArrayList<Estoque> estoque             │
│  │   ├── Estoque(Produto1, 5 unidades)     │
│  │   └── Estoque(Produto2, 10 unidades)    │
│  │                                          │
│  VendaRepository                            │
│  ├── ArrayList<Venda> vendas                │
│  │   ├── Venda(1, João, [Item1, Item2])    │
│  │   └── Venda(2, Maria, [Item3])          │
│                                             │
└─────────────────────────────────────────────┘
         ↓ PERDIDO AO FECHAR O PROGRAMA ↓
```

## ⚙️ Validações em Cada Camada

```
┌──────────────────────────────────────────────┐
│       VALIDAÇÕES NO VENDASERVICE             │
├──────────────────────────────────────────────┤
│                                              │
│  1. CPF válido?                              │
│     └─ if (cpfCliente == null || isEmpty)   │
│                                              │
│  2. ID Produto válido?                       │
│     └─ if (idProduto == null || <= 0)       │
│                                              │
│  3. Quantidade válida?                       │
│     └─ if (quantidade == null || <= 0)      │
│                                              │
│  4. Cliente existe?                          │
│     └─ clienteRepo.buscarPorCPF()            │
│        └─ throws RuntimeException            │
│                                              │
│  5. Produto no estoque?                      │
│     └─ estoqueService.obterQuantidadeDisp() │
│        └─ throws RuntimeException            │
│                                              │
│  6. Quantidade suficiente?                   │
│     └─ if (quantidadeDisponivel < qtd)      │
│        └─ throws RuntimeException            │
│                                              │
└──────────────────────────────────────────────┘
```

## 🔄 Ciclo de Vida de uma Venda

```
INICIO
  │
  ├─→ Usuario aciona MenuVendas
  │
  ├─→ Coleta dados: CPF + ID + QTD
  │
  ├─→ VendaService.realizarVenda()
  │   │
  │   ├─→ Valida dados
  │   ├─→ Busca cliente
  │   ├─→ Verifica estoque
  │   ├─→ Cria Venda
  │   ├─→ Cria ItemVenda
  │   ├─→ Remove do estoque
  │   ├─→ Salva venda
  │   └─→ Exibe comprovante
  │
  ├─→ Venda é registrada no histórico
  │
  ├─→ Estoque é atualizado
  │
  └─→ Usuario vê confirmação
```

## 📱 Fluxo de Menus Integrados

```
┌──────────────────────────────────┐
│      MENU PRINCIPAL (Main)       │
│                                  │
│  1 → Clientes                    │
│  2 → Produtos                    │
│  3 → Vendas      ⭐ NOVO         │
│  4 → Estoque     ⭐ NOVO         │
│  0 → Sair                        │
└──────────────────────────────────┘
         │
         ├─1──────────────┐
         │                ▼
         │           MenuClientes
         │           a: Cadastrar
         │           b: Editar
         │           c: Excluir
         │           d: Listar
         │           e: Voltar
         │
         ├─2──────────────┐
         │                ▼
         │           MenuProdutos
         │           a: Cadastrar ⭐ Auto-adiciona ao estoque
         │           b: Editar
         │           c: Excluir
         │           d: Listar ⭐ NOVO
         │           e: Voltar
         │
         ├─3──────────────┐
         │                ▼
         │           MenuVendas ⭐ NOVO
         │           1: Realizar Venda ⭐ NOVO
         │           2: Listar Vendas ⭐ NOVO
         │           3: Vendas por Cliente ⭐ NOVO
         │           0: Voltar
         │
         ├─4──────────────┐
         │                ▼
         │           MenuEstoque ⭐ NOVO
         │           1: Listar Estoque ⭐ NOVO
         │           2: Adicionar Qtd ⭐ NOVO
         │           3: Remover Qtd ⭐ NOVO
         │           0: Voltar
         │
         └─0──────────────┐
                          ▼
                    ENCERRAR PROGRAMA
```

## ✅ Checklist de Integração

- ✅ Repositórios: ClienteRepository, ProdutoRepository, EstoqueRepository, VendaRepository
- ✅ Services: ClienteService, ProdutoService, EstoqueService, VendaService
- ✅ Controllers: ClienteController, ProdutoController, EstoqueController, VendaController
- ✅ Menus: MenuClientes, MenuProdutos, MenuEstoque, MenuVendas
- ✅ Models: Cliente, Produto, Estoque, Venda, ItemVenda, Pessoa
- ✅ Integração ProdutoService ↔ EstoqueService
- ✅ Integração VendaService ↔ EstoqueService
- ✅ Integração VendaService ↔ ClienteRepository
- ✅ Injeção de dependência em Main.java
- ✅ Validações em todas as camadas
- ✅ Tratamento de exceções

