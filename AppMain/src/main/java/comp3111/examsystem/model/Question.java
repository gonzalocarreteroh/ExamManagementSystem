package comp3111.examsystem.model;

/**
 * Represents a question in the exam system, including its ID, title, multiple choice options,
 * correct answer, and point value.
 */
public class Question {
    private final int id;
    private final String title;
    private final String a, b, c, d;
    private final String answer;
    private final int points;

    /**
     * Constructs a Question instance with the specified details.
     *
     * @param id      the unique identifier of the question
     * @param title   the title or text of the question
     * @param a       the text of the first answer option
     * @param b       the text of the second answer option
     * @param c       the text of the third answer option
     * @param d       the text of the fourth answer option
     * @param answer  the correct answer option (can be a letter: 'a', 'b', 'c', or 'd')
     * @param points  the number of points this question is worth
     */
    public Question(int id, String title, String a, String b, String c, String d, String answer, int points) {
        this.id = id;
        this.title = title;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.answer = answer;
        this.points = points;
    }

    /**
     * Returns the unique identifier of the question.
     *
     * @return the question ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the title or text of the question.
     *
     * @return the question title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the text of the first answer option.
     *
     * @return the first answer option
     */
    public String getA() {
        return a;
    }

    /**
     * Returns the text of the second answer option.
     *
     * @return the second answer option
     */
    public String getB() {
        return b;
    }

    /**
     * Returns the text of the third answer option.
     *
     * @return the third answer option
     */
    public String getC() {
        return c;
    }

    /**
     * Returns the text of the fourth answer option.
     *
     * @return the fourth answer option
     */
    public String getD() {
        return d;
    }

    /**
     * Returns the correct answer option (either 'a', 'b', 'c', or 'd').
     *
     * @return the correct answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Returns the number of points this question is worth.
     *
     * @return the points value of the question
     */
    public int getPoints() {
        return points;
    }

    /**
     * Determines whether this question is a single-choice question (i.e., has a single correct answer).
     *
     * @return {@code true} if the answer is a single option (a, b, c, or d); {@code false} if it is multiple-choice
     */
    public boolean isSingle() {
        return answer.length() == 1;
    }

    /**
     * Determines whether this question is a multiple-choice question (i.e., has multiple correct answers).
     *
     * @return {@code true} if the question is multiple-choice; {@code false} if it is a single-choice question
     */
    public boolean isMultiple() {
        return !isSingle();
    }
}
