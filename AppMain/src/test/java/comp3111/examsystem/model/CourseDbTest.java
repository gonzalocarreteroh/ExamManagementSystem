package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class CourseDbTest {
    @Test
    void creationTest() {
        CourseDb db = new CourseDb();
        db.add("COMP3111", "SwEng", "CS");
        db.add("COMP3111", "SwEng", "CS");
        db.add("COMP3111", "SwEng", "CS");
        db.add("COMP3111", "SwEng", "CS");
        db.add("COMP3111", "SwEng", "CS");

        for (Course c : db.all()) {
            assertEquals("COMP3111", c.getCode());
            assertEquals("SwEng", c.getName());
            assertEquals("CS", c.getDepartment());
        }
    }

    @Test
    void initFromMapTest() {
        HashMap<Integer, Course> hash = new HashMap<>();
        hash.put(4, new Course(4, "COMP3111", "SwEng", "CS"));
        CourseDb cb = new CourseDb(hash);

        Course c = cb.all()[0];
        assertEquals(4, c.getId());
        assertEquals("COMP3111", c.getCode());
        assertEquals("SwEng", c.getName());
        assertEquals("CS", c.getDepartment());
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
        cb.update(77, "NEVER", "Not a name", "XX");

        cb.add("COMP3111", "SwEng", "CS");
        cb.add("COMP3111", "SwEng", "CS");
        cb.add("COMP3111", "SwEng", "CS");

        int first = cb.all()[0].getId();
        cb.update(first, "COMP2222", "Www", "CSE");
        assertEquals("COMP2222", cb.get(first).getCode());
        assertEquals("Www", cb.get(first).getName());
        assertEquals("CSE", cb.get(first).getDepartment());
    }

    @Test
    void filteringTest() {
        CourseDb db = new CourseDb();
        db.add("COMP3111", "SwEng", "CS");
        db.add("COMP3123", "SEng", "CSE");
        db.add("COMP3213", "EngSw", "CS");
        db.add("COMP3312", "EngS", "CSE");

        assertEquals(2, db.all("COMP31", "", "").length);
        assertEquals(1, db.all("COMP31", "Sw", "").length);
        assertEquals(2, db.all("", "", "CSE").length);
    }
}
