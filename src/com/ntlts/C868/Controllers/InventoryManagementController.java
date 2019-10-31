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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InventoryManagementController implements Initializable {
    private User user;
    @FXML private TableView<InventoryView> inventoryTable;
    @FXML private TableColumn<InventoryView, String> deptColumn;
    @FXML private TableColumn<InventoryView, String> categoryColumn;
    @FXML private TableColumn<InventoryView, String> itemColumn;
    @FXML private TableColumn<InventoryView, Integer> amountColumn;
    @FXML private TextField amountText;
    @FXML private TextField searchText;
    @FXML private Label categoryLabel;
    @FXML private ChoiceBox<String> itemChoice;
    @FXML private ChoiceBox<String> departmentChoice;
    @FXML private Label errorLabel;
    private ObservableList<InventoryView> inventoryList;
    private ObservableList<Department> departmentList;
    private ObservableList<Item> itemList;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //To set data from databases
        deptColumn.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        inventoryTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<InventoryView>() {
            @Override
            public void changed(ObservableValue<? extends InventoryView> observableValue, InventoryView inventoryView, InventoryView t1) {
                if(inventoryTable.getSelectionModel().getSelectedItem() != null) {
                    amountText.setText(Integer.toString(inventoryTable.getSelectionModel().getSelectedItem().getAmount()));
                    categoryLabel.setText(inventoryTable.getSelectionModel().getSelectedItem().getCategoryName());
                    //Item choicebox selected item set
                    int selectItem = 0;
                    for(Item i : itemList) {
                        if(inventoryTable.getSelectionModel().getSelectedItem().getItemId() == i.getItemId()) {
                            itemChoice.getSelectionModel().select(selectItem);
                        }
                        selectItem += 1;
                    }
                    //Department choicebox selected department set
                    int selectDepartment = 0;
                    for(Department d : departmentList) {
                        if(inventoryTable.getSelectionModel().getSelectedItem().getDepartmentId() == d.getDepartmentId()) {
                            departmentChoice.getSelectionModel().select(selectDepartment);
                        }
                        selectDepartment += 1;
                    }
                }
            }
        });
        set();
    }

    public void clickSearchButton(ActionEvent event) {
        InventoryViewDAO inventoryViewDAO = new InventoryViewDAO();
        ObservableList<InventoryView> inventoryList = inventoryViewDAO.getInventoryView(searchText.getText());
        //Do not user set() here because it rewrites the search result
        inventoryTable.setItems(inventoryList);
        setItemChoice();
        setDepartmentChoice();
    }

    public void clickBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/modeselect.fxml"));
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

    public void clickAddButton(ActionEvent event) {
        try {
            Integer.parseInt(amountText.getText());
        } catch (NumberFormatException e) {
            errorLabel.setText("Only numbers are allowed in amount.");
            return;
        }
        if(itemChoice.getSelectionModel().getSelectedItem() == null ||
                departmentChoice.getSelectionModel().getSelectedItem() == null) {
            errorLabel.setText("Please select Item and Department.");
            return;
        }
        if(admin == 0) {
            if (departmentList.get(departmentChoice.getSelectionModel().getSelectedIndex()).getDepartmentId()
                    != departmentId) {
                errorLabel.setText("You need to select department you belong.");
                return;
            }
        }
        InventoryDAO inventoryDAO = new InventoryDAO();
        int itemIdchk = itemList.get(itemChoice.getSelectionModel().getSelectedIndex()).getItemId();
        int departmentIdchk = departmentList.get(departmentChoice.getSelectionModel().getSelectedIndex()).getDepartmentId();
        if(inventoryDAO.countInventory(itemIdchk, departmentIdchk) != 0) {
            errorLabel.setText("The record already exists.");
        } else {
            Inventory inventory = new Inventory();
            inventory.setItemId(itemList.get(itemChoice.getSelectionModel().getSelectedIndex()).getItemId());
            inventory.setDepartmentId(departmentList.get(departmentChoice.getSelectionModel().getSelectedIndex()).getDepartmentId());
            inventory.setAmount(Integer.parseInt(amountText.getText()));
            inventoryDAO.addInventory(inventory);
            errorLabel.setText("The record has been added.");
        }
        set();
    }

    public void clickUpdateButton(ActionEvent event) {
        try {
            Integer.parseInt(amountText.getText());
        } catch (NumberFormatException e) {
            errorLabel.setText("Only numbers are allowed in amount.");
            return;
        }
        if(inventoryTable.getSelectionModel().getSelectedItem() != null) {
            Inventory inventory = new Inventory();
            inventory.setDepartmentId(inventoryTable.getSelectionModel().getSelectedItem().getDepartmentId());
            inventory.setItemId(inventoryTable.getSelectionModel().getSelectedItem().getItemId());
            inventory.setAmount(Integer.parseInt(amountText.getText()));
            InventoryDAO inventoryDAO = new InventoryDAO();
            inventoryDAO.updateInventory(inventory);
            errorLabel.setText("The amount has been updated but you cannot update Departments and Items.");
        } else {
            errorLabel.setText("Please select a line.");
        }
        set();
    }
    public void clickDeleteButton(ActionEvent event) {
        if(inventoryTable.getSelectionModel().getSelectedItem() != null) {
            Inventory inventory = new Inventory();
            inventory.setDepartmentId(inventoryTable.getSelectionModel().getSelectedItem().getDepartmentId());
            inventory.setItemId(inventoryTable.getSelectionModel().getSelectedItem().getItemId());
            InventoryDAO inventoryDAO = new InventoryDAO();
            inventoryDAO.deleteInventory(inventory);
            errorLabel.setText("The record has been deleted.");
        } else {
            errorLabel.setText("Please select a line.");
        }
        set();
    }
    private void set() {
        setInventoryTable();
        setItemChoice();
        setDepartmentChoice();
    }

    private void setInventoryTable(){
        InventoryViewDAO inventoryViewDAO = new InventoryViewDAO();
        inventoryList = inventoryViewDAO.getInventoryView();
        inventoryTable.setItems(inventoryList);
    }
    private void setItemChoice() {
        ItemDAO itemDAO = new ItemDAO();
        itemList = itemDAO.getItem();
        ObservableList<String> itemStringList = FXCollections.observableArrayList();
        for(Item i : itemList) {
            itemStringList.add(i.getItemId() + " : " + i.getItemName());
        }
        itemChoice.setItems(itemStringList);
    }

    private void setDepartmentChoice() {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        departmentList = departmentDAO.getDepartment();
        ObservableList<String> departmentStringList = FXCollections.observableArrayList();
        for (Department d : departmentList) {
            departmentStringList.add(d.getDepartmentId() + " : " + d.getDepartmentName());
        }
        departmentChoice.setItems(departmentStringList);
    }

}
