package comp3111.examsystem.model;

/**
 * A container class that holds all database objects for the exam system.
 * This class serves as a central point of access to all data storage components
 * of the application, including courses, exams, grades, users, and questions.
 */
public class DataCollection {
    /** Database for storing and managing course information */
    private final CourseDb courses;

    /** Database for storing and managing exam information */
    private final ExamDb exams;

    /** Database for storing and managing student grades */
    private final GradeDb grades;

    /** Database for storing and managing system manager information */
    private final ManagerDb managers;

    /** Database for storing and managing exam questions */
    private final QuestionDb questions;

    /** Database for storing and managing student information */
    private final StudentDb students;

    /** Database for storing and managing teacher information */
    private final TeacherDb teachers;

    /**
     * Constructs a new DataCollection with all required database components.
     *
     * @param courses   The database containing course information
     * @param exams     The database containing exam information
     * @param grades    The database containing grade information
     * @param managers  The database containing manager information
     * @param questions The database containing question information
     * @param students  The database containing student information
     * @param teachers  The database containing teacher information
     */
    public DataCollection(CourseDb courses, ExamDb exams, GradeDb grades,
                          ManagerDb managers, QuestionDb questions, StudentDb students,
                          TeacherDb teachers) {
        this.courses = courses;
        this.exams = exams;
        this.grades = grades;
        this.managers = managers;
        this.questions = questions;
        this.students = students;
        this.teachers = teachers;
    }

    /**
     * Returns the database containing course information.
     * @return The course database
     */
    public CourseDb getCourses() {
        return courses;
    }

    /**
     * Returns the database containing exam information.
     * @return The exam database
     */
    public ExamDb getExams() {
        return exams;
    }

    /**
     * Returns the database containing grade information.
     * @return The grade database
     */
    public GradeDb getGrades() {
        return grades;
    }

    /**
     * Returns the database containing manager information.
     * @return The manager database
     */
    public ManagerDb getManagers() {
        return managers;
    }

    /**
     * Returns the database containing question information.
     * @return The question database
     */
    public QuestionDb getQuestions() {
        return questions;
    }

    /**
     * Returns the database containing student information.
     * @return The student database
     */
    public StudentDb getStudents() {
        return students;
    }

    /**
     * Returns the database containing teacher information.
     * @return The teacher database
     */
    public TeacherDb getTeachers() {
        return teachers;
    }
}