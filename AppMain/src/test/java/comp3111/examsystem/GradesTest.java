package comp3111.examsystem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GradesTest {
    @Test
    void averageTest() {
        Grades g = new Grades();
        g.add(10, 20);
        g.add(10, 20);
        g.add(10, 20);
        assertEquals(50, g.getAverage());
    }

    @Test
    void gradeTest() {
        Grades g = new Grades();
        g.add(0, 1);
        assertEquals(g.getGrade(), "F");
        g.add(3, 4);
        assertEquals(g.getGrade(), "D");
        g.add(7, 10);
        assertEquals(g.getGrade(), "C");
        g.add(75, 85);
        assertEquals(g.getGrade(), "B");
        g.add(1000, 1000);
        assertEquals(g.getGrade(), "A");
    }
}
