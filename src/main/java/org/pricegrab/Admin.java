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
            System.out.println("\t-1- Manage Registered Users\n");
            System.out.println("\t-9- Change User Type");
            System.out.println("\t-0- to Exit the program");
            try {
                choice = Integer.parseInt(sc.nextLine());
            }
            catch(NumberFormatException nfe){
                System.out.println("Please enter a number from the choices above.");
                continue;
            }
            switch (choice) {
                case 1:
                    manageRegisteredUsers(); break;
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
        String username;
        boolean backToAdminOperations = false; //delete this
        do {
            System.out.println("\n-----------Managing Users-----------");
            System.out.println("Choose an Operation:");
            System.out.println("\t-1- View list of Users");
            System.out.println("\t-2- Delete User\n");
            System.out.println("\t-0- Back to Admin Operations");
            try{
            choice = Integer.parseInt(sc.nextLine());
            }
            catch(NumberFormatException nfe){
                System.out.println("\nPlease enter a Number from the choices provided.");
                continue;
            }
            switch (choice) {
                case 1:
                    new UserManagement().viewUsers();
                    break;
                case 2:
                    System.out.println("Enter Username to delete:");
                    username = sc.nextLine();
                    new UserManagement().deleteUsers(username);
                    break;
                case 0:
                    return;
            }
        } while (true);
    }
}