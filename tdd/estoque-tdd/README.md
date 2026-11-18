# Estoque — Exemplo de TDD

Projeto Maven com Java 17 e JUnit 5 para demonstração de TDD.

## Executar

```bash
mvn test
```

## Casos de teste

1. Produto inicia com estoque zero.
2. Adicionar unidades ao estoque.
3. Acumular várias entradas.
4. Retirar unidades do estoque.
5. Não permitir retirada maior que o estoque disponível.
6. Não permitir entrada com quantidade negativa.

A proposta didática é construir a implementação incrementalmente, executando
os testes durante os ciclos RED → GREEN → REFACTOR.

## Resolução

### Como rodar

```bash
cd tdd/estoque-tdd
mvn test
```

Relatório de cobertura (JaCoCo): `target/site/jacoco/index.html`.

### Testes

| Teste | O que verifica | Origem |
|---|---|---|
| `produtoDeveIniciarComEstoqueZero` | produto novo começa com 0 (Requisito 1) | Professor |
| `deveAdicionarProdutoAoEstoque` | adicionar 10 deixa estoque 10 (Requisito 2) | Professor |
| `deveAcumularQuantidadeEmEstoque` | 10 + 5 resulta em 15 (Requisito 3) | Professor |
| `deveRetirarProdutoDoEstoque` | estoque 10, retirar 3 deixa 7 (Requisito 4) | Professor |
| `naoDeveRetirarQuantidadeMaiorQueEstoque` | retirar 15 com estoque 10 não altera (Requisito 5) | Professor |
| `naoDeveAdicionarQuantidadeNegativa` | adicionar -10 não altera (Requisito 6) | Professor |
| `deveGuardarNomeDoProduto` | `getNome()` devolve o nome informado | Adicionado |
| `naoDeveAdicionarQuantidadeZero` | adicionar 0 não altera (parte "igual a zero" do Requisito 6) | Adicionado |
| `naoDeveAlterarEstoqueExistenteComEntradaNegativa` | entrada negativa não altera estoque já preenchido | Adicionado |
| `devePermitirRetirarTodoOEstoque` | fronteira: retirar exatamente o disponível deixa 0 | Adicionado |
| `naoDeveRetirarDeEstoqueVazio` | retirada em estoque zerado é ignorada | Adicionado |
| `naoDeveRetirarQuantidadeNegativa` | retirada negativa é ignorada (não aumenta o estoque) | Adicionado |

### Decisões

- Operações inválidas são **ignoradas** (sem exceção), como pedem os Requisitos 5 e 6
  ("a operação inválida não deve alterar ...").
- Retirada com quantidade menor ou igual a zero não é citada nos requisitos; foi tratada como
  inválida, no mesmo padrão do Requisito 6, porque uma retirada negativa aumentaria o estoque.

### Cobertura obtida

12 testes, todos passando. Classe `Produto`: **100% de linhas, 100% de branches e 100% de instruções** (JaCoCo).
