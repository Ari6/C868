package com.ntlts.C868.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.ntlts.C868.Database.connection;

public class CategoryDAO {
    public Category getCategory(int categoryId) {
        String sql = "select * from categories where categoryId = ?;";
        ResultSet rs = null;
        Category category = new Category();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, categoryId);
            rs = state.executeQuery();
            rs.next();
            category.setCategoryId(rs.getInt("categoryId"));
            category.setCategoryName(rs.getString("categoryName"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return category;
    }

    public ObservableList<Category> getCategory(){
        String sql = "select * from categories;";
        ResultSet rs = null;
        ObservableList<Category> categoryList = FXCollections.observableArrayList();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            rs = state.executeQuery();
            while(rs.next()){
                Category category = new Category();
                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                categoryList.add(category);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categoryList;
    }

    public void addCategory(Category category) {
        String sql = "insert into categories (categoryName) values(?);";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setString(1, category.getCategoryName());
            state.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateCategory(Category category) {
        String sql = "update categories set categoryName = ? where categoryId = ?;";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setString(1, category.getCategoryName());
            state.setInt(2, category.getCategoryId());
            state.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteCategory(Category category) {
        String sql = "delete from categories where categoryId = ?;";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, category.getCategoryId());
            state.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
