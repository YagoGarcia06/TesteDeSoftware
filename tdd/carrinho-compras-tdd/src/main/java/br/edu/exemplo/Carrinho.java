package br.edu.exemplo;

import java.util.ArrayList;
import java.util.List;

/**
 * Carrinho de compras que armazena objetos {@link Produto}
 * (relação Carrinho ──> Produto, conforme REQUISITOS.md).
 */
public class Carrinho {

    private final List<Produto> produtos = new ArrayList<>();

    public void adicionar(Produto produto) {
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
        return total;
    }
}
