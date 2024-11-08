package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

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
        QuestionDb qb = new QuestionDb(new Question[]{new Question(4, "1+1=", "1", "2", "11", "10", "B", 4)});

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

        assertEquals(3, qb.all().length);
        qb.remove(qb.all("", null, null)[0].getId());
        assertEquals(2, qb.all().length);
        qb.remove(qb.all("", null, null)[0].getId());
        assertEquals(1, qb.all().length);
    }

    @Test
    void updateTest() {
        QuestionDb qb = new QuestionDb();
        qb.update(new Question(77, "1+0=", "1", "2", "11", "10", "A", 4));

        qb.add("1+1=", "1", "2", "11", "10", "B", 4);
        qb.add("1+1=", "1", "2", "11", "10", "B", 4);
        qb.add("1+1=", "1", "2", "11", "10", "B", 4);

        int first = qb.all()[0].getId();
        qb.update(new Question(first, "1+0=", "1", "2", "11", "10", "A", 4));
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

        assertEquals(3, qb.all("1+", null, null).length);
        assertEquals(1, qb.all("1+", null, 4).length);
        assertEquals(0, qb.all("1+", true, null).length);
    }
}
