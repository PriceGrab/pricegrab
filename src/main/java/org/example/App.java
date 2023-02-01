package org.example;

import utils.DBConnection;
import utils.Product;

import java.sql.Array;
import java.sql.SQLException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        try {
            DBConnection db = DBConnection.getInstance();


            String[] stores = {"acb", "xyz"};
            Array anArray = db.getConnection().createArrayOf("text", stores);


            Product product = new Product("Rice", 35.95, anArray);

            product.updateTask(1, "test", 29.20, new String[] {"asd","ASd"});
            // Retrieve all tasks
            product.retrieveProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
