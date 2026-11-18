package br.edu.exemplo;

public class Produto {

    private String nome;
    private int quantidade;

    public Produto(String nome) {
        this.nome = nome;
        this.quantidade = 0;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void adicionarEstoque(int quantidade) {
        // Requisito 6: entrada negativa ou igual a zero não altera o estoque
        if (quantidade <= 0) {
            return;
        }
        this.quantidade += quantidade;
    }

    public void retirarEstoque(int quantidade) {
        // Requisito 5: retirada maior que o disponível não altera o estoque.
        // Validação extra (não pedida): retirada negativa aumentaria o estoque,
        // então também é ignorada, no mesmo padrão do Requisito 6.
        if (quantidade <= 0 || quantidade > this.quantidade) {
            return;
        }
        this.quantidade -= quantidade;
    }
}
