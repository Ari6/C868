package com.ntlts.C868.Test;

import com.ntlts.C868.Database;
import com.ntlts.C868.Models.Inventory;
import com.ntlts.C868.Models.InventoryDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.*;

import java.sql.PreparedStatement;
import java.sql.Statement;

import static com.ntlts.C868.Database.connection;
import static org.junit.Assert.assertEquals;

public class InventoryDAOTest {
    static Database db = new Database();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        db.connectDatabase();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        db.disconnectDatabase();

    }

    @Before
    public void setUp() throws Exception {

        Statement state = connection.createStatement();
        state.executeUpdate("Create table IF NOT EXISTS inventories2 as select * from inventories where 1 = 2;");
        state.executeUpdate("insert into inventories2 select * from inventories;");
        state.executeUpdate("delete from inventories;");
        state.executeUpdate("insert into inventories (itemId, departmentId, amount) values (1, 2, 10);");
        state.executeUpdate("insert into inventories (itemId, departmentId, amount) values (3, 4, 2000);");
    }

    @After
    public void tearDown() throws Exception {
        PreparedStatement state = connection.prepareStatement("delete from inventories;");
        state.executeUpdate();
        state = connection.prepareStatement("insert into inventories select * from inventories2;");
        state.executeUpdate();
        state = connection.prepareStatement("delete from inventories2;");
        state.executeUpdate();
    }

    @Test
    public void getInventory() {
        InventoryDAO dao = new InventoryDAO();
        Inventory expected = new Inventory();
        expected.setItemId(1);
        expected.setDepartmentId(2);
        expected.setAmount(10);
        Inventory actual = new Inventory();
        actual = dao.getInventory(1, 2);
        assertEquals(1, actual.getItemId());
        assertEquals(2, actual.getDepartmentId());
        assertEquals(10, actual.getAmount());

        ObservableList<Inventory> actualList = dao.getInventory();
        ObservableList<Inventory> expectedList = FXCollections.observableArrayList();
        Inventory expected2 = new Inventory();
        expected2.setItemId(3);
        expected2.setDepartmentId(4);
        expected2.setAmount(2000);
        expectedList.add(expected);
        expectedList.add(expected2);
        int i = 0;
        for(Inventory it : actualList) {
            assertEquals(expectedList.get(i).getItemId(), it.getItemId());
            assertEquals(expectedList.get(i).getDepartmentId(), it.getDepartmentId());
            assertEquals(expectedList.get(i).getAmount(), it.getAmount());
            i++;
        }
    }
    @Test
    public void countInventory() {
        InventoryDAO dao = new InventoryDAO();
        Inventory expected = new Inventory();
        expected.setItemId(1);
        expected.setDepartmentId(2);
        expected.setAmount(10);
        int actual = dao.countInventory(1, 2);
        assertEquals(1, actual);

        expected.setItemId(2);
        expected.setDepartmentId(5);
        expected.setAmount(10);
        actual = dao.countInventory(2, 1);
        assertEquals(0, actual);

    }
    @Test
    public void addInventory() {
        InventoryDAO dao = new InventoryDAO();
        Inventory expected = new Inventory();
        expected.setItemId(5);
        expected.setDepartmentId(6);
        expected.setAmount(20);
        dao.addInventory(expected);
        assertEquals(5, dao.getInventory(5, 6).getItemId());
        assertEquals(6, dao.getInventory(5, 6).getDepartmentId());
        assertEquals(20, dao.getInventory(5,6 ).getAmount());
    }

    @Test
    public void updateInventory() {
        InventoryDAO dao = new InventoryDAO();
        Inventory expected = new Inventory();
        expected.setItemId(3);
        expected.setDepartmentId(4);
        expected.setAmount(9999);
        dao.updateInventory(expected);
        assertEquals(3, dao.getInventory(3, 4).getItemId());
        assertEquals(4, dao.getInventory(3, 4).getDepartmentId());
        assertEquals(9999, dao.getInventory(3, 4).getAmount());
    }

    @Test
    public void deleteInventory() {
        InventoryDAO dao = new InventoryDAO();
        Inventory expected = new Inventory();
        expected.setItemId(3);
        expected.setDepartmentId(4);
        expected.setAmount(0);
        dao.deleteInventory(expected);
        ObservableList<Inventory> actual = dao.getInventory();
        assertEquals(1, actual.get(0).getItemId());
        assertEquals(2, actual.get(0).getDepartmentId());
        assertEquals(10, actual.get(0).getAmount());
    }
}
