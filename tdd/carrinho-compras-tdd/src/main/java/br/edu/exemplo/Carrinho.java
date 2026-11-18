package br.edu.exemplo;

public class Carrinho {

    private double total;

    public double getTotal() {
        return total;
    }

    public void adicionar(Produto produto) {
        // Mínimo para o Requisito 2: só existe um produto no carrinho
        total = produto.getPreco();
    }
}
