# GUIA DE TESTES E EXEMPLOS DE USO

## 🧪 Testes Básicos

### Teste 1: Cadastro de Cliente
**Objetivo**: Verificar se o cliente é cadastrado com sucesso

```
Menu Principal → 1 (Clientes)
Opção: a (Cadastrar Clientes)

Entrada:
- Nome: João da Silva
- CPF: 12345678901
- Telefone: 1198765432
- Idade: 30
- Endereço: Rua Principal, 100

Saída Esperada:
✓ Cliente cadastrado com sucesso!
```

---

### Teste 2: Cadastro de Produto com Auto-Adição ao Estoque
**Objetivo**: Verificar se o produto é cadastrado e automaticamente adicionado ao estoque

```
Menu Principal → 2 (Produtos)
Opção: a (Cadastrar Produtos)

Entrada:
- Nome do produto: Notebook Dell
- Marca: Dell
- Preço: 3500.00
- ID: 1
- Código: 1234567890123

Saída Esperada:
✓ Notebook Dell cadastrado com sucesso!!

Verificação (Menu 4 → Estoque → Opção 1):
ID: 1 | Produto: Notebook Dell | Marca: Dell | Preço: R$ 3500.00 | Quantidade: 0
```

---

### Teste 3: Adicionar Quantidade ao Estoque
**Objetivo**: Verificar se a quantidade é adicionada corretamente

```
Menu Principal → 4 (Estoque)
Opção: 2 (Adicionar Quantidade)

Entrada:
- ID do produto: 1
- Quantidade: 5

Saída Esperada:
✓ 5 unidade(ns) adicionada(s) ao produto Notebook Dell
```

---

### Teste 4: Listar Estoque
**Objetivo**: Verificar a exibição correta do estoque

```
Menu Principal → 4 (Estoque)
Opção: 1 (Listar Estoque)

Saída Esperada:
========== ESTOQUE ==========
ID: 1 | Produto: Notebook Dell | Marca: Dell | Preço: R$ 3500.00 | Quantidade: 5
==============================
```

---

### Teste 5: Realizar Venda com Sucesso
**Objetivo**: Verificar se a venda é realizada e o estoque é atualizado

```
Menu Principal → 3 (Vendas)
Opção: 1 (Realizar Venda)

Entrada:
- CPF do cliente: 12345678901
- ID do produto: 1
- Quantidade desejada: 2

Saída Esperada:
========== COMPRA REALIZADA COM SUCESSO ==========
Cliente: João da Silva
CPF: 12345678901
Data: [data/hora atual]

Itens comprados:
  - Notebook Dell | Quantidade: 2 | Preço unitário: R$ 3500.00

>>> TOTAL: R$ 7000.00 <<<
==================================================

Verificação (Menu 4 → Estoque → Opção 1):
Quantidade: 3 (era 5, vendemos 2)
```

---

### Teste 6: Listar Histórico de Vendas
**Objetivo**: Verificar se o histórico de vendas é exibido corretamente

```
Menu Principal → 3 (Vendas)
Opção: 2 (Listar Todas as Vendas)

Saída Esperada:
========== HISTÓRICO DE VENDAS ==========
--- Venda ID: 1 ---
Cliente: João da Silva
CPF: 12345678901
Data: [data/hora]
Itens comprados:
  - Notebook Dell | Quantidade: 2 | Preço unitário: R$ 3500.00 | Subtotal: R$ 7000.00
Total da venda: R$ 7000.00

========================================
```

---

### Teste 7: Listar Vendas por Cliente
**Objetivo**: Verificar se as vendas de um cliente específico são listadas

```
Menu Principal → 3 (Vendas)
Opção: 3 (Listar Vendas por Cliente)

Entrada:
- CPF do cliente: 12345678901

Saída Esperada:
========== VENDAS DO CLIENTE ==========
--- Venda ID: 1 ---
Cliente: João da Silva
CPF: 12345678901
...
=====================================
```

---

## ⚠️ Testes de Validação e Erros

### Teste 8: Venda com CPF Inválido
**Objetivo**: Verificar se a validação de CPF funciona

```
Menu Principal → 3 (Vendas)
Opção: 1 (Realizar Venda)

Entrada:
- CPF do cliente: 99999999999 (não cadastrado)
- ID do produto: 1
- Quantidade: 1

Saída Esperada:
❌ Erro na venda: Cliente com CPF 99999999999 não encontrado
```

---

### Teste 9: Venda com Quantidade Insuficiente
**Objetivo**: Verificar se a validação de estoque funciona

```
Menu Principal → 3 (Vendas)
Opção: 1 (Realizar Venda)

Entrada:
- CPF do cliente: 12345678901
- ID do produto: 1
- Quantidade desejada: 100 (temos apenas 3)

Saída Esperada:
❌ Erro na venda: Quantidade insuficiente em estoque. Disponível: 3
```

---

### Teste 10: Remoção com Quantidade Negativa
**Objetivo**: Verificar se a validação de quantidade funciona

```
Menu Principal → 4 (Estoque)
Opção: 3 (Remover Quantidade)

Entrada:
- ID do produto: 1
- Quantidade a remover: 100 (temos apenas 3)

Saída Esperada:
❌ Erro: Quantidade insuficiente em estoque. Disponível: 3
```

---

### Teste 11: Cadastro de Cliente com Nome Inválido
**Objetivo**: Verificar validação de nome

```
Menu Principal → 1 (Clientes)
Opção: a (Cadastrar Clientes)

Entrada:
- Nome: João123 (contém números)
- ...

Saída Esperada:
❌ Erro: Nome inválido
```

---

### Teste 12: Cadastro de Produto com Preço Negativo
**Objetivo**: Verificar validação de preço

```
Menu Principal → 2 (Produtos)
Opção: a (Cadastrar Produtos)

Entrada:
- Nome: Mouse
- Marca: Logitech
- Preço: -50 (negativo!)
- ...

Saída Esperada:
❌ Erro: O campo de preço não pode estar vazio ou com um valor negativo
```

---

## 🎯 Cenário Completo de Fluxo

### Cenário: Loja Virtual com 3 Produtos

#### Passo 1: Cadastrar Clientes

```
Menu 1 → a
Nome: Maria Santos | CPF: 11111111111 | Tel: 1187654321 | Idade: 25 | End: Av Brasil
Nome: Pedro Costa | CPF: 22222222222 | Tel: 1195432198 | Idade: 35 | End: Rua Norte
```

#### Passo 2: Cadastrar Produtos

```
Menu 2 → a
1) Notebook → Dell → R$ 3500.00 → ID: 1 → Código: 1234567890123
2) Mouse → Logitech → R$ 150.00 → ID: 2 → Código: 1234567890124
3) Teclado → Corsair → R$ 450.00 → ID: 3 → Código: 1234567890125
```

#### Passo 3: Adicionar Quantidades ao Estoque

```
Menu 4 → 2
ID: 1 → Quantidade: 10
ID: 2 → Quantidade: 50
ID: 3 → Quantidade: 30
```

#### Passo 4: Maria compra um Notebook e um Mouse

```
Menu 3 → 1
CPF: 11111111111 → ID: 1 → Qtd: 1

========== COMPRA REALIZADA COM SUCESSO ==========
Cliente: Maria Santos
CPF: 11111111111
Data: 13/02/2026 10:30:45

Itens comprados:
  - Notebook | Quantidade: 1 | Preço unitário: R$ 3500.00

>>> TOTAL: R$ 3500.00 <<<
==================================================

Menu 3 → 1
CPF: 11111111111 → ID: 2 → Qtd: 2

========== COMPRA REALIZADA COM SUCESSO ==========
Cliente: Maria Santos
CPF: 11111111111
Data: 13/02/2026 10:35:12

Itens comprados:
  - Mouse | Quantidade: 2 | Preço unitário: R$ 150.00

>>> TOTAL: R$ 300.00 <<<
==================================================
```

#### Passo 5: Pedro compra Teclado

```
Menu 3 → 1
CPF: 22222222222 → ID: 3 → Qtd: 1

========== COMPRA REALIZADA COM SUCESSO ==========
Cliente: Pedro Costa
CPF: 22222222222
Data: 13/02/2026 11:00:00

Itens comprados:
  - Teclado | Quantidade: 1 | Preço unitário: R$ 450.00

>>> TOTAL: R$ 450.00 <<<
==================================================
```

#### Passo 6: Verificar Estoque Final

```
Menu 4 → 1

========== ESTOQUE ==========
ID: 1 | Produto: Notebook | Marca: Dell | Preço: R$ 3500.00 | Quantidade: 9
ID: 2 | Produto: Mouse | Marca: Logitech | Preço: R$ 150.00 | Quantidade: 48
ID: 3 | Produto: Teclado | Marca: Corsair | Preço: R$ 450.00 | Quantidade: 29
==============================
```

#### Passo 7: Listar Todas as Vendas

```
Menu 3 → 2

========== HISTÓRICO DE VENDAS ==========
--- Venda ID: 1 ---
Cliente: Maria Santos
CPF: 11111111111
Data: 13/02/2026 10:30:45
Itens comprados:
  - Notebook | Quantidade: 1 | Preço unitário: R$ 3500.00 | Subtotal: R$ 3500.00
Total da venda: R$ 3500.00

--- Venda ID: 2 ---
Cliente: Maria Santos
CPF: 11111111111
Data: 13/02/2026 10:35:12
Itens comprados:
  - Mouse | Quantidade: 2 | Preço unitário: R$ 150.00 | Subtotal: R$ 300.00
Total da venda: R$ 300.00

--- Venda ID: 3 ---
Cliente: Pedro Costa
CPF: 22222222222
Data: 13/02/2026 11:00:00
Itens comprados:
  - Teclado | Quantidade: 1 | Preço unitário: R$ 450.00 | Subtotal: R$ 450.00
Total da venda: R$ 450.00

========================================
```

#### Passo 8: Listar Vendas de Maria

```
Menu 3 → 3
CPF: 11111111111

========== VENDAS DO CLIENTE ==========
--- Venda ID: 1 ---
Cliente: Maria Santos
CPF: 11111111111
Data: 13/02/2026 10:30:45
Itens comprados:
  - Notebook | Quantidade: 1 | Preço unitário: R$ 3500.00 | Subtotal: R$ 3500.00
Total da venda: R$ 3500.00

--- Venda ID: 2 ---
Cliente: Maria Santos
CPF: 11111111111
Data: 13/02/2026 10:35:12
Itens comprados:
  - Mouse | Quantidade: 2 | Preço unitário: R$ 150.00 | Subtotal: R$ 300.00
Total da venda: R$ 300.00

=====================================

Total gasto pela Maria: R$ 3800.00
```

---

## 📋 Checklist de Testes

### Clientes
- [ ] Cadastrar cliente com dados válidos
- [ ] Editar dados de cliente existente
- [ ] Tentar cadastrar com CPF inválido (< 11 dígitos)
- [ ] Tentar cadastrar com nome contendo números
- [ ] Listar clientes

### Produtos
- [ ] Cadastrar produto com dados válidos
- [ ] Verificar se aparece no estoque com quantidade 0
- [ ] Editar dados do produto
- [ ] Excluir produto
- [ ] Listar produtos

### Estoque
- [ ] Listar estoque (após cadastrar produtos)
- [ ] Adicionar quantidade com sucesso
- [ ] Tentar adicionar quantidade negativa
- [ ] Remover quantidade com sucesso
- [ ] Tentar remover mais do que existe

### Vendas
- [ ] Realizar venda com dados válidos
- [ ] Verificar se estoque diminui após venda
- [ ] Tentar vender com CPF não cadastrado
- [ ] Tentar vender produto não no estoque
- [ ] Tentar vender quantidade maior que disponível
- [ ] Listar histórico de todas as vendas
- [ ] Listar vendas de cliente específico

---

## 🎓 Pontos de Aprendizado

### 1. Integração Entre Serviços
A integração entre `ProdutoService` e `EstoqueService` mostra como:
- Um serviço pode injetar e usar outro serviço
- A lógica de negócio pode ser distribuída

### 2. Validação em Múltiplas Camadas
As validações ocorrem em:
- Controller (entrada básica)
- Service (regras de negócio)
- Repository (integridade de dados)

### 3. Transações Implícitas
Quando uma venda é realizada:
1. Valida tudo
2. Remove do estoque
3. Registra a venda
4. Tudo ou nada (sem mudanças parciais)

### 4. Histórico de Operações
As vendas ficam registradas para:
- Auditoria
- Relatórios
- Análise de dados

---

## 🚀 Próximos Testes Avançados

### Para Implementar:
1. Teste de cancelamento de venda
2. Teste de devolução de produto
3. Teste de busca de produtos por nome
4. Teste de relatório de vendas por período
5. Teste de produto mais vendido
6. Teste de cliente que mais comprou

