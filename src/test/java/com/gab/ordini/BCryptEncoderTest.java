package com.gab.ordini;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.gab.ordini.config.BCryptEncoder;

class BCryptEncoderTest {

    @Test
    void testCodifica() {
        String pass = BCryptEncoder.encode("Pass01$");
        assertNotNull(pass); // Verifica che la password codificata non sia null
        System.out.println(pass);
    }
}
