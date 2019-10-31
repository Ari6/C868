package com.ntlts.C868.Test;

import com.ntlts.C868.Database;
import com.ntlts.C868.Models.Item;
import com.ntlts.C868.Models.ItemDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.*;

import java.sql.PreparedStatement;
import java.sql.Statement;

import static com.ntlts.C868.Database.connection;
import static org.junit.Assert.assertEquals;

public class ItemDAOTest {
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
        state.executeUpdate("Create table IF NOT EXISTS items2 as select * from items where 1 = 2;");
        state.executeUpdate("insert into items2 select * from items;");
        state.executeUpdate("delete from items;");
        state.executeUpdate("insert into items (itemId, itemName, categoryId) values (1, 'Test Item Name', 3);");
        state.executeUpdate("insert into items (itemId, itemName, categoryId) values (2, 'Test Item Name2', 4);");
    }

    @After
    public void tearDown() throws Exception {
        PreparedStatement state = connection.prepareStatement("delete from items;");
        state.executeUpdate();
        state = connection.prepareStatement("insert into items select * from items2;");
        state.executeUpdate();
        state = connection.prepareStatement("delete from items2;");
        state.executeUpdate();
    }

    @Test
    public void getItem() {
        ItemDAO dao = new ItemDAO();
        Item expected = new Item();
        expected.setItemId(2);
        expected.setItemName("Test Item Name2");
        expected.setCateogryId(4);
        Item actual = new Item();
        actual = dao.getItem(2);
        assertEquals(2, actual.getItemId());
        assertEquals("Test Item Name2", actual.getItemName());
        assertEquals(4, actual.getCateogryId());

        ObservableList<Item> actualList = dao.getItem();
        ObservableList<Item> expectedList = FXCollections.observableArrayList();
        Item expected2 = new Item();
        expected2.setItemId(1);
        expected2.setItemName("Test Item Name");
        expected2.setCateogryId(3);
        expectedList.add(expected2);
        expectedList.add(expected);
        int i = 0;
        for(Item it : actualList) {
            assertEquals(expectedList.get(i).getItemId(), it.getItemId());
            assertEquals(expectedList.get(i).getItemName(), it.getItemName());
            assertEquals(expectedList.get(i).getCateogryId(), it.getCateogryId());
            i++;
        }
    }

    @Test
    public void addItem() {
        ItemDAO dao = new ItemDAO();
        Item expected = new Item();
        expected.setItemId(3);
        expected.setItemName("Test 3");
        expected.setCateogryId(6);
        dao.addItem(expected);
        assertEquals(3, dao.getItem(3).getItemId());
        assertEquals("Test 3", dao.getItem(3).getItemName());
        assertEquals(6, dao.getItem(3).getCateogryId());
    }

    @Test
    public void updateItem() {
        ItemDAO dao = new ItemDAO();
        Item expected = new Item();
        expected.setItemId(2);
        expected.setItemName("Edited");
        expected.setCateogryId(8);
        dao.updateItem(expected);
        assertEquals(2, dao.getItem(2).getItemId());
        assertEquals("Edited", dao.getItem(2).getItemName());
        assertEquals(8, dao.getItem(2).getCateogryId());
    }

    @Test
    public void deleteItem() {
        ItemDAO dao = new ItemDAO();
        Item expected = new Item();
        expected.setItemId(2);
        expected.setItemName("delete");
        expected.setCateogryId(9);
        dao.deleteItem(expected);
        ObservableList<Item> actual = dao.getItem();
        assertEquals(1, actual.get(0).getItemId());
        assertEquals("Test Item Name", actual.get(0).getItemName());
        assertEquals(3, actual.get(0).getCateogryId());
    }
}
