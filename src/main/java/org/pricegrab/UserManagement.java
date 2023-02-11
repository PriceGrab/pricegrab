package org.pricegrab;

import org.pricegrab.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserManagement {
    private String username;
    private String password;

    public UserManagement() {
    }

    public UserManagement(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // https://docs.oracle.com/javase/tutorial/jdbc/basics/array.html helpful resource for multi-values columns
    public void addNewUser(String username, String password) throws SQLException {
        Connection dbConnection = DBConnection.getInstance().getConnection();
        Statement stmt = dbConnection.createStatement();
        PreparedStatement insertStmt = dbConnection.prepareStatement(
                "INSERT INTO pricegrab (username, password) VALUES (?, ?);");
        insertStmt.setString(1, username);
        insertStmt.setString(2, password);
        int rows = insertStmt.executeUpdate();
        System.out.println("Rows affected: " + rows);
    }

    public boolean validateUser(String username, String password) {
        try {
            Connection dbConnection = DBConnection.getInstance().getConnection();
            Statement stmt = dbConnection.createStatement();
            PreparedStatement validatestmt =
                    dbConnection.prepareStatement("SELECT * FROM pricegrab WHERE username = ?");
            validatestmt.setString(1, username);
            ResultSet resultSet = validatestmt.executeQuery();
            resultSet.next();

            String tablePass = resultSet.getString(2);
            return tablePass.equals(password);

        } catch (SQLException e) {
            return false;
        }
    }

    public void viewFavoriteList(String username) {
        Scanner sc = new Scanner(System.in);
        try {
            Connection dbConnection = DBConnection.getInstance().getConnection();
            Statement stmt = dbConnection.createStatement();
            PreparedStatement viewFavListStmt =
                    dbConnection.prepareStatement("SELECT * FROM favorite_list WHERE username = ?");
            viewFavListStmt.setString(1, username);
            ResultSet rs = viewFavListStmt.executeQuery();
            if (!rs.isBeforeFirst()) { //this returns false if the cursor is not before the first record or if there are no rows in the ResultSet.
                System.out.println("Favorite list is Empty.");
                return;
            }
            int count = 1;
            while (rs.next()) {
                System.out.print(count++ + "- ");
                String row = "Product Name: " + rs.getString("name") + " -> Product price: "
                        + rs.getString("price") + " " + rs.getString("currency")
                        + " -> Seller URL: " + rs.getString("seller_url");
                System.out.println(row + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertFavoriteToDB(String username, ArrayList<ProductInfo> favoriteList) {
        try {
            Connection dbConnection = DBConnection.getInstance().getConnection();
            Statement stmt = dbConnection.createStatement();
            for (int i = 0; i < favoriteList.size(); i++) {
                PreparedStatement insertStmt = dbConnection.prepareStatement(
                        "INSERT INTO favorite_list (seller_url, seller, currency, name, price, username) VALUES (?, ?, ?, ?, ?, ?);");
                insertStmt.setString(1, favoriteList.get(i).getSellerURL());
                insertStmt.setString(2, favoriteList.get(i).getSeller());
                insertStmt.setString(3, favoriteList.get(i).getCurrency());
                insertStmt.setString(4, favoriteList.get(i).getName());
                insertStmt.setString(5, favoriteList.get(i).getPrice());
                insertStmt.setString(6, username);
                int rows = insertStmt.executeUpdate();
                System.out.println("Rows affected: " + rows);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewUsers() {
        System.out.println("-----------Users List-----------");
        try {
            Connection dbConnection = DBConnection.getInstance().getConnection();
            Statement stmt = dbConnection.createStatement();
            String query = "SELECT * FROM pricegrab";
            ResultSet rs = stmt.executeQuery(query);
            int count = 1;
            while (rs.next()) {
                //Display values
                String row =
                        "\t" + count++ + "- Username: " + rs.getString("username") + " - password: "
                                + rs.getString("password") + "\n";
                System.out.print(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUsers(String username) {
        try {
            Connection dbConnection = DBConnection.getInstance().getConnection();
            Statement stmt = dbConnection.createStatement();

            PreparedStatement deleteFavList =
                    dbConnection.prepareStatement("DELETE FROM favorite_list WHERE username = ?");
            deleteFavList.setString(1, username);
            deleteFavList.executeUpdate();

            PreparedStatement deleteStmt =
                    dbConnection.prepareStatement("DELETE FROM pricegrab WHERE username = ?");
            deleteStmt.setString(1, username);
            int row = deleteStmt.executeUpdate();
            System.out.println("Account name " + username + " has been deactivated");
            System.out.println("Rows updated: " + row);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}