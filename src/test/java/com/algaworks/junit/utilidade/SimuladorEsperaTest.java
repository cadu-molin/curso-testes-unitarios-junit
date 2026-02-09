package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class SimuladorEsperaTest {

    @Test
    public void deveEsperarSemTimeout() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> SimuladorEspera.esperar(Duration.ofMillis(10)));
    }
}