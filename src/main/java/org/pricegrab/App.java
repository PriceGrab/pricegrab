package org.pricegrab;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException, SQLException {
        execute();
    }

    //head and shoulders shampoo 90ml uk (neblum - british) - (groceries - sainsburys)
    public static void execute() throws IOException {
                Scanner sc = new Scanner(System.in);
                int choice = 10;
                do {
                    System.out.println("\n-----------pricegrab-----------"
                            + "\nChoose access type:\n\tPress -1- for Admin Access\n\tPress -2- for Visitor Access\n\tPress -0- to Exit the program");
                    try {
                        choice = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException nfe) {
                        System.out.println("\nPlease enter a Number from the choices provided.");
                        continue;
                    }
                    if (choice == 1) {
                        choice = new Admin().run();
                    } else if (choice == 2) {
                        choice = new Visitor().run();
                    }
                } while (choice!=0);
    }
}