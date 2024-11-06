package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {
    @Test
    void initTest() {
        Course c = new Course("COMP3111", "Rubbish Engineering", "CS");
        assertEquals("COMP3111", c.getId());
        assertEquals("Rubbish Engineering", c.getName());
        assertEquals("CS", c.getDepartment());
    }
}
