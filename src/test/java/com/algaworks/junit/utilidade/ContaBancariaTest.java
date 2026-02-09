package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ContaBancariaTest {

    @Nested
    class Saldo {
        @Test
        void deveCriarContaComSaldoValido() {
            ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.TEN);

            assertEquals(BigDecimal.TEN, contaBancaria.saldo());
        }

        @Test
        void naoDeveCriarContaComSaldoNulo() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new ContaBancaria(null));

            assertEquals("O valor do saldo não pode ser nulo!", exception.getMessage());
        }
    }

    @Nested
    class Saque {
        @Test
        void deveSacarQuandoSaldoSuficiente() {
            ContaBancaria conta = new ContaBancaria(BigDecimal.TEN);

            conta.saque(BigDecimal.TEN);

            assertEquals(BigDecimal.ZERO, conta.saldo());
        }

        @Test
        void deveLancarExcecaoQuandoSacarValorNulo() {
            assertThrows(IllegalArgumentException.class,
                    () -> new ContaBancaria(BigDecimal.TEN).saque(null));
        }

        @Test
        void deveLancarExcecaoQuandoSacarValorNegativo() {
            assertThrows(IllegalArgumentException.class,
                    () -> new ContaBancaria(BigDecimal.TEN).saque(new BigDecimal(-1)));
        }

        @Test
        void deveLancarExcecaoQuandoSacarValorZero() {
            assertThrows(IllegalArgumentException.class,
                    () -> new ContaBancaria(BigDecimal.TEN).saque(BigDecimal.ZERO));
        }

        @Test
        void deveLancarExcecaoQuandoSaldoInsuficiente() {
            assertThrows(RuntimeException.class,
                    () -> new ContaBancaria(BigDecimal.TEN).saque(new BigDecimal(11)));
        }
    }

    @Nested
    class Deposito {
        @Test
        void deveDepositarValorValido() {
            ContaBancaria conta = new ContaBancaria(BigDecimal.ZERO);

            conta.deposito(BigDecimal.TEN);

            assertEquals(BigDecimal.TEN, conta.saldo());
        }

        @Test
        void deveLancarExcecaoQuandoDepositarValorNulo() {
            assertThrows(IllegalArgumentException.class,
                    () -> new ContaBancaria(BigDecimal.ZERO).deposito(null));
        }

        @Test
        void deveLancarExcecaoQuandoDepositarValorNegativo() {
            assertThrows(IllegalArgumentException.class,
                    () -> new ContaBancaria(BigDecimal.ZERO).deposito(new BigDecimal(-1)));
        }

        @Test
        void deveLancarExcecaoQuandoDepositarValorZero() {
            assertThrows(IllegalArgumentException.class,
                    () -> new ContaBancaria(BigDecimal.ZERO).deposito(BigDecimal.ZERO));
        }
    }
}
