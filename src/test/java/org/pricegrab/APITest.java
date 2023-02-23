package org.pricegrab;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pricegrab.utils.API;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class APITest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        return testOut.toString();
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    void isNumericTest() {
        API api = new API();
        assertFalse(api.isNumeric("hello"));
        assertFalse(api.isNumeric("123abc"));
        assertFalse(api.isNumeric("$%^d"));
        assertTrue(api.isNumeric("299"));
        assertTrue(api.isNumeric("129.9"));
        assertTrue(api.isNumeric("333.99"));
    }

    @Test
    public void postTest() {
        API api = new API();

        try {
            api.post("google");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertNull(null);
    }

    @Test
    public void addingToFavoriteListTest() {
        API api = new API();
        api.setUsername("newUserTest");
        ArrayList<ProductInfo> productInfos = new ArrayList<>();
        productInfos.add(new ProductInfo("sellerURLTest", "sellerTest", "currencyTest", "nameTest",
                "priceTest"));
        productInfos.add(
                new ProductInfo("sellerURLTest2", "sellerTest2", "currencyTest2", "nameTest2",
                        "priceTest2"));
        api.addingToFavoriteList(productInfos, new Scanner("1\ny"));
        assertNull(null);
    }
}