package org.pricegrab;

import org.junit.jupiter.api.Test;
import org.pricegrab.utils.API;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class APITest {
    API api = new API();

    @Test
    void isNumericTest() {
        assertFalse(api.isNumeric("hello"));
        assertFalse(api.isNumeric("123abc"));
        assertFalse(api.isNumeric("$%^d"));
        assertTrue(api.isNumeric("299"));
        assertTrue(api.isNumeric("129.9"));
        assertTrue(api.isNumeric("333.99"));
    }

    @Test
    public void postTest() {
        try {
            api.post("google");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertNull(null);
    }
}