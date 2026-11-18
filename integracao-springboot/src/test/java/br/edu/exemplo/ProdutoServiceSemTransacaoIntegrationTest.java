package br.edu.exemplo;
import br.edu.exemplo.model.Produto;
import br.edu.exemplo.repository.ProdutoRepository;
import br.edu.exemplo.service.ProdutoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste EXTRA (fora da lista do professor): repete o cenário do CT03 SEM @Transactional na classe de teste.
 * Assim cada chamada ao service usa apenas a fronteira transacional da própria aplicação
 * (as transações do repositório do Spring Data, já que ProdutoService não é @Transactional e
 * open-in-view=false), e a consulta final abre um contexto de persistência novo, lendo do H2.
 * Como nada é desfeito por rollback, os registros criados aqui são apagados no @AfterEach.
 */
@SpringBootTest
class ProdutoServiceSemTransacaoIntegrationTest {
 @Autowired ProdutoService service;
 @Autowired ProdutoRepository repository;

 private final List<Long> idsCriados = new ArrayList<>();

 @AfterEach
 void limparRegistrosCriados(){
  idsCriados.forEach(repository::deleteById);
  idsCriados.clear();
 }

 @Test @DisplayName("EXTRA04 (CT03 sem @Transactional) - Atualização de preço é persistida pela transação real da aplicação")
 void deveAtualizarPrecoPersistidoSemTransacaoDoTeste(){
  Produto mouse = service.cadastrar("Mouse", 80.0);
  Long id = mouse.getId();
  assertNotNull(id, "O produto cadastrado deveria receber um ID gerado pelo banco");
  idsCriados.add(id);

  service.atualizarPreco(id, 95.0);

  Produto atualizado = repository.findById(id)
   .orElseThrow(() -> new AssertionError("Produto não encontrado após a atualização"));
  assertEquals(95.0, atualizado.getPreco(), "O preço persistido deveria ser o novo valor");
  assertEquals("Mouse", atualizado.getNome(), "A atualização de preço não deveria alterar o nome");
 }
}
