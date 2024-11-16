package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ExamTest {
    @Test
    void gettersTest() {
        CourseDb db = new CourseDb();
        db.add("COMP3111", "SwEng", "CS");
        int courseId = db.all()[0].getId();

        QuestionDb qb = new QuestionDb();
        qb.add("1+1=", "1", "2", "11", "10", "B", Type.Single, 4);
        qb.add("1+1=", "1", "2", "11", "10", "B",Type.Single, 4);
        int qId1 = qb.all()[0].getId(), qId2 = qb.all()[1].getId();

        Exam x = new Exam(3, "Midterm", 60, courseId, false, new int[]{qId1, qId2});
        assertEquals(3, x.getId());
        assertEquals("Midterm", x.getName());
        assertEquals(60, x.getDuration());
        assertEquals(courseId, x.getCourse(db).getId());
        assertFalse(x.getPublished());
        assertEquals(qId1, x.getQuestions(qb)[0].getId());
        assertEquals(qId2, x.getQuestions(qb)[1].getId());
    }
}
