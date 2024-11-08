package comp3111.examsystem.model;

public class SystemData {
    private final CourseDb courses;
    private final ExamDb exams;
    private final GradeDb grades;
    private final ManagerDb managers;
    private final QuestionDb questions;
    private final StudentDb students;
    private final TeacherDb teachers;

    public SystemData(CourseDb courses, ExamDb exams, GradeDb grades, ManagerDb managers, QuestionDb questions, StudentDb students, TeacherDb teachers) {
        this.courses = courses;
        this.exams = exams;
        this.grades = grades;
        this.managers = managers;
        this.questions = questions;
        this.students = students;
        this.teachers = teachers;
    }

    public CourseDb getCourses() {
        return courses;
    }

    public ExamDb getExams() {
        return exams;
    }

    public GradeDb getGrades() {
        return grades;
    }

    public ManagerDb getManagers() {
        return managers;
    }

    public QuestionDb getQuestions() {
        return questions;
    }

    public StudentDb getStudents() {
        return students;
    }

    public TeacherDb getTeachers() {
        return teachers;
    }
}
