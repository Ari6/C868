package Test;

import static com.ntlts.C868.Database.connection;
import static org.junit.Assert.*;

import java.sql.*;

import com.ntlts.C868.Database;
import com.ntlts.C868.Models.Category;
import com.ntlts.C868.Models.CategoryDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.junit.*;


public class CategoryDAOTest {
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
        state.executeUpdate("Create table IF NOT EXISTS categories2 as select * from categories where 1 = 2;");
        state.executeUpdate("insert into categories2 (categoryId, categoryName) select * from categories;");
        state.executeUpdate("delete from categories;");
        state.executeUpdate("insert into categories (categoryId, categoryName) values (1, 'Test Category Name');");
        state.executeUpdate("insert into categories (categoryId, categoryName) values (2, 'Test Category Name2');");
    }

    @After
    public void tearDown() throws Exception {
        PreparedStatement state = connection.prepareStatement("delete from categories;");
        state.executeUpdate();
        state = connection.prepareStatement("insert into categories (categoryId, categoryName) select * from categories2;");
        state.executeUpdate();
        state = connection.prepareStatement("delete from categories2;");
        state.executeUpdate();
    }

    @Test
    public void getCategory() {
        CategoryDAO cdao = new CategoryDAO();
        Category expected = new Category();
        expected.setCategoryId(2);
        expected.setCategoryName("Test Category Name2");
        Category actual = new Category();
        actual = cdao.getCategory(2);
        assertEquals(2, actual.getCategoryId());
        assertEquals("Test Category Name2", actual.getCategoryName());

        ObservableList<Category> actualList = cdao.getCategory();
        ObservableList<Category> expectedList = FXCollections.observableArrayList();
        Category expected2 = new Category();
        expected2.setCategoryId(1);
        expected2.setCategoryName("Test Category Name");
        expectedList.add(expected2);
        expectedList.add(expected);
        int i = 0;
        for(Category c : actualList) {
            assertEquals(expectedList.get(i).getCategoryId(), c.getCategoryId());
            assertEquals(expectedList.get(i).getCategoryName(), c.getCategoryName());
            i++;
        }
    }

    @Test
    public void addCategory() {
        CategoryDAO cdao = new CategoryDAO();
        Category expected = new Category();
        expected.setCategoryId(3);
        expected.setCategoryName("Test 3");
        cdao.addCategory(expected);
        assertEquals(3, cdao.getCategory(3).getCategoryId());
        assertEquals("Test 3", cdao.getCategory(3).getCategoryName());
    }

    @Test
    public void updateCategory() {
        CategoryDAO cdao = new CategoryDAO();
        Category expected = new Category();
        expected.setCategoryId(2);
        expected.setCategoryName("Edited");
        cdao.updateCategory(expected);
        assertEquals(2, cdao.getCategory(2).getCategoryId());
        assertEquals("Edited", cdao.getCategory(2).getCategoryName());
    }

    @Test
    public void deleteCategory() {
        CategoryDAO cdao = new CategoryDAO();
        Category expected = new Category();
        expected.setCategoryId(2);
        expected.setCategoryName("delete");
        cdao.deleteCategory(expected);
        ObservableList<Category> actual = cdao.getCategory();
        assertEquals(1, actual.get(0).getCategoryId());
        assertEquals("Test Category Name", actual.get(0).getCategoryName());
    }
}