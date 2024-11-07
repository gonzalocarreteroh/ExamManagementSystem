package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {
    @Test
    void gettersTest() {
        Manager m = new Manager(2, "123456", "john@ust.hk");
        assertEquals(2, m.getId());
        assertEquals("123456", m.getPassword());
        assertEquals("john@ust.hk", m.getUsername());
        assertEquals(m, m.getManager());
        assertNull(m.getTeacher());
        assertNull(m.getStudent());
    }
}
