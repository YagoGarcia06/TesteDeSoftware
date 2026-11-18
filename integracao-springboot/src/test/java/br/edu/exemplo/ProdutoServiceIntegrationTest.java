package br.edu.exemplo;
import br.edu.exemplo.model.Produto;
import br.edu.exemplo.repository.ProdutoRepository;
import br.edu.exemplo.service.ProdutoService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest @Transactional
class ProdutoServiceIntegrationTest {
 @Autowired ProdutoService service;
 @Autowired ProdutoRepository repository;
 // Usado apenas para sincronizar com o H2 (flush) e esvaziar o cache de 1º nível (clear),
 // garantindo que findById() leia o registro do banco e não a mesma instância em memória.
 @Autowired EntityManager entityManager;

 private void sincronizarComBanco(){
  entityManager.flush();
  entityManager.clear();
 }

 @Test @DisplayName("CT01 - Cadastro válido: Teclado por 150.0 recebe ID e é encontrado no repositório")
 void deveCadastrarERecuperarProduto(){
  Produto salvo = service.cadastrar("Teclado", 150.0);
  assertNotNull(salvo.getId(), "O produto cadastrado deveria receber um ID gerado pelo banco");

  sincronizarComBanco();

  Produto encontrado = repository.findById(salvo.getId())
   .orElseThrow(() -> new AssertionError("Produto não encontrado no repositório após o cadastro"));
  assertNotSame(salvo, encontrado, "A consulta deveria vir do banco, não da instância em memória");
  assertEquals(salvo.getId(), encontrado.getId());
  assertEquals("Teclado", encontrado.getNome(), "Nome persistido incorreto");
  assertEquals(150.0, encontrado.getPreco(), "Preço persistido incorreto");
 }

 @Test @DisplayName("CT02 - Nome inválido: nome em branco lança IllegalArgumentException e nada é persistido")
 void naoDevePersistirProdutoComNomeEmBranco(){
  long antes = repository.count();

  IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
   () -> service.cadastrar("   ", 80.0));
  assertEquals("Nome obrigatório", erro.getMessage());

  assertEquals(antes, repository.count(), "A contagem de registros não deveria aumentar");
 }

 @Test @DisplayName("CT03 - Atualização: Mouse de 80.0 para 95.0 é refletido no repositório")
 void deveAtualizarPrecoPersistido(){
  Produto mouse = service.cadastrar("Mouse", 80.0);
  Long id = mouse.getId();
  sincronizarComBanco();

  service.atualizarPreco(id, 95.0);
  sincronizarComBanco();

  Produto atualizado = repository.findById(id)
   .orElseThrow(() -> new AssertionError("Produto não encontrado após a atualização"));
  assertEquals(95.0, atualizado.getPreco(), "O preço persistido deveria ser o novo valor");
  assertEquals("Mouse", atualizado.getNome(), "A atualização de preço não deveria alterar o nome");
 }
}
