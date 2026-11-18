# Bubble Sort — TDD

Projeto Maven com Java 17 e JUnit 5.

A implementação do Bubble Sort foi propositalmente omitida.

## Executar

```bash
mvn test
```

Consulte `REQUISITOS.md` para a descrição da atividade.

## Resolução

> O texto acima desta seção é o enunciado original do professor e descreve o estado inicial do projeto.

### Como rodar

```bash
cd tdd/bubblesort-tdd
mvn test
```

Relatório de cobertura (JaCoCo): `target/site/jacoco/index.html`.

### Como o TDD aparece no histórico

Os testes do professor já vieram prontos e falhando: esse foi o RED inicial de cada requisito. Por isso o histórico mostra commits `feat` levando esses testes ao GREEN (alguns agrupando requisitos relacionados), seguidos de `refactor` e dos testes de borda adicionados (`test`).

### Testes

| Teste | O que verifica | Origem |
|---|---|---|
| `deveOrdenarVetorDesordenado` | `[5,3,1,4,2]` vira `[1,2,3,4,5]` (Requisito 1) | Professor |
| `deveManterVetorJaOrdenado` | vetor já ordenado permanece igual (Requisito 2) | Professor |
| `deveOrdenarVetorComValoresRepetidos` | `[3,1,3,2,1]` vira `[1,1,2,3,3]` (Requisito 3) | Professor |
| `deveOrdenarVetorEmOrdemDecrescente` | pior caso `[5,4,3,2,1]`, exige todas as passadas | Adicionado |
| `deveManterVetorVazio` | vetor vazio não gera erro | Adicionado |
| `deveManterVetorComUmElemento` | vetor de um elemento permanece igual | Adicionado |
| `deveOrdenarVetorComNegativos` | ordena negativos e zero: `[-5,-1,0,2,3]` | Adicionado |
| `deveManterVetorComValoresIguais` | todos os valores iguais permanecem iguais | Adicionado |

### Cobertura obtida

8 testes, todos passando. Classe `BubbleSort`: **100% de linhas, 100% de branches e 100% de instruções** (JaCoCo).

A implementação usa a otimização clássica: cada passada ignora o final já ordenado e o
algoritmo para assim que uma passada não faz nenhuma troca. Essas otimizações afetam só o
desempenho, não o resultado, por isso não são verificáveis pelos testes, que conferem o vetor final.

### Fora do escopo

- Vetor `null` não é tratado (lança `NullPointerException`).
