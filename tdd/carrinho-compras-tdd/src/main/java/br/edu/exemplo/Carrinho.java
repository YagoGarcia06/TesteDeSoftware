package br.edu.exemplo;

import java.util.ArrayList;
import java.util.List;

/**
 * Carrinho de compras que armazena objetos {@link Produto}
 * (relação Carrinho ──> Produto, conforme REQUISITOS.md).
 */
public class Carrinho {

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

    /** Soma dos preços de todos os produtos do carrinho. */
    public double getTotal() {
        double total = 0;
        for (Produto produto : produtos) {
            total += produto.getPreco();
        }
        if (total >= 500) {
            total = total * 0.9;
        }
        return total;
    }
}
