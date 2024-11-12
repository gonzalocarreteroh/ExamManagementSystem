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
        n.put("type", question.getType() == Type.Single ? "Single" : "Multiple");
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
        n.put("id", student.getId());
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
        n.put("id", teacher.getId());
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

    private Course deserializeCourse(JsonNode n) {
        return new Course(
                n.get("id").asInt(),
                n.get("code").asText(),
                n.get("name").asText(),
                n.get("department").asText()
        );
    }

    private CourseDb deserializeCourseDb(JsonNode n) {
        var courses = new Course[n.size()];
        for (int i = 0; i < courses.length; ++i) {
            courses[i] = deserializeCourse(n.get(i));
        }
        return new CourseDb(courses);
    }

    private Exam deserializeExam(JsonNode n) {
        var qn = n.get("questionIds");
        int[] questionIds = new int[qn.size()];
        for (int i = 0; i < questionIds.length; ++i) {
            questionIds[i] = qn.get(i).asInt();
        }

        return new Exam(
                n.get("id").asInt(),
                n.get("name").asText(),
                n.get("duration").asInt(),
                n.get("courseId").asInt(),
                n.get("published").asBoolean(),
                questionIds
        );
    }

    private ExamDb deserializeExamDb(JsonNode n) {
        var exams = new Exam[n.size()];
        for (int i = 0; i < exams.length; ++i) {
            exams[i] = deserializeExam(n.get(i));
        }
        return new ExamDb(exams);
    }

    private Grade deserializeGrade(JsonNode n) {
        return new Grade(n.get("studentId").asInt(), n.get("examId").asInt(), n.get("points").asInt());
    }

    private GradeDb deserializeGradeDb(JsonNode n) {
        var grades = new Grade[n.size()];
        for (int i = 0; i < grades.length; ++i) {
            grades[i] = deserializeGrade(n.get(i));
        }
        return new GradeDb(grades);
    }

    private Manager deserializeManager(JsonNode n) {
        return new Manager(n.get("username").asText(), n.get("password").asText());
    }

    private ManagerDb deserializeManagerDb(JsonNode n) {
        var managers = new Manager[n.size()];
        for (int i = 0; i < managers.length; ++i) {
            managers[i] = deserializeManager(n.get(i));
        }
        return new ManagerDb(managers);
    }

    private Question deserializeQuestion(JsonNode n) {
        return new Question(
                n.get("id").asInt(),
                n.get("title").asText(),
                n.get("a").asText(),
                n.get("b").asText(),
                n.get("c").asText(),
                n.get("d").asText(),
                n.get("answer").asText(),
                n.get("type").asText().equals("single") ? Type.Single : Type.Multiple,
                n.get("points").asInt()
        );
    }

    private QuestionDb deserializeQuestionDb(JsonNode n) {
        var question = new Question[n.size()];
        for (int i = 0; i < question.length; ++i) {
            question[i] = deserializeQuestion(n.get(i));
        }
        return new QuestionDb(question);
    }

    private Student deserializeStudent(JsonNode n) {
        return new Student(
                n.get("id").asInt(),
                n.get("username").asText(),
                n.get("password").asText(),
                n.get("name").asText(),
                n.get("age").asInt(),
                n.get("department").asText(),
                n.get("gender").asText().equals("male") ? Gender.Male : Gender.Female
        );
    }

    private StudentDb deserializeStudentDb(JsonNode n) {
        var students = new Student[n.size()];
        for (int i = 0; i < students.length; ++i) {
            students[i] = deserializeStudent(n.get(i));
        }
        return new StudentDb(students);
    }

    private Teacher deserializeTeacher(JsonNode n) {
        return new Teacher(
                n.get("id").asInt(),
                n.get("username").asText(),
                n.get("password").asText(),
                n.get("name").asText(),
                n.get("age").asInt(),
                n.get("department").asText(),
                n.get("position").asText()
        );
    }

    private TeacherDb deserializeTeacherDb(JsonNode n) {
        var teachers = new Teacher[n.size()];
        for (int i = 0; i < teachers.length; ++i) {
            teachers[i] = deserializeTeacher(n.get(i));
        }
        return new TeacherDb(teachers);
    }

    public DataCollection deserialize(String json) throws JsonProcessingException {
        var tree = mapper.readTree(json);
        var courseDb = deserializeCourseDb(tree.get("courses"));
        var examDb = deserializeExamDb(tree.get("exams"));
        var gradeDb = deserializeGradeDb(tree.get("grades"));
        var managerDb = deserializeManagerDb(tree.get("managers"));
        var questionDb = deserializeQuestionDb(tree.get("questions"));
        var studentDb = deserializeStudentDb(tree.get("students"));
        var teacherDb = deserializeTeacherDb(tree.get("teachers"));
        return new DataCollection(courseDb, examDb, gradeDb, managerDb, questionDb, studentDb, teacherDb);
    }
}
