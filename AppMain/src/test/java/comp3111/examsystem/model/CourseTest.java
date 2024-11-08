package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {
    @Test
    void initTest() {
        Course c = new Course(4, "COMP3111", "Rubbish Engineering", "CS");
        assertEquals(4, c.getId());
        assertEquals("COMP3111", c.getCode());
        assertEquals("Rubbish Engineering", c.getName());
        assertEquals("CS", c.getDepartment());
    }
}
