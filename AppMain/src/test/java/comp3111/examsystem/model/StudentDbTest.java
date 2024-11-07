package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentDbTest {
    @Test
    void creationTest() {
        StudentDb db = new StudentDb();
        db.add("123456", "john.smith", "John", 20, "CS", Gender.Male);
        db.add("123456", "jane.smith", "Jane", 20, "CS", Gender.Female);
        db.add("123456", "anne.smith", "Anna", 20, "CS", Gender.Female);
        db.add("123456", "carl.smith", "Carl", 20, "CS", Gender.Male);
        db.add("123456", "rosie.smith",  "Rosie", 20, "CS", Gender.Female);

        int first = db.all()[0].getId();
        assertEquals(db.get(first).getId(), first);
        assertEquals(5, db.size());
        assertEquals(5, db.all().length);
        assertEquals(2, db.filter("j", "", "").length);
        assertEquals(0, db.filter("x", "", "").length);
        assertEquals(0, db.filter("", "x", "").length);
        assertEquals(0, db.filter("", "", "x").length);
    }

    @Test
    void deletionTest() {
        StudentDb db = new StudentDb();
        db.add("123456", "john.smith", "John", 20, "CS", Gender.Male);
        db.add("123456", "jane.smith",  "Jane", 20, "CS", Gender.Female);
        db.add("123456", "anne.smith", "Anne", 20, "CS", Gender.Female);
        db.add("123456", "carl.smith", "Carl", 20, "CS", Gender.Male);

        assertEquals(4, db.size());
        db.remove(db.filter("john.smith", "", "")[0].getId());
        assertEquals(3, db.size());
    }

    @Test
    void updateTest() {
        StudentDb db = new StudentDb();
        db.update(new Student(7, "999111", "john.smith", "Carl", 20, "CS", Gender.Male));
        db.add("123456", "john.smith", "John", 20, "CS", Gender.Male);
        db.add("123456", "jane.smith",  "Jane", 20, "CS", Gender.Female);
        db.add("123456", "anne.smith", "Anne", 20, "CS", Gender.Female);
        db.add("123456", "carl.smith", "Carl", 20, "CS", Gender.Male);

        assertEquals(4, db.size());
        db.update(new Student(db.filter("john.smith", "", "")[0].getId(), "999111", "john.smith", "John", 20, "CS", Gender.Male));
        assertEquals("999111", db.filter("john.smith", "", "")[0].getPassword());
    }
}
