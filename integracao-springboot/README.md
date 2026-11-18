# Lista prática — Testes de integração com Spring Boot e JUnit 5

**Tecnologias:** Java 17, Maven, Spring Boot 3.x, Spring Data JPA, H2, JUnit 5 e MockMvc.  
**Entrega:** repositório GitHub com o código e `README.md` descrevendo os testes executados.  
**Orientação:** usar o projeto-base fornecido; completar os métodos `TODO` das classes de teste. Não utilizar mocks para substituir `ProdutoRepository` ou `ProdutoService` nos exercícios.

## Exercício 1 — Integração entre Service, Repository e banco H2

**Objetivo:** verificar que as regras de cadastro e atualização se integram à persistência real.

**Cenário:** o serviço recebe dados de um produto, valida as regras e utiliza `ProdutoRepository` para armazenar os registros.

| Caso | Preparação / entrada | Verificação esperada |
|---|---|---|
| CT01 — Cadastro válido | Cadastrar `Teclado`, preço `150.0` | O produto recebe ID e é encontrado no repositório com nome e preço corretos. |
| CT02 — Nome inválido | Tentar cadastrar nome em branco e preço `80.0` | `IllegalArgumentException`; a contagem de registros não aumenta. |
| CT03 — Atualização (desafio) | Cadastrar `Mouse` por `80.0`, atualizar para `95.0` | A consulta posterior ao repositório retorna preço `95.0`. |

**Passos:**
1. Abrir `ProdutoServiceIntegrationTest` e completar CT01 e CT02; implementar CT03 como desafio.
2. Utilizar `@SpringBootTest`, `@Autowired` e `@Transactional` já presentes no projeto.
3. Usar `assertNotNull`, `assertEquals` e `assertThrows` quando apropriado.
4. Executar `mvn test` e registrar o resultado.

**Discussão:** por que a consulta com `repository.findById()` fornece uma evidência diferente de verificar apenas o objeto retornado pelo serviço? O que `@Transactional` faz ao término de cada teste?

## Exercício 2 — Integração HTTP com MockMvc

**Objetivo:** testar o fluxo Controller → Service → Repository → H2 por meio de requisições HTTP simuladas.

| Caso | Requisição | Verificação esperada |
|---|---|---|
| CT04 — Cadastro via API | `POST /produtos` com `{"nome":"Mouse","preco":80.0}` | HTTP `200`; resposta JSON com ID numérico, nome `Mouse` e preço `80.0`. |
| CT05 — Consulta via API | Cadastrar via POST; recuperar o ID; realizar `GET /produtos/{id}` | HTTP `200`; JSON da consulta corresponde ao produto cadastrado. |

**Passos:**
1. Abrir `ProdutoControllerIntegrationTest` e completar CT04 e CT05.
2. Utilizar `mockMvc.perform(...)`, `contentType(MediaType.APPLICATION_JSON)`, `status()` e `jsonPath()`.
3. Para CT05, capturar o corpo da resposta do POST e extrair o ID com `ObjectMapper` ou `JsonNode`.
4. Executar a suíte inteira com `mvn test`.

**Discussão:** quais componentes são efetivamente exercitados? Por que o MockMvc não precisa iniciar um servidor HTTP real? Qual é a diferença entre esse teste e um teste unitário do controller com dependências simuladas?

## Critérios de avaliação sugeridos

| Critério | Pontuação |
|---|---:|
| Exercício 1 — CT01 e CT02 | 4,0 |
| Exercício 1 — CT03 (desafio) | 1,0 |
| Exercício 2 — CT04 e CT05 | 4,0 |
| README com evidências e explicação do isolamento | 1,0 |
| **Total** | **10,0** |

## Execução

```bash
mvn test
```

**Observações:** o projeto-base contém a aplicação funcional e os testes com espaços `TODO`, sem as asserções prontas. Os testes incompletos não falham automaticamente: os estudantes devem preenchê-los antes de interpretar o resultado da suíte. Em testes com `@Transactional`, o Spring normalmente desfaz a transação ao final de cada método de teste; o uso de `MockMvc` aqui ocorre na mesma aplicação e sem servidor HTTP real.

---

## Resolução

### Como rodar

```bash
cd integracao-springboot
mvn test
```

Requer Java 17+ e Maven. O banco é H2 em memória (configurado em `application.properties`); não há servidor a iniciar. O relatório de cobertura JaCoCo é gerado em `target/site/jacoco/index.html`.

Nenhum código de produção (`src/main`) foi alterado. Não há mocks: `ProdutoService`, `ProdutoRepository`, o controller e o H2 são os componentes reais do contexto Spring.

### Testes executados

| Caso | Classe / método | O que faz | Resultado |
|---|---|---|---|
| CT01 — Cadastro válido | `ProdutoServiceIntegrationTest.deveCadastrarERecuperarProduto` | Cadastra `Teclado` por `150.0` via service; confere ID não nulo; faz `flush`/`clear` e busca com `repository.findById` conferindo nome e preço | Passou |
| CT02 — Nome inválido | `ProdutoServiceIntegrationTest.naoDevePersistirProdutoComNomeEmBranco` | Mede `repository.count()`, tenta cadastrar nome em branco com `80.0`, espera `IllegalArgumentException` ("Nome obrigatório") e confere que a contagem não mudou | Passou |
| CT03 — Atualização (desafio) | `ProdutoServiceIntegrationTest.deveAtualizarPrecoPersistido` | Cadastra `Mouse` por `80.0`, atualiza para `95.0` com `service.atualizarPreco(id, 95.0)` e confere no repositório que o preço persistido é `95.0` (e o nome segue `Mouse`) | Passou |
| CT04 — Cadastro via API | `ProdutoControllerIntegrationTest.deveCadastrarProdutoPelaApi` | `POST /produtos` com `{"nome":"Mouse","preco":80.0}`; espera HTTP 200, `$.id` numérico, `$.nome` = `Mouse`, `$.preco` = `80.0` | Passou |
| CT05 — Consulta via API | `ProdutoControllerIntegrationTest.deveBuscarProdutoPelaApi` | Faz o POST, captura o corpo e extrai o ID com `ObjectMapper.readTree` (`JsonNode`); faz `flush`/`clear` e então `GET /produtos/{id}` e confere HTTP 200 e `id`, `nome` e `preco` iguais aos do cadastro | Passou |
| EXTRA01 | `ProdutoServiceIntegrationTest.naoDevePersistirProdutoComPrecoNaoPositivo` | Cadastro com preço `0.0` lança `IllegalArgumentException` ("Preço deve ser positivo") e a contagem não muda | Passou |
| EXTRA02 | `ProdutoServiceIntegrationTest.naoDeveAtualizarParaPrecoInvalido` | Atualizar para `-10.0` é rejeitado e o preço persistido continua `80.0` | Passou |
| EXTRA03 | `ProdutoServiceIntegrationTest.naoDeveAtualizarProdutoInexistente` | `atualizarPreco` em ID inexistente lança `IllegalArgumentException` ("Produto não encontrado") | Passou |
| EXTRA04 (CT03 sem `@Transactional`) | `ProdutoServiceSemTransacaoIntegrationTest.deveAtualizarPrecoPersistidoSemTransacaoDoTeste` | Repete o CT03 sem transação no teste: cada chamada usa só a fronteira transacional da aplicação e a consulta final lê do H2. Apaga o que criou no `@AfterEach` | Passou |

Os testes EXTRA não fazem parte da lista do professor; cobrem regras que já existem no `ProdutoService`. Não foi criado teste HTTP para ID inexistente porque a aplicação não define um status para esse caso (não há `@ExceptionHandler`): a exceção apenas propaga.

**Saída do `mvn test`:**

```
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0 -- in br.edu.exemplo.ProdutoControllerIntegrationTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0 -- in br.edu.exemplo.ProdutoServiceSemTransacaoIntegrationTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0 -- in br.edu.exemplo.ProdutoServiceIntegrationTest
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

**Cobertura JaCoCo** (`target/site/jacoco/jacoco.csv`):

| Classe | Instruções | Branches | Linhas |
|---|---:|---:|---:|
| `ProdutoService` | 70/70 (100%) | 7/8 (87,5%) | 7/7 (100%) |
| `ProdutoController` | 19/19 (100%) | — | 3/3 (100%) |
| `Produto` | 25/25 (100%) | — | 6/6 (100%) |
| `ProdutoRequest` | 9/9 (100%) | — | 1/1 (100%) |
| `Aplicacao` | 3/8 (37,5%) | — | 1/1 (100%) |
| **Total** | **126/131 (96,2%)** | **7/8 (87,5%)** | **18/18 (100%)** |

O único branch não coberto é `nome == null` (o CT02 usa nome em branco). As instruções não cobertas de `Aplicacao` são o `main`, que o `@SpringBootTest` não executa.

**Verificação de que os testes detectam erros:** foram feitas mutações temporárias no código de produção (depois desfeitas, não comitadas). Cada uma fez pelo menos um teste falhar:

| Mutação | Testes que falharam |
|---|---|
| Salvar `preco + 1` | CT01, CT04, CT05, EXTRA02 |
| Salvar o nome em maiúsculas | CT01, CT03, CT04, CT05 |
| `atualizarPreco` sem `setPreco` | CT03 |
| Aceitar nome em branco | CT02 |
| `nome` marcado como `@Transient` (não é gravado no banco) | CT01, CT03, CT05, EXTRA04 |
| `atualizarPreco` sem `repository.save(...)` | EXTRA04 |

A última mutação passa em todos os testes com `@Transactional`: dentro da transação do teste, a entidade continua gerenciada e o Hibernate grava a alteração sozinho no `flush` (*dirty checking*). Na aplicação real isso não acontece, porque `ProdutoService` não é `@Transactional` e a entidade fica desanexada depois do `findById`. Foi por isso que o EXTRA04 foi criado.

### Decisões

- **CT03:** o `ProdutoService` já tem o método `atualizarPreco(Long id, double novoPreco)`, e o teste usa esse método. Nenhuma API nova foi criada.
- **`flush()` + `clear()` nos testes de service:** dentro de um teste `@Transactional`, `repository.findById()` devolveria a mesma instância em cache no contexto de persistência, sem consultar o H2. Por isso os testes injetam o `EntityManager` real e chamam `flush()` (envia o SQL ao banco) e `clear()` (esvazia o cache) antes da consulta. O CT01 também confere com `assertNotSame` que o objeto lido não é o mesmo devolvido pelo service. Isso não é mock: o `EntityManager` é o do próprio contexto.
- **CT05:** o ID é extraído com `ObjectMapper.readTree(...)` (o `ObjectMapper` do Spring é injetado com `@Autowired`), e o GET é comparado com os valores do JSON do POST e também com os literais `Mouse`/`80.0`. Entre o POST e o GET são chamados `flush()` e `clear()`: como o MockMvc roda na mesma transação do teste, sem isso o GET seria atendido pelo cache do JPA e não leria o H2.
- **EXTRA04 sem `@Transactional`:** como não há *rollback* automático nessa classe, o teste apaga no `@AfterEach` os registros que criou.

### Respostas da discussão

**Exercício 1 — Por que `repository.findById()` é uma evidência diferente do objeto retornado pelo serviço?**
O objeto que o serviço retorna só mostra o que o método devolveu em memória. Ele pode estar correto mesmo que nada tenha sido gravado, ou que tenha sido gravado com outros valores (por exemplo, se o serviço montar um objeto para retornar e salvar outro). Consultar pelo repositório confere o estado persistido, que é o que a aplicação vai encontrar depois. Isso só vale se a consulta realmente chegar ao banco. Como o teste roda em uma única transação, o `findById` pode devolver a mesma instância do cache de primeiro nível do JPA. Por isso usei `flush()` e `clear()` antes de consultar: o registro é relido do H2 pelo mapeamento JPA, e a verificação passa a cobrir também o SQL e as colunas.

**Exercício 1 — O que `@Transactional` faz ao término de cada teste?**
Em uma classe de teste, `@Transactional` abre uma transação antes de cada método de teste e, por padrão, faz *rollback* no final, em vez de commit. Tudo o que o teste inseriu ou alterou é desfeito, mesmo depois de um `flush()`, porque o SQL foi executado mas nunca confirmado. Assim cada teste começa com o banco no mesmo estado e não depende da ordem de execução nem de dados deixados por outro teste. O comportamento pode ser alterado com `@Commit` ou `@Rollback(false)`, mas aqui não foi necessário.

Há um efeito colateral: a transação do teste é mais longa que a da aplicação real. Aqui `ProdutoService` não é `@Transactional` e `spring.jpa.open-in-view=false`, então em produção cada chamada ao repositório tem a sua própria transação curta. No teste, tudo fica dentro de uma só, e as entidades continuam gerenciadas. Isso pode esconder defeitos: um `atualizarPreco` sem `save` passaria nos testes transacionais, porque o Hibernate grava a alteração no `flush`. O EXTRA04, sem `@Transactional`, cobre esse caso.

**Exercício 2 — Quais componentes são efetivamente exercitados no teste com MockMvc?**
Quase toda a pilha da aplicação. O `DispatcherServlet` do Spring MVC recebe a requisição e o mapeamento de `@RequestMapping`/`@PostMapping`/`@GetMapping` escolhe o método do `ProdutoController`. O Jackson converte o JSON para `ProdutoRequest` (e depois o `Produto` de volta para JSON) e o `@PathVariable` é resolvido. Em seguida rodam o `ProdutoService` real, com suas validações, o `ProdutoRepository` gerado pelo Spring Data JPA, o Hibernate e o banco H2 em memória. Também é testada a configuração do contexto Spring (injeção de dependências e auto-configuração). O INSERT do POST chega ao H2, mas, como a requisição roda dentro da transação do teste, o GET só lê do banco porque o CT05 chama `flush()` e `clear()` antes dele.

**Exercício 2 — Por que o MockMvc não precisa iniciar um servidor HTTP real?**
O MockMvc chama o `DispatcherServlet` diretamente, dentro da mesma JVM e da mesma thread do teste, usando objetos simulados da API Servlet (`MockHttpServletRequest`/`MockHttpServletResponse`). Não abre porta, não há socket nem Tomcat. Por isso o `@SpringBootTest` padrão (`webEnvironment = MOCK`) basta. Também por rodar na mesma thread, a transação do teste envolve as requisições e o *rollback* desfaz o que o POST gravou. Ficam de fora a camada de rede e do container (porta, conectores, filtros específicos do Tomcat), que um teste com `RANDOM_PORT` e `TestRestTemplate`/`WebTestClient` cobriria. A fronteira transacional também é diferente da produção: no teste a requisição roda dentro da transação do método de teste; na aplicação real, não.

**Exercício 2 — Qual é a diferença entre esse teste e um teste unitário do controller com dependências simuladas?**
Um teste unitário do controller (por exemplo, `@WebMvcTest` com o `ProdutoService` mockado, ou o controller instanciado com um dublê) isola a camada web. Ele verifica rotas, conversão de JSON e status HTTP, mas a resposta do serviço é a que o teste programou, então as regras de negócio, o JPA e o banco não são exercitados. É mais rápido e aponta defeitos só do controller, mas pode passar mesmo que o serviço ou a persistência estejam quebrados. O teste de integração deste exercício usa os componentes reais, então erros de validação, de serialização e de gravação ou leitura no banco fazem o teste falhar, como mostraram as mutações acima. Para a leitura, isso vale porque o CT05 limpa o cache do JPA antes do GET. Em troca, ele é mais lento e, quando falha, não indica tão diretamente em qual camada está o defeito.
