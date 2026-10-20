
package com.shopsphere.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    private static final String URL =
        "jdbc:mysql://localhost:3306/shopsphere"
        + "?allowPublicKeyRetrieval=true&useSSL=false";

    private static final String USER = "root";
    private static final String PASSWORD = "YOUR_DB_PASSWORD"; // Replace with your actual MySQL password

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver not found", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
