package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {
    @Test
    void gettersTest() {
        Manager m = new Manager("john@ust.hk", "123456");
        assertEquals("john@ust.hk", m.getUsername());
        assertEquals("123456", m.getPassword());
    }
}
