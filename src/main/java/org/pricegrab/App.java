package org.pricegrab;

import org.postgresql.util.PSQLException;
import org.pricegrab.utils.*;


import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.Scanner;

public class App { //Code needs to get cleaned when I have time..! literally don't wanna see more than 10 lines in this class!
    public static void main(String[] args) throws IOException {
        execute();
    }

    public static void execute() throws IOException {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n-----------Welcome to pricegrab-----------");
            System.out.println(
                    ">>Choose access type:\n\tPress -1- for Admin Access\n\tPress -2- for Visitor Access\n\tPress -0- to Exit the program");
            choice = Integer.parseInt(sc.nextLine()); //needs exception handling (later)
            if (choice == 1) {
                new Admin().run();
            } else if (choice == 2) {
                new Visitor().run();
            } else if (choice == 0)
                System.exit(0);
        } while (true);
    }


}