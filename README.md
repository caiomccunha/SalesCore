# SalesCore - Documentação

## Visão Geral

Este é um sistema de gerenciamento de vendas que funciona completamente no terminal (sem banco de dados). O sistema permite gerenciar clientes, produtos, estoque e realizar vendas.

## Estrutura do Sistema

### Modelos (Model)
- **Cliente**: Representa um cliente com dados pessoais (nome, CPF, telefone, idade, endereço)
- **Produto**: Representa um produto com informações comerciais (nome, marca, preço, ID, código)
- **Estoque**: Representa a quantidade de um produto disponível em estoque
- **Venda**: Representa uma transação de venda completa
- **ItemVenda**: Representa um item dentro de uma venda (produto + quantidade)

### Repositórios (Repository)
- **ClienteRepository**: Armazena e gerencia clientes em memória
- **ProdutoRepository**: Armazena e gerencia produtos em memória
- **EstoqueRepository**: Armazena e gerencia itens do estoque em memória
- **VendaRepository**: Armazena e gerencia vendas realizadas em memória

### Serviços (Service)
- **ClienteService**: Lógica de negócio para clientes (cadastro, edição, exclusão)
- **ProdutoService**: Lógica de negócio para produtos (cadastro, edição, exclusão) - **integrado com EstoqueService**
- **EstoqueService**: Lógica de negócio para estoque (adicionar, remover quantidades)
- **VendaService**: Lógica de negócio para vendas (realizar venda, listar vendas)

### Controllers (Controller)
- **ProdutoController**: Interface entre Menu e ProdutoService
- **ClienteController**: Interface entre Menu e ClienteService
- **EstoqueController**: Interface entre Menu e EstoqueService
- **VendaController**: Interface entre Menu e VendaService

### Menus (Menus)
- **MenuProdutos**: Menu para gerenciar produtos
- **MenuClientes**: Menu para gerenciar clientes
- **MenuEstoque**: Menu para gerenciar estoque
- **MenuVendas**: Menu para realizar e consultar vendas

## Fluxo de Funcionamento

### 1. Cadastro de Produto
Quando um novo produto é cadastrado:
1. O produto é adicionado ao repositório de produtos
2. **Automaticamente**, o produto é adicionado ao estoque com quantidade 0
3. O usuário pode então adicionar a quantidade desejada através do menu de estoque

### 2. Gerenciamento de Estoque
- **Adicionar Quantidade**: Aumenta a quantidade de um produto no estoque
- **Remover Quantidade**: Diminui a quantidade (com validação de saldo disponível)
- **Listar Estoque**: Mostra todos os produtos e suas quantidades

### 3. Realizar Venda
O fluxo de uma venda é:
1. O cliente informado precisa estar cadastrado
2. O produto deve estar no estoque
3. Deve haver quantidade suficiente
4. A venda é registrada
5. A quantidade é automaticamente removida do estoque
6. Um comprovante é exibido com:
   - Nome do cliente
   - CPF do cliente
   - Data/hora da compra
   - Produtos comprados (nome, quantidade, preço unitário)
   - Total da compra

## Como Usar

### Menu Principal
```
===== MENU PRINCIPAL =====
1 - Clientes
2 - Produtos
3 - Vendas
4 - Estoque
0 - Sair
```

### Menu de Clientes
```
a - Cadastrar Clientes
b - Editar Clientes
c - Excluir Clientes
d - Listar Clientes
e - Voltar
```

**Dados necessários para cadastro:**
- Nome (apenas letras e espaços)
- CPF (mínimo 11 dígitos)
- Telefone (mínimo 9 dígitos)
- Idade (número positivo)
- Endereço

### Menu de Produtos
```
a - Cadastrar Produtos
b - Editar Produtos
c - Excluir Produtos
d - Listar Produtos
e - Voltar
```

**Dados necessários para cadastro:**
- Nome
- Marca
- Preço (valor maior que zero)
- ID (número inteiro positivo único)
- Código (mínimo 13 caracteres)

**⚠️ IMPORTANTE:** Ao cadastrar um produto, ele é automaticamente adicionado ao estoque!

### Menu de Estoque
```
1. Listar Estoque
2. Adicionar Quantidade
3. Remover Quantidade
0. Voltar
```

Para adicionar ou remover quantidade, você precisa do ID do produto.

### Menu de Vendas
```
1. Realizar Venda
2. Listar Todas as Vendas
3. Listar Vendas por Cliente
0. Voltar
```

**Para Realizar uma Venda, forneça:**
- CPF do cliente (deve estar cadastrado)
- ID do produto (deve estar no estoque)
- Quantidade desejada (deve haver saldo disponível)

## Exemplo de Uso Completo

### Passo 1: Cadastrar um Cliente
```
Menu Principal → 1 (Clientes)
Opção: a (Cadastrar)
Nome: João Silva
CPF: 12345678901
Telefone: 1198765432
Idade: 30
Endereço: Rua A, 123
```

### Passo 2: Cadastrar um Produto
```
Menu Principal → 2 (Produtos)
Opção: a (Cadastrar)
Nome: Notebook
Marca: Dell
Preço: 3500.00
ID: 1
Código: 1234567890123
```

### Passo 3: Adicionar Quantidade ao Estoque
```
Menu Principal → 4 (Estoque)
Opção: 2 (Adicionar Quantidade)
ID do produto: 1
Quantidade: 5
```

### Passo 4: Realizar uma Venda
```
Menu Principal → 3 (Vendas)
Opção: 1 (Realizar Venda)
CPF do cliente: 12345678901
ID do produto: 1
Quantidade desejada: 2
```

**Resultado:** A venda é registrada, o cliente recebe um comprovante, e o estoque é atualizado de 5 para 3 unidades.

## Validações Importantes

### Clientes
- Nome deve conter apenas letras e espaços
- CPF deve ter no mínimo 11 dígitos
- Telefone deve ter no mínimo 9 dígitos
- Idade deve ser positiva
- Endereço não pode estar vazio

### Produtos
- Nome não pode estar vazio
- Marca não pode estar vazia
- Preço deve ser maior que zero
- ID deve ser positivo
- Código deve ter no mínimo 13 caracteres

### Estoque
- Quantidade a adicionar deve ser maior que zero
- Quantidade a remover deve ser menor ou igual à disponível

### Vendas
- Cliente deve estar cadastrado
- Produto deve estar no estoque
- Quantidade disponível deve ser suficiente

## Estrutura de Dados em Memória

Todos os dados são armazenados em **listas na memória (ArrayList)**. Isso significa que:

✅ **Vantagem**: Funcionamento rápido e sem necessidade de banco de dados
❌ **Limitação**: Os dados são perdidos quando o programa encerra

Para persistência de dados, seria necessário implementar um sistema de arquivo ou banco de dados.

## Estrutura de Pastas

```
SalesCore-master/
├── Model/
│   ├── Cliente.java
│   ├── Produto.java
│   ├── Estoque.java
│   ├── Venda.java
│   ├── ItemVenda.java
│   └── Pessoa.java
├── Repository/
│   ├── ClienteRepository.java
│   ├── ProdutoRepository.java
│   ├── EstoqueRepository.java
│   └── VendaRepository.java
├── Service/
│   ├── ClienteService.java
│   ├── ProdutoService.java
│   ├── EstoqueService.java
│   └── VendaService.java
├── Controller/
│   ├── ClienteController.java
│   ├── ProdutoController.java
│   ├── EstoqueController.java
│   └── VendaController.java
├── Menus/
│   ├── MenuClientes.java
│   ├── MenuProdutos.java
│   ├── MenuEstoque.java
│   └── MenuVendas.java
└── Main.java
```

## Integração Entre Módulos

1. **Produto → Estoque**: Quando um produto é cadastrado, ele é automaticamente adicionado ao estoque
2. **Venda → Estoque**: Quando uma venda é realizada, o estoque é automaticamente atualizado
3. **Venda → Cliente/Produto**: A venda usa dados do cliente e produto para gerar o comprovante

## Próximas Melhorias Sugeridas

- [ ] Persistência de dados em arquivo (JSON/XML)
- [ ] Banco de dados real
- [ ] Relatórios de vendas
- [ ] Busca de produtos por nome/marca
- [ ] Histórico de operações no estoque
- [ ] Interface gráfica (GUI)
