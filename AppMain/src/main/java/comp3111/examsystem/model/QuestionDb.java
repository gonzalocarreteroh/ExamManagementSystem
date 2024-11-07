package comp3111.examsystem.model;

import java.util.*;

public class QuestionDb {
    private int lastId;
    private final HashMap<Integer, Question> questions;

    public QuestionDb(HashMap<Integer, Question> questions) {
        lastId = questions.keySet().stream().max(Comparator.naturalOrder()).orElse(0);
        this.questions = questions;
    }

    public QuestionDb() {
        this(new HashMap<>());
    }

    public void add(String title, String a, String b, String c, String d, String answer, int points) {
        lastId += 1;
        questions.put(lastId, new Question(lastId, title, a, b, c, d, answer, points));
    }

    public void update(Question question) {
        if (questions.containsKey(question.getId())) {
            questions.put(question.getId(), question);
        }
    }

    public Question get(int id) {
        return questions.get(id);
    }

    public void remove(int id) {
        questions.remove(id);
    }

    public Question[] list(String title, Boolean multiple, Integer points) {
        return questions.values().stream().filter(q -> {
            boolean titleMatch = q.getTitle().startsWith(title);
            boolean typeMatch = multiple == null || multiple.equals(q.isMultiple());
            boolean pointsMatch = points == null || points.equals(q.getPoints());
            return titleMatch && typeMatch && pointsMatch;
        }).toList().toArray(new Question[0]);
    }

    public Question[] list() {
        return list("", null, null);
    }

    public int size() {
        return questions.size();
    }
}
