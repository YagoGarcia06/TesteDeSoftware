package br.edu.exemplo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;
@SpringBootTest @AutoConfigureMockMvc @Transactional
class ProdutoControllerIntegrationTest {
 @Autowired MockMvc mockMvc;
 @Autowired ObjectMapper objectMapper;

 @Test @DisplayName("CT04 - Cadastro via API: POST /produtos retorna 200 com ID, nome e preço")
 void deveCadastrarProdutoPelaApi() throws Exception {
  mockMvc.perform(post("/produtos")
    .contentType(MediaType.APPLICATION_JSON)
    .content("""
     {"nome":"Mouse","preco":80.0}
     """))
   .andExpect(status().isOk())
   .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
   .andExpect(jsonPath("$.id").isNumber())
   .andExpect(jsonPath("$.nome").value("Mouse"))
   .andExpect(jsonPath("$.preco").value(80.0));
 }

 @Test @DisplayName("CT05 - Consulta via API: GET /produtos/{id} retorna o produto cadastrado pelo POST")
 void deveBuscarProdutoPelaApi() throws Exception {
  MvcResult resultadoPost = mockMvc.perform(post("/produtos")
    .contentType(MediaType.APPLICATION_JSON)
    .content("""
     {"nome":"Mouse","preco":80.0}
     """))
   .andExpect(status().isOk())
   .andReturn();

  JsonNode cadastrado = objectMapper.readTree(resultadoPost.getResponse().getContentAsString());
  assertTrue(cadastrado.path("id").isNumber(), "A resposta do POST deveria conter um ID numérico");
  long id = cadastrado.get("id").asLong();

  mockMvc.perform(get("/produtos/{id}", id).accept(MediaType.APPLICATION_JSON))
   .andExpect(status().isOk())
   .andExpect(jsonPath("$.id").value(id))
   .andExpect(jsonPath("$.nome").value(cadastrado.get("nome").asText()))
   .andExpect(jsonPath("$.preco").value(cadastrado.get("preco").asDouble()))
   .andExpect(jsonPath("$.nome").value("Mouse"))
   .andExpect(jsonPath("$.preco").value(80.0));
 }
}
