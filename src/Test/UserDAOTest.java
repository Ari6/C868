package Test;

import com.ntlts.C868.Database;
import com.ntlts.C868.Models.User;
import com.ntlts.C868.Models.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.*;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;

import static com.ntlts.C868.Database.connection;
import static org.junit.Assert.assertEquals;

public class UserDAOTest {
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
        state.executeUpdate("Create table IF NOT EXISTS users2 as select * from users where 1 = 2;");
        state.executeUpdate("insert into users2 select * from users;");
        state.executeUpdate("delete from users;");
        state.executeUpdate("insert into users (id, userName, password, departmentId, lastUpdateId, lastUpdated) values (1, 'Test User Name', 'password1', 10, 6, datetime('now'));");
        state.executeUpdate("insert into users (id, userName, password, departmentId, lastUpdateId, lastUpdated) values (2, 'Test User Name2', 'password2', 11,  7, datetime('now'));");
    }

    @After
    public void tearDown() throws Exception {
        PreparedStatement state = connection.prepareStatement("delete from users;");
        state.executeUpdate();
        state = connection.prepareStatement("insert into users select * from users2;");
        state.executeUpdate();
        state = connection.prepareStatement("delete from users2;");
        state.executeUpdate();
    }

    @Test
    public void getUser() {
        UserDAO dao = new UserDAO();
        User expected = new User();
        expected.setUserId(2);
        expected.setUsername("Test User Name2");
        expected.setPassword("password2");
        expected.setDepartmentId(11);
        expected.setLastUpdateId(7);
        expected.setLastUpdated(LocalDateTime.now());
        User actual = dao.getUser(2);

        assertEquals(2, actual.getUserId());
        assertEquals("Test User Name2", actual.getUsername());
        assertEquals("password2", actual.getPassword());
        assertEquals(11, actual.getDepartmentId());
        assertEquals(7, actual.getLastUpdateId());
        System.out.println("expected: " + expected.getLastUpdated() + " actual: " + actual.getLastUpdated());

        ObservableList<User> actualList = dao.getUser();
        ObservableList<User> expectedList = FXCollections.observableArrayList();
        User expected2 = new User();
        expected2.setUserId(1);
        expected2.setUsername("Test User Name");
        expected2.setPassword("password1");
        expected2.setDepartmentId(10);
        expected2.setLastUpdateId(6);
        expected2.setLastUpdated(LocalDateTime.now());
        expectedList.add(expected2);
        expectedList.add(expected);
        int i = 0;
        for(User c : actualList) {
            assertEquals(expectedList.get(i).getUserId(), c.getUserId());
            assertEquals(expectedList.get(i).getUsername(), c.getUsername());
            assertEquals(expectedList.get(i).getPassword(), c.getPassword());
            assertEquals(expectedList.get(i).getDepartmentId(), c.getDepartmentId());
            assertEquals(expectedList.get(i).getLastUpdateId(), c.getLastUpdateId());
            System.out.println("expected: " + expectedList.get(i).getLastUpdated() + " " + "actual: " + c.getLastUpdated());
            i++;
        }
    }

    @Test
    public void addUser() {
        UserDAO dao = new UserDAO();
        User expected = new User();
        expected.setUserId(3);
        expected.setUsername("Test 3");
        expected.setPassword("password3");
        expected.setDepartmentId(100);
        expected.setLastUpdateId(2);
        expected.setLastUpdated(LocalDateTime.now());
        dao.addUser(expected);
        assertEquals(3, dao.getUser(3).getUserId());
        assertEquals("Test 3", dao.getUser(3).getUsername());
        assertEquals("password3", dao.getUser(3).getPassword());
        assertEquals(100, dao.getUser(3).getDepartmentId());
        assertEquals(2, dao.getUser(3).getLastUpdateId());
        System.out.println("expected: " + expected.getLastUpdated() + " actual: " + dao.getUser(3).getLastUpdated());

    }

    @Test
    public void updateUser() {
        UserDAO dao = new UserDAO();
        User expected = new User();
        expected.setUserId(2);
        expected.setUsername("Edited");
        expected.setPassword("password999");
        expected.setDepartmentId(111);
        expected.setLastUpdateId(999);
        expected.setLastUpdated(LocalDateTime.now());
        dao.updateUser(expected);
        assertEquals(2, dao.getUser(2).getUserId());
        assertEquals("Edited", dao.getUser(2).getUsername());
        assertEquals("password999", dao.getUser(2).getPassword());
        assertEquals(111, dao.getUser(2).getDepartmentId());
        assertEquals(999, dao.getUser(2).getLastUpdateId());
        System.out.println("expected: " + expected.getLastUpdated() + " actual: " + dao.getUser(2).getLastUpdated());
    }

    @Test
    public void deleteUser() {
        UserDAO dao = new UserDAO();
        User expected = new User();
        expected.setUserId(2);
        expected.setUsername("delete");
        dao.deleteUser(expected);
        ObservableList<User> actual = dao.getUser();
        assertEquals(1, actual.get(0).getUserId());
        assertEquals("Test User Name", actual.get(0).getUsername());
        assertEquals("password1", actual.get(0).getPassword());
        assertEquals(10, actual.get(0).getDepartmentId());
        assertEquals(6, actual.get(0).getLastUpdateId());
        System.out.println("expected: " + actual.get(0).getLastUpdated() + " " + "actual: " + expected.getLastUpdated());

    }
}
