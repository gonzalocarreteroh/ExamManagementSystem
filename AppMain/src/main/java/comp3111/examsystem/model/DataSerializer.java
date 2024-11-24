package comp3111.examsystem.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class for serializing and deserializing objects related to the exam system.
 * This class provides methods to convert objects to JSON format and vice versa.
 */
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
        String username = manager.getUsername();
        String password = manager.getPassword();

        var n = mapper.createObjectNode();

        n.put("username", username);
        n.put("password", password);
        return n;
    }

    private JsonNode serialize(ManagerDb managerDb) {
        var n = mapper.createArrayNode();
        for (int i = 0; i < managerDb.size(); ++i) {
            Manager manager = managerDb.get(i);
            JsonNode serialized = serialize(manager);
            n.add(serialized);
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
        if (question.getType() == Type.Single) {
            n.put("type", "Single");
        } else {
            n.put("type", "Multiple");
        }
        n.put("points", question.getPoints());
        return n;
    }

    private JsonNode serialize(QuestionDb questionDb) {
        var n = mapper.createArrayNode();
        for (var question : questionDb.all()) {
            JsonNode serialized = serialize(question);
            n.add(serialized);
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
        if (student.getGender() == Gender.Male) {
            n.put("gender", "male");
        } else {
            n.put("gender", "female");
        }
        return n;
    }

    private JsonNode serialize(StudentDb studentDb) {
        var n = mapper.createArrayNode();
        for (var student : studentDb.all()) {
            JsonNode serialized = serialize(student);
            n.add(serialized);
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
            JsonNode serialized = serialize(teacher);
            n.add(serialized);
        }
        return n;
    }

    public String serialize(DataCollection data) throws JsonProcessingException {
        var n = mapper.createObjectNode();

        CourseDb courses = data.getCourses();
        ExamDb exams = data.getExams();
        GradeDb grades = data.getGrades();
        ManagerDb managers = data.getManagers();
        QuestionDb questions = data.getQuestions();
        StudentDb students = data.getStudents();
        TeacherDb teachers = data.getTeachers();

        n.put("courses", serialize(courses));
        n.put("exams", serialize(exams));
        n.put("grades", serialize(grades));
        n.put("managers", serialize(managers));
        n.put("questions", serialize(questions));
        n.put("students", serialize(students));
        n.put("teachers", serialize(teachers));

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
            JsonNode node = n.get(i);
            Course deserialized = deserializeCourse(node);
            courses[i] = deserialized;
        }
        return new CourseDb(courses);
    }

    private Exam deserializeExam(JsonNode n) {
        var qn = n.get("questionIds");
        int[] questionIds = new int[qn.size()];
        for (int i = 0; i < questionIds.length; ++i) {
            JsonNode q = qn.get(i);
            questionIds[i] = q.asInt();
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
        return new Grade(
                n.get("studentId").asInt(),
                n.get("examId").asInt(),
                n.get("points").asInt()
        );
    }

    private GradeDb deserializeGradeDb(JsonNode n) {
        var grades = new Grade[n.size()];
        for (int i = 0; i < grades.length; ++i) {
            JsonNode g = n.get(i);
            grades[i] = deserializeGrade(g);
        }
        return new GradeDb(grades);
    }

    private Manager deserializeManager(JsonNode n) {
        return new Manager(
                n.get("username").asText(),
                n.get("password").asText()
        );
    }

    private ManagerDb deserializeManagerDb(JsonNode n) {
        var managers = new Manager[n.size()];
        for (int i = 0; i < managers.length; ++i) {
            JsonNode m = n.get(i);
            managers[i] = deserializeManager(m);
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
                n.get("type").asText().equals("Single") ? Type.Single : Type.Multiple,
                n.get("points").asInt()
        );
    }

    private QuestionDb deserializeQuestionDb(JsonNode n) {
        var question = new Question[n.size()];
        for (int i = 0; i < question.length; ++i) {
            JsonNode q = n.get(i);
            question[i] = deserializeQuestion(q);
        }
        return new QuestionDb(question);
    }

    private Student deserializeStudent(JsonNode n) {
        if (n.get("gender").asText().equals("male")) {
            return new Student(
                    n.get("id").asInt(),
                    n.get("username").asText(),
                    n.get("password").asText(),
                    n.get("name").asText(),
                    n.get("age").asInt(),
                    n.get("department").asText(),
                    Gender.Male
            );
        } else {
            return new Student(
                    n.get("id").asInt(),
                    n.get("username").asText(),
                    n.get("password").asText(),
                    n.get("name").asText(),
                    n.get("age").asInt(),
                    n.get("department").asText(),
                    Gender.Female
            );
        }
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
            JsonNode t = n.get(i);
            teachers[i] = deserializeTeacher(t);
        }
        return new TeacherDb(teachers);
    }

    public DataCollection deserialize(String json) throws JsonProcessingException {
        var tree = mapper.readTree(json);

        JsonNode courses = tree.get("courses");
        var courseDb = deserializeCourseDb(courses);

        JsonNode exams = tree.get("exams");
        var examDb = deserializeExamDb(exams);

        JsonNode grades = tree.get("grades");
        var gradeDb = deserializeGradeDb(grades);

        JsonNode managers = tree.get("managers");
        var managerDb = deserializeManagerDb(managers);

        JsonNode questions = tree.get("questions");
        var questionDb = deserializeQuestionDb(questions);

        JsonNode students = tree.get("students");
        var studentDb = deserializeStudentDb(students);

        JsonNode teachers = tree.get("teachers");
        var teacherDb = deserializeTeacherDb(teachers);

        return new DataCollection(courseDb, examDb, gradeDb, managerDb, questionDb, studentDb, teacherDb);
    }
}
