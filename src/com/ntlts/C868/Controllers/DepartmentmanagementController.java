package com.ntlts.C868.Controllers;

import com.ntlts.C868.Models.Department;
import com.ntlts.C868.Models.DepartmentDAO;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class DepartmentmanagementController implements Initializable {
    @FXML private TableView<Department> departmentTable;
    @FXML private TableColumn<Department, Integer> departmentIdColumn;
    @FXML private TableColumn<Department, String> departmentNameColumn;
    @FXML private TextField departmentNameText;
    @FXML private Label departmentIdLabel;
    @FXML private Label errorLabel;
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
    public void initialize(URL url, ResourceBundle rb) {
        departmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("departmentId"));
        departmentNameColumn.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        departmentTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Department>() {
            @Override
            public void changed(ObservableValue<? extends Department> observableValue, Department department, Department t1) {
                if(departmentTable.getSelectionModel().getSelectedItem() != null) {
                    departmentIdLabel.setText(Integer.toString(departmentTable.getSelectionModel().getSelectedItem().getDepartmentId()));
                    departmentNameText.setText(departmentTable.getSelectionModel().getSelectedItem().getDepartmentName());
                }
            }
        });
        setDepartmentTable();
    }
    public void clickAddButton(ActionEvent event) {
        if(departmentNameText.getText() != null) {
            Department department = new Department();
            DepartmentDAO departmentDAO = new DepartmentDAO();
            department.setDepartmentName(departmentNameText.getText());
            department.setLastUpdateId(userId);
            department.setLastUpdated(LocalDateTime.now());
            departmentDAO.addDepartment(department);
            setDepartmentTable();
            errorLabel.setText("The department has been added.");
        } else {
            errorLabel.setText("Please input name.");
        }
    }

    public void clickUpdateButton(ActionEvent event) {
        if(departmentTable.getSelectionModel().getSelectedItem() != null ) {
            Department department = new Department();
            DepartmentDAO departmentDAO = new DepartmentDAO();
            department.setDepartmentId(departmentTable.getSelectionModel().getSelectedItem().getDepartmentId());
            department.setDepartmentName(departmentNameText.getText());
            department.setLastUpdateId(user.getUserId());
            department.setLastUpdated(LocalDateTime.now());
            departmentDAO.updateDepartment(department);
            setDepartmentTable();
            errorLabel.setText("The department has been updated.");
        } else {
            errorLabel.setText("Please select one of departments.");
        }
    }

    public void clickDeleteButton(ActionEvent event) {
        if(departmentTable.getSelectionModel().getSelectedItem() != null ) {
            Department department = new Department();
            DepartmentDAO departmentDAO = new DepartmentDAO();
            department.setDepartmentId(departmentTable.getSelectionModel().getSelectedItem().getDepartmentId());
            departmentDAO.deleteDepartment(department);
            setDepartmentTable();
            errorLabel.setText("The department has been deleted.");
        } else {
            errorLabel.setText("Please select one of departments.");
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

    private void setDepartmentTable(){
        DepartmentDAO departmentDAO = new DepartmentDAO();
        departmentTable.setItems(departmentDAO.getDepartment());
    }
}
