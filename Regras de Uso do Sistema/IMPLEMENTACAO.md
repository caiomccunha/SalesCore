# SISTEMA DE ESTOQUE E VENDAS - RESUMO DE IMPLEMENTAÇÃO

## 📋 Arquivos Criados

### 📦 Model (Modelos de Dados)
1. **Estoque.java** - Representa um item de estoque com produto e quantidade
2. **Venda.java** - Representa uma venda realizada com cliente, itens e total
3. **ItemVenda.java** - Representa um item dentro de uma venda (produto + quantidade)

### 🗄️ Repository (Acesso a Dados)
1. **EstoqueRepository.java** - Gerencia lista de itens em estoque
2. **VendaRepository.java** - Gerencia histórico de vendas

### ⚙️ Service (Lógica de Negócio)
1. **EstoqueService.java** - Lógica para adicionar/remover produtos do estoque
2. **VendaService.java** - Lógica para realizar vendas e consultar histórico

### 🎮 Controller (Interface com Menus)
1. **EstoqueController.java** - Controlador de estoque
2. **VendaController.java** - Controlador de vendas

### 📱 Menu (Interface com Usuário)
1. **MenuEstoque.java** - Menu para gerenciar estoque
2. **MenuVendas.java** - Menu para realizar e consultar vendas

## ✏️ Arquivos Modificados

### 📝 Service
- **ProdutoService.java** 
  - ✅ Integração com EstoqueService
  - ✅ Ao cadastrar produto, ele é automaticamente adicionado ao estoque

### 📱 Menu
- **MenuClientes.java**
  - ✅ Refatorizado para receber Controller e Repository como parâmetros
  - ✅ Remoção de inicializações locais

- **MenuProdutos.java**
  - ✅ Refatorizado para receber Controller e Repository como parâmetros
  - ✅ Adicionada opção de listar produtos
  - ✅ Remoção de inicializações locais

### 🚀 Main.java
- ✅ Criadas instâncias centralizadas de Repositórios, Services e Controllers
- ✅ Injeção de dependência nos Menus
- ✅ Integração com os novos menus de Estoque e Vendas
- ✅ Menu principal atualizado com opções 3 (Vendas) e 4 (Estoque)

## 🔄 Fluxo de Funcionamento

### Cadastro de Produto
```
Usuario cadastra Produto 
    ↓
ProdutoController → ProdutoService → ProdutoRepository
    ↓
ProdutoService.setEstoqueService() → EstoqueService.adicionarProdutoAoEstoque()
    ↓
Produto adicionado ao repositório e ao estoque com quantidade 0
```

### Gerenciamento de Estoque
```
Usuario interage com MenuEstoque
    ↓
EstoqueController ↔ EstoqueService ↔ EstoqueRepository
    ↓
Operações: Listar, Adicionar Quantidade, Remover Quantidade
```

### Realização de Venda
```
Usuario escolhe Realizar Venda (CPF + ID Produto + Quantidade)
    ↓
VendaController → VendaService
    ↓
VendaService valida:
  - Cliente existe?
  - Produto está no estoque?
  - Quantidade disponível?
    ↓
Se válido:
  - Cria Venda com ItemVenda
  - Remove quantidade do EstoqueService
  - Salva em VendaRepository
  - Exibe comprovante
```

## 📊 Estrutura de Dados

### Estoque
```
Estoque
├── Produto (referência ao produto)
└── quantidade (Integer)

Operações:
- adicionarQuantidade(qtd)
- removerQuantidade(qtd)
```

### Venda
```
Venda
├── id (Integer)
├── cliente (Cliente)
├── itens (List<ItemVenda>)
├── dataVenda (LocalDateTime)
└── total (Double)

ItemVenda
├── produto (Produto)
├── quantidade (Integer)
├── precoUnitario (Double)
└── subtotal (Double)
```

## ✨ Características Principais

### 1. Integração Automática
- ✅ Produtos são automaticamente adicionados ao estoque ao cadastro
- ✅ Estoque é automaticamente atualizado ao realizar venda

### 2. Validações Completas
- ✅ Existência de cliente (CPF)
- ✅ Disponibilidade de produto
- ✅ Quantidade suficiente em estoque
- ✅ Validação de dados de entrada

### 3. Histórico de Vendas
- ✅ Registro completo de todas as vendas
- ✅ Consulta por cliente (CPF)
- ✅ Comprovante detalhado

### 4. Gerenciamento de Estoque
- ✅ Listagem com todas as informações (ID, Nome, Marca, Preço, Quantidade)
- ✅ Adicionar/remover quantidades
- ✅ Controle de quantidade negativa

## 🎯 Padrões de Projeto Utilizados

1. **Repository Pattern** - Abstração de acesso a dados
2. **Service Layer Pattern** - Lógica de negócio centralizada
3. **Controller Pattern** - Interface entre UI e serviços
4. **Dependency Injection** - Injeção de dependências nos construtores
5. **Model-View-Controller (MVC)** - Separação de responsabilidades

## 🔐 Segurança e Validações

- ✅ Validação de CPF (mínimo 11 dígitos)
- ✅ Validação de quantidade (não permitir negativas)
- ✅ Validação de existência de recursos
- ✅ Mensagens de erro claras
- ✅ Try-catch para tratar exceções

## 📈 Próximas Melhorias Recomendadas

1. Persistência em arquivo (JSON/XML)
2. Banco de dados real
3. Relatórios de vendas
4. Busca avançada de produtos
5. Sistema de usuários/autenticação
6. Interface gráfica (Swing/JavaFX)
7. Cancelamento de vendas
8. Devolução de produtos
9. Desconto por quantidade
10. Estoque mínimo com alertas

## 📞 Como Testar o Sistema

### Teste Básico
1. Execute Main.java
2. Cadastre um cliente (Menu 1)
3. Cadastre um produto (Menu 2)
4. Adicione quantidade ao estoque (Menu 4 → Opção 2)
5. Realize uma venda (Menu 3 → Opção 1)
6. Consulte o histórico de vendas (Menu 3 → Opção 2)

### Verificação de Integridade
- ✅ Estoque diminui após venda
- ✅ Venda aparece no histórico
- ✅ Comprovante exibe dados corretos
- ✅ Validações funcionam corretamente

## 📄 Documentação

Consulte o arquivo **README.md** para:
- Estrutura completa do projeto
- Exemplos de uso passo a passo
- Descrição de todos os menus
- Validações implementadas

