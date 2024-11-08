package comp3111.examsystem.model;

/**
 * Represents a course in the exam system.
 * Each course has an ID, code, name, and associated department.
 */
public class Course {
    private final int id;
    private final String code;
    private final String name;
    private final String department;

    /**
     * Constructs a new Course with the specified ID, code, name, and department.
     *
     * @param id         the unique identifier for the course (internal, not displayed to user)
     * @param code       the course code, typically used for identifying the course
     * @param name       the name of the course
     * @param department the department that offers the course
     */
    public Course(int id, String code, String name, String department) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.department = department;
    }

    /**
     * Returns the unique identifier of the course.
     *
     * @return the course ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the course code.
     *
     * @return the course code
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the name of the course.
     *
     * @return the course name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the department offering the course.
     *
     * @return the department name
     */
    public String getDepartment() {
        return department;
    }
}
