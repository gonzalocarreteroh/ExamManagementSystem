package comp3111.examsystem.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataSerializerTest {
    @Test
    void serializerTest() throws JsonProcessingException {
        var courses = new CourseDb(new Course[]{new Course(4, "COMP", "Programming I", "CS")});
        var exams = new ExamDb(new Exam[]{new Exam(3, "Midterm", 60, 4, false, new int[]{1})});
        var grades = new GradeDb(new Grade[]{new Grade(1, 3, 95)});
        var managers = new ManagerDb(new Manager[]{new Manager("john.smith", "123456")});
        var questions = new QuestionDb(new Question[]{new Question(1, "1+1=", "1", "2", "11", "10", "B", Type.Single, 4)});
        var students = new StudentDb(new Student[]{new Student(1, "john@ust.hk", "123456", "John", 20, "CS", Gender.Male), new Student(2, "jane@ust.hk", "123456", "Jane", 22, "CS", Gender.Female)});
        var teachers = new TeacherDb(new Teacher[]{new Teacher(1, "john@ust.hk", "123456", "John", 20, "CS", "Assistant Professor")});

        var collection = new DataCollection(courses, exams, grades, managers, questions, students, teachers);
        var string = """
                        {"courses":[{"id":4,"code":"COMP","name":"Programming I","department":"CS"}],"exams":[{"id":3,"name":"Midterm","duration":60,"courseId":4,"published":false,"questionIds":[1]}],"grades":[{"examId":3,"studentId":1,"points":95}],"managers":[{"username":"john.smith","password":"123456"}],"questions":[{"id":1,"title":"1+1=","a":"1","b":"2","c":"11","d":"10","answer":"B","type":"Single","points":4}],"students":[{"id":1,"username":"john@ust.hk","password":"123456","name":"John","age":20,"department":"CS","gender":"male"},{"id":2,"username":"jane@ust.hk","password":"123456","name":"Jane","age":22,"department":"CS","gender":"female"}],"teachers":[{"id":1,"username":"john@ust.hk","password":"123456","name":"John","age":20,"department":"CS","position":"Assistant Professor"}]}""";

        DataSerializer ds = new DataSerializer();
        assertEquals(string, ds.serialize(collection));
        assertEquals(string, ds.serialize(ds.deserialize(ds.serialize(collection))));
    }
}