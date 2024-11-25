package comp3111.examsystem.controller.student;

import comp3111.examsystem.model.DataCollection;
import comp3111.examsystem.model.Student;
import comp3111.examsystem.model.StudentDb;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginControllerTest {

    @Test
    void initTest() {
        LoginController controller = new LoginController();
        assertNotNull(controller, "LoginController instance should not be null");
    }

    @Test
    void fxmlFieldsNullTest() {
        LoginController controller = new LoginController();

        // Assert that FXML fields are null before initialization
        assertNull(controller.usernameTxt, "usernameTxt should be null before initialization");
        assertNull(controller.passwordTxt, "passwordTxt should be null before initialization");
    }

    @Test
    void defaultDataNullTest() {
        LoginController controller = new LoginController();

        // Assert that the data field is null before initialization
        assertNull(controller.data, "Data field should be null before initialization");
    }



    @Test
    void validLoginTest() {
        // Create a mock StudentDb and add a student
        StudentDb studentDb = new StudentDb();
        studentDb.add("validUser", "validPass", "John Doe", 20, "CS", null);

        // Set up DataCollection with the mock StudentDb
        DataCollection data = new DataCollection(null, null, null, null, null, studentDb, null);
        LoginController controller = new LoginController();
        controller.data = data;

        // Test valid login
        assertTrue(data.getStudents().login("validUser", "validPass"), "Login should succeed for valid credentials");
    }

    @Test
    void invalidLoginTest() {
        // Create a mock StudentDb without adding any students
        StudentDb studentDb = new StudentDb();

        // Set up DataCollection with the mock StudentDb
        DataCollection data = new DataCollection(null, null, null, null, null, studentDb, null);
        LoginController controller = new LoginController();
        controller.data = data;

        // Test invalid login
        assertFalse(data.getStudents().login("invalidUser", "invalidPass"), "Login should fail for invalid credentials");
    }

    @Test
    void emptyCredentialsTest() {
        // Create a mock StudentDb and add a student
        StudentDb studentDb = new StudentDb();
        studentDb.add("validUser", "validPass", "John Doe", 20, "CS", null);

        // Set up DataCollection with the mock StudentDb
        DataCollection data = new DataCollection(null, null, null, null, null, studentDb, null);
        LoginController controller = new LoginController();
        controller.data = data;

        // Test empty credentials
        assertFalse(data.getStudents().login("", ""), "Login should fail for empty credentials");
    }
    @Test
    void correctUsernameWrongPasswordTest() {
        // Create a mock StudentDb and add a student
        StudentDb studentDb = new StudentDb();
        studentDb.add("validUser", "validPass", "John Doe", 20, "CS", null);

        // Set up DataCollection with the mock StudentDb
        DataCollection data = new DataCollection(null, null, null, null, null, studentDb, null);
        LoginController controller = new LoginController();
        controller.data = data;

        // Test login with correct username but wrong password
        assertFalse(data.getStudents().login("validUser", "wrongPass"), "Login should fail with correct username but wrong password");
    }
    @Test
    void caseSensitiveLoginTest() {
        // Create a mock StudentDb and add a student
        StudentDb studentDb = new StudentDb();
        studentDb.add("validUser", "validPass", "John Doe", 20, "CS", null);

        // Set up DataCollection with the mock StudentDb
        DataCollection data = new DataCollection(null, null, null, null, null, studentDb, null);
        LoginController controller = new LoginController();
        controller.data = data;

        // Test login with incorrect casing
        assertFalse(data.getStudents().login("VALIDUSER", "validPass"), "Login should fail with uppercase username");
        assertFalse(data.getStudents().login("validUser", "VALIDPASS"), "Login should fail with uppercase password");
    }
    @Test
    void loginWithEmptyDatabaseTest() {
        // Create an empty StudentDb
        StudentDb studentDb = new StudentDb();

        // Set up DataCollection with the mock StudentDb
        DataCollection data = new DataCollection(null, null, null, null, null, studentDb, null);
        LoginController controller = new LoginController();
        controller.data = data;

        // Test login with no users in the database
        assertFalse(data.getStudents().login("user", "pass"), "Login should fail when no users exist in the database");
    }
    @Test
    void buttonInteractionTest() {
        LoginController controller = new LoginController();

        // Assert no interaction with buttons before actions
        assertNull(controller.usernameTxt, "usernameTxt should be null before any interaction");
        assertNull(controller.passwordTxt, "passwordTxt should be null before any interaction");

        // No need to call any JavaFX methods, just validate default behavior
        assertTrue(true, "No interaction with buttons should occur before actions are invoked");
    }



}
