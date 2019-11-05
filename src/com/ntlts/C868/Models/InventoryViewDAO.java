package com.ntlts.C868.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.ntlts.C868.Database.connection;

public class InventoryViewDAO {
    public InventoryView getInventoryView(int itemId, int departmentId) {
        ResultSet rs = null;
        InventoryView inventoryView = new InventoryView();
        String sql = "select inv.inventoryId inventoryid , inv.itemId itemid, i.categoryid catId, inv.departmentId departmentid, " +
                "inv.amount amount, i.itemName itemname, c.categoryName categoryname, d.departmentName departmentname " +
                "from inventories inv join items i on inv.itemId = i.itemId join categories c on i.categoryId = c.categoryId " +
                "join departments d on in.departmentId = d.departmentId where inv.itemId = ? and where inv.departmentId = ?;";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, itemId);
            state.setInt(2, departmentId);
            rs = state.executeQuery();
            rs.next();
            inventoryView.setItemId(rs.getInt("itemid"));
            inventoryView.setDepartmentId(rs.getInt("departmentid"));
            inventoryView.setAmount(rs.getInt("amount"));
            inventoryView.setItemName(rs.getString("itemname"));
            inventoryView.setCategoryName(rs.getString("categoryname"));
            inventoryView.setDepartmentName(rs.getString("departmentname"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return inventoryView;
    }

    public ObservableList<InventoryView> getInventoryView(int departmentId) {
        ResultSet rs = null;
        ObservableList<InventoryView> inventoryViewList = FXCollections.observableArrayList();
        String sql = "select inv.itemId itemid, i.categoryid catId, inv.departmentId departmentid, " +
                "inv.amount amount, i.itemName itemname, c.categoryName categoryname, d.departmentName departmentname " +
                "from inventories inv join items i on inv.itemId = i.itemId join categories c on i.categoryId = c.categoryId " +
                "join departments d on inv.departmentId = d.departmentId where inv.departmentId = ?;";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, departmentId);
            rs = state.executeQuery();
            while(rs.next()) {
                InventoryView inventoryView = new InventoryView();
                inventoryView.setItemId(rs.getInt("itemid"));
                inventoryView.setDepartmentId(rs.getInt("departmentid"));
                inventoryView.setAmount(rs.getInt("amount"));
                inventoryView.setItemName(rs.getString("itemname"));
                inventoryView.setCategoryName(rs.getString("categoryname"));
                inventoryView.setDepartmentName(rs.getString("departmentname"));
                inventoryViewList.add(inventoryView);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return inventoryViewList;
    }
    public ObservableList<InventoryView> getInventoryView(String itemName) {
        ResultSet rs = null;
        ObservableList<InventoryView> inventoryViewList = FXCollections.observableArrayList();
        String sql = "select inv.itemId itemid, i.categoryid catId, inv.departmentId departmentid, " +
                "inv.amount amount, i.itemName itemname, c.categoryName categoryname, d.departmentName departmentname " +
                "from inventories inv join items i on inv.itemId = i.itemId join categories c on i.categoryId = c.categoryId " +
                "join departments d on inv.departmentId = d.departmentId where i.itemName like ?;";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            String search = "%" + itemName + "%";
            state.setString(1, search);
            rs = state.executeQuery();
            while(rs.next()) {
                InventoryView inventoryView = new InventoryView();
                inventoryView.setItemId(rs.getInt("itemid"));
                inventoryView.setDepartmentId(rs.getInt("departmentid"));
                inventoryView.setAmount(rs.getInt("amount"));
                inventoryView.setItemName(rs.getString("itemname"));
                inventoryView.setCategoryName(rs.getString("categoryname"));
                inventoryView.setDepartmentName(rs.getString("departmentname"));
                inventoryViewList.add(inventoryView);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return inventoryViewList;
    }
    public ObservableList<InventoryView> getInventoryView() {
        ResultSet rs = null;
        ObservableList<InventoryView> inventoryViewList = FXCollections.observableArrayList();
        String sql = "select inv.itemId itemid, i.categoryid catId, inv.departmentId departmentid, " +
                "inv.amount amount, i.itemName itemname, c.categoryName categoryname, d.departmentName departmentname " +
                "from inventories inv join items i on inv.itemId = i.itemId join categories c on i.categoryId = c.categoryId " +
                "join departments d on inv.departmentId = d.departmentId;";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            rs = state.executeQuery();
            while(rs.next()) {
                InventoryView inventoryView = new InventoryView();
                inventoryView.setItemId(rs.getInt("itemid"));
                inventoryView.setDepartmentId(rs.getInt("departmentid"));
                inventoryView.setAmount(rs.getInt("amount"));
                inventoryView.setItemName(rs.getString("itemname"));
                inventoryView.setCategoryName(rs.getString("categoryname"));
                inventoryView.setDepartmentName(rs.getString("departmentname"));
                inventoryViewList.add(inventoryView);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return inventoryViewList;
    }
}
