package org.pricegrab;

import org.pricegrab.utils.DBConnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

public class UserManagement {
    // https://docs.oracle.com/javase/tutorial/jdbc/basics/array.html helpful resource for multi-values columns
    public void addNewUser(String username, String password) throws SQLException {
        Connection dbConnection = DBConnection.getInstance().getConnection();
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
            PreparedStatement validateStmt =
                    dbConnection.prepareStatement("SELECT * FROM pricegrab WHERE username = ?");
            validateStmt.setString(1, username);
            ResultSet resultSet = validateStmt.executeQuery();
            resultSet.next();

            String tablePass = resultSet.getString(2);
            return tablePass.equals(password);

        } catch (SQLException e) {
            return false;
        }
    }

    public void viewFavoriteList(String username) {
        try {
            Connection dbConnection = DBConnection.getInstance().getConnection();
            PreparedStatement viewFavListStmt =
                    dbConnection.prepareStatement("SELECT * FROM favorite_list WHERE username = ?");
            viewFavListStmt.setString(1, username);
            ResultSet rs = viewFavListStmt.executeQuery();
            if (!rs.isBeforeFirst()) { //Returns false if there are no rows in the ResultSet. Or if the cursor is not before the first record
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
            PreparedStatement insertStmt = dbConnection.prepareStatement(
                    "INSERT INTO favorite_list (seller_url, seller, currency, name, price, username) VALUES (?, ?, ?, ?, ?, ?);");
            for (ProductInfo productInfo : favoriteList) {
                insertStmt.setString(1, productInfo.sellerURL());
                insertStmt.setString(2, productInfo.seller());
                insertStmt.setString(3, productInfo.currency());
                insertStmt.setString(4, productInfo.name());
                insertStmt.setString(5, productInfo.price());
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
            try(PrintWriter printWriter = new PrintWriter(
                    "src/main/java/org/pricegrab/viewRegisteredUsersList.txt")) {
                while (rs.next()) {
                    //Display values
                    String row =
                            "\t" + count++ + "- Username: " + rs.getString("username") + " - password: "
                                    + rs.getString("password") + "\n";
                    printWriter.print(row);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUsers(String username) {
        try {
            Connection dbConnection = DBConnection.getInstance().getConnection();
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