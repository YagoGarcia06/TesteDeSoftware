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

    public void remover(Produto produto) {
        // Esqueleto mínimo só para compilar: ainda não implementado (RED)
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
