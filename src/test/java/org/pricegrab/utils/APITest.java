package org.pricegrab.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class APITest {

    @Test
    void testIfStringIsFloat(){
        var api = new API();
        assertEquals(false , api.isNumeric("hello"));
        assertEquals(false , api.isNumeric("123abc"));
        assertEquals(false , api.isNumeric("$%^d"));
        assertEquals(true , api.isNumeric("299"));
        assertEquals(true , api.isNumeric("129.9"));
        assertEquals(true , api.isNumeric("333.99"));


    }
}