package org.pricegrab.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private final Connection connection;

    private static DBConnection instance;

    private DBConnection() throws SQLException {
        String dbName = "pricegrab";
        int port = 5432;
        String url = "jdbc:postgresql://localhost:" + port + "/" + dbName;
        Properties props = new Properties();
        props.setProperty("user", System.getenv("DB_USER"));
        props.setProperty("password", System.getenv("DB_PASS"));
        props.setProperty("ssl", "false");
        this.connection = DriverManager.getConnection(url, props);
    }

    public Connection getConnection() {
        return this.connection;
    }

    public static DBConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }
}