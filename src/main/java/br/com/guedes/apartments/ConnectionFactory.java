package br.com.guedes.apartments;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public Connection getConnection() {
        try {
            String jdbcUrl = "jdbc:postgresql://localhost/desenv";
            String username = "postgres";
            String password = "123";

            return DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error establishing connection to the database.", e);
        }
    }
}