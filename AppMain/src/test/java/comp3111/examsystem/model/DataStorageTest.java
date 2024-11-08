package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataStorageTest {
    @Test
    void successTest() throws IOException {
        var courses = new CourseDb(new Course[]{new Course(4, "COMP", "Programming I", "CS")});
        var exams = new ExamDb(new Exam[]{new Exam(3, "Midterm", 60, 4, false, new int[]{1})});
        var grades = new GradeDb(new Grade[]{new Grade(1, 3, 95)});
        var managers = new ManagerDb(new Manager[]{new Manager("john.smith", "123456")});
        var questions = new QuestionDb(new Question[]{new Question(1, "1+1=", "1", "2", "11", "10", "B", 4)});
        var students = new StudentDb(new Student[]{new Student(1, "john@ust.hk", "123456", "John", 20, "CS", Gender.Male), new Student(2, "jane@ust.hk", "123456", "Jane", 22, "CS", Gender.Female)});
        var teachers = new TeacherDb(new Teacher[]{new Teacher(1, "john@ust.hk", "123456", "John", 20, "CS", "Assistant Professor")});

        var collection = new DataCollection(courses, exams, grades, managers, questions, students, teachers);
        DataSerializer serializer = new DataSerializer();

        var tempPath = Files.createTempFile("", "").toRealPath().toString();
        DataStorage storage = new DataStorage(tempPath);
        storage.store(collection);
        assertEquals(serializer.serialize(collection), serializer.serialize(storage.load()));
    }

    @Test
    void errorTest() throws IOException {
        var courses = new CourseDb(new Course[]{new Course(4, "COMP", "Programming I", "CS")});
        var exams = new ExamDb(new Exam[]{new Exam(3, "Midterm", 60, 4, false, new int[]{1})});
        var grades = new GradeDb(new Grade[]{new Grade(1, 3, 95)});
        var managers = new ManagerDb(new Manager[]{new Manager("john.smith", "123456")});
        var questions = new QuestionDb(new Question[]{new Question(1, "1+1=", "1", "2", "11", "10", "B", 4)});
        var students = new StudentDb(new Student[]{new Student(1, "john@ust.hk", "123456", "John", 20, "CS", Gender.Male), new Student(2, "jane@ust.hk", "123456", "Jane", 22, "CS", Gender.Female)});
        var teachers = new TeacherDb(new Teacher[]{new Teacher(1, "john@ust.hk", "123456", "John", 20, "CS", "Assistant Professor")});
        var collection = new DataCollection(courses, exams, grades, managers, questions, students, teachers);

        DataStorage storage = new DataStorage("this/path/does\\not//exist");

        boolean failStore = false;
        try {
            storage.store(collection);
        } catch (RuntimeException e) {
            failStore = true;
        }
        assertTrue(failStore);

        boolean failLoad = false;
        try {
            storage.load();
        } catch (RuntimeException e) {
            failLoad = true;
        }
        assertTrue(failLoad);
    }
}