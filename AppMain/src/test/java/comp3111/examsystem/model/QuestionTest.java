package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {
    @Test
    void singleTest() {
        Question q = new Question(1, "1+1=", "1", "2", "11", "10", "B", 4);
        assertEquals(1, q.getId());
        assertEquals("1+1=", q.getTitle());
        assertEquals("1", q.getA());
        assertEquals("2", q.getB());
        assertEquals("11", q.getC());
        assertEquals("10", q.getD());
        assertEquals("B", q.getAnswer());
        assertEquals(4, q.getPoints());
        assertTrue(q.isSingle());
        assertFalse(q.isMultiple());
    }

    @Test
    void multipleTest() {
        Question q = new Question(1, "1+1 <", "1", "2", "11", "10", "CD", 4);
        assertEquals(1, q.getId());
        assertEquals("1+1 <", q.getTitle());
        assertEquals("1", q.getA());
        assertEquals("2", q.getB());
        assertEquals("11", q.getC());
        assertEquals("10", q.getD());
        assertEquals("CD", q.getAnswer());
        assertEquals(4, q.getPoints());
        assertFalse(q.isSingle());
        assertTrue(q.isMultiple());
    }
}
