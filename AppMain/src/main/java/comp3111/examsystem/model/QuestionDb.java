package comp3111.examsystem.model;

import java.util.*;

/**
 * A database management system for exam questions.
 * This class provides functionality to store, retrieve, update and filter exam questions.
 */
public class QuestionDb {
    private int lastId;
    private final HashMap<Integer, Question> questions;

    /**
     * Constructs a new QuestionDb with an initial set of questions.
     * The lastId is set to the highest ID among the provided questions.
     *
     * @param questions An array of Question objects to initialize the database with.
     */
    public QuestionDb(Question[] questions) {
        lastId = Arrays.stream(questions).map(Question::getId).max(Comparator.naturalOrder()).orElse(0);

        this.questions = new HashMap<>();
        for (Question c : questions) {
            this.questions.put(c.getId(), c);
        }
    }

    /**
     * Constructs an empty QuestionDb.
     */
    public QuestionDb() {
        this(new Question[0]);
    }

    /**
     * Adds a new question to the database with auto-generated ID.
     *
     * @param title The question text
     * @param a First answer choice
     * @param b Second answer choice
     * @param c Third answer choice
     * @param d Fourth answer choice
     * @param answer The correct answer
     * @param type The question type
     * @param points Points awarded for correct answer
     */
    public void add(String title, String a, String b, String c, String d, String answer, Type type, int points) {
        lastId += 1;
        questions.put(lastId, new Question(lastId, title, a, b, c, d, answer, type, points));
    }

    /**
     * Updates an existing question in the database.
     * If the question ID doesn't exist, no action is taken.
     *
     * @param question The updated Question object
     */
    public void update(Question question) {
        if (questions.containsKey(question.getId())) {
            questions.put(question.getId(), question);
        }
    }

    /**
     * Retrieves a question by its ID.
     *
     * @param id The ID of the question to retrieve
     * @return The Question object if found, null otherwise
     */
    public Question get(int id) {
        return questions.get(id);
    }

    /**
     * Removes a question from the database.
     *
     * @param id The ID of the question to remove
     */
    public void remove(int id) {
        questions.remove(id);
    }

    /**
     * Retrieves questions matching the specified criteria.
     * All filter parameters are optional - null or empty values will match all questions.
     *
     * @param title Filter questions containing this text in their title (case-sensitive).
     *              Null or empty string matches all titles.
     * @param multiple Filter questions by type. Null matches all types.
     * @param points Filter questions by point value. Null or -1 matches all point values.
     * @return Array of Questions matching all specified criteria
     */
    public Question[] all(String title, Type multiple, Integer points) {
        return questions.values().stream().filter(q -> {
            boolean titleMatch = title == null || title.isEmpty() || q.getTitle().contains(title);
            boolean multipleMatch = multiple == null || q.getType() == multiple;
            boolean pointsMatch = points == null || points.equals(-1) || q.getPoints() == points;
            return titleMatch && multipleMatch && pointsMatch;
        }).toList().toArray(new Question[0]);
    }

    /**
     * Retrieves questions matching the specified title and type.
     *
     * @param title Filter questions containing this text in their title
     * @param multiple Filter questions by type
     * @return Array of Questions matching the criteria
     */
    public Question[] all(String title, Type multiple) {
        return all(title, multiple, null);
    }

    /**
     * Retrieves questions matching the specified title.
     *
     * @param title Filter questions containing this text in their title
     * @return Array of Questions matching the title
     */
    public Question[] all(String title) {
        return all(title, null);
    }

    /**
     * Retrieves all questions in the database.
     *
     * @return Array containing all Questions
     */
    public Question[] all() {
        return all(null);
    }
}