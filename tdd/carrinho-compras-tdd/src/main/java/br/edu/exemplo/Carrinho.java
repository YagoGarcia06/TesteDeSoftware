package br.edu.exemplo;

public class Carrinho {

    private double total;

    public double getTotal() {
        return total;
    }

    public void adicionar(Produto produto) {
        total += produto.getPreco();
    }
}
