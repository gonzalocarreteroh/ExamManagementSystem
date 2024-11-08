package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GradeTest {
    @Test
    void gettersTest() {
        CourseDb cb = new CourseDb();
        QuestionDb qb = new QuestionDb();
        StudentDb sb = new StudentDb();
        ExamDb eb = new ExamDb();

        cb.add("COMP3111", "SwEng", "CS");
        qb.add("1+1=", "1", "2", "11", "10", "B", 4);
        qb.add("1+1=", "1", "2", "11", "10", "B", 4);
        sb.add("john.smith", "123456", "John", 20, "CS", Gender.Male);

        int courseId = cb.all()[0].getId();
        int qId1 = qb.all()[0].getId();
        int qId2 = qb.all()[1].getId();

        eb.add("Final", 90, courseId, false, new int[]{qId1, qId2});

        Grade x = new Grade(sb.all()[0].getId(), eb.all()[0].getId(),97);
        assertEquals(97, x.getPoints());
        assertEquals(20, x.getStudent(sb).getAge());
        assertEquals("Final", x.getExam(eb).getName());
    }
}
