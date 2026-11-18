package br.edu.exemplo;

public class ContaBancaria {
    private double saldo;

    public ContaBancaria() {
        this.saldo = 0;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double valor) {
        // Validação extra (não pedida no README): depósito negativo reduziria o saldo,
        // então é ignorado, no mesmo padrão silencioso do saque inválido.
        if (valor <= 0) {
            return;
        }
        saldo += valor;
    }

    public void sacar(double valor) {
        // Saque maior que o saldo é ignorado: o saldo permanece inalterado.
        // Validação extra: saque negativo aumentaria o saldo, então também é ignorado.
        if (valor <= 0 || valor > saldo) {
            return;
        }
        saldo -= valor;
    }
}
