package com.algaworks.junit.utilidade;

import org.assertj.core.api.Condition;

public class SaudacaoUtilConditions {

    private SaudacaoUtilConditions() {}

    public static Condition<String> igual(String saudacaoCorreta) {
        return new Condition<>((s) -> s.equals(saudacaoCorreta), "igual a %s", saudacaoCorreta);
    }

    public static Condition<String> igualBomDia() {
        return igual("Bom dia");
    }

    public static Condition<String> igualBoaTarde() {
        return igual("Boa tarde");
    }

    public static Condition<String> igualBoaNoite() {
        return igual("Boa noite");
    }
}
