package com.ntlts.C868;

import java.sql.*;

public class Database {
    public static Connection connection;

    public void connectDatabase() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = null;
        try {
            /* create a database connection */
            connection = DriverManager.getConnection("jdbc:sqlite:C868.db");
            if(connection == null ) {
                System.out.println("No database");
            } else {
                System.out.println("Connected");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void disconnectDatabase() throws SQLException {
        try {
            if(connection != null)
                connection.close();
        } catch(SQLException e) {
            System.err.println(e);
        }
    }
}
