package br.edu.exemplo;

import java.util.ArrayList;
import java.util.List;

/**
 * Carrinho de compras que armazena objetos {@link Produto}
 * (relação Carrinho ──> Produto, conforme REQUISITOS.md).
 */
public class Carrinho {

    /** Valor a partir do qual a compra recebe desconto (Requisito 6). */
    private static final double VALOR_MINIMO_DESCONTO = 500.00;

    /** Percentual de desconto aplicado (10%). */
    private static final double PERCENTUAL_DESCONTO = 0.10;

    private final List<Produto> produtos = new ArrayList<>();

    /**
     * Adiciona um produto ao carrinho.
     *
     * @throws IllegalArgumentException se o preço do produto for negativo (Requisito 5)
     */
    public void adicionar(Produto produto) {
        if (produto.getPreco() < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo: " + produto.getNome());
        }
        produtos.add(produto);
    }

    /** Remove uma ocorrência do produto informado; se ele não estiver no carrinho, nada muda. */
    public void remover(Produto produto) {
        produtos.remove(produto);
    }

    /** Total a pagar: subtotal com 10% de desconto se for igual ou superior a R$ 500,00. */
    public double getTotal() {
        double subtotal = calcularSubtotal();
        if (subtotal >= VALOR_MINIMO_DESCONTO) {
            return subtotal - subtotal * PERCENTUAL_DESCONTO;
        }
        return subtotal;
    }

    private double calcularSubtotal() {
        double subtotal = 0;
        for (Produto produto : produtos) {
            subtotal += produto.getPreco();
        }
        return subtotal;
    }
}
