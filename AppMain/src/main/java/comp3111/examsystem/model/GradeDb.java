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

        Grade newGrade = new Grade(studentId, examId, points);
        grades.get(studentId).put(examId, newGrade);
    }

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

    public Grade[] all() {
        return all(null, null);
    }
}
