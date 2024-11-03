package comp3111.examsystem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;

public class MainTest {
    @Test
    public void getPassword_exists() {
        HashMap<String, String> example = new HashMap<>();
        example.put("username", "password");
        assertEquals("password", Main.getPassword(example, "username"));
        // Non existent username
        assertNull(Main.getPassword(example, "nonexistent"));
    }

    @Test
    public void getAverageGrade() {
        HashMap<String, double[]> example = new HashMap<>();
        // A user with grades
        example.put("username1", new double[] {1, 2, 3});
        assertEquals(2, Main.getAverageGrade(example, "username1"));
        // A user with no grades
        example.put("username2", new double[] {});
        assertEquals(0, Main.getAverageGrade(example, "username2"));
    }
}