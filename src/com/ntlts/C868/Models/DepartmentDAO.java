package com.ntlts.C868.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.ntlts.C868.Database.connection;

public class DepartmentDAO {
    public Department getDepartment(int departmentId) {
        Department department = new Department();
        ResultSet rs = null;
        String sql = "select * from departments where departmentId = ?;";

        try{
            PreparedStatement state = connection.prepareStatement(sql);
            rs = state.executeQuery();
            rs.next();
            department.setDepartmentId(rs.getInt("departmentId"));
            department.setDepartmentName(rs.getString("departmentName"));
            department.setLastUpdateId(rs.getInt("lastUpdateId"));
            LocalDateTime ldt = LocalDateTime.ofEpochSecond(rs.getInt("lastUpdated"), 0, ZoneOffset.UTC);
            department.setLastUpdated(ldt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return department;
    }

    public ObservableList<Department> getDepartment() {
        ResultSet rs = null;
        ObservableList<Department> departmentList = FXCollections.observableArrayList();
        String sql = "select * from departments;";
        try{
            PreparedStatement state = connection.prepareStatement(sql);
            rs = state.executeQuery();
            while(rs.next()){
                Department department = new Department();
                department.setDepartmentId(rs.getInt("departmentId"));
                department.setDepartmentName(rs.getString("departmentName"));
                department.setLastUpdateId(rs.getInt("lastUpdateId"));
                LocalDateTime ldt = LocalDateTime.ofEpochSecond(rs.getInt("lastUpdated"), 0, ZoneOffset.UTC);
                department.setLastUpdated(ldt);
                departmentList.add(department);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return departmentList;
    }

    public void addDepartment(Department department){
        String sql = "insert into departments (departmentName, lastUpdateId, lastUpdated) values (?,?,?);";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setString(1, department.getDepartmentName());
            state.setInt(2, department.getLastUpdateId());
            state.setLong(3, department.getLastUpdated().toEpochSecond(ZoneOffset.UTC));
            state.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateDepartment(Department department){
        String sql = "update departments set departmentName = ?, lastUpdateId = ?, " +
                "lastUpdated = ? where departmentId = ?;";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setString(1, department.getDepartmentName());
            state.setInt(2, department.getLastUpdateId());
            state.setLong(3, department.getLastUpdated().toEpochSecond(ZoneOffset.UTC));
            state.setInt(4, department.getDepartmentId());
            state.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteDepartment(Department department){
        String sql = "delete from departments where departmentId = ?;";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, department.getDepartmentId());
            state.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
