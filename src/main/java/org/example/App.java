package org.example;

import org.example.utils.*;


import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.Scanner;

public class App { //Code needs to get cleaned when I have time..!
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
                admin();
                break;
            } else if (choice == 2) {
                visitor();
                break;
            } else if (choice == 0)
                System.exit(0);
        } while (true);
    }

    public static void admin() throws IOException {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n-----------Admin-----------");
            System.out.println("Choose an Operation:");
            System.out.println("\t-1- Add Product");
            System.out.println("\t-2- Update Product");
            System.out.println("\t-3- Remove Product");
            System.out.println("\t-4- List All Products\n");
            System.out.println("\t-9- Change User Type");
            System.out.println("\t-0- to Exit the program");
            choice = Integer.parseInt(sc.nextLine()); //needs exception handling (later)

            switch (choice) {
                case 1:
                    insertProduct();
                    break;
                case 2:
                    updateProduct();
                    break;
                case 3:
                    deleteProduct();
                    break;
                case 4:
                    System.out.println("SS");
                case 9:
                    execute();
                    break;
            }
            if (choice == 0)
                System.exit(0);
        } while (true);
    }

    public static void insertProduct() {
        Scanner sc = new Scanner(System.in);
        String name;
        double price;
        String stores;
        String[] storesArray;

        System.out.println("\n-----------Adding Product-----------");
        System.out.print("Product Name: ");
        name = sc.nextLine();
        System.out.print("Product Price: ");
        price = Double.parseDouble(sc.nextLine());
        System.out.print("Stores: "); //must separate each store with only a single space..
        stores = sc.nextLine();
        storesArray = stores.split(" ");

        try {
            DBConnection db = DBConnection.getInstance();

            Array anArray = db.getConnection().createArrayOf("text", storesArray);

            AdminManagement product = new AdminManagement(name, price, anArray);
            product.insertProduct();
            // Retrieve all tasks
            product.retrieveProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateProduct() {
        Scanner sc = new Scanner(System.in);
        int id;
        String name;
        double price;
        String stores;
        String[] storesArray;

        System.out.println("\n-----------Updating Product-----------");
        System.out.print("Product ID: ");
        id = Integer.parseInt(sc.nextLine());
        System.out.print("New Name: ");
        name = sc.nextLine();
        System.out.print("New Price: ");
        price = Double.parseDouble(sc.nextLine());
        System.out.print("New Stores: "); //must separate each store with only a single space..
        stores = sc.nextLine();
        storesArray = stores.split(" ");

        AdminManagement product = new AdminManagement();
        product.updateTask(id, name, price, storesArray);
        // Retrieve all tasks
        product.retrieveProducts();
    }

    public static void deleteProduct() {
        Scanner sc = new Scanner(System.in);
        int id;

        System.out.println("\n-----------Delete Product-----------");
        System.out.print("Product ID: ");
        id = Integer.parseInt(sc.nextLine());

        AdminManagement product = new AdminManagement();
        product.deleteProduct(id);
        // Retrieve all tasks
        product.retrieveProducts();
    }

    public static void visitor() throws IOException {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n-----------Welcome to Pricegrab-----------");
            System.out.println("\nChoose Operation:");
            System.out.println("\t-1- Search Product\n\t :");

            System.out.println("\t-9- Change User Type");
            System.out.println("\t-0- to Exit the program");

            choice = Integer.parseInt(sc.nextLine()); //needs exception handling (later)
            switch (choice) {
                case 1:
                    visitorSearch();
                    break;
                case 9:
                    execute();
                    break;
            }
            if (choice == 0)
                System.exit(0);
        } while (true);
    }

    public static void visitorSearch() throws IOException {
        Scanner sc = new Scanner(System.in);
        String searchValue;
        String country;
        System.out.println("\n-----------Search Products-----------");
        System.out.print("Search: ");
        searchValue = sc.nextLine();
        System.out.println();
        System.out.print("Country: ");
        country = sc.nextLine();
        System.out.println();

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

        new API("google", country,
                searchValue);// ex. "amazon", "us", "macbook pro 2021 silver 14-inch m1 16gb ram new 512GB refurbished" more detailed search == higher accuracy results
        //fixed stores for now just to simplify things
        //the google value gives us several stores in fact, like walmart, amazon, google shopping, apple.com, adorama.com and many more...
        //the amazon value gives us several products but within amazon.com only.
    }
}