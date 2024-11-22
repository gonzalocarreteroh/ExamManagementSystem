package comp3111.examsystem.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class ExamDb {
    private int lastId;
    private final HashMap<Integer, Exam> exams;

    public ExamDb(Exam[] exams) {
        lastId = Arrays.stream(exams).map(Exam::getId).max(Comparator.naturalOrder()).orElse(0);

        this.exams = new HashMap<>();
        for (Exam c : exams) {
            int examId = c.getId();
            this.exams.put(examId, c);
        }
    }

    public ExamDb() {
        this(new Exam[0]);
    }

    public void add(String name, int duration, int courseId, boolean published, int[] questionIds) {
        lastId += 1;
        Exam exam = new Exam(lastId, name, duration, courseId, published, questionIds);
        exams.put(lastId, exam);
    }

    public void update(Exam exam) {
        int examId = exam.getId();
        if (exams.containsKey(examId)) {
            exams.put(examId, exam);
        }
    }

    public Exam get(int id) {
        return exams.get(id);
    }

    public void remove(int id) {
        exams.remove(id);
    }

    public Exam[] all(String name, Integer courseId, Boolean published) {
        return exams.values().stream().filter(s -> {
            String examName = s.getName();
            boolean nameMatch = name == null || name.isEmpty() || name.equals(examName);
            int examCourseId = s.getCourseId();
            boolean courseIdMatch = courseId == null || courseId.equals(examCourseId);
            boolean isExamPublished = s.getPublished();
            boolean publishedMatch = published == null || published.equals(isExamPublished);
            return nameMatch && courseIdMatch && publishedMatch;
        }).toList().toArray(new Exam[0]);
    }

    public Exam[] all(String name, Integer courseId) {
        return all(name, courseId, null);
    }

    public Exam[] all(String name) {
        return all(name, null);
    }

    public Exam[] all() {
        return all("");
    }
}
