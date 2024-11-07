package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDbTest {
    @Test
    void creationTest() {
        UserDb db = new UserDb();
        db.addManager("123456", "john.smith");
        db.addManager("123456", "jane.smith");
        db.addManager("123456", "anne.smith");
        db.addStudent("123456", "carl.smith", "Carl", 20, "CS", Gender.Male);
        db.addTeacher("123456", "rosie.smith",  "Rosie", 20, "CS", "Professor");

        int first = db.managers()[0].getId();
        assertEquals(db.get(first).getId(), first);
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
        db.addManager("123456", "john.smith");
        db.addTeacher("123456", "jane.smith",  "Jane", 20, "CS", "Professor");
        db.addManager("123456", "anne.smith");
        db.addStudent("123456", "carl.smith", "Carl", 20, "CS", Gender.Male);

        assertEquals(4, db.size());
        db.remove(db.managers("john.smith")[0].getId());
        assertEquals(3, db.size());
    }

    @Test
    void updateTest() {
        UserDb db = new UserDb();
        db.update(new Manager(7, "999111", "john.smith"));
        db.addManager("123456", "john.smith");
        db.addTeacher("123456", "jane.smith",  "Jane", 20, "CS", "Professor");
        db.addManager("123456", "anne.smith");
        db.addStudent("123456", "carl.smith", "Carl", 20, "CS", Gender.Male);

        assertEquals(4, db.size());
        db.update(new Manager(db.managers("john.smith")[0].getId(), "999111", "john.smith"));
        assertEquals("999111", db.managers("john.smith")[0].getPassword());
    }

    @Test
    void filteringTest() {
        QuestionDb qb = new QuestionDb();
        qb.add("1+0=", "1", "2", "11", "10", "A", 4);
        qb.add("1+1=", "1", "2", "11", "10", "B", 6);
        qb.add("1+9=", "1", "2", "11", "10", "D", 8);
        qb.add("2+9=", "1", "2", "11", "10", "C", 2);

        assertEquals(3, qb.list("1+", null, null).length);
        assertEquals(1, qb.list("1+", null, 4).length);
        assertEquals(0, qb.list("1+", true, null).length);
    }
}
