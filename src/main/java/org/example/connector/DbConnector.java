package org.example.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {

    public Connection createConnection(String address, String userName, String password) {
        String url = String.format("jdbc:postgresql://%s", address);
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC driver not found: " + e.getMessage());
            return null;
        }
    }
}