package org.pricegrab;

import org.postgresql.util.PSQLException;
import org.pricegrab.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class UserManagement { //might wanna cancel this class and just make it for Admin
    //to manage registered users (if we implemented Auth feature)..
    //so the DB would be for users info & authentication purposes.
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
            PreparedStatement validatestmt = dbConnection.prepareStatement(
                    "SELECT * FROM pricegrab WHERE username = ?");
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
        try {
            Connection dbConnection = DBConnection.getInstance().getConnection();
            Statement stmt = dbConnection.createStatement();
            String query = "SELECT * FROM pricegrab WHERE username = ?";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //Display values
                String row = "ID: " + rs.getInt("id") + " - Product Name: " + rs.getString("name")
                        + " - Price: " + rs.getDouble("price") + " - Stores: " + rs.getString(
                        "stores") + "\n";
                System.out.print(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateProduct(int id, String updateProductName, double price, String[] stores) {
        try {
            Connection dbConnection = DBConnection.getInstance().getConnection();
            Statement stmt = dbConnection.createStatement();
            PreparedStatement updateStmt = dbConnection.prepareStatement(
                    "UPDATE pricegrab SET name = ?, price = ?, stores = ? WHERE id = ?");
            updateStmt.setString(1, updateProductName);
            updateStmt.setDouble(2, price);
            Array anArray =
                    dbConnection.createArrayOf("text", stores); //create array objects of stores
            updateStmt.setArray(3, anArray);
            updateStmt.setInt(4, id);
            int row = updateStmt.executeUpdate();
            System.out.println("Rows updated: " + row);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        try {
            Connection dbConnection = DBConnection.getInstance().getConnection();
            Statement stmt = dbConnection.createStatement();
            PreparedStatement deleteStmt =
                    dbConnection.prepareStatement("DELETE FROM pricegrab WHERE id = ?");
            deleteStmt.setInt(1, id);
            int row = deleteStmt.executeUpdate();
            System.out.println("Rows updated: " + row);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void retrieveProducts() {
        try {
            Connection dbConnection = DBConnection.getInstance().getConnection();
            Statement stmt = dbConnection.createStatement();
            String query = "SELECT * FROM pricegrab";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //Display values
                String row = "ID: " + rs.getInt("id") + " - Product Name: " + rs.getString("name")
                        + " - Price: " + rs.getDouble("price") + " - Stores: " + rs.getString(
                        "stores") + "\n";
                System.out.print(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertFavoriteToDB(String username, ArrayList<ProductInfo> favoriteList) {
        try {
            Connection dbConnection = DBConnection.getInstance().getConnection();
            Statement stmt = dbConnection.createStatement();
            for(int i = 0; i < favoriteList.size(); i++) {
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
}
