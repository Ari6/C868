package com.ntlts.C868.Controllers;

import com.ntlts.C868.Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModeSelectController implements Initializable {
    @FXML private Button userManagementButton;
    private User user;
    private int admin;
    private int userId;
    private int departmentId;
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId;}

    public void setUserId(int userId) {this.userId = userId;}
    public void setAdmin(int admin) {
        this.admin = admin;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setUserManagement(int admin) {
        if(admin != 0) {
            userManagementButton.setDisable(false);
        }
    }
    public void clickInventoryManagement(ActionEvent event) throws IOException {
        System.out.println(admin);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/inventorymanagement.fxml"));
        Parent root = loader.load();
        InventoryManagementController inventoryCTL = loader.getController();
        inventoryCTL.setAdmin(admin);
        inventoryCTL.setUserId(userId);
        inventoryCTL.setDepartmentId(departmentId);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();

        ((Stage) ((Node) (event.getSource())).getScene().getWindow()).close();
    }

    public void clickCategoryManagementButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/categorymanagement.fxml"));
        Parent root = loader.load();
        CategoryManagementController categoryCTL = loader.getController();
        categoryCTL.setAdmin(admin);
        categoryCTL.setUserId(userId);
        categoryCTL.setDepartmentId(departmentId);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Category Management");
        stage.setScene(scene);
        stage.show();

        ((Stage) ((Node) (event.getSource())).getScene().getWindow()).close();
    }

    public void clickItemManagementButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/itemmanagement.fxml"));
        Parent root = loader.load();
        ItemManagementController itemCTL = loader.getController();
        itemCTL.setAdmin(admin);
        itemCTL.setUserId(userId);
        itemCTL.setDepartmentId(departmentId);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Item Management");
        stage.setScene(scene);
        stage.show();

        ((Stage) ((Node) (event.getSource())).getScene().getWindow()).close();
    }

    public void clickLogoutButton(ActionEvent event) throws IOException {
        this.user = null;
        Parent root = FXMLLoader.load(getClass().getResource("../Views/login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Inventory System Login");
        stage.setScene(scene);
        stage.show();

        ((Stage) ((Node) (event.getSource())).getScene().getWindow()).close();
    }

    public void clickDepartmentManagementButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/departmentmanagement.fxml"));
        Parent root = loader.load();
        DepartmentmanagementController itemCTL = loader.getController();
        itemCTL.setAdmin(admin);
        itemCTL.setUserId(userId);
        itemCTL.setDepartmentId(departmentId);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Department Management");
        stage.setScene(scene);
        stage.show();

        ((Stage) ((Node) (event.getSource())).getScene().getWindow()).close();
    }

    public void clickUserManagementButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/usermanagement.fxml"));
        Parent root = loader.load();
        UserManagementController userCTL = loader.getController();
        userCTL.setAdmin(admin);
        userCTL.setUserId(userId);
        userCTL.setDepartmentId(departmentId);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("User Management");
        stage.setScene(scene);
        stage.show();

        ((Stage) ((Node) (event.getSource())).getScene().getWindow()).close();
    }
}
