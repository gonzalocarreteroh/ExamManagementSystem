package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManagerDbTest {
    @Test
    void creationTest() {
        ManagerDb db = new ManagerDb();
        db.add("123456", "john.smith");
        db.add("123456", "jane.smith");
        db.add("123456", "anne.smith");

        int first = db.list()[0].getId();
        assertEquals(db.get(first).getId(), first);
        assertEquals(3, db.size());
        assertEquals(3, db.list().length);
        assertEquals(2, db.list("j").length);
        assertEquals(0, db.list("x").length);
    }

    @Test
    void deletionTest() {
        ManagerDb db = new ManagerDb();
        db.add("123456", "john.smith");
        db.add("123456", "anne.smith");

        assertEquals(2, db.size());
        db.remove(db.list("john.smith")[0].getId());
        assertEquals(1, db.size());
    }

    @Test
    void updateTest() {
        ManagerDb db = new ManagerDb();
        db.update(new Manager(7, "999111", "john.smith"));
        db.add("123456", "john.smith");
        db.add("123456", "anne.smith");

        assertEquals(2, db.size());
        db.update(new Manager(db.list("john.smith")[0].getId(), "999111", "john.smith"));
        assertEquals("999111", db.list("john.smith")[0].getPassword());
    }
}
