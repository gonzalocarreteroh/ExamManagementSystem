package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerDbTest {
    @Test
    void creationTest() {
        ManagerDb db = new ManagerDb(new Manager[]{
            new Manager("john.smith", "123456"),
            new Manager("jane.smith", "456789"),
            new Manager("anne.smith", "789123")
        });

        assertTrue(db.login("john.smith", "123456"));
        assertTrue(db.login("jane.smith", "456789"));
        assertTrue(db.login("anne.smith", "789123"));
        assertFalse(db.login("johny.smith", "123456"));
        assertFalse(db.login("janey.smith", "456789"));
        assertFalse(db.login("anney.smith", "789123"));
        assertEquals(3, db.size());
        assertEquals("john.smith", db.get(0).getUsername());
    }
}
