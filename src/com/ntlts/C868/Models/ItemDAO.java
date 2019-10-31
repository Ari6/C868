package com.ntlts.C868.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.ntlts.C868.Database.connection;

public class ItemDAO {
    public Item getItem(int itemId) {
        Item item = new Item();
        ResultSet rs = null;
        String sql = "select * from items where itemId = ?;";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, itemId);
            rs = state.executeQuery();
            rs.next();
            item.setItemId(rs.getInt("itemId"));
            item.setItemName(rs.getString("itemName"));
            item.setCateogryId(rs.getInt("categoryId"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return item;
    }

    public ObservableList<Item> getItem() {
        ResultSet rs = null;
        ObservableList<Item> itemList = FXCollections.observableArrayList();
        String sql = "select * from items;";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            rs = state.executeQuery();
            while(rs.next()){
                Item item = new Item();
                item.setItemId(rs.getInt("itemId"));
                item.setItemName(rs.getString("itemName"));
                item.setCateogryId(rs.getInt("categoryId"));
                itemList.add(item);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return itemList;
    }

    public void addItem(Item item) {
        String sql = "insert into items (itemName, categoryId) values (?, ?);";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setString(1, item.getItemName());
            state.setInt(2, item.getCateogryId());
            state.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateItem(Item item) {
        String sql = "update items set itemName = ?, categoryId = ? where itemId = ?;";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setString(1, item.getItemName());
            state.setInt(2, item.getCateogryId());
            state.setInt(3, item.getItemId());
            state.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteItem(Item item) {
        String sql = "delete from items where itemId = ?;";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, item.getItemId());
            state.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
