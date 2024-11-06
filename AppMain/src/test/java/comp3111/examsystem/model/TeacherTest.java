package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TeacherTest {
    @Test
    void gettersTest() {
        Teacher t = new Teacher("123456", "john@ust.hk", "John", 20, "CS", "Assistant Professor");
        assertEquals("123456", t.getPassword());
        assertEquals("john@ust.hk", t.getUsername());
        assertEquals("John", t.getName());
        assertEquals(20, t.getAge());
        assertEquals("CS", t.getDepartment());
        assertEquals("Assistant Professor", t.getPosition());
        assertEquals(t, t.getTeacher());
        assertNull(t.getStudent());
        assertNull(t.getManager());
    }
}
