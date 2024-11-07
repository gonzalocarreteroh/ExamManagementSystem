package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeacherDbTest {
    @Test
    void creationTest() {
        TeacherDb db = new TeacherDb();
        db.add("123456", "carl.smith", "Carl", 20, "CS", "Professor");
        db.add("123456", "rosie.smith",  "Rosie", 20, "CS", "Professor");

        int first = db.all()[0].getId();
        assertEquals(db.get(first).getId(), first);
        assertEquals(2, db.size());
        assertEquals(2, db.all().length);
        assertEquals(1, db.filter("c", "", "").length);
        assertEquals(0, db.filter("x", "", "").length);
        assertEquals(0, db.filter("c", "x", "").length);
        assertEquals(0, db.filter("c", "", "x").length);
    }

    @Test
    void deletionTest() {
        TeacherDb db = new TeacherDb();
        db.add("123456", "jane.smith",  "Jane", 20, "CS", "Professor");
        db.add("123456", "carl.smith", "Carl", 20, "CS", "Professor");

        assertEquals(2, db.size());
        db.remove(db.filter("jane.smith", "", "")[0].getId());
        assertEquals(1, db.size());
    }

    @Test
    void updateTest() {
        TeacherDb db = new TeacherDb();
        db.update(new Teacher(7, "123456", "jane.smith",  "Jane", 20, "CS", "Professor"));
        db.add("123456", "jane.smith",  "Jane", 20, "CS", "Professor");
        db.add("123456", "carl.smith", "Carl", 20, "CS", "Professor");

        assertEquals(2, db.size());
        db.update(new Teacher(db.filter("jane.smith", "", "")[0].getId(), "999111", "jane.smith", "Jane", 20, "CS", "Professor"));
        assertEquals("999111", db.filter("jane.smith", "", "")[0].getPassword());
    }
}
