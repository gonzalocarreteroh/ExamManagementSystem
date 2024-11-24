package comp3111.examsystem.model;

import java.util.HashMap;

/**
 * A database management class for storing and retrieving student exam grades.
 * This class provides functionality to store grades in a two-level HashMap structure,
 * where grades are indexed first by student ID and then by exam ID.
 */
public class GradeDb {
    /**
     * Two-level HashMap storing grades: outer map keys are student IDs,
     * inner map keys are exam IDs, and values are Grade objects.
     */
    private final HashMap<Integer, HashMap<Integer, Grade>> grades;

    /**
     * Constructs a new GradeDb and initializes it with an array of grades.
     *
     * @param grades An array of Grade objects to be added to the database.
     *              Each grade will be stored and indexed by both student ID and exam ID.
     */
    public GradeDb(Grade[] grades) {
        this();
        for (Grade g : grades) {
            add(g.getStudentId(), g.getExamId(), g.getPoints());
        }
    }

    /**
     * Constructs an empty GradeDb.
     */
    public GradeDb() {
        grades = new HashMap<>();
    }

    /**
     * Adds a new grade to the database.
     * If a grade already exists for the given student ID and exam ID combination,
     * it will be overwritten with the new grade.
     *
     * @param studentId The ID of the student for whom to add the grade
     * @param examId The ID of the exam for which to add the grade
     * @param points The number of points achieved in the exam
     */
    public void add(int studentId, int examId, int points) {
        if (!grades.containsKey(studentId)) {
            grades.put(studentId, new HashMap<>());
        }

        Grade newGrade = new Grade(studentId, examId, points);
        grades.get(studentId).put(examId, newGrade);
    }

    /**
     * Retrieves grades from the database based on optional student ID and exam ID filters.
     * If both IDs are null, returns all grades.
     * If only studentId is provided, returns all grades for that student.
     * If only examId is provided, returns all grades for that exam.
     * If both IDs are provided, returns the single matching grade or an empty array if none exists.
     *
     * @param studentId The ID of the student to filter by, or null to not filter by student
     * @param examId The ID of the exam to filter by, or null to not filter by exam
     * @return An array of Grade objects matching the specified criteria
     */
    public Grade[] all(Integer studentId, Integer examId) {
        if (studentId != null) {
            var studentGrades = grades.getOrDefault(studentId, new HashMap<>());
            if (examId != null) {
                Grade grade = studentGrades.get(examId);
                return grade == null ? new Grade[0] : new Grade[] {grade};
            } else {
                return studentGrades.values().stream().toList().toArray(new Grade[0]);
            }

        } else {
            var gs = grades.values().stream().flatMap(gradeMap -> gradeMap.values().stream());
            if (examId != null) {
                gs = gs.filter(grade -> {
                    int gradeExamId = grade.getExamId();
                    return gradeExamId == examId;
                });
            }
            return gs.toList().toArray(new Grade[0]);
        }
    }

    /**
     * Retrieves all grades stored in the database.
     * This is equivalent to calling all(null, null).
     *
     * @return An array containing all Grade objects in the database
     */
    public Grade[] all() {
        return all(null, null);
    }
}