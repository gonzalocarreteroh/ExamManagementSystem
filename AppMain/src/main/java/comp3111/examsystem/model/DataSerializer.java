package comp3111.examsystem.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A class responsible for serializing and deserializing exam system data to/from JSON format.
 * This class handles the conversion of various database objects and their contained entities
 * to JSON representation for persistence and back to Java objects for runtime use.
 */
public class DataSerializer {
    /** The Jackson ObjectMapper instance used for JSON processing */
    private final ObjectMapper mapper;

    /**
     * Constructs a new DataSerializer with a fresh ObjectMapper instance.
     */
    public DataSerializer() {
        mapper = new ObjectMapper();
    }

    /**
     * Serializes a Course object to JSON.
     * @param course The Course object to serialize
     * @return A JsonNode representing the serialized course
     */
    private JsonNode serialize(Course course) {
        var n = mapper.createObjectNode();
        n.put("id", course.getId());
        n.put("code", course.getCode());
        n.put("name", course.getName());
        n.put("department", course.getDepartment());
        return n;
    }

    /**
     * Serializes a CourseDb object to JSON.
     * @param courseDb The CourseDb object to serialize
     * @return A JsonNode containing an array of serialized courses
     */
    private JsonNode serialize(CourseDb courseDb) {
        var n = mapper.createArrayNode();
        for (var course : courseDb.all()) {
            n.add(serialize(course));
        }
        return n;
    }

    /**
     * Serializes an Exam object to JSON.
     * @param exam The Exam object to serialize
     * @return A JsonNode representing the serialized exam
     */
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

    /**
     * Serializes an ExamDb object to JSON.
     * @param examDb The ExamDb object to serialize
     * @return A JsonNode containing an array of serialized exams
     */
    private JsonNode serialize(ExamDb examDb) {
        var n = mapper.createArrayNode();
        for (var exam : examDb.all()) {
            n.add(serialize(exam));
        }
        return n;
    }

    /**
     * Serializes a Grade object to JSON.
     * @param grade The Grade object to serialize
     * @return A JsonNode representing the serialized grade
     */
    private JsonNode serialize(Grade grade) {
        var n = mapper.createObjectNode();
        n.put("examId", grade.getExamId());
        n.put("studentId", grade.getStudentId());
        n.put("points", grade.getPoints());
        return n;
    }

    /**
     * Serializes a GradeDb object to JSON.
     * @param gradeDb The GradeDb object to serialize
     * @return A JsonNode containing an array of serialized grades
     */
    private JsonNode serialize(GradeDb gradeDb) {
        var n = mapper.createArrayNode();
        for (var grade : gradeDb.all()) {
            n.add(serialize(grade));
        }
        return n;
    }

    /**
     * Serializes a Manager object to JSON.
     * @param manager The Manager object to serialize
     * @return A JsonNode representing the serialized manager
     */
    private JsonNode serialize(Manager manager) {
        String username = manager.getUsername();
        String password = manager.getPassword();

        var n = mapper.createObjectNode();

        n.put("username", username);
        n.put("password", password);
        return n;
    }

    /**
     * Serializes a ManagerDb object to JSON.
     * @param managerDb The ManagerDb object to serialize
     * @return A JsonNode containing an array of serialized managers
     */
    private JsonNode serialize(ManagerDb managerDb) {
        var n = mapper.createArrayNode();
        for (int i = 0; i < managerDb.size(); ++i) {
            Manager manager = managerDb.get(i);
            JsonNode serialized = serialize(manager);
            n.add(serialized);
        }
        return n;
    }

    /**
     * Serializes a Question object to JSON.
     * @param question The Question object to serialize
     * @return A JsonNode representing the serialized question
     */
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

    /**
     * Serializes a QuestionDb object to JSON.
     * @param questionDb The QuestionDb object to serialize
     * @return A JsonNode containing an array of serialized questions
     */
    private JsonNode serialize(QuestionDb questionDb) {
        var n = mapper.createArrayNode();
        for (var question : questionDb.all()) {
            JsonNode serialized = serialize(question);
            n.add(serialized);
        }
        return n;
    }

    /**
     * Serializes a Student object to JSON.
     * @param student The Student object to serialize
     * @return A JsonNode representing the serialized student
     */
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

    /**
     * Serializes a StudentDb object to JSON.
     * @param studentDb The StudentDb object to serialize
     * @return A JsonNode containing an array of serialized students
     */
    private JsonNode serialize(StudentDb studentDb) {
        var n = mapper.createArrayNode();
        for (var student : studentDb.all()) {
            JsonNode serialized = serialize(student);
            n.add(serialized);
        }
        return n;
    }

    /**
     * Serializes a Teacher object to JSON.
     * @param teacher The Teacher object to serialize
     * @return A JsonNode representing the serialized teacher
     */
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

    /**
     * Serializes a TeacherDb object to JSON.
     * @param teacherDb The TeacherDb object to serialize
     * @return A JsonNode containing an array of serialized teachers
     */
    private JsonNode serialize(TeacherDb teacherDb) {
        var n = mapper.createArrayNode();
        for (var teacher : teacherDb.all()) {
            JsonNode serialized = serialize(teacher);
            n.add(serialized);
        }
        return n;
    }

    /**
     * Serializes a complete DataCollection object to a JSON string.
     * @param data The DataCollection object to serialize
     * @return A JSON string containing all serialized data
     * @throws JsonProcessingException if there is an error processing the JSON
     */
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

    /**
     * Deserializes a Course from a JSON node.
     * @param n The JsonNode containing course data
     * @return A new Course object
     */
    private Course deserializeCourse(JsonNode n) {
        return new Course(
                n.get("id").asInt(),
                n.get("code").asText(),
                n.get("name").asText(),
                n.get("department").asText()
        );
    }

    /**
     * Deserializes a CourseDb from a JSON node.
     * @param n The JsonNode containing course database data
     * @return A new CourseDb object
     */
    private CourseDb deserializeCourseDb(JsonNode n) {
        var courses = new Course[n.size()];
        for (int i = 0; i < courses.length; ++i) {
            JsonNode node = n.get(i);
            Course deserialized = deserializeCourse(node);
            courses[i] = deserialized;
        }
        return new CourseDb(courses);
    }

    /**
     * Deserializes an Exam from a JSON node.
     * @param n The JsonNode containing exam data
     * @return A new Exam object
     */
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

    /**
     * Deserializes an ExamDb from a JSON node.
     * @param n The JsonNode containing exam database data
     * @return A new ExamDb object
     */
    private ExamDb deserializeExamDb(JsonNode n) {
        var exams = new Exam[n.size()];
        for (int i = 0; i < exams.length; ++i) {
            exams[i] = deserializeExam(n.get(i));
        }
        return new ExamDb(exams);
    }

    /**
     * Deserializes a Grade from a JSON node.
     * @param n The JsonNode containing grade data
     * @return A new Grade object
     */
    private Grade deserializeGrade(JsonNode n) {
        return new Grade(
                n.get("studentId").asInt(),
                n.get("examId").asInt(),
                n.get("points").asInt()
        );
    }

    /**
     * Deserializes a GradeDb from a JSON node.
     * @param n The JsonNode containing grade database data
     * @return A new GradeDb object
     */
    private GradeDb deserializeGradeDb(JsonNode n) {
        var grades = new Grade[n.size()];
        for (int i = 0; i < grades.length; ++i) {
            JsonNode g = n.get(i);
            grades[i] = deserializeGrade(g);
        }
        return new GradeDb(grades);
    }

    /**
     * Deserializes a Manager from a JSON node.
     * @param n The JsonNode containing manager data
     * @return A new Manager object
     */
    private Manager deserializeManager(JsonNode n) {
        return new Manager(
                n.get("username").asText(),
                n.get("password").asText()
        );
    }

    /**
     * Deserializes a ManagerDb from a JSON node.
     * @param n The JsonNode containing manager database data
     * @return A new ManagerDb object
     */
    private ManagerDb deserializeManagerDb(JsonNode n) {
        var managers = new Manager[n.size()];
        for (int i = 0; i < managers.length; ++i) {
            JsonNode m = n.get(i);
            managers[i] = deserializeManager(m);
        }
        return new ManagerDb(managers);
    }

    /**
     * Deserializes a Question from a JSON node.
     * @param n The JsonNode containing question data
     * @return A new Question object
     */
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

    /**
     * Deserializes a QuestionDb from a JSON node.
     * @param n The JsonNode containing question database data
     * @return A new QuestionDb object
     */
    private QuestionDb deserializeQuestionDb(JsonNode n) {
        var question = new Question[n.size()];
        for (int i = 0; i < question.length; ++i) {
            JsonNode q = n.get(i);
            question[i] = deserializeQuestion(q);
        }
        return new QuestionDb(question);
    }

    /**
     * Deserializes a Student from a JSON node.
     * @param n The JsonNode containing student data
     * @return A new Student object
     */
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