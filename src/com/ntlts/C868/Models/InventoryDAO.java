package com.ntlts.C868.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static com.ntlts.C868.Database.connection;

public class InventoryDAO {
    public ObservableList<Inventory> getInventory(int departmentId) {
        String sql = "select * from inventories where departmentId = ?;";
        ResultSet rs = null;
        ObservableList<Inventory> inventoryList = FXCollections.observableArrayList();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, departmentId);
            rs = state.executeQuery();
            while (rs.next()) {
                Inventory inventory = new Inventory();
                inventory.setDepartmentId(rs.getInt("departmentId"));
                inventory.setItemId(rs.getInt("itemId"));
                inventory.setAmount(rs.getInt("amount"));
                //LocalDate ldt = LocalDate.ofEpochDay(rs.getLong("expire"));
                //inventory.setExpire(ldt);
                //ldt = LocalDate.ofEpochDay(rs.getLong("lastPurchase"));
                //inventory.setLastPurchase(ldt);
                inventoryList.add(inventory);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return inventoryList;
    }
    public Inventory getInventory(int itemId, int departmentId) {
        Inventory inventory = new Inventory();
        String sql = "select * from inventories where itemId = ? and departmentId = ?;";
        ResultSet rs = null;
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, itemId);
            state.setInt(2, departmentId);
            rs = state.executeQuery();
            rs.next();
            inventory.setDepartmentId(rs.getInt("departmentId"));
            inventory.setItemId(rs.getInt("itemId"));
            inventory.setAmount(rs.getInt("amount"));
            //LocalDate ldt = LocalDate.ofEpochDay(rs.getLong("expire"));
            //inventory.setExpire(ldt);
            //ldt = LocalDate.ofEpochDay(rs.getLong("lastPurchase"));
            //inventory.setLastPurchase(ldt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return inventory;
    }
    public int countInventory(int itemId, int departmentId) {
        int i = 0;
        String sql = "select count(*) numberrows from inventories where itemId = ? and departmentId = ?;";
        ResultSet rs = null;
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, itemId);
            state.setInt(2, departmentId);
            rs = state.executeQuery();
            rs.next();
            i = rs.getInt("numberrows");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return i;
    }

    public ObservableList<Inventory> getInventory() {
        Inventory inventory = new Inventory();
        String sql = "select * from inventories;";
        ResultSet rs = null;
        ObservableList<Inventory> inventoryList = FXCollections.observableArrayList();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            rs = state.executeQuery();
            while (rs.next()) {
                inventory.setDepartmentId(rs.getInt("departmentId"));
                inventory.setItemId(rs.getInt("itemId"));
                inventory.setAmount(rs.getInt("amount"));
                //LocalDate ldt = LocalDate.ofEpochDay(rs.getLong("expire"));
                //inventory.setExpire(ldt);
                //ldt = LocalDate.ofEpochDay(rs.getLong("lastPurchase"));
                //inventory.setLastPurchase(ldt);
                //inventoryList.add(inventory);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return inventoryList;
    }

    public void addInventory(Inventory inventory) {
        String sql = "insert into inventories (departmentId, itemId, amount) values (?, ?, ?);";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, inventory.getDepartmentId());
            state.setInt(2, inventory.getItemId());
            state.setInt(3, inventory.getAmount());
            state.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateInventory(Inventory inventory) {
        String sql = "update inventories set amount = ? " +
                "where departmentId = ? and itemId = ?;";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, inventory.getAmount());
            state.setInt(2, inventory.getDepartmentId());
            state.setInt(3, inventory.getItemId());
            state.executeUpdate();
        } catch (SQLException e) {

        }

    }
    public void deleteInventory(Inventory inventory) {
        String sql = "delete from inventories where departmentId = ? and itemId = ?;";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, inventory.getDepartmentId());
            state.setInt(2, inventory.getItemId());
            state.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
