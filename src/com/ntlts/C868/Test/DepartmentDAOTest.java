package com.ntlts.C868.Test;
import static com.ntlts.C868.Database.connection;
import static org.junit.Assert.*;

import java.sql.*;
import java.time.LocalDateTime;

import com.ntlts.C868.Database;
import com.ntlts.C868.Models.Department;
import com.ntlts.C868.Models.DepartmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.junit.*;
public class DepartmentDAOTest {
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
        state.executeUpdate("Create table IF NOT EXISTS departments2 as select * from departments where 1 = 2;");
        state.executeUpdate("insert into departments2 select * from departments;");
        state.executeUpdate("delete from departments;");
        state.executeUpdate("insert into departments (departmentId, departmentName, lastUpdateId, lastUpdated) values (1, 'Test Department Name', 6, datetime('now'));");
        state.executeUpdate("insert into departments (departmentId, departmentName, lastUpdateId, lastUpdated) values (2, 'Test Department Name2', 7, datetime('now'));");
    }

    @After
    public void tearDown() throws Exception {
        PreparedStatement state = connection.prepareStatement("delete from departments;");
        state.executeUpdate();
        state = connection.prepareStatement("insert into departments select * from departments2;");
        state.executeUpdate();
        state = connection.prepareStatement("delete from departments2;");
        state.executeUpdate();
    }

    @Test
    public void getDepartment() {
        DepartmentDAO dao = new DepartmentDAO();
        Department expected = new Department();
        expected.setDepartmentId(2);
        expected.setDepartmentName("Test Department Name2");
        expected.setLastUpdateId(7);
        expected.setLastUpdated(LocalDateTime.now());
        Department actual = dao.getDepartment(2);

        assertEquals(2, actual.getDepartmentId());
        assertEquals("Test Department Name2", actual.getDepartmentName());
        assertEquals(7, actual.getLastUpdateId());
        System.out.println("expected: " + expected.getLastUpdated() + " actual: " + actual.getLastUpdated());

        ObservableList<Department> actualList = dao.getDepartment();
        ObservableList<Department> expectedList = FXCollections.observableArrayList();
        Department expected2 = new Department();
        expected2.setDepartmentId(1);
        expected2.setDepartmentName("Test Department Name");
        expected2.setLastUpdateId(6);
        expected2.setLastUpdated(LocalDateTime.now());
        expectedList.add(expected2);
        expectedList.add(expected);
        int i = 0;
        for(Department c : actualList) {
            assertEquals(expectedList.get(i).getDepartmentId(), c.getDepartmentId());
            assertEquals(expectedList.get(i).getDepartmentName(), c.getDepartmentName());
            assertEquals(expectedList.get(i).getLastUpdateId(), c.getLastUpdateId());
            System.out.println("expected: " + expectedList.get(i).getLastUpdated() + " " + "actual: " + c.getLastUpdated());
            i++;
        }
    }

    @Test
    public void addDepartment() {
        DepartmentDAO dao = new DepartmentDAO();
        Department expected = new Department();
        expected.setDepartmentId(3);
        expected.setDepartmentName("Test 3");
        expected.setLastUpdateId(2);
        expected.setLastUpdated(LocalDateTime.now());
        dao.addDepartment(expected);
        assertEquals(3, dao.getDepartment(3).getDepartmentId());
        assertEquals("Test 3", dao.getDepartment(3).getDepartmentName());
        assertEquals(2, dao.getDepartment(3).getLastUpdateId());
        System.out.println("expected: " + expected.getLastUpdated() + " actual: " + dao.getDepartment(3).getLastUpdated());

    }

    @Test
    public void updateDepartment() {
        DepartmentDAO dao = new DepartmentDAO();
        Department expected = new Department();
        expected.setDepartmentId(2);
        expected.setDepartmentName("Edited");
        expected.setLastUpdateId(999);
        expected.setLastUpdated(LocalDateTime.now());
        dao.updateDepartment(expected);
        assertEquals(2, dao.getDepartment(2).getDepartmentId());
        assertEquals("Edited", dao.getDepartment(2).getDepartmentName());
        assertEquals(999, dao.getDepartment(2).getLastUpdateId());
        System.out.println("expected: " + expected.getLastUpdated() + " actual: " + dao.getDepartment(2).getLastUpdated());
    }

    @Test
    public void deleteDepartment() {
        DepartmentDAO dao = new DepartmentDAO();
        Department expected = new Department();
        expected.setDepartmentId(2);
        expected.setDepartmentName("delete");
        dao.deleteDepartment(expected);
        ObservableList<Department> actual = dao.getDepartment();
        assertEquals(1, actual.get(0).getDepartmentId());
        assertEquals("Test Department Name", actual.get(0).getDepartmentName());
        assertEquals(6, actual.get(0).getLastUpdateId());
        System.out.println("expected: " + actual.get(0).getLastUpdated() + " " + "actual: " + expected.getLastUpdated());

    }
}
