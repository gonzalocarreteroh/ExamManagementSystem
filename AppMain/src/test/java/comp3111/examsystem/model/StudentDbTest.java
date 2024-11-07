package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentDbTest {
    @Test
    void creationTest() {
        StudentDb db = new StudentDb();
        db.add("john.smith", "123456", "John", 20, "CS", Gender.Male);
        db.add("jane.smith", "123456", "Jane", 20, "CS", Gender.Female);
        db.add("anne.smith", "123456", "Anna", 20, "CS", Gender.Female);
        db.add("carl.smith", "123456", "Carl", 20, "CS", Gender.Male);
        db.add("rosie.smith",  "123456", "Rosie", 20, "CS", Gender.Female);

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
        db.add("john.smith", "123456", "John", 20, "CS", Gender.Male);
        db.add("jane.smith",  "123456", "Jane", 20, "CS", Gender.Female);
        db.add("anne.smith", "123456", "Anne", 20, "CS", Gender.Female);
        db.add("carl.smith", "123456", "Carl", 20, "CS", Gender.Male);

        assertEquals(4, db.size());
        db.remove(db.filter("john.smith", "", "")[0].getId());
        assertEquals(3, db.size());
    }

    @Test
    void updateTest() {
        StudentDb db = new StudentDb();
        db.update(new Student(7, "999111", "john.smith", "Carl", 20, "CS", Gender.Male));
        db.add("john.smith", "123456", "John", 20, "CS", Gender.Male);
        db.add("jane.smith",  "123456", "Jane", 20, "CS", Gender.Female);
        db.add("anne.smith", "123456", "Anne", 20, "CS", Gender.Female);
        db.add("carl.smith", "123456", "Carl", 20, "CS", Gender.Male);

        assertEquals(4, db.size());
        db.update(new Student(db.filter("john.smith", "", "")[0].getId(), "john.smith", "999111", "John", 20, "CS", Gender.Male));
        assertEquals("999111", db.filter("john.smith", "", "")[0].getPassword());
    }
}
