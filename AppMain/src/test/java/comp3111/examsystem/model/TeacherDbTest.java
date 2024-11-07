package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeacherDbTest {
    @Test
    void creationTest() {
        TeacherDb db = new TeacherDb();
        db.add("123456", "carl.smith", "Carl", 20, "CS", "Professor");
        db.add("123456", "rosie.smith",  "Rosie", 20, "CS", "Professor");

        int first = db.list()[0].getId();
        assertEquals(db.get(first).getId(), first);
        assertEquals(2, db.size());
        assertEquals(2, db.list().length);
        assertEquals(1, db.list("c", "", "").length);
        assertEquals(0, db.list("x", "", "").length);
        assertEquals(0, db.list("c", "x", "").length);
        assertEquals(0, db.list("c", "", "x").length);
    }

    @Test
    void deletionTest() {
        TeacherDb db = new TeacherDb();
        db.add("123456", "jane.smith",  "Jane", 20, "CS", "Professor");
        db.add("123456", "carl.smith", "Carl", 20, "CS", "Professor");

        assertEquals(2, db.size());
        db.remove(db.list("jane.smith", "", "")[0].getId());
        assertEquals(1, db.size());
    }

    @Test
    void updateTest() {
        TeacherDb db = new TeacherDb();
        db.update(new Teacher(7, "123456", "jane.smith",  "Jane", 20, "CS", "Professor"));
        db.add("123456", "jane.smith",  "Jane", 20, "CS", "Professor");
        db.add("123456", "carl.smith", "Carl", 20, "CS", "Professor");

        assertEquals(2, db.size());
        db.update(new Teacher(db.list("jane.smith", "", "")[0].getId(), "999111", "jane.smith", "Jane", 20, "CS", "Professor"));
        assertEquals("999111", db.list("jane.smith", "", "")[0].getPassword());
    }
}
