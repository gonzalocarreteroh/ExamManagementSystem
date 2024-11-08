package comp3111.examsystem.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataSerializer {
    private final ObjectMapper mapper;

    public DataSerializer() {
        mapper = new ObjectMapper();
    }

    private JsonNode serialize(Course course) {
        var n = mapper.createObjectNode();
        n.put("id", course.getId());
        n.put("code", course.getCode());
        n.put("name", course.getName());
        n.put("department", course.getDepartment());
        return n;
    }

    private JsonNode serialize(CourseDb courseDb) {
        var n = mapper.createArrayNode();
        for (var course : courseDb.all()) {
            n.add(serialize(course));
        }
        return n;
    }

    private JsonNode serialize(Exam exam) {
        var n = mapper.createObjectNode();
        n.put("id", exam.getId());
        n.put("name", exam.getName());
        n.put("duration", exam.getDuration());
        n.put("courseId", exam.getCourseId());
        n.put("published", exam.getPublished());

        var arr = mapper.createArrayNode();
        for (int id : exam.getQuestionIds()) {
            arr.add(id);
        }
        n.put("questionIds", arr);
        return n;
    }

    private JsonNode serialize(ExamDb examDb) {
        var n = mapper.createArrayNode();
        for (var exam : examDb.all()) {
            n.add(serialize(exam));
        }
        return n;
    }

    private JsonNode serialize(Grade grade) {
        var n = mapper.createObjectNode();
        n.put("examId", grade.getExamId());
        n.put("studentId", grade.getStudentId());
        n.put("points", grade.getPoints());
        return n;
    }

    private JsonNode serialize(GradeDb gradeDb) {
        var n = mapper.createArrayNode();
        for (var grade : gradeDb.all()) {
            n.add(serialize(grade));
        }
        return n;
    }

    private JsonNode serialize(Manager manager) {
        var n = mapper.createObjectNode();
        n.put("username", manager.getUsername());
        n.put("password", manager.getPassword());
        return n;
    }

    private JsonNode serialize(ManagerDb managerDb) {
        var n = mapper.createArrayNode();
        for (int i = 0; i < managerDb.size(); ++i) {
            n.add(serialize(managerDb.get(i)));
        }
        return n;
    }

    private JsonNode serialize(Question question) {
        var n = mapper.createObjectNode();
        n.put("id", question.getId());
        n.put("title", question.getTitle());
        n.put("a", question.getA());
        n.put("b", question.getB());
        n.put("c", question.getC());
        n.put("d", question.getD());
        n.put("answer", question.getAnswer());
        n.put("points", question.getPoints());
        return n;
    }

    private JsonNode serialize(QuestionDb questionDb) {
        var n = mapper.createArrayNode();
        for (var question : questionDb.all()) {
            n.add(serialize(question));
        }
        return n;
    }

    private JsonNode serialize(Student student) {
        var n = mapper.createObjectNode();
        n.put("username", student.getUsername());
        n.put("password", student.getPassword());
        n.put("name", student.getName());
        n.put("age", student.getAge());
        n.put("department", student.getDepartment());
        n.put("gender", student.getGender() == Gender.Male ? "male" : "female");
        return n;
    }

    private JsonNode serialize(StudentDb studentDb) {
        var n = mapper.createArrayNode();
        for (var student : studentDb.all()) {
            n.add(serialize(student));
        }
        return n;
    }

    private JsonNode serialize(Teacher teacher) {
        var n = mapper.createObjectNode();
        n.put("username", teacher.getUsername());
        n.put("password", teacher.getPassword());
        n.put("name", teacher.getName());
        n.put("age", teacher.getAge());
        n.put("department", teacher.getDepartment());
        n.put("position", teacher.getPosition());
        return n;
    }

    private JsonNode serialize(TeacherDb teacherDb) {
        var n = mapper.createArrayNode();
        for (var teacher : teacherDb.all()) {
            n.add(serialize(teacher));
        }
        return n;
    }

    public String serialize(DataCollection data) throws JsonProcessingException {
        var n = mapper.createObjectNode();
        n.put("courses", serialize(data.getCourses()));
        n.put("exams", serialize(data.getExams()));
        n.put("grades", serialize(data.getGrades()));
        n.put("managers", serialize(data.getManagers()));
        n.put("questions", serialize(data.getQuestions()));
        n.put("students", serialize(data.getStudents()));
        n.put("teachers", serialize(data.getTeachers()));

        return mapper.writeValueAsString(n);
    }
}
