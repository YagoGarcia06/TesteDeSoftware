# Conta Bancária — TDD

Projeto Maven com Java 17 e JUnit 5.

## Executar
```bash
mvn test
```

## Prática
1. Execute os testes antes da implementação (RED).
2. Implemente o mínimo necessário.
3. Execute novamente até passar (GREEN).
4. Repita para o próximo comportamento.
5. Refatore mantendo os testes passando.

A classe `ContaBancaria` está propositalmente incompleta.

## Resolução

> O texto acima desta seção é o enunciado original do professor e descreve o estado inicial do projeto.

### Como rodar

```bash
cd tdd/conta-bancaria-tdd-maven
mvn test
```

Relatório de cobertura (JaCoCo): `target/site/jacoco/index.html`.

### Como o TDD aparece no histórico

Os testes do professor já vieram prontos e falhando: esse foi o RED inicial de cada requisito. Por isso o histórico mostra commits `feat` levando esses testes ao GREEN (alguns agrupando requisitos relacionados), seguidos de `refactor` e dos testes de borda adicionados (`test`).

### Testes

| Teste | O que verifica | Origem |
|---|---|---|
| `deveDepositarValorNaConta` | depositar 100 deixa saldo 100 | Professor |
| `deveAcumularDepositos` | 100 + 50 resulta em saldo 150 | Professor |
| `deveSacarValorDaConta` | saldo 100, sacar 40 deixa 60 | Professor |
| `naoDevePermitirSaqueMaiorQueSaldo` | saque de 150 com saldo 100 é ignorado | Professor |
| `contaDeveIniciarComSaldoZero` | conta nova começa com saldo 0 | Adicionado |
| `devePermitirSacarTodoOSaldo` | fronteira: sacar exatamente o saldo deixa 0 | Adicionado |
| `naoDevePermitirSaqueComSaldoZero` | saque em conta sem saldo é ignorado | Adicionado |
| `naoDeveDepositarValorNegativo` | depósito negativo é ignorado | Adicionado |
| `naoDeveAlterarSaldoComDepositoZero` | depósito zero não altera o saldo | Adicionado |
| `naoDeveSacarValorNegativo` | saque negativo é ignorado (não aumenta o saldo) | Adicionado |

### Decisões

- Operações inválidas são **ignoradas** (sem exceção), seguindo o comportamento definido
  pelo teste do professor `naoDevePermitirSaqueMaiorQueSaldo`.
- Depósito/saque com valor menor ou igual a zero não são citados no README; foram tratados
  como inválidos porque um depósito negativo reduziria o saldo e um saque negativo o aumentaria.
- O caso "valor zero" não é observável pela API: somar ou subtrair 0 não muda o saldo, então
  `valor <= 0` e `valor < 0` se comportam igual para os testes. O teste de depósito zero documenta
  o comportamento, mas não distingue as duas regras.

### Fora do escopo

- Valores `NaN` ou infinitos não são tratados (passariam pela validação e contaminariam o saldo).

### Cobertura obtida

10 testes, todos passando. Classe `ContaBancaria`: **100% de linhas, 100% de branches e 100% de instruções** (JaCoCo).
