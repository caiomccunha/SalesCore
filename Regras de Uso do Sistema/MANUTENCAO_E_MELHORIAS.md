# 🔧 GUIA DE MANUTENÇÃO E MELHORIAS FUTURAS

## 📋 Visão Geral

Este documento fornece orientações para manter, expandir e melhorar o sistema de vendas.

---

## 🛠️ Manutenção Básica

### 1. Adicionando um Novo Módulo

Para adicionar um novo módulo (ex: Fornecedores), siga este padrão:

#### Passo 1: Criar o Model
```java
// Model/Fornecedor.java
public class Fornecedor {
    private Integer id;
    private String nome;
    private String cnpj;
    private String telefone;
    
    // getters e setters
}
```

#### Passo 2: Criar o Repository
```java
// Repository/FornecedorRepository.java
public class FornecedorRepository {
    private final List<Fornecedor> fornecedores = new ArrayList<>();
    
    public void adicionar(Fornecedor fornecedor) {
        fornecedores.add(fornecedor);
    }
    
    public List<Fornecedor> listar() {
        return fornecedores;
    }
    
    public Fornecedor buscarPorId(Integer id) {
        // implementar busca
    }
    
    public void remover(Fornecedor fornecedor) {
        fornecedores.remove(fornecedor);
    }
}
```

#### Passo 3: Criar o Service
```java
// Service/FornecedorService.java
public class FornecedorService {
    private final FornecedorRepository repo;
    
    public FornecedorService(FornecedorRepository repo) {
        this.repo = repo;
    }
    
    public void adicionar(String nome, String cnpj, String telefone) {
        // validações
        Fornecedor f = new Fornecedor(nome, cnpj, telefone);
        repo.adicionar(f);
    }
}
```

#### Passo 4: Criar o Controller
```java
// Controller/FornecedorController.java
public class FornecedorController {
    private final FornecedorService service;
    
    public FornecedorController(FornecedorService service) {
        this.service = service;
    }
    
    public void adicionarFornecedor(String nome, String cnpj, String telefone) {
        service.adicionar(nome, cnpj, telefone);
    }
}
```

#### Passo 5: Criar o Menu
```java
// Menus/MenuFornecedores.java
public class MenuFornecedores {
    private final FornecedorController controller;
    
    public MenuFornecedores(FornecedorController controller) {
        this.controller = controller;
    }
    
    public void exibirMenu() {
        // implementar menu
    }
}
```

#### Passo 6: Atualizar Main.java
```java
// Em Main.java
// Adicionar instâncias
FornecedorRepository fornecedorRepo = new FornecedorRepository();
FornecedorService fornecedorService = new FornecedorService(fornecedorRepo);
FornecedorController fornecedorController = new FornecedorController(fornecedorService);
MenuFornecedores fornecedores = new MenuFornecedores(fornecedorController);

// Adicionar ao switch
case 5:
    fornecedores.exibirMenu();
    break;
```

---

## 🔄 Melhorias Recomendadas (Por Prioridade)

### 🔴 ALTA PRIORIDADE

#### 1. Persistência de Dados
**O Problema**: Dados são perdidos ao fechar o programa
**Solução Recomendada**: Salvar em arquivo JSON

```java
// Exemplo: Salvar EstoqueRepository em JSON
import com.google.gson.Gson;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EstoqueRepository {
    private final String ARQUIVO = "estoque.json";
    
    public void salvarEmArquivo() {
        Gson gson = new Gson();
        String json = gson.toJson(estoque);
        Files.write(Paths.get(ARQUIVO), json.getBytes());
    }
    
    public void carregarDeArquivo() {
        // carregar do arquivo
    }
}
```

#### 2. Busca Avançada de Produtos
**Adicionar ao ProdutoService**:
```java
public List<Produto> buscarPorNome(String nome) {
    return produtos.stream()
        .filter(p -> p.getNome().toLowerCase().contains(nome.toLowerCase()))
        .collect(Collectors.toList());
}

public List<Produto> buscarPorMarca(String marca) {
    return produtos.stream()
        .filter(p -> p.getMarca().equalsIgnoreCase(marca))
        .collect(Collectors.toList());
}
```

#### 3. Cancelamento de Venda
**Adicionar ao VendaService**:
```java
public void cancelarVenda(Integer idVenda) {
    Venda venda = vendaRepo.buscarVendaPorId(idVenda);
    
    // Devolver produtos ao estoque
    for (ItemVenda item : venda.getItens()) {
        estoqueService.adicionarQuantidade(item.getProduto().getId(), item.getQuantidade());
    }
    
    // Remover da lista de vendas
    vendaRepo.removerVenda(venda);
}
```

### 🟡 MÉDIA PRIORIDADE

#### 4. Desconto em Vendas
**Modificar ItemVenda**:
```java
public class ItemVenda {
    // ... campos existentes ...
    private Double desconto = 0.0;
    
    public void aplicarDesconto(Double percentual) {
        this.desconto = this.subtotal * (percentual / 100);
        this.subtotal = this.subtotal - desconto;
    }
}
```

#### 5. Estoque Mínimo com Alerta
```java
public class Estoque {
    private Integer quantidadeMinima = 5;
    
    public boolean estaAbaixoDoMinimo() {
        return quantidade < quantidadeMinima;
    }
    
    public void alertarSeAbaixoDoMinimo() {
        if (estaAbaixoDoMinimo()) {
            System.out.println("⚠️ ALERTA: " + produto.getNome() + 
                             " está abaixo do estoque mínimo!");
        }
    }
}
```

#### 6. Relatório de Vendas por Período
```java
public class VendaService {
    public Double vendidoNoMes(int mes, int ano) {
        return vendas.stream()
            .filter(v -> v.getDataVenda().getMonthValue() == mes && 
                        v.getDataVenda().getYear() == ano)
            .mapToDouble(Venda::getTotal)
            .sum();
    }
    
    public int quantidadeVendasNoMes(int mes, int ano) {
        return (int) vendas.stream()
            .filter(v -> v.getDataVenda().getMonthValue() == mes && 
                        v.getDataVenda().getYear() == ano)
            .count();
    }
}
```

#### 7. Produto Mais Vendido
```java
public class VendaService {
    public Produto getProdutoMaisVendido() {
        Map<Integer, Integer> vendidosPorProduto = new HashMap<>();
        
        for (Venda v : vendas) {
            for (ItemVenda item : v.getItens()) {
                vendidosPorProduto.put(
                    item.getProduto().getId(),
                    vendidosPorProduto.getOrDefault(item.getProduto().getId(), 0) 
                        + item.getQuantidade()
                );
            }
        }
        
        Integer idMaisVendido = vendidosPorProduto.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
            
        return estoqueService.obterProdutoDoEstoque(idMaisVendido);
    }
}
```

### 🟢 BAIXA PRIORIDADE

#### 8. Interface Gráfica (GUI)
**Usar Swing ou JavaFX**:
```java
// Exemplo com Swing
import javax.swing.*;

public class VendasGUI extends JFrame {
    public VendasGUI() {
        this.setTitle("Sistema de Vendas");
        this.setSize(800, 600);
        
        JButton btnVender = new JButton("Realizar Venda");
        btnVender.addActionListener(e -> realizarVenda());
        
        this.add(btnVender);
        this.setVisible(true);
    }
}
```

#### 9. Banco de Dados
**Usar JDBC ou JPA/Hibernate**:
```java
// Exemplo com JPA
@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nome")
    private String nome;
    
    // getters e setters
}
```

#### 10. API REST
**Usar Spring Boot**:
```java
@RestController
@RequestMapping("/api/vendas")
public class VendaAPI {
    
    @PostMapping
    public ResponseEntity<?> realizarVenda(@RequestBody VendaRequest request) {
        // implementar
    }
    
    @GetMapping
    public ResponseEntity<?> listarVendas() {
        // implementar
    }
}
```

---

## 🧪 Testes Unitários

### Estrutura Recomendada

```
src/
├── main/
│   └── java/
│       └── (código atual)
└── test/
    └── java/
        ├── Service/
        │   ├── VendaServiceTest.java
        │   ├── EstoqueServiceTest.java
        │   └── ClienteServiceTest.java
        └── Repository/
            ├── VendaRepositoryTest.java
            └── EstoqueRepositoryTest.java
```

### Exemplo de Teste com JUnit 4

```java
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class VendaServiceTest {
    
    private VendaService vendaService;
    private ClienteRepository clienteRepo;
    private EstoqueService estoqueService;
    
    @Before
    public void setUp() {
        clienteRepo = new ClienteRepository();
        estoqueService = new EstoqueService(new EstoqueRepository());
        vendaService = new VendaService(new VendaRepository(), clienteRepo, estoqueService);
    }
    
    @Test
    public void testRealizarVendaComSucesso() {
        // Arrange
        Cliente cliente = new Cliente("João", "12345678901", "123", 30, "Rua");
        clienteRepo.salvar(cliente);
        
        // Act
        Venda venda = vendaService.realizarVenda("12345678901", 1, 5);
        
        // Assert
        assertNotNull(venda);
        assertEquals("João", venda.getCliente().getNome());
    }
    
    @Test(expected = RuntimeException.class)
    public void testRealizarVendaComClienteInexistente() {
        vendaService.realizarVenda("99999999999", 1, 5);
    }
}
```

---

## 📊 Métricas de Qualidade

### O que Monitorar

1. **Cobertura de Teste**: Mínimo 80%
2. **Complexidade Ciclomática**: Máximo 10
3. **Duplicação de Código**: Máximo 5%
4. **Documentação**: 100% das classes públicas

### Ferramentas Recomendadas

- **JUnit** - Testes unitários
- **SonarQube** - Análise de qualidade
- **Jacoco** - Cobertura de testes
- **Checkstyle** - Estilo de código

---

## 📚 Padrões de Código

### Naming Conventions

```java
// Classes - PascalCase
public class ClienteService { }
public class VendaRepository { }

// Métodos - camelCase
public void realizarVenda() { }
public List<Produto> buscarPorNome() { }

// Constantes - UPPER_SNAKE_CASE
public static final int QUANTIDADE_MINIMA = 5;
public static final String ARQUIVO_DADOS = "dados.json";

// Variáveis - camelCase
int quantidadeDisponivel;
String nomeCliente;
```

### Comentários

```java
// ✅ BOM - Explica POR QUE
public void removerQuantidade(Integer qtd) {
    // Validar para evitar estoque negativo
    if (this.quantidade < qtd) {
        throw new RuntimeException("...");
    }
}

// ❌ RUIM - Explica O QUÊ (óbvio)
public void removerQuantidade(Integer qtd) {
    // Remover quantidade
    this.quantidade -= qtd;
}
```

### Organização de Métodos

```java
public class MinhaClasse {
    
    // 1. Constantes
    private static final String CONSTANTE = "valor";
    
    // 2. Variáveis de instância
    private String variavel;
    
    // 3. Construtor
    public MinhaClasse() { }
    
    // 4. Métodos públicos
    public void metodoPublico() { }
    
    // 5. Métodos privados
    private void metodoPrivado() { }
    
    // 6. Getters e Setters
    public String getVariavel() { }
    public void setVariavel(String valor) { }
    
    // 7. toString, equals, hashCode
    @Override
    public String toString() { }
}
```

---

## 🚀 Plano de Roadmap

### Fase 1 (Atual) ✅
- [x] Gerenciamento de Clientes
- [x] Gerenciamento de Produtos
- [x] Gerenciamento de Estoque
- [x] Realização de Vendas

### Fase 2 (Próxima)
- [ ] Persistência em JSON
- [ ] Busca avançada de produtos
- [ ] Cancelamento de vendas
- [ ] Relatórios básicos

### Fase 3
- [ ] Interface gráfica
- [ ] Desconto em vendas
- [ ] Estoque mínimo com alerta
- [ ] Histórico de operações

### Fase 4
- [ ] Banco de dados
- [ ] Autenticação de usuários
- [ ] API REST
- [ ] Dashboard analítico

---

## 🐛 Tratamento de Bugs

### Como Reportar

```
TÍTULO: [BUG] Descrição breve do problema
DESCRIÇÃO:
- Passos para reproduzir
- Comportamento esperado
- Comportamento atual
- Screenshots/logs (se aplicável)

EXEMPLO:
TÍTULO: [BUG] Venda não remove quantidade do estoque
DESCRIÇÃO:
1. Cadastrar produto com 10 unidades
2. Realizar venda de 3 unidades
3. Listar estoque
Esperado: 7 unidades
Atual: 10 unidades
```

### Processo de Correção

1. Reproduzir o bug
2. Identificar a causa raiz
3. Escrever teste que falha
4. Corrigir o código
5. Verificar se o teste passa
6. Documentar a correção

---

## 📖 Referências e Recursos

### Documentação
- [Java Documentation](https://docs.oracle.com/javase/)
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)

### Padrões de Design
- [Refactoring Guru - Design Patterns](https://refactoring.guru/design-patterns)
- [Martin Fowler - Patterns](https://martinfowler.com/)

### Boas Práticas
- Clean Code (Robert C. Martin)
- Effective Java (Joshua Bloch)
- Design Patterns (Gang of Four)

---

## 📞 Suporte

Para dúvidas ou sugestões, consulte:
1. **README.md** - Como usar
2. **ARQUITETURA.md** - Como funciona
3. **IMPLEMENTACAO.md** - O que foi feito
4. **TESTES_E_EXEMPLOS.md** - Como testar

---

## ✨ Conclusão

Este guia fornece as orientações necessárias para manter e evoluir o sistema de vendas. Sempre priorize a qualidade do código e a satisfação do usuário!

