package br.edu.exemplo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*
 * Testes escritos um requisito por vez, seguindo o ciclo
 * RED -> GREEN -> REFACTOR descrito em REQUISITOS.md.
 */
class CarrinhoTest {

    private static final double DELTA = 0.001;

    // Requisito 1 — Carrinho vazio
    @Test
    @DisplayName("Requisito 1: carrinho recém-criado deve ter total R$ 0,00")
    void carrinhoNovoDeveTerTotalZero() {
        Carrinho carrinho = new Carrinho();

        assertEquals(0, carrinho.getTotal(), DELTA);
    }

    // Requisito 2 — Adicionar um produto
    @Test
    @DisplayName("Requisito 2: adicionar um Teclado de R$ 100,00 deixa total R$ 100,00")
    void deveAdicionarUmProduto() {
        Carrinho carrinho = new Carrinho();

        carrinho.adicionar(new Produto("Teclado", 100.00));

        assertEquals(100.00, carrinho.getTotal(), DELTA);
    }

    // Requisito 3 — Adicionar vários produtos
    @Test
    @DisplayName("Requisito 3: Teclado (R$ 100,00) + Mouse (R$ 50,00) somam R$ 150,00")
    void deveSomarPrecosDeVariosProdutos() {
        Carrinho carrinho = new Carrinho();

        carrinho.adicionar(new Produto("Teclado", 100.00));
        carrinho.adicionar(new Produto("Mouse", 50.00));

        assertEquals(150.00, carrinho.getTotal(), DELTA);
    }

    // Requisito 4 — Remover produto
    @Test
    @DisplayName("Requisito 4: remover o Mouse de um carrinho de R$ 150,00 deixa R$ 100,00")
    void deveRemoverProduto() {
        Carrinho carrinho = new Carrinho();
        Produto teclado = new Produto("Teclado", 100.00);
        Produto mouse = new Produto("Mouse", 50.00);
        carrinho.adicionar(teclado);
        carrinho.adicionar(mouse);

        carrinho.remover(mouse);

        assertEquals(100.00, carrinho.getTotal(), DELTA);
    }

    // Requisito 5 — Produto com preço inválido
    // Decisão: lançar IllegalArgumentException (falha explícita, em vez de ignorar
    // silenciosamente) e manter o carrinho inalterado.
    @Test
    @DisplayName("Requisito 5: adicionar produto com preço negativo lança IllegalArgumentException")
    void naoDevePermitirProdutoComPrecoNegativo() {
        Carrinho carrinho = new Carrinho();
        Produto tecladoInvalido = new Produto("Teclado", -100.00);

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> carrinho.adicionar(tecladoInvalido)
        );

        assertEquals("Preço não pode ser negativo: Teclado", erro.getMessage());
        assertEquals(0, carrinho.getTotal(), DELTA);
    }
}
