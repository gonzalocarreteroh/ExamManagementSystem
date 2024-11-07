package comp3111.examsystem.model;

import java.util.Comparator;
import java.util.HashMap;

public class ExamDb {
    private int lastId;
    private final HashMap<Integer, Exam> exams;

    public ExamDb(HashMap<Integer, Exam> exams) {
        lastId = exams.keySet().stream().max(Comparator.naturalOrder()).orElse(0);
        this.exams = exams;
    }

    public ExamDb() {
        this(new HashMap<>());
    }

    public void add(String name, int duration, int courseId, boolean published, int[] questionIds) {
        lastId += 1;
        exams.put(lastId, new Exam(lastId, name, duration, courseId, published, questionIds));
    }

    public void update(Exam exam) {
        if (exams.containsKey(exam.getId())) {
            exams.put(exam.getId(), exam);
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
            boolean nameMatch = name == "" || name.equals(s.getName());
            boolean courseIdMatch = courseId == null || courseId.equals(s.getCourseId());
            boolean publishedMatch = published == null || published.equals(s.getPublished());
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
