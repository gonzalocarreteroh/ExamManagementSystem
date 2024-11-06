package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserDbTest {
    @Test
    void creationTest() {
        UserDb db = new UserDb();
        db.add(new Manager("123456", "john.smith"));
        db.add(new Manager("123456", "john.smith"));
        db.add(new Manager("123456", "jane.smith"));
        db.add(new Manager("123456", "anne.smith"));
        db.add(new Student("123456", "carl.smith", "Carl", 20, "CS", Gender.Male));
        db.add(new Teacher("123456", "rosie.smith",  "Rosie", 20, "CS", "Professor"));

        assertEquals(5, db.size());
        assertEquals(3, db.managers().length);
        assertEquals(2, db.managers("j").length);
        assertEquals(0, db.managers("x").length);
        assertEquals(1, db.students().length);
        assertEquals(0, db.students("x", "", "").length);
        assertEquals(0, db.students("", "x", "").length);
        assertEquals(0, db.students("", "", "x").length);
        assertEquals(1, db.teachers().length);
        assertEquals(0, db.teachers("x", "", "").length);
        assertEquals(0, db.teachers("", "x", "").length);
        assertEquals(0, db.teachers("", "", "x").length);
    }

    @Test
    void deletionTest() {
        UserDb db = new UserDb();
        db.add(new Manager("123456", "john.smith"));
        db.add(new Teacher("123456", "jane.smith",  "Jane", 20, "CS", "Professor"));
        db.add(new Manager("123456", "anne.smith"));
        db.add(new Student("123456", "carl.smith", "Carl", 20, "CS", Gender.Male));

        assertEquals(4, db.size());
        db.remove("john.doe");
        assertEquals(4, db.size());
        db.remove("john.smith");
        assertEquals(3, db.size());
    }

    @Test
    void updateTest() {
        UserDb db = new UserDb();
        db.add(new Manager("123456", "john.smith"));
        db.add(new Teacher("123456", "jane.smith",  "Jane", 20, "CS", "Professor"));
        db.add(new Manager("123456", "anne.smith"));
        db.add(new Student("123456", "carl.smith", "Carl", 20, "CS", Gender.Male));

        assertEquals(4, db.size());
        db.update(new Manager("123456", "john.doe"));
        assertNull(db.get("john.doe"));
        db.update(new Manager("999111", "john.smith"));
        assertEquals("999111", db.get("john.smith").getPassword());
    }

    @Test
    void filteringTest() {
        QuestionDb qb = new QuestionDb();
        qb.add("1+0=", "1", "2", "11", "10", "A", 4);
        qb.add("1+1=", "1", "2", "11", "10", "B", 6);
        qb.add("1+9=", "1", "2", "11", "10", "D", 8);
        qb.add("2+9=", "1", "2", "11", "10", "C", 2);

        assertEquals(3, qb.all("1+", null, null).length);
        assertEquals(1, qb.all("1+", null, 4).length);
        assertEquals(0, qb.all("1+", true, null).length);
    }
}
