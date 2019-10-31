package com.ntlts.C868.Controllers;

import com.ntlts.C868.Database;
import com.ntlts.C868.Models.PasswordHash;
import com.ntlts.C868.Models.Salt;
import com.ntlts.C868.Models.User;
import com.ntlts.C868.Models.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    @FXML private TextField usernameText;
    @FXML private PasswordField passwordText;
    @FXML private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Database db = new Database();
        try {
            db.connectDatabase();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickLoginButton(ActionEvent event) throws SQLException, IOException {
        UserDAO userDao = new UserDAO();
        User user;
        if((usernameText.getText() != null && !usernameText.equals("")) &&
           (passwordText.getText() != null) && !passwordText.getText().equals("")) {
            user = userDao.getUser(usernameText.getText());
            //User exists
            if (user != null) {
                //Username and password matches
                //if (user.getPassword().equals(passwordText.getText())) {
                if(user.getPassword().equals(PasswordHash.getHashedPassword(passwordText.getText(), Salt.SALT))){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/modeselect.fxml"));
                    Parent root = loader.load();
                    ModeSelectController modeselectCTL = loader.getController();
                    modeselectCTL.setUser(user);
                    modeselectCTL.setAdmin(user.getAdmin());
                    modeselectCTL.setUserManagement(user.getAdmin());
                    modeselectCTL.setUserId(user.getUserId());
                    modeselectCTL.setDepartmentId(user.getDepartmentId());
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Mode Select");
                    stage.setScene(scene);
                    stage.show();

                    ((Stage) ((Node) (event.getSource())).getScene().getWindow()).close();
                } else {
                    errorLabel.setText("Username and password combination is not matched.");
                }
            } else {
                errorLabel.setText("Username and password combination is not matched.");
            }
        } else {
            errorLabel.setText("Username and password are required");
        }
    }
}
