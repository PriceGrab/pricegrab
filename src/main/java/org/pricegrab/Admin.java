package org.pricegrab;

import java.util.Scanner;

public class Admin {
    public Admin() {
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n-----------Admin-----------");
            System.out.println("Choose an Operation:");
            System.out.println("\t-1- Manage Registered Users\n"); //to be implemented
            System.out.println("\t-9- Change User Type");
            System.out.println("\t-0- to Exit the program");
            choice = Integer.parseInt(sc.nextLine()); //needs exception handling (later)

            switch (choice) {
                case 1:
                    manageRegisteredUsers();
                case 2:
                    //                    updateProduct();
                    break;
                case 3:
                    //                    deleteProduct();
                    break;
                case 4:
                    //                    System.out.println("SS");
                case 9:
                    return;
            }
            if (choice == 0)
                System.exit(0);
        } while (true);
    }

    public void manageRegisteredUsers() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n-----------Managing Users-----------");
            System.out.println("Choose an Operation:");
            System.out.println("\t-1- View list of Users");
            System.out.println("\t-2- Delete User\n"); //to be implemented
            System.out.println("\t-0- Admin Operations"); //to be implemented
            choice = Integer.parseInt(sc.nextLine()); //needs exception handling (later)

            switch (choice) {
                case 1:
                    new UserManagement().viewUsers(); break;
                case 2:
                    //                    new UserManagement().deleteUser();
                case 0:
                    return;
            }
        } while (true);
    }
}