# Prova 02 prática — Teste de Software

Disciplina **Projeto, Implementação e Teste de Software 2026** (Unicesumar), prof. João Choma.
Todas as soluções da avaliação prática da Prova 02 estão neste repositório, como o enunciado pede.

| Item | Enunciado | Pasta |
|---|---|---|
| 1 — TDD (Semana 10) | Implementar TDD nos quatro projetos até a implementação e cobertura total | [`tdd/`](tdd) |
| 2 — Integração (Semana 11) | Implementar teste de integração no projeto Spring Boot | [`integracao-springboot/`](integracao-springboot) |
| 3 — Teste de usuário (Semana 14) | Executar o teste de usuário para o problema proposto | [`teste-usuario/`](teste-usuario) |

## Como executar

Requisitos: **Java 17** e **Maven 3.9+**.

```bash
# todos os projetos de uma vez (pom agregador na raiz)
mvn test

# um projeto só
cd tdd/carrinho-compras-tdd
mvn test
```

O relatório de cobertura do JaCoCo é gerado em `target/site/jacoco/index.html` de cada projeto.

## Resultados

| Projeto | Testes | Cobertura de linhas | Cobertura de branches |
|---|---|---|---|
| `tdd/bubblesort-tdd` | 8 | 100% | 100% |
| `tdd/carrinho-compras-tdd` | 16 | 100% | 100% |
| `tdd/conta-bancaria-tdd-maven` | 10 | 100% | 100% |
| `tdd/estoque-tdd` | 12 | 100% | 100% |
| `integracao-springboot` | 9 | 100% | 87,5% (falta só `nome == null`) |

Os detalhes de cada projeto (tabela de testes, decisões tomadas e cobertura) estão na seção **Resolução** do README de cada pasta.

## Como o TDD aparece no histórico

O primeiro commit contém os projetos exatamente como foram fornecidos pelo professor. A partir dele:

- **bubblesort, conta bancária e estoque** já vieram com os testes em RED. Os commits `feat` levam cada um ao GREEN, seguidos de `refactor` e de testes de borda (`test`).
- **carrinho de compras** começou sem testes. Cada requisito tem um commit `test` com o teste falhando (RED) e, logo depois, um commit `feat` com o mínimo para passar (GREEN), além de `refactor` quando necessário.

Para ver a sequência: `git log --reverse --oneline -- tdd/carrinho-compras-tdd`.

## Colaboradores

| Nome | GitHub | Participação |
|---|---|---|
| Yago Garcia do Canto (RA 24011308-2) | [@YagoGarcia06](https://github.com/YagoGarcia06) | Itens 1 e 2 e estrutura do repositório |
| Guilherme Sousa da Silva (RA 24458923-2) | [@GuiSousa1804](https://github.com/GuiSousa1804) | Item 3 — teste de usuário |
