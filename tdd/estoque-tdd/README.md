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

> O texto acima desta seção é o enunciado original do professor e descreve o estado inicial do projeto.

### Como rodar

```bash
cd tdd/estoque-tdd
mvn test
```

Relatório de cobertura (JaCoCo): `target/site/jacoco/index.html`.

### Como o TDD aparece no histórico

Os testes do professor já vieram prontos e falhando: esse foi o RED inicial de cada requisito. Por isso o histórico mostra commits `feat` levando esses testes ao GREEN (alguns agrupando requisitos relacionados), seguidos de `refactor` e dos testes de borda adicionados (`test`).

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
- O caso "quantidade zero" não é observável pela API: somar ou subtrair 0 não muda o estoque,
  então `<= 0` e `< 0` se comportam igual para os testes. O teste de entrada zero documenta o
  comportamento do Requisito 6, mas não distingue as duas regras.

### Fora do escopo

- Estouro de `int` (por exemplo, adicionar `Integer.MAX_VALUE` a um estoque positivo) não é tratado.

### Cobertura obtida

12 testes, todos passando. Classe `Produto`: **100% de linhas, 100% de branches e 100% de instruções** (JaCoCo).
