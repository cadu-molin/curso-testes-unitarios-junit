package com.algaworks.junit.utilidade;

import java.math.BigDecimal;

import static java.util.Objects.isNull;

public class ContaBancaria {
    private BigDecimal saldo;

    public ContaBancaria(BigDecimal saldo) {
        if(isNull(saldo)) {
            throw new IllegalArgumentException("O valor do saldo não pode ser nulo!");
        }

        this.saldo = saldo;
    }

    public void saque(BigDecimal valor) {
        if (isNull(valor) || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de saque inválido!");
        }

        if (saldo.compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente!");
        }

        saldo = saldo.subtract(valor);
    }

    public void deposito(BigDecimal valor) {
        if (isNull(valor) || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de depósito inválido!");
        }

        saldo = saldo.add(valor);
    }

    public BigDecimal saldo() {
        return saldo;
    }
}
