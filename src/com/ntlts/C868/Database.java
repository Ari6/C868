package com.ntlts.C868;

import com.ntlts.C868.Models.PasswordHash;
import com.ntlts.C868.Models.Salt;

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
        try {
            Statement state = connection.createStatement();
            state.executeUpdate("CREATE TABLE if not exists users ( " +
                    "id integer primary key, " +
                    "username text(10) not null unique, " +
                    "password text not null, " +
                    "departmentId number, " +
                    "admin number, " +
                    "lastUpdateId number, " +
                    "lastUpdated integer, " +
                    "constraint fk_department foreign key(departmentId) references department(departmentId) on delete cascade);");
            if(state.executeQuery("select count(*) from users;").getInt(1) == 0) {
                PreparedStatement preState = connection.prepareStatement("insert into users (" +
                        "id, userName, password, departmentId, admin, lastUpdateId, lastUpdated) " +
                        "values (1, 'admin', ?, 0, 1, 0, datetime('now'));");
                preState.setString(1, PasswordHash.getHashedPassword("admin", Salt.SALT));
                preState.executeUpdate();
            }
            state.executeUpdate("CREATE TABLE IF NOT EXISTS departments (" +
                    "departmentId integer primary key, " +
                    "departmentName text, " +
                    "lastUpdateId number, " +
                    "lastUpdated integer);");
            state.executeUpdate("create table IF NOT EXISTS categories (" +
                    "categoryId integer primary key, " +
                    "categoryName text " +
                    ");");
            state.executeUpdate("create table IF NOT EXISTS items (" +
                    "itemId integer primary key, " +
                    "itemName text, " +
                    "categoryId number, " +
                    "constraint fk_category foreign key(categoryId) references categories(categoryId) on delete cascade" +
                    ");");
            state.executeUpdate("create table IF NOT EXISTS inventories (" +
                    "departmentId number, " +
                    "itemId number, " +
                    "amount number, " +
                    "expire integer, " +
                    "lastPurchase integer, " +
                    "primary key (departmentId, itemId), " +
                    "constraint fk_item foreign key(itemId) references items(itemId) on delete cascade, " +
                    "constraint fk_inv_dept foreign key(departmentId) references departments(departmentId) on delete cascade" +
                    ");");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
