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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemManagementController implements Initializable {
    private User user;
    @FXML private TableView<ItemCategory> itemTable;
    @FXML private TableColumn<ItemCategory, Integer> itemIdColumn;
    @FXML private TableColumn<ItemCategory, String> itemNameColumn;
    @FXML private TableColumn<ItemCategory, String> categoryColumn;
    @FXML private ChoiceBox<String> categoryCombo;
    @FXML private TextField itemNameText;
    @FXML private Label errorLabel;
    private int admin;
    private int userId;
    private int departmentId;
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId;}
    public void setUserId(int userId) {this.userId = userId;}
    public void setAdmin(int admin) {
        this.admin = admin;
    }
    public void setUser(User user){
        this.user = user;
    }
    private ObservableList<Category> categoryList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));

        CategoryDAO categoryDAO = new CategoryDAO();
        categoryList = categoryDAO.getCategory();

        //ComboBox set up
        ObservableList<String> categoryStringList = FXCollections.observableArrayList();
        for(Category c : categoryList){
            categoryStringList.add(c.getCategoryId() + " : " + c.getCategoryName());
        }
        categoryCombo.setItems(categoryStringList);

        //This makes selected line to show input and select choicebox.
        itemTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ItemCategory>() {
            @Override
            public void changed(ObservableValue<? extends ItemCategory> observableValue, ItemCategory itemCategory, ItemCategory t1) {
                if(itemTable.getSelectionModel().getSelectedItem() != null) {
                    itemNameText.setText(itemTable.getSelectionModel().getSelectedItem().getItemName());
                    int select = 0;
                    for(Category c : categoryList) {
                        if(itemTable.getSelectionModel().getSelectedItem().getCategoryId() == c.getCategoryId()) {
                            categoryCombo.getSelectionModel().select(select);
                        }
                        select += 1;
                    }
                }
            }
        });
        setItemTable();
    }

    public void clickAddButton(ActionEvent event) {
        if(itemNameText != null && categoryCombo.getSelectionModel().getSelectedItem() != null) {
            Item item = new Item();
            item.setItemName(itemNameText.getText());
            item.setCateogryId(categoryList.get(categoryCombo.getSelectionModel().getSelectedIndex()).getCategoryId());
            ItemDAO itemDAO = new ItemDAO();
            itemDAO.addItem(item);
            errorLabel.setText("The item has been updated.");
            setItemTable();
        } else {
            errorLabel.setText("Please input item name and select a category.");
        }
    }

    public void clickUpdateButton(ActionEvent event) {
        if(itemTable.getSelectionModel().getSelectedItem() != null) {
            Item item = new Item();
            item.setItemId(itemTable.getSelectionModel().getSelectedItem().getItemId());
            item.setCateogryId(categoryList.get(categoryCombo.getSelectionModel().getSelectedIndex()).getCategoryId());
            item.setItemName(itemNameText.getText());
            ItemDAO itemDAO = new ItemDAO();
            itemDAO.updateItem(item);
            errorLabel.setText("The item has been updated.");
            setItemTable();
        } else {
            errorLabel.setText("Please select a line.");
        }
    }

    public void clickDeleteButton(ActionEvent event) {
        if(itemTable.getSelectionModel().getSelectedItem() != null) {
            Item item = new Item();
            item.setItemId(itemTable.getSelectionModel().getSelectedItem().getItemId());
            item.setCateogryId(categoryList.get(categoryCombo.getSelectionModel().getSelectedIndex()).getCategoryId());
            item.setItemName(itemNameText.getText());
            ItemDAO itemDAO = new ItemDAO();
            itemDAO.deleteItem(item);
            errorLabel.setText("The item has been deleted.");
            setItemTable();
        } else {
            errorLabel.setText("Please select a line.");
        }
    }

    public void clickBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/modeselect.fxml"));
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

    public void setItemTable() {
        ItemCategoryDAO itemCategoryDAO = new ItemCategoryDAO();
        ObservableList<ItemCategory> itemCategoryList = itemCategoryDAO.getItemCategory();
        itemTable.setItems(itemCategoryList);
    }

}
