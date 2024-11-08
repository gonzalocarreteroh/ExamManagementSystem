package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataCollectionTest {
    @Test
    void sdTest() {
        var courses = new CourseDb();
        var exams = new ExamDb();
        var grades = new GradeDb();
        var managers = new ManagerDb(new Manager[]{new Manager("john.smith", "123456")});
        var questions = new QuestionDb();
        var students = new StudentDb();
        var teachers = new TeacherDb();

        var sd = new DataCollection(courses, exams, grades, managers, questions, students, teachers);
        assertEquals(courses, sd.getCourses());
        assertEquals(exams, sd.getExams());
        assertEquals(grades, sd.getGrades());
        assertEquals(managers, sd.getManagers());
        assertEquals(questions, sd.getQuestions());
        assertEquals(students, sd.getStudents());
        assertEquals(teachers, sd.getTeachers());
    }
}