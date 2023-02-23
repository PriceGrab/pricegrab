package org.pricegrab;

import java.io.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdminTest {
    Admin admin = new Admin();
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
    public void runTest() {
        provideInput("a\n" + "9\n");
        admin.run();
        assertEquals(9, 9);
        provideInput("0");
        assertEquals(0, admin.run());
    }

    @Test
    public void manageRegisteredUsersTest() {
        provideInput("b\n" + "1\n" + "0\n");
        admin.manageRegisteredUsers();
        assertEquals("\n" + "-----------Managing Users-----------\n" + "Choose an Operation:\n"
                + "\t-1- View list of Users\n" + "\t-2- Delete User\n" + "\n"
                + "\t-0- Back to Admin Operations\n" + "\n"
                + "Please enter a Number from the choices provided.\n" + "\n"
                + "-----------Managing Users-----------\n" + "Choose an Operation:\n"
                + "\t-1- View list of Users\n" + "\t-2- Delete User\n" + "\n"
                + "\t-0- Back to Admin Operations\n" + "-----------Users List-----------\n" + "\n"
                + "-----------Managing Users-----------\n" + "Choose an Operation:\n"
                + "\t-1- View list of Users\n" + "\t-2- Delete User\n" + "\n"
                + "\t-0- Back to Admin Operations\n", getOutput());

    }
}