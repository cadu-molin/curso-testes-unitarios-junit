package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Conta bancária")
public class ContaBancariaBDDTest {

    @Nested
    @DisplayName("Dado uma conta bancária com saldo de R$ 10,00")
    class ContaBancariaComSaldo{

        private ContaBancaria contaBancaria;

        @BeforeEach
        void beforeEach() {
            contaBancaria = new ContaBancaria(BigDecimal.TEN);
        }

        @Nested
        @DisplayName("Quando efetuar o saque com valor menor")
        class SaqueComValorMenor {

            private BigDecimal valorSaque = new BigDecimal(9);

            @Test
            @DisplayName("Então não deve lançar exception")
            void deveLancarSaqueSemException() {
                assertDoesNotThrow(() -> contaBancaria.saque(valorSaque));
            }

            @Test
            @DisplayName("E deve subtrair do saldo")
            void deveSubtrairDoSaldo() {
                contaBancaria.saque(valorSaque);

                assertEquals(new BigDecimal(1), contaBancaria.saldo());
            }
        }

        @Nested
        @DisplayName("Quando efetuar o saque com valor maior")
        class SaqueComValorMaior {

            private BigDecimal valorSaque = new BigDecimal(20);

            @Test
            @DisplayName("Então deve lançar exception")
            void deveFalhar() {
                assertThrows(RuntimeException.class, () -> contaBancaria.saque(valorSaque));
            }

            @Test
            @DisplayName("E não deve alterar saldo")
            void naoDeveAlterarSaldo() {
                try {
                    contaBancaria.saque(valorSaque);
                } catch (Exception e) {}

                assertEquals(BigDecimal.TEN, contaBancaria.saldo());
            }
        }
    }

    @Nested
    @DisplayName("Dado uma conta bancária com saldo de R$ 0,00")
    class ContaBancariaComSaldoZero {

        private ContaBancaria contaBancaria;

        @BeforeEach
        void beforeEach() {
            contaBancaria = new ContaBancaria(BigDecimal.ZERO);
        }

        @Nested
        @DisplayName("Quando efetuar o saque com valor maior")
        class SaqueComValorMaior {

            private BigDecimal valorSaque = new BigDecimal(1);

            @Test
            @DisplayName("Então deve lançar exception")
            void deveFalhar() {
                assertThrows(RuntimeException.class, () -> contaBancaria.saque(valorSaque));
            }
        }

        @Nested
        @DisplayName("Quando efetuar um deposito de R$ 10,00")
        class DepositoComDezReais {

            private BigDecimal valorDeposito = new BigDecimal(10);

            @Test
            @DisplayName("Então deve acumular ao saldo")
            void deveSomarAoSaldo() {
                contaBancaria.deposito(valorDeposito);

                assertEquals(new BigDecimal(10), contaBancaria.saldo());
            }
        }
    }
}
