package comp3111.examsystem.model;

public class Grade {
    private final int studentId;
    private final int examId;
    private final int points;

    public Grade(int studentId, int examId, int points) {
        this.studentId = studentId;
        this.examId = examId;
        this.points = points;
    }

    public int getStudentId() {
        return studentId;
    }

    public Student getStudent(StudentDb db) {
        return db.get(getStudentId());
    }

    public int getExamId() {
        return examId;
    }

    public Exam getExam(ExamDb db) {
        return db.get(getExamId());
    }

    public int getPoints() {
        return points;
    }
}
