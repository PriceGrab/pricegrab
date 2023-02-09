package org.pricegrab;

import org.pricegrab.utils.DBConnection;

import java.sql.*;

public class AdminManagement { //might wanna cancel this class and just make it for Admin
    //to manage registered users (if we implemented Auth feature)..
    //so the DB would be for users info & authentication purposes.
    private String name;
    private double price;
    private Array stores;

    public AdminManagement() {
    }

    public AdminManagement(String name, double price, Array stores) {
        this.name = name;
        this.price = price;
        this.stores = stores;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double isPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStores(Array stores) {
        this.stores = stores;
    }

    public Array getStores() {
        return stores;
    }

    // https://docs.oracle.com/javase/tutorial/jdbc/basics/array.html helpful resource for multi-values columns
    public void insertProduct() { //replacing insertTask
        try {
            Connection dbConnection = DBConnection.getInstance().getConnection();
            Statement stmt = dbConnection.createStatement();
            PreparedStatement insertStmt = dbConnection.prepareStatement(
                    "INSERT INTO pricegrab (name, price, stores) VALUES (?, ?, ?);");
            insertStmt.setString(1, this.name);
            insertStmt.setDouble(2, this.price);
            insertStmt.setArray(3, this.stores);
            int rows = insertStmt.executeUpdate();
            System.out.println("Rows affected: " + rows);
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
}
