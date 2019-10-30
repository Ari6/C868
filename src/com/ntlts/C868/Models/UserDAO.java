package com.ntlts.C868.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.ntlts.C868.Database.connection;

public class UserDAO {
    public User getUser(String username) {
        ResultSet rs = null;
        String sql = "select * from users where username = ?;";
        User user = new User();

        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setString(1, username);
            rs = state.executeQuery();
            if(rs.next()) {
                user.setUserId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword((rs.getString("password")));
                user.setDepartmentId(rs.getInt("departmentId"));
                user.setAdmin(rs.getInt("admin"));
                user.setLastUpdateId(rs.getInt("lastUpdateId"));
                LocalDateTime ldt = LocalDateTime.ofEpochSecond(rs.getInt("lastUpdated"),0, ZoneOffset.UTC);
                user.setLastUpdated(ldt);
            } else {
                user = null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
    public User getUser(int userId) {
        ResultSet rs = null;
        String sql = "select * from users where id = ?;";
        User user = new User();

        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, userId);
            rs = state.executeQuery();
            if(rs.next()) {
                user.setUserId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword((rs.getString("password")));
                user.setDepartmentId(rs.getInt("departmentId"));
                user.setAdmin(rs.getInt("admin"));
                user.setLastUpdateId(rs.getInt("lastUpdateId"));
                LocalDateTime ldt = LocalDateTime.ofEpochSecond(rs.getInt("lastUpdated"),0, ZoneOffset.UTC);
                user.setLastUpdated(ldt);
            } else {
                user = null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
    public ObservableList<User> getUser() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        ResultSet rs = null;
        String sql = "select * from users;";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            rs = state.executeQuery();
            while(rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword((rs.getString("password")));
                user.setDepartmentId(rs.getInt("departmentId"));
                user.setAdmin(rs.getInt("admin"));
                user.setLastUpdateId(rs.getInt("lastUpdateId"));
                LocalDateTime ldt = LocalDateTime.ofEpochSecond(rs.getInt("lastUpdated"),0, ZoneOffset.UTC);
                user.setLastUpdated(ldt);
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }
    public void addUser(User user) {
        String sql = "insert into users (username, password, departmentId, admin, lastUpdateId, lastUpdated) " +
                "values (?,?,?,?,?,?);";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setString(1, user.getUsername());
            state.setString(2, user.getPassword());
            state.setInt(3, user.getDepartmentId());
            state.setInt(4, user.getAdmin());
            state.setInt(5, user.getLastUpdateId());
            state.setLong(6, user.getLastUpdated().toEpochSecond(ZoneOffset.UTC));
            state.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void updateUser(User user) {
        String sql = "update users set username = ?, password = ?, departmentId = ?, admin = ?, lastUpdateId = ?, " +
                "lastUpdated = ? where id = ?;";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setString(1, user.getUsername());
            state.setString(2, user.getPassword());
            state.setInt(3, user.getDepartmentId());
            state.setInt(4, user.getAdmin());
            state.setInt(5, user.getLastUpdateId());
            state.setLong(6, user.getLastUpdated().toEpochSecond(ZoneOffset.UTC));
            state.setInt(7, user.getUserId());
            state.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteUser(User user) {
        String sql = "delete from users where id = ?;";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, user.getUserId());
            state.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
