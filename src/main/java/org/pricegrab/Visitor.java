package org.pricegrab;

import org.pricegrab.utils.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Visitor {
    Scanner sc = new Scanner(System.in);

    public Visitor() {
    }

    public int run() throws IOException {
        int choice;

        do {
            System.out.println("\n-----------Welcome to Pricegrab-----------"
                    + "\nChoose Operation:"
                    + "\n\t-1- Search Product"
                    + "\n\t-2- Register"
                    + "\n\t-3- Login\n\t :"
                    + "\n\t-9- Change User Type"
                    + "\n\t-0- to Exit the program");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException nfe) {
                System.out.println("\nPlease enter a number from the choices provided.");
                continue;
            }
            switch (choice) {
                case 1:
                    visitorSearch();
                    break;
                case 2:
                    registerNewUser();
                    break;
                case 3:
                    login();
                    break;
                case 9:
                    return 9;
            }
            if (choice == 0)
                return 0;
        } while (true);
    }

    public void visitorSearch() throws IOException {
        String searchValue;
        String country;
        System.out.print("\n-----------Search Products-----------\n"
                + "Search: ");
        searchValue = sc.nextLine();
        System.out.print("\nCountry: ");
        country = sc.nextLine();
        System.out.println();

        new API(country, searchValue).post(
                "google");// head and shoulders Classic shampoo 90 ml --- ex. "amazon", "us", "macbook pro 2021 silver 14-inch m1 16gb ram new 512GB refurbished" more detailed search == higher accuracy results
        //fixed stores for now just to simplify things
        //the google value gives us several stores in fact, like walmart, amazon, google shopping, apple.com, adorama.com and many more...
        //the amazon value gives us several products but within amazon.com only.

         /*
        source - website from which to collect data; possible sources: ebay, amazon, google, idealo etc.)
        country - alpha-2 country code of the source to request from (de, at,us, etc.)
        values – a value for which to search

        Available sources and countries:
        amazon: us, ca, mx, br, uk, de, es, fr, it, jp, cn, in, ae, au, at*, ch*
        google: us, de, uk, au, at, br, ca, cz, dk, fr, in, ie, it, jp, mx, no, pl, nz, nl, ru, sg, za, es, se, fi, ch, tr
        idealo: de, uk, at, it, fr, es
        billiger: de
        ebay: us, uk, de, au, at, be, ca, fr, de, ie, it, hk, my, nl, ph, pl, sg, es, ch, au
        * at and ch on Amazon use DE marketplace with AT or CH address
         */
    }

    public void registeredUsersSearch(String username) throws IOException {
        String searchValue;
        String country;
        System.out.print("\n-----------Search Products-----------\n"
                + "Search: ");
        searchValue = sc.nextLine();
        System.out.print("\nCountry: ");
        country = sc.nextLine();
        System.out.println();

        API api = new API();
        api.setSearchValue(searchValue);
        api.setCountry(country);
        api.setUsername(username);
        api.setLoggedInOrNot(true);
        api.post("google");
    }

    public void registerNewUser() {
        String newUsername;
        String newPassword;
        boolean flag = true;
        do {
            try {
                System.out.println("\n-----------Register New User-----------");
                System.out.print("\nEnter New Username: ");
                newUsername = sc.nextLine();
                System.out.print("\nEnter New Password: ");
                newPassword = sc.nextLine();
                new UserManagement().addNewUser(newUsername,
                        newPassword); //needs -> duplicate key values exception handling, reenter pass + username until it's unique.
                flag = false;
            } catch (SQLException e) {
                System.out.println("Username already taken..\ntry again...");
            }
        } while (flag);
    }

    public void login() throws IOException {
        String username;
        String password;
        boolean flag;
        int choice;

        do { //needs cleaning -> cancel the Login attempts, and make the user go back to just a normal visitor that uses main UI
            System.out.println("\n-----------Login-----------");
            System.out.print("\n\tEnter Username: ");
            username = sc.nextLine();
            System.out.print("\tEnter Password: ");
            password = sc.nextLine();

            flag = new UserManagement().validateUser(username, password);
            if (!flag)
                System.out.println("\n\tWrong credentials.\n\tPlease try again...");
        } while (!flag);

        boolean logout = false;
        do {
            System.out.println("\n-----------Welcome to Pricegrab " + username + "-----------"
                    + "\nChoose Operation:"
                    + "\n\t-1- Search Product"
                    + "\n\t-2- View favorite list"
                    + "\n\t-3- Logout");

            System.out.println("\t-0- to Exit the program");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException nfe) {
                System.out.println("\nPlease enter a number from the choices provided.");
                continue;
            }
            switch (choice) {
                case 1:
                    registeredUsersSearch(username);
                    break;
                case 2:
                    viewFavoriteList(username);
                    break;
                case 3:
                    logout = true;
                    break;
            }
        } while (!logout);
    }

    public void viewFavoriteList(String username) {
        new UserManagement().viewFavoriteList(username);
    }
}