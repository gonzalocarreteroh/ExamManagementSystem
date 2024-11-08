package comp3111.examsystem.model;

/**
 * Represents an exam in the system, containing information about its ID, name, duration,
 * associated course, publication status, and questions.
 */
public class Exam {
    private final int id;
    private final String name;
    private final int duration;
    private final int courseId;
    private final boolean published;
    private final int[] questionIds;

    /**
     * Constructs an Exam instance with the specified details.
     *
     * @param id           the unique identifier of the exam
     * @param name         the name of the exam
     * @param duration     the duration of the exam in minutes
     * @param courseId     the ID of the course associated with this exam
     * @param published    the publication status of the exam
     * @param questionIds  an array of IDs for questions included in this exam
     */
    public Exam(int id, String name, int duration, int courseId, boolean published, int[] questionIds) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.courseId = courseId;
        this.published = published;
        this.questionIds = questionIds;
    }

    /**
     * Returns the unique identifier of the exam.
     *
     * @return the exam ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the exam.
     *
     * @return the exam name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the duration of the exam in minutes.
     *
     * @return the duration of the exam
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Returns the ID of the course associated with this exam.
     *
     * @return the course ID
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * Retrieves the course associated with this exam from the database.
     *
     * @param db the course database
     * @return the {@code Course} object corresponding to the course ID of this exam
     */
    public Course getCourse(CourseDb db) {
        return db.get(getCourseId());
    }

    /**
     * Returns the publication status of the exam.
     *
     * @return {@code true} if the exam is published; {@code false} otherwise
     */
    public boolean getPublished() {
        return published;
    }

    /**
     * Returns a copy of the array of question IDs associated with this exam.
     *
     * @return an array of question IDs
     */
    public int[] getQuestionIds() {
        return questionIds.clone();
    }

    /**
     * Retrieves the questions associated with this exam from the database.
     *
     * @param db the question database
     * @return an array of {@code Question} objects corresponding to the question IDs of this exam
     */
    public Question[] getQuestions(QuestionDb db) {
        var questionIds = getQuestionIds();
        Question[] questions = new Question[questionIds.length];
        for (int i = 0; i < questions.length; ++i) {
            questions[i] = db.get(questionIds[i]);
        }
        return questions;
    }
}
