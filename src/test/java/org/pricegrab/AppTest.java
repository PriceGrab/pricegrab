package org.pricegrab;


import java.io.*;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AppTest {
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
    public void executeTest() {
        provideInput("a\n0");
        try {
            App.main(new String[] {"0"});
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertEquals("\n" + "-----------pricegrab-----------\n" + "Choose access type:\n"
                + "\tPress -1- for Admin Access\n" + "\tPress -2- for Visitor Access\n"
                + "\tPress -0- to Exit the program\n" + "\n"
                + "Please enter a Number from the choices provided.\n" + "\n"
                + "-----------pricegrab-----------\n" + "Choose access type:\n"
                + "\tPress -1- for Admin Access\n" + "\tPress -2- for Visitor Access\n"
                + "\tPress -0- to Exit the program\n", getOutput());
    }
}