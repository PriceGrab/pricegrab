package org.pricegrab;

import java.io.*;
<<<<<<< HEAD
import java.sql.SQLException;
=======
>>>>>>> ff3cb1e (untracking unnecessary files)
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