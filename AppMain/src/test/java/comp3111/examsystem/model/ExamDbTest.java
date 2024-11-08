package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExamDbTest {
    CourseDb cb = new CourseDb();
    int courseId;
    QuestionDb qb = new QuestionDb();
    int qId1, qId2;;

    ExamDbTest() {
        cb.add("COMP3111", "SwEng", "CS");
        qb.add("1+1=", "1", "2", "11", "10", "B", 4);
        qb.add("1+1=", "1", "2", "11", "10", "B", 4);
        courseId = cb.all()[0].getId();
        qId1 = qb.all()[0].getId();
        qId2 = qb.all()[1].getId();
    }

    @Test
    void creationTest() {
        ExamDb db = new ExamDb(new Exam[]{new Exam(1, "Midterm", 60, courseId, true, new int[]{qId1})});
        db.add("Final", 90, courseId, false, new int[]{qId1, qId2});

        assertEquals(2, db.all().length);
        Exam a = db.get(db.all("Midterm")[0].getId());
        Exam b = db.get(db.all("Final", courseId)[0].getId());
        Exam c = db.get(db.all("", courseId, false)[0].getId());
        assertEquals("Midterm", a.getName());
        assertEquals("Final", b.getName());
        assertEquals("Final", c.getName());
        assertEquals(0, db.all("", courseId+1).length);
    }

    @Test
    void deletionTest() {
        ExamDb db = new ExamDb();
        db.add("Midterm", 60, courseId, false, new int[]{qId1});
        db.add("Final", 90, courseId, false, new int[]{qId1, qId2});

        db.remove(db.all("Midterm")[0].getId());
        assertEquals("Final", db.all()[0].getName());
    }

    @Test
    void updateTest() {
        ExamDb db = new ExamDb();
        db.update(new Exam(77, "Midterm", 30, courseId, false, new int[]{qId1}));

        db.add("Midterm", 60, courseId, false, new int[]{qId1});
        db.add("Final", 90, courseId, false, new int[]{qId1, qId2});

        db.update(new Exam(db.all("Midterm")[0].getId(), "Midterm", 30, courseId, false, new int[]{qId1}));
        assertEquals(30, db.all()[0].getDuration());
    }
}
