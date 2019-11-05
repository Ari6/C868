package com.ntlts.C868.Controllers;

import com.ntlts.C868.Models.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class UserManagementController implements Initializable {
    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, Integer> userIdColumn;
    @FXML private TableColumn<User, String> userNameColumn;
    @FXML private TableColumn<User, Integer> departmentColumn;
    @FXML private TableColumn<User, Integer> adminColumn;
    @FXML private TableColumn<User, LocalDateTime> lastUpdatedColumn;
    @FXML private Label userIdLabel;
    @FXML private TextField userNameText;
    @FXML private PasswordField password1;
    @FXML private PasswordField password2;
    @FXML private ChoiceBox<String> departmentChoice;
    @FXML private ChoiceBox<Integer> adminChoice;
    @FXML private Label errorLabel;
    private ObservableList<Department> departmentList;
    private int admin;
    private int userId;
    private int departmentId;
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId;}
    public void setUserId(int userId) {this.userId = userId;}
    public void setAdmin(int admin) {
        this.admin = admin;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("departmentId"));
        adminColumn.setCellValueFactory(new PropertyValueFactory<>("admin"));
        lastUpdatedColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));

        userTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observableValue, User inventoryView, User t1) {
                if(userTable.getSelectionModel().getSelectedItem() != null) {
                    userIdLabel.setText(Integer.toString(userTable.getSelectionModel().getSelectedItem().getUserId()));
                    userNameText.setText(userTable.getSelectionModel().getSelectedItem().getUsername());
                    //Admin choicebox selected department set
                    adminChoice.getSelectionModel().select(userTable.getSelectionModel().getSelectedItem().getAdmin());
                    //Department choicebox selected item set
                    int selectItem = 0;
                    for(Department d : departmentList) {
                        if(userTable.getSelectionModel().getSelectedItem().getDepartmentId() == d.getDepartmentId()) {
                            departmentChoice.getSelectionModel().select(selectItem);
                        }
                        selectItem += 1;
                    }
                }
            }
        });
        set();
    }

    private int inputCheck() {
        //Input check: username, passwords fields
        if(userNameText.getText().equals(" ") || password1.getText().equals(" ") || password2.getText().equals(" ")) {
            errorLabel.setText("username and password fields are required.");
            return 1;
        }
        //Check passwords match
        if(!password1.getText().equals(password2.getText())){
            errorLabel.setText("password fields have to be matched.");
            return 2;
        }
        //Check if space is used in password
        if(password1.getText().contains(" ")) {
            errorLabel.setText("Space is not allowed in password.");
            return 3;
        }
        //Check if numeric and alphabet are used in username
        if(!userNameText.getText().matches("[0-9a-zA-Z]+")) {
            errorLabel.setText("Please remove any space or special character from username.");
            return 4;
        }
        //username must be 4 to 8 characters.
        if(!userNameText.getText().matches("[0-9a-zA-Z]{4,8}")) {
            errorLabel.setText("Username must be between 4 to 8 characters.");
            return 5;
        }
        //password must be 4 to 10 characters.
        if(password1.getText().length() < 4 || password1.getText().length() > 10) {
            errorLabel.setText("Password must be between 4 to 10 characters.");
            return 6;
        }
        //department must be selected
        if(departmentChoice.getSelectionModel().getSelectedItem() == null) {
            errorLabel.setText("Department must be selected.");
            return 7;
        }
        //admin must be selected
        if(adminChoice.getSelectionModel().getSelectedItem() == null) {
            errorLabel.setText("Admin must be selected.");
            return 8;
        }
        return 0;
    }

    public void clickAddButton(ActionEvent event) {
        int ret = inputCheck();
        if(ret == 0) {
            User user = new User();
            UserDAO userDAO = new UserDAO();
            if(userDAO.getUser(userNameText.getText()) == null) {
                user.setUsername(userNameText.getText());
                user.setPassword(PasswordHash.getHashedPassword(password1.getText(), Salt.SALT));
                //user.setPassword(password1.getText());
                user.setDepartmentId(
                        departmentList.get(departmentChoice.getSelectionModel().getSelectedIndex()).getDepartmentId());
                user.setAdmin(adminChoice.getSelectionModel().getSelectedItem());
                user.setLastUpdateId(userId);
                user.setLastUpdated(LocalDateTime.now());
                userDAO.addUser(user);
                errorLabel.setText("The user has been added.");
            } else {
                errorLabel.setText("The username already exists.");
                return;
            }
        }
        set();
    }

    public void clickUpdateButton(ActionEvent event) {
        int ret = inputCheck();
        if (userTable.getSelectionModel().getSelectedItem() != null) {
            if (ret == 0) {
                User user = new User();
                UserDAO userDAO = new UserDAO();
                if (userDAO.getUser(userTable.getSelectionModel().getSelectedItem().getUserId()) != null) {
                    user.setUserId(userTable.getSelectionModel().getSelectedItem().getUserId());
                    user.setUsername(userNameText.getText());
                    user.setPassword(PasswordHash.getHashedPassword(password1.getText(), Salt.SALT));
                    //user.setPassword(password1.getText());
                    user.setDepartmentId(
                            departmentList.get(departmentChoice.getSelectionModel().getSelectedIndex()).getDepartmentId());
                    user.setAdmin(adminChoice.getSelectionModel().getSelectedItem());
                    user.setLastUpdateId(userId);
                    user.setLastUpdated(LocalDateTime.now());
                    userDAO.updateUser(user);
                    errorLabel.setText("The user has been updated.");
                    clear();
                    set();
                } else {
                    errorLabel.setText("The username does not exist.");
                    return;
                }
            }
        } else {
            errorLabel.setText("Please select a user.");
            return;
        }
    }

    public void clickDeleteButton(ActionEvent event) {
        if (userTable.getSelectionModel().getSelectedItem() != null) {
            User user = new User();
            user.setUserId(userTable.getSelectionModel().getSelectedItem().getUserId());
            UserDAO userDAO = new UserDAO();
            userDAO.deleteUser(user);
            errorLabel.setText("The user has been deleted.");
            set();
        } else {
            errorLabel.setText("Please select a user.");
            return;
        }
    }

    public void clickBackButton(ActionEvent event) throws IOException {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/modeselect.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ntlts/C868/Views/modeselect.fxml"));
        Parent root = loader.load();
        ModeSelectController modeController = loader.getController();
        modeController.setUserId(userId);
        modeController.setDepartmentId(departmentId);
        modeController.setAdmin(admin);
        modeController.setUserManagement(admin);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Mode Select");
        stage.setScene(scene);
        stage.show();

        ((Stage) ((Node) (event.getSource())).getScene().getWindow()).close();
    }

    private void set() {
        setTable();
        setDepartment();
        setAdmin();
    }

    private void setTable() {
        UserDAO userDAO = new UserDAO();
        ObservableList<User> userList = userDAO.getUser();
        userTable.setItems(userList);
    }

    private void setDepartment() {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        departmentList = departmentDAO.getDepartment();
        ObservableList<String> departmentStringList = FXCollections.observableArrayList();
        for(Department d : departmentList) {
            departmentStringList.add(d.getDepartmentId() + " : " + d.getDepartmentName());
        }
        departmentChoice.setItems(departmentStringList);
    }

    private void setAdmin() {
        ObservableList<Integer> adminList = FXCollections.observableArrayList();
        adminList.add(0);
        adminList.add(1);
        adminChoice.setItems(adminList);
    }

    private void clear() {
        userIdLabel.setText("");
        userNameText.setText("");
        password1.setText("");
        password2.setText("");
    }
}
