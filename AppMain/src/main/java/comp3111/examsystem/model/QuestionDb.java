package comp3111.examsystem.model;

import java.util.*;

public class QuestionDb {
    private int lastId;
    private final HashMap<Integer, Question> questions;

    public QuestionDb(Question[] questions) {
        lastId = Arrays.stream(questions).map(Question::getId).max(Comparator.naturalOrder()).orElse(0);

        this.questions = new HashMap<>();
        for (Question c : questions) {
            this.questions.put(c.getId(), c);
        }
    }

    public QuestionDb() {
        this(new Question[0]);
    }

    public void add(String title, String a, String b, String c, String d, String answer, Type type, int points) {
        lastId += 1;
        questions.put(lastId, new Question(lastId, title, a, b, c, d, answer, type, points));
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

    public Question[] all(String title, Type multiple, Integer points) {
        return questions.values().stream().filter(q -> {
            boolean titleMatch = title.isEmpty() || q.getTitle().contains(title);
            boolean multipleMatch = multiple == null || q.getType() == multiple;
            boolean pointsMatch = points == null || points.equals(-1) || q.getPoints() == points;
            return titleMatch && multipleMatch && pointsMatch;
        }).toList().toArray(new Question[0]);
    }

    public Question[] all(String title, Type multiple) {
        return all(title, multiple, null);
    }

    public Question[] all(String title) {
        return all(title, null);
    }

    public Question[] all() {
        return all("");
    }
}
