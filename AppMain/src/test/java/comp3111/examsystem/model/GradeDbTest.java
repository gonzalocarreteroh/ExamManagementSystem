package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GradeDbTest {
    @Test
    void creationTest() {
        GradeDb db0 = new GradeDb();
        assertEquals(0, db0.all().length);

        GradeDb db = new GradeDb(new Grade[]{new Grade(2, 3, 95)});
        db.add(1, 2, 10);
        db.add(1, 3, 20);
        assertEquals(3, db.all().length);
        assertEquals(2, db.all(1, null).length);
        assertEquals(2, db.all(null, 3).length);

        assertEquals(95, db.all(2, 3)[0].getPoints());
        assertEquals(10, db.all(1, 2)[0].getPoints());
        assertEquals(20, db.all(1, 3)[0].getPoints());
        assertEquals(0, db.all(99, 99).length);
    }

    @Test
    void deletionTest() {
        CourseDb cb = new CourseDb();
        cb.add("COMP3111", "SwEng", "CS");
        cb.add("COMP3111", "SwEng", "CS");
        cb.add("COMP3111", "SwEng", "CS");

        assertEquals(3, cb.all().length);
        cb.remove(cb.all("", "", "")[0].getId());
        assertEquals(2, cb.all().length);
        cb.remove(cb.all()[0].getId());
        assertEquals(1, cb.all().length);
    }

    @Test
    void updateTest() {
        CourseDb cb = new CourseDb();
        cb.update(new Course(77, "NEVER", "Not a name", "XX"));

        cb.add("COMP3111", "SwEng", "CS");
        cb.add("COMP3111", "SwEng", "CS");
        cb.add("COMP3111", "SwEng", "CS");

        int first = cb.all()[0].getId();
        cb.update(new Course(first, "COMP2222", "Www", "CSE"));
        assertEquals("COMP2222", cb.get(first).getCode());
        assertEquals("Www", cb.get(first).getName());
        assertEquals("CSE", cb.get(first).getDepartment());
    }

    @Test
    void filteringTest() {
        CourseDb db = new CourseDb();
        db.add("COMP3111", "SwEng", "CS");
        db.add("COMP3111", "SEng", "CSE");
        db.add("COMP3213", "EngSw", "CS");
        db.add("COMP3312", "EngS", "CSE");

        assertEquals(2, db.all("COMP3111", "", "").length);
        assertEquals(1, db.all("COMP3111", "Sw", "").length);
        assertEquals(2, db.all("", "", "CSE").length);
    }
}
