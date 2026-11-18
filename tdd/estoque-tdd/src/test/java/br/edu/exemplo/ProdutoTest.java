package br.edu.exemplo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProdutoTest {

    @Test
    void produtoDeveIniciarComEstoqueZero() {
        Produto produto = new Produto("Teclado");

        assertEquals(0, produto.getQuantidade());
    }

    @Test
    void deveAdicionarProdutoAoEstoque() {
        Produto produto = new Produto("Teclado");

        produto.adicionarEstoque(10);

        assertEquals(10, produto.getQuantidade());
    }

    @Test
    void deveAcumularQuantidadeEmEstoque() {
        Produto produto = new Produto("Teclado");

        produto.adicionarEstoque(10);
        produto.adicionarEstoque(5);

        assertEquals(15, produto.getQuantidade());
    }

    @Test
    void deveRetirarProdutoDoEstoque() {
        Produto produto = new Produto("Teclado");

        produto.adicionarEstoque(10);
        produto.retirarEstoque(3);

        assertEquals(7, produto.getQuantidade());
    }

    @Test
    void naoDeveRetirarQuantidadeMaiorQueEstoque() {
        Produto produto = new Produto("Teclado");

        produto.adicionarEstoque(10);
        produto.retirarEstoque(15);

        assertEquals(10, produto.getQuantidade());
    }

    @Test
    void naoDeveAdicionarQuantidadeNegativa() {
        Produto produto = new Produto("Teclado");

        produto.adicionarEstoque(-10);

        assertEquals(0, produto.getQuantidade());
    }

    // ---- Casos de borda adicionados ----

    @Test
    @DisplayName("Deve guardar o nome informado na criação do produto")
    void deveGuardarNomeDoProduto() {
        Produto produto = new Produto("Teclado");

        assertEquals("Teclado", produto.getNome());
    }

    @Test
    @DisplayName("Não deve adicionar quantidade igual a zero (Requisito 6)")
    void naoDeveAdicionarQuantidadeZero() {
        Produto produto = new Produto("Teclado");

        produto.adicionarEstoque(0);

        assertEquals(0, produto.getQuantidade());
    }

    @Test
    @DisplayName("Entrada negativa não deve alterar estoque já existente")
    void naoDeveAlterarEstoqueExistenteComEntradaNegativa() {
        Produto produto = new Produto("Teclado");

        produto.adicionarEstoque(10);
        produto.adicionarEstoque(-5);

        assertEquals(10, produto.getQuantidade());
    }

    @Test
    @DisplayName("Deve permitir retirar exatamente todo o estoque disponível")
    void devePermitirRetirarTodoOEstoque() {
        Produto produto = new Produto("Teclado");

        produto.adicionarEstoque(10);
        produto.retirarEstoque(10);

        assertEquals(0, produto.getQuantidade());
    }

    @Test
    @DisplayName("Não deve retirar de produto sem estoque")
    void naoDeveRetirarDeEstoqueVazio() {
        Produto produto = new Produto("Teclado");

        produto.retirarEstoque(1);

        assertEquals(0, produto.getQuantidade());
    }

    @Test
    @DisplayName("Retirada negativa deve ser ignorada (não pode aumentar o estoque)")
    void naoDeveRetirarQuantidadeNegativa() {
        Produto produto = new Produto("Teclado");

        produto.adicionarEstoque(10);
        produto.retirarEstoque(-5);

        assertEquals(10, produto.getQuantidade());
    }
}
