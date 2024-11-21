package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExamTest {
    @Test
    void gettersTest() {
        CourseDb db = new CourseDb();
        db.add("COMP3111", "SwEng", "CS");
        db.add("COMP3031", "PPL", "CS");
        int courseId = db.all()[0].getId();
        int courseId2 = db.all()[1].getId();

        QuestionDb qb = new QuestionDb();
        qb.add("1+1=", "1", "2", "11", "10", "B", Type.Single, 4);
        qb.add("1+1=", "1", "2", "11", "10", "B",Type.Single, 4);
        int qId1 = qb.all()[0].getId(), qId2 = qb.all()[1].getId();

        Exam x = new Exam(3, "Midterm", 60, courseId, false, new int[]{qId1, qId2});
        assertEquals(3, x.getId());
        assertEquals("Midterm", x.getName());
        x.setName("Final");
        assertEquals("Final", x.getName());
        assertEquals(60, x.getDuration());
        x.setDuration(90);
        assertEquals(90, x.getDuration());
        assertEquals(courseId, x.getCourse(db).getId());
        x.setCourseId(courseId2);
        assertEquals(courseId2, x.getCourse(db).getId());
        assertFalse(x.getPublished());
        x.setPublished(true);
        assertTrue(x.getPublished());
        assertEquals(qId1, x.getQuestions(qb)[0].getId());
        assertEquals(qId2, x.getQuestions(qb)[1].getId());
        x.setQuestionIds(new int[]{qId2});
        assertEquals(qId2, x.getQuestions(qb)[0].getId());
        assertEquals(1, x.getQuestions(qb).length);
    }
}
