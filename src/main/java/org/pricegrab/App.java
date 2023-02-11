package org.pricegrab;

import java.io.IOException;
import java.util.Scanner;

public class App {
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
            choice = Integer.parseInt(sc.nextLine());
            if (choice == 1) {
                new Admin().run();
            } else if (choice == 2) {
                new Visitor().run();
            } else if (choice == 0)
                System.exit(0);
        } while (true);
    }
}