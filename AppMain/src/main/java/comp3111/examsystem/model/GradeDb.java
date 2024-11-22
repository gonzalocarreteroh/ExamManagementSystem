package comp3111.examsystem.model;

import java.util.HashMap;

public class GradeDb {
    private final HashMap<Integer, HashMap<Integer, Grade>> grades;

    public GradeDb(Grade[] grades) {
        this();
        for (Grade g : grades) {
            add(g.getStudentId(), g.getExamId(), g.getPoints());
        }
    }

    public GradeDb() {
        grades = new HashMap<>();
    }

    public void add(int studentId, int examId, int points) {
        if (!grades.containsKey(studentId)) {
            grades.put(studentId, new HashMap<>());
        }
        grades.get(studentId).put(examId, new Grade(studentId, examId, points));
    }

    public Grade get(int studentId, int examId) {
        var studentGrades = grades.get(studentId);
        return studentGrades == null ? null : studentGrades.get(examId);
    }

    public Grade[] all() {
        return grades.values().stream().flatMap(g -> g.values().stream()).toList().toArray(new Grade[0]);
    }
}
