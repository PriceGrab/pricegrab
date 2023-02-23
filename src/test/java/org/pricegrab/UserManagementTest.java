package org.pricegrab;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserManagementTest {
    UserManagement userManagement = new UserManagement();
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
    public void addNewUserTest() {
        int min = 0;
        int max = 1000000000;
        int randomNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);

        try {
            userManagement.addNewUser("newUserTest"+ randomNumber, "newPassTest" + randomNumber);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertEquals("Rows affected: 1\n", getOutput());
    }

    @Test
    public void validateUserTest() {
        boolean result = userManagement.validateUser("newUserTest", "newPassTest");
        assertTrue(result);
    }

    @Test
    public void viewFavoriteListTest() {
        userManagement.viewFavoriteList("newUserTest");
        assertNull(null);
    }

    @Test
    public void insertFavoriteToDBTest() {
        ArrayList<ProductInfo> productInfos = new ArrayList<>();
        ProductInfo productInfo = new ProductInfo("urlTest", "sellerTest", "currencyTest", "nameTest", "priceTest");
        productInfos.add(productInfo);
        userManagement.insertFavoriteToDB("newUserTest", productInfos);
        assertNull(null);
    }

    @Test
    public void viewUsersTest() {
        userManagement.viewUsers();
        assertNull(null);
    }

    @Test
    public void deleteUsersTest() {
        userManagement.deleteUsers(null);
        assertNull(null);
    }
}