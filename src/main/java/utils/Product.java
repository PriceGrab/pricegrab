package utils;

import java.sql.*;

public class Product {
    private String name;
    private double price;
    private Array stores;

    public Product(String name, double price, Array stores) {
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


    public void insertProduct() { //replacing insertTask
        try {
            Connection dbConnection = DBConnection.getInstance().getConnection();
            Statement stmt = dbConnection.createStatement();
            PreparedStatement insertStmt =
                    dbConnection.prepareStatement("INSERT INTO pricegrab (name, price, stores) VALUES (?, ?, ?);");
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
                String row =
                        "ID: " + rs.getInt("id") + " Product Name: " + rs.getString(
                                "name") + ", Price: " + rs.getDouble("price") + "  Stores: " + rs.getString("stores") + "\n";
                System.out.print(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateTask(int id, String updateProductName, double price, String[] stores) {
        try {
            Connection dbConnection = DBConnection.getInstance().getConnection();
            Statement stmt = dbConnection.createStatement();
            PreparedStatement updateStmt =
                    dbConnection.prepareStatement("UPDATE pricegrab SET name = ?, price = ?, stores = ? WHERE id = ?");
            updateStmt.setString(1, updateProductName);
            updateStmt.setDouble(2, price);
            Array anArray = dbConnection.createArrayOf("text", stores); //create array objects of stores
            updateStmt.setArray(3, anArray);
            updateStmt.setInt(4, id);
            int row = updateStmt.executeUpdate();
            System.out.println("Rows updated: " + row);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int id) {
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
