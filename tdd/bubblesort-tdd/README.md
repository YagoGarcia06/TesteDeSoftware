# Bubble Sort — TDD

Projeto Maven com Java 17 e JUnit 5.

A implementação do Bubble Sort foi propositalmente omitida.

## Executar

```bash
mvn test
```

Consulte `REQUISITOS.md` para a descrição da atividade.

## Resolução

### Como rodar

```bash
cd tdd/bubblesort-tdd
mvn test
```

Relatório de cobertura (JaCoCo): `target/site/jacoco/index.html`.

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
algoritmo para assim que uma passada não faz nenhuma troca.
