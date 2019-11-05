package com.ntlts.C868.Controllers;

import com.ntlts.C868.Models.Category;
import com.ntlts.C868.Models.CategoryDAO;
import com.ntlts.C868.Models.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CategoryManagementController implements Initializable {
    private User user;
    @FXML private TableView<Category> categoryTable;
    @FXML private TableColumn<Category, Integer> categoryIdColumn;
    @FXML private TableColumn<Category, String> categoryNameColumn;
    @FXML private TextField categoryNameText;
    @FXML private Label categoryId;
    @FXML private Label errorLabel;
    private int admin;
    private int userId;
    private int departmentId;
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId;}
    public void setUserId(int userId) {this.userId = userId;}
    public void setAdmin(int admin) {
        this.admin = admin;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryIdColumn.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        categoryTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Category>() {
            @Override
            public void changed(ObservableValue<? extends Category> observableValue, Category category, Category t1) {
                if(categoryTable.getSelectionModel().getSelectedItem() != null) {
                    categoryId.setText(Integer.toString(categoryTable.getSelectionModel().getSelectedItem().getCategoryId()));
                    categoryNameText.setText(categoryTable.getSelectionModel().getSelectedItem().getCategoryName());
                }
            }
        });
        setCategoryTable();

    }

    public void setUser(User user){
        this.user = user;
    }

    public void clickAddButton(ActionEvent event) {
        if(categoryNameText.getText() != null) {
            Category category = new Category();
            CategoryDAO categoryDAO = new CategoryDAO();
            category.setCategoryName(categoryNameText.getText());
            categoryDAO.addCategory(category);
            setCategoryTable();
            errorLabel.setText("The category has been added.");
        } else {
            errorLabel.setText("Please input name.");
        }
    }

    public void clickUpdateButton(ActionEvent event) {
        if(categoryTable.getSelectionModel().getSelectedItem() != null) {
            CategoryDAO categoryDAO = new CategoryDAO();
            Category category = new Category();
            category.setCategoryId(Integer.parseInt(categoryId.getText()));
            category.setCategoryName(categoryNameText.getText());
            categoryDAO.updateCategory(category);
            setCategoryTable();
            errorLabel.setText("The category has been updated.");
        } else {
            errorLabel.setText("Please select a category.");
        }
    }

    public void clickDeleteButton(ActionEvent event) {
        if(categoryTable.getSelectionModel().getSelectedItem() != null) {
            CategoryDAO categoryDAO = new CategoryDAO();
            categoryDAO.deleteCategory(categoryTable.getSelectionModel().getSelectedItem());
            setCategoryTable();
            errorLabel.setText("The category has been deleted.");
        } else {
            errorLabel.setText("Please select a category.");
        }
    }

    public void clickBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ntlts/C868/Views/modeselect.fxml"));
        Parent root = loader.load();
        ModeSelectController modeCTL = loader.getController();
        modeCTL.setAdmin(admin);
        modeCTL.setUserId(userId);
        modeCTL.setDepartmentId(departmentId);
        modeCTL.setUserManagement(admin);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Mode Select");
        stage.setScene(scene);
        stage.show();

        ((Stage) ((Node) (event.getSource())).getScene().getWindow()).close();
    }
    private void setCategoryTable(){
        CategoryDAO categoryDAO = new CategoryDAO();
        categoryTable.setItems(categoryDAO.getCategory());
    }
}
