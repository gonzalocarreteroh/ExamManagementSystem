package comp3111.examsystem.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Represents a database management system for exams in an examination system.
 * This class provides functionality to store, retrieve, update and filter exam records.
 */
public class ExamDb {
    /** Tracks the last used exam ID for auto-incrementing IDs */
    private int lastId;
    /** Stores exam objects with their IDs as keys */
    private final HashMap<Integer, Exam> exams;

    /**
     * Constructs an ExamDb with an initial set of exams.
     * Sets the lastId to the highest exam ID in the provided array.
     *
     * @param exams An array of Exam objects to initialize the database
     */
    public ExamDb(Exam[] exams) {
        lastId = Arrays.stream(exams).map(Exam::getId).max(Comparator.naturalOrder()).orElse(0);

        this.exams = new HashMap<>();
        for (Exam c : exams) {
            int examId = c.getId();
            this.exams.put(examId, c);
        }
    }

    /**
     * Constructs an empty ExamDb.
     */
    public ExamDb() {
        this(new Exam[0]);
    }

    /**
     * Adds a new exam to the database with an auto-generated ID.
     *
     * @param name The name of the exam
     * @param duration The duration of the exam in minutes
     * @param courseId The ID of the course this exam belongs to
     * @param published Whether the exam is published
     * @param questionIds Array of question IDs included in this exam
     */
    public void add(String name, int duration, int courseId, boolean published, int[] questionIds) {
        lastId += 1;
        Exam exam = new Exam(lastId, name, duration, courseId, published, questionIds);
        exams.put(lastId, exam);
    }

    /**
     * Updates an existing exam in the database.
     * If the exam ID doesn't exist, no action is taken.
     *
     * @param exam The exam object containing updated information
     */
    public void update(Exam exam) {
        int examId = exam.getId();
        if (exams.containsKey(examId)) {
            exams.put(examId, exam);
        }
    }

    /**
     * Retrieves an exam by its ID.
     *
     * @param id The ID of the exam to retrieve
     * @return The exam object if found, null otherwise
     */
    public Exam get(int id) {
        return exams.get(id);
    }

    /**
     * Removes an exam from the database.
     *
     * @param id The ID of the exam to remove
     */
    public void remove(int id) {
        exams.remove(id);
    }

    /**
     * Retrieves all exams matching the specified filters.
     *
     * @param name The name to filter by (null or empty string for no filter)
     * @param courseId The course ID to filter by (null for no filter)
     * @param published The published status to filter by (null for no filter)
     * @return Array of exams matching all specified filters
     */
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

    /**
     * Retrieves all exams matching the specified name and course ID filters.
     *
     * @param name The name to filter by (null or empty string for no filter)
     * @param courseId The course ID to filter by (null for no filter)
     * @return Array of exams matching the specified filters
     */
    public Exam[] all(String name, Integer courseId) {
        return all(name, courseId, null);
    }

    /**
     * Retrieves all exams matching the specified name filter.
     *
     * @param name The name to filter by (null or empty string for no filter)
     * @return Array of exams matching the name filter
     */
    public Exam[] all(String name) {
        return all(name, null);
    }

    /**
     * Retrieves all exams in the database.
     *
     * @return Array of all exams
     */
    public Exam[] all() {
        return all("");
    }
}