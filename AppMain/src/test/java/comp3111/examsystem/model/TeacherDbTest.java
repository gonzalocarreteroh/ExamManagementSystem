package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherDbTest {
    @Test
    void creationTest() {
        TeacherDb db = new TeacherDb(new Teacher[]{new Teacher(7, "carl.smith", "123456", "Carl", 20, "CS", "Professor")});
        db.add("rosie.smith", "123456",  "Rosie", 20, "CS", "Professor");

        int first = db.all()[0].getId();
        assertEquals(db.get(first).getId(), first);
        assertEquals(2, db.all().length);
        assertEquals(1, db.all("carl.smith", "", "").length);
        assertEquals(0, db.all("x", "", "").length);
        assertEquals(0, db.all("", "x", "").length);
        assertEquals(0, db.all("carl.smith", "x", "").length);
        assertEquals(0, db.all("carl.smith", "", "x").length);
        assertTrue(db.login("rosie.smith", "123456"));
        assertFalse(db.login("rosie.smith", "abcdef"));
    }

    @Test
    void deletionTest() {
        TeacherDb db = new TeacherDb();
        db.add("jane.smith", "123456",  "Jane", 20, "CS", "Professor");
        db.add("carl.smith", "123456", "Carl", 20, "CS", "Professor");

        assertEquals(2, db.all().length);
        db.remove(db.all("jane.smith", "", "")[0].getId());
        assertEquals(1, db.all().length);
    }

    @Test
    void updateTest() {
        TeacherDb db = new TeacherDb();
        db.update(new Teacher(7, "jane.smith", "123456",  "Jane", 20, "CS", "Professor"));
        db.add("jane.smith", "123456",  "Jane", 20, "CS", "Professor");
        db.add("carl.smith", "123456", "Carl", 20, "CS", "Professor");

        assertEquals(2, db.all().length);
        db.update(new Teacher(db.all("jane.smith", "", "")[0].getId(), "jane.smith", "999111", "Jane", 20, "CS", "Professor"));
        assertEquals("999111", db.all("jane.smith", "", "")[0].getPassword());
    }
}
