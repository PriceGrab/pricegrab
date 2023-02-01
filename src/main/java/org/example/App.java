package org.example;

import utils.DBConnection;
import utils.Product;
import utils.API;

import java.sql.Array;
import java.sql.SQLException;
import java.util.Scanner;
/**
 * Hello world!
 */
public class App { //Code needs to get cleaned when I have time..!
    public static void main(String[] args) {

        execute();

    }

    public static void execute() {
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

    public static void admin() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n-----------Admin-----------");
            System.out.println("Choose an Operation:");
            System.out.println("\t-1- Add a Product");
            System.out.println("\t-2- Update a Product");
            System.out.println("\t-3- Remove a Product");
            System.out.println("\t-4- List All Available Products\n");
            System.out.println("\t-9- Change User Type");
            System.out.println("\t-0- to Exit the program");
            choice = Integer.parseInt(sc.nextLine()); //needs exception handling (later)

            switch (choice) {
                case 1:
                    insertProduct(); break;
                case 2:
                    updateProduct(); break;
                case 3:
                    deleteProduct(); break;
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

            Product product = new Product(name, price, anArray);
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

        Product product = new Product();
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

        Product product = new Product();
        product.deleteProduct(id);
        // Retrieve all tasks
        product.retrieveProducts();
    }

    public static void visitor() {

    }
}