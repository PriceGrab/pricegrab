package org.pricegrab;


import java.io.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VisitorTest {
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
    public void VisitorSearchTestAndNumberFormatExceptionTest() {
        provideInput("abc\n1\nshampoo\nus\nn\n0");
        Visitor visitor = new Visitor();

        try {
            visitor.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals("\n" + "-----------Welcome to Pricegrab-----------\n" + "Choose Operation:\n"
                + "\t-1- Search Product\n" + "\t-2- Register\n" + "\t-3- Login\n" + "\t :\n"
                + "\t-9- Change User Type\n" + "\t-0- to Exit the program\n" + "\n"
                + "Please enter a number from the choices provided.\n" + "\n"
                + "-----------Welcome to Pricegrab-----------\n" + "Choose Operation:\n"
                + "\t-1- Search Product\n" + "\t-2- Register\n" + "\t-3- Login\n" + "\t :\n"
                + "\t-9- Change User Type\n" + "\t-0- to Exit the program\n" + "\n"
                + "-----------Search Products-----------\n" + "Search: \n" + "Country: \n"
                + "Fetching your request. hold on a moment!\n" + "\n"
                + "-----------Welcome to Pricegrab-----------\n" + "Choose Operation:\n"
                + "\t-1- Search Product\n" + "\t-2- Register\n" + "\t-3- Login\n" + "\t :\n"
                + "\t-9- Change User Type\n" + "\t-0- to Exit the program\n" + "\n"
                + "Please enter a number from the choices provided.\n" + "\n"
                + "-----------Welcome to Pricegrab-----------\n" + "Choose Operation:\n"
                + "\t-1- Search Product\n" + "\t-2- Register\n" + "\t-3- Login\n" + "\t :\n"
                + "\t-9- Change User Type\n" + "\t-0- to Exit the program\n", getOutput());
    }

//    @Test
//    public void loginTest() {
//        provideInput("3\n" + "newUserTest\n" + "newPassTest\n" + "3\n" + "0");
//        Visitor visitor = new Visitor();
//
//        try {
//            visitor.run();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        assertEquals("\n" + "-----------Welcome to Pricegrab-----------\n" + "Choose Operation:\n"
//                + "\t-1- Search Product\n" + "\t-2- Register\n" + "\t-3- Login\n" + "\t :\n"
//                + "\t-9- Change User Type\n" + "\t-0- to Exit the program\n" + "\n"
//                + "-----------Login-----------\n" + "\n" + "\tEnter Username: \tEnter Password: \n"
//                + "-----------Welcome to Pricegrab newUserTest-----------\n" + "Choose Operation:\n"
//                + "\t-1- Search Product\n" + "\t-2- View favorite list\n" + "\t-3- Logout\n"
//                + "\t-0- to Exit the program\n" + "\n"
//                + "-----------Welcome to Pricegrab-----------\n" + "Choose Operation:\n"
//                + "\t-1- Search Product\n" + "\t-2- Register\n" + "\t-3- Login\n" + "\t :\n"
//                + "\t-9- Change User Type\n" + "\t-0- to Exit the program\n", getOutput());
//
//        provideInput("9");
//        Visitor visitor2 = new Visitor();
//        try {
//            assertEquals(9, visitor2.run());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Test
    public void registeredUsersSearchTest() {
        provideInput("3\nnewUserTest\nnewPassTest\n1\nshampoo\nus\nn");
        Visitor visitor = new Visitor();
        try {
            visitor.registeredUsersSearch("ahmad");
            assertNull(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}