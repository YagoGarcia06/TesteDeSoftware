# Carrinho de Compras — TDD

Projeto Maven para prática de TDD com Java 17 e JUnit 5.

## Executar os testes

```bash
mvn test
```

## Atividade

Leia `REQUISITOS.md` e implemente os requisitos sequencialmente.

Os testes não são fornecidos prontos: o objetivo desta prática é que cada
requisito seja convertido em um teste antes de sua implementação.

## Resolução

### Como rodar

```bash
cd tdd/carrinho-compras-tdd
mvn test
```

Relatório de cobertura (JaCoCo): `target/site/jacoco/index.html`.

### Como foi feito (TDD)

Cada requisito virou um commit `test(carrinho): requisito N - ...` (RED verificado com `mvn test`;
quando o teste precisava de um método inexistente, o commit incluiu só um esqueleto que compila e
falha) seguido de um commit `feat(carrinho): requisito N - ...` com o mínimo para ficar GREEN, e
commits `refactor(carrinho): ...` quando houve refatoração. Veja `git log -- tdd/carrinho-compras-tdd`.

### API do `Carrinho`

| Método | Comportamento |
|---|---|
| `adicionar(Produto)` | adiciona o produto; preço negativo lança `IllegalArgumentException` |
| `remover(Produto)` | remove uma ocorrência do produto (se não estiver no carrinho, nada muda) |
| `getTotal()` | soma dos preços; com 10% de desconto se a soma for ≥ R$ 500,00 |

### Testes

Todos os testes foram escritos por mim (o professor forneceu `CarrinhoTest` vazio).

| Teste | O que verifica | Requisito |
|---|---|---|
| `carrinhoNovoDeveTerTotalZero` | carrinho novo tem total 0 | 1 |
| `deveAdicionarUmProduto` | Teclado R$ 100 → total R$ 100 | 2 |
| `deveSomarPrecosDeVariosProdutos` | Teclado R$ 100 + Mouse R$ 50 → R$ 150 | 3 |
| `deveRemoverProduto` | remover Mouse de R$ 150 → R$ 100 | 4 |
| `naoDevePermitirProdutoComPrecoNegativo` | preço -100 lança `IllegalArgumentException` com mensagem e o total fica 0 | 5 |
| `deveAplicarDescontoDeDezPorCentoAcimaDoLimite` | R$ 600 → R$ 540 | 6 |
| `naoDeveAplicarDescontoAbaixoDoLimite` | fronteira R$ 499,99 → sem desconto | 6 |
| `deveAplicarDescontoExatamenteNoLimite` | fronteira R$ 500,00 → R$ 450,00 | 6 |
| `deveAplicarDescontoLogoAcimaDoLimite` | logo acima da fronteira: R$ 500,10 → R$ 450,09 | 6 |
| `deveAplicarDescontoSobreASomaDosProdutos` | 300 + 200 = 500 → R$ 450 (desconto sobre a soma) | 6 |
| `removerProdutoAusenteNaoAlteraTotal` | remover produto que não está no carrinho não altera | borda |
| `removerProdutoRepetidoRetiraApenasUmaUnidade` | produto adicionado 2x, removido 1x → sobra uma unidade | borda |
| `removerUnicoProdutoZeraTotal` | remover o único produto volta a 0 | borda |
| `devePermitirProdutoComPrecoZero` | preço zero é aceito (só negativo é inválido) | borda |
| `produtoInvalidoNaoAlteraCarrinhoExistente` | exceção não altera produtos já adicionados | borda |
| `removerProdutoPodeRetirarODesconto` | 510 → 459; ao remover o Mouse fica 460 sem desconto | borda |

### Decisões

- **Requisito 5:** optei por **lançar `IllegalArgumentException`** (mensagem
  `"Preço não pode ser negativo: <nome>"`) em vez de ignorar, para que o erro não passe
  despercebido; o carrinho permanece inalterado. A validação fica em `Carrinho.adicionar`,
  pois `Produto` foi fornecido pronto e `Carrinho` é a classe da atividade.
- **Requisito 6:** "igual ou superior a R$ 500,00" → condição `subtotal >= 500`. Os valores
  são `double` (tipo usado por `Produto`), sem arredondamento; os testes comparam com tolerância
  de 0,001.
- **Remoção:** remove pela mesma instância de `Produto` (a classe não define `equals`).

### Cobertura obtida

16 testes, todos passando. Classes `Carrinho` e `Produto`: **100% de linhas, 100% de branches
e 100% de instruções** (JaCoCo).
