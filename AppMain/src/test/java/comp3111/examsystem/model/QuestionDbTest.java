package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionDbTest {
    @Test
    void creationTest() {
        QuestionDb qb = new QuestionDb();
        qb.add("1+1=", "1", "2", "11", "10", "B", 4);
        qb.add("1+1=", "1", "2", "11", "10", "B", 4);
        qb.add("1+1=", "1", "2", "11", "10", "B", 4);
        qb.add("1+1=", "1", "2", "11", "10", "B", 4);
        qb.add("1+1=", "1", "2", "11", "10", "B", 4);

        for (Question q : qb.all()) {
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
    }

    @Test
    void initFromMapTest() {
        HashMap<Integer, Question> hash = new HashMap<>();
        hash.put(4, new Question(4, "1+1=", "1", "2", "11", "10", "B", 4));
        QuestionDb qb = new QuestionDb(hash);

        Question q = qb.all()[0];
        assertEquals(4, q.getId());
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
    void deletionTest() {
        QuestionDb qb = new QuestionDb();
        qb.add("1+1=", "1", "2", "11", "10", "B", 4);
        qb.add("1+1=", "1", "2", "11", "10", "B", 4);
        qb.add("1+1=", "1", "2", "11", "10", "B", 4);

        assertEquals(3, qb.size());
        qb.remove(qb.filter("", null, null)[0].getId());
        assertEquals(2, qb.size());
        qb.remove(qb.filter("", null, null)[0].getId());
        assertEquals(1, qb.size());
    }

    @Test
    void updateTest() {
        QuestionDb qb = new QuestionDb();
        qb.add("1+1=", "1", "2", "11", "10", "B", 4);
        qb.add("1+1=", "1", "2", "11", "10", "B", 4);
        qb.add("1+1=", "1", "2", "11", "10", "B", 4);

        int first = qb.all()[0].getId();
        qb.update(first, "1+0=", "1", "2", "11", "10", "A", 4);
        assertEquals("1+0=", qb.get(first).getTitle());
        assertEquals("A", qb.get(first).getAnswer());
    }

    @Test
    void filteringTest() {
        QuestionDb qb = new QuestionDb();
        qb.add("1+0=", "1", "2", "11", "10", "A", 4);
        qb.add("1+1=", "1", "2", "11", "10", "B", 6);
        qb.add("1+9=", "1", "2", "11", "10", "D", 8);
        qb.add("2+9=", "1", "2", "11", "10", "C", 2);

        assertEquals(3, qb.filter("1+", null, null).length);
        assertEquals(1, qb.filter("1+", null, 4).length);
        assertEquals(0, qb.filter("1+", true, null).length);
    }
}
