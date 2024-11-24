package comp3111.examsystem.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * A database management system for Course objects.
 * Provides functionality to store, retrieve, update, and filter course records.
 */
public class CourseDb {
    /** The last used course ID in the database */
    private int lastId;

    /** HashMap storing course objects with their IDs as keys */
    private final HashMap<Integer, Course> courses;

    /**
     * Constructs a CourseDb with an initial array of courses.
     * Sets the lastId to the highest course ID in the provided array.
     *
     * @param courses An array of Course objects to initialize the database
     */
    public CourseDb(Course[] courses) {
        lastId = Arrays.stream(courses).map(Course::getId).max(Comparator.naturalOrder()).orElse(0);

        this.courses = new HashMap<>();
        for (Course c : courses) {
            int courseId = c.getId();
            this.courses.put(courseId, c);
        }
    }

    /**
     * Constructs an empty CourseDb.
     */
    public CourseDb() {
        this(new Course[0]);
    }

    /**
     * Adds a new course to the database with an automatically generated ID.
     *
     * @param code The course code
     * @param name The course name
     * @param department The department offering the course
     */
    public void add(String code, String name, String department) {
        lastId += 1;
        Course course = new Course(lastId, code, name, department);
        courses.put(lastId, course);
    }

    /**
     * Updates an existing course in the database.
     * If the course ID exists, creates and stores a new copy of the course.
     *
     * @param course The course object containing updated information
     */
    public void update(Course course) {
        int courseId = course.getId();
        if (courses.containsKey(courseId)) {
            Course cclone = new Course(courseId, course.getCode(), course.getName(), course.getDepartment());
            courses.put(courseId, cclone);
        }
    }

    /**
     * Retrieves a course by its ID.
     *
     * @param id The ID of the course to retrieve
     * @return The Course object if found, null otherwise
     */
    public Course get(int id) {
        return courses.get(id);
    }

    /**
     * Removes a course from the database.
     *
     * @param id The ID of the course to remove
     */
    public void remove(Integer id) {
        courses.remove(id);
    }

    /**
     * Retrieves all courses matching the specified filters.
     * Filters are applied based on code, name, and department.
     * Empty or null filter values are ignored.
     *
     * @param code The course code to filter by (exact match)
     * @param name The course name to filter by (prefix match)
     * @param department The department to filter by (prefix match)
     * @return An array of Course objects matching all specified filters
     */
    public Course[] all(String code, String name, String department) {
        return courses.values().stream().filter(s -> {
            return (code == null || code.isEmpty() || s.getCode().equals(code))
                    && (name.isEmpty() || s.getName().startsWith(name))
                    && (department.isEmpty() || s.getDepartment().startsWith(department));
        }).toList().toArray(new Course[0]);
    }

    /**
     * Retrieves all courses matching the specified code and name filters.
     *
     * @param code The course code to filter by
     * @param name The course name to filter by
     * @return An array of matching Course objects
     */
    public Course[] all(String code, String name) {
        return all(code, name, "");
    }

    /**
     * Retrieves all courses matching the specified code filter.
     *
     * @param code The course code to filter by
     * @return An array of matching Course objects
     */
    public Course[] all(String code) {
        return all(code, "");
    }

    /**
     * Retrieves all courses in the database.
     *
     * @return An array of all Course objects
     */
    public Course[] all() {
        return all("");
    }
}