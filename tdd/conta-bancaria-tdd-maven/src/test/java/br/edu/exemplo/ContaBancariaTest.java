package br.edu.exemplo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ContaBancariaTest {

    @Test
    void deveDepositarValorNaConta() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        assertEquals(100, conta.getSaldo(), 0.001);
    }

    @Test
    void deveAcumularDepositos() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        conta.depositar(50);
        assertEquals(150, conta.getSaldo(), 0.001);
    }

    @Test
    void deveSacarValorDaConta() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        conta.sacar(40);
        assertEquals(60, conta.getSaldo(), 0.001);
    }

    @Test
    void naoDevePermitirSaqueMaiorQueSaldo() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        conta.sacar(150);
        assertEquals(100, conta.getSaldo(), 0.001);
    }

    // ---- Casos de borda adicionados ----

    @Test
    @DisplayName("Conta nova deve iniciar com saldo zero")
    void contaDeveIniciarComSaldoZero() {
        ContaBancaria conta = new ContaBancaria();
        assertEquals(0, conta.getSaldo(), 0.001);
    }

    @Test
    @DisplayName("Deve permitir sacar exatamente o saldo disponível")
    void devePermitirSacarTodoOSaldo() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        conta.sacar(100);
        assertEquals(0, conta.getSaldo(), 0.001);
    }

    @Test
    @DisplayName("Não deve permitir saque em conta sem saldo")
    void naoDevePermitirSaqueComSaldoZero() {
        ContaBancaria conta = new ContaBancaria();
        conta.sacar(10);
        assertEquals(0, conta.getSaldo(), 0.001);
    }

    @Test
    @DisplayName("Depósito de valor negativo deve ser ignorado")
    void naoDeveDepositarValorNegativo() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        conta.depositar(-50);
        assertEquals(100, conta.getSaldo(), 0.001);
    }

    @Test
    @DisplayName("Depósito de valor zero não deve alterar o saldo")
    void naoDeveAlterarSaldoComDepositoZero() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        conta.depositar(0);
        assertEquals(100, conta.getSaldo(), 0.001);
    }

    @Test
    @DisplayName("Saque de valor negativo deve ser ignorado (não pode aumentar o saldo)")
    void naoDeveSacarValorNegativo() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        conta.sacar(-50);
        assertEquals(100, conta.getSaldo(), 0.001);
    }
}
