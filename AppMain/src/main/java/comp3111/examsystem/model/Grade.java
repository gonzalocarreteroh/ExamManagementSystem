package comp3111.examsystem.model;

/**
 * Represents a grade earned by a student on an exam.
 */
public class Grade {
    private final int studentId;
    private final int examId;
    private final int points;

    /**
     * Constructs a new Grade object with the given student ID, exam ID, and points.
     *
     * @param studentId the ID of the student
     * @param examId    the ID of the exam
     * @param points    the points earned by the student on the exam
     */
    public Grade(int studentId, int examId, int points) {
        this.studentId = studentId;
        this.examId = examId;
        this.points = points;
    }

    /**
     * Returns the ID of the student who earned this grade.
     *
     * @return the student ID
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Retrieves the student object associated with this grade from the given student database.
     *
     * @param db the student database to use
     * @return the student object
     */
    public Student getStudent(StudentDb db) {
        return db.get(getStudentId());
    }

    /**
     * Returns the ID of the exam for which this grade was earned.
     *
     * @return the exam ID
     */
    public int getExamId() {
        return examId;
    }

    /**
     * Retrieves the exam object associated with this grade from the given exam database.
     *
     * @param db the exam database to use
     * @return the exam object
     */
    public Exam getExam(ExamDb db) {
        return db.get(getExamId());
    }

    /**
     * Returns the points earned by the student on the exam.
     *
     * @return the points earned
     */
    public int getPoints() {
        return points;
    }
}