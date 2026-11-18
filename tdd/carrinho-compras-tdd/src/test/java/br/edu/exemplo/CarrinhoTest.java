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

    // Requisito 6 — Desconto de 10% para compras a partir de R$ 500,00
    @Test
    @DisplayName("Requisito 6: subtotal de R$ 600,00 recebe 10% de desconto e fica R$ 540,00")
    void deveAplicarDescontoDeDezPorCentoAcimaDoLimite() {
        Carrinho carrinho = new Carrinho();

        carrinho.adicionar(new Produto("Monitor", 600.00));

        assertEquals(540.00, carrinho.getTotal(), DELTA);
    }

    @Test
    @DisplayName("Requisito 6 (fronteira): R$ 499,99 não recebe desconto")
    void naoDeveAplicarDescontoAbaixoDoLimite() {
        Carrinho carrinho = new Carrinho();

        carrinho.adicionar(new Produto("Cadeira", 499.99));

        assertEquals(499.99, carrinho.getTotal(), DELTA);
    }

    @Test
    @DisplayName("Requisito 6 (fronteira): exatamente R$ 500,00 recebe desconto e fica R$ 450,00")
    void deveAplicarDescontoExatamenteNoLimite() {
        Carrinho carrinho = new Carrinho();

        carrinho.adicionar(new Produto("Cadeira", 500.00));

        assertEquals(450.00, carrinho.getTotal(), DELTA);
    }

    @Test
    @DisplayName("Requisito 6 (fronteira): R$ 500,01 recebe desconto e fica R$ 450,009")
    void deveAplicarDescontoLogoAcimaDoLimite() {
        Carrinho carrinho = new Carrinho();

        carrinho.adicionar(new Produto("Cadeira", 500.01));

        assertEquals(450.009, carrinho.getTotal(), DELTA);
    }

    @Test
    @DisplayName("Requisito 6: desconto considera a soma de vários produtos (300 + 200 = 500)")
    void deveAplicarDescontoSobreASomaDosProdutos() {
        Carrinho carrinho = new Carrinho();

        carrinho.adicionar(new Produto("Monitor", 300.00));
        carrinho.adicionar(new Produto("Teclado", 200.00));

        assertEquals(450.00, carrinho.getTotal(), DELTA);
    }

    // ---- Casos de borda adicionados ----

    @Test
    @DisplayName("Remover produto que não está no carrinho não altera o total")
    void removerProdutoAusenteNaoAlteraTotal() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Teclado", 100.00));

        carrinho.remover(new Produto("Mouse", 50.00));

        assertEquals(100.00, carrinho.getTotal(), DELTA);
    }

    @Test
    @DisplayName("Remover produto adicionado duas vezes retira apenas uma unidade")
    void removerProdutoRepetidoRetiraApenasUmaUnidade() {
        Carrinho carrinho = new Carrinho();
        Produto mouse = new Produto("Mouse", 50.00);
        carrinho.adicionar(mouse);
        carrinho.adicionar(mouse);

        carrinho.remover(mouse);

        assertEquals(50.00, carrinho.getTotal(), DELTA);
    }

    @Test
    @DisplayName("Remover o único produto volta o total para R$ 0,00")
    void removerUnicoProdutoZeraTotal() {
        Carrinho carrinho = new Carrinho();
        Produto teclado = new Produto("Teclado", 100.00);
        carrinho.adicionar(teclado);

        carrinho.remover(teclado);

        assertEquals(0, carrinho.getTotal(), DELTA);
    }

    @Test
    @DisplayName("Produto com preço zero é permitido (só o negativo é inválido)")
    void devePermitirProdutoComPrecoZero() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Brinde", 0.00));
        carrinho.adicionar(new Produto("Teclado", 100.00));

        assertEquals(100.00, carrinho.getTotal(), DELTA);
    }

    @Test
    @DisplayName("Produto inválido não afeta os produtos já adicionados")
    void produtoInvalidoNaoAlteraCarrinhoExistente() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Teclado", 100.00));

        assertThrows(IllegalArgumentException.class,
                () -> carrinho.adicionar(new Produto("Mouse", -50.00)));

        assertEquals(100.00, carrinho.getTotal(), DELTA);
    }

    @Test
    @DisplayName("Ao remover produto e o subtotal ficar abaixo de R$ 500,00, o desconto deixa de valer")
    void removerProdutoPodeRetirarODesconto() {
        Carrinho carrinho = new Carrinho();
        Produto monitor = new Produto("Monitor", 460.00);
        Produto mouse = new Produto("Mouse", 50.00);
        carrinho.adicionar(monitor);
        carrinho.adicionar(mouse);
        assertEquals(459.00, carrinho.getTotal(), DELTA); // 510 - 10%

        carrinho.remover(mouse);

        assertEquals(460.00, carrinho.getTotal(), DELTA); // sem desconto
    }
}
