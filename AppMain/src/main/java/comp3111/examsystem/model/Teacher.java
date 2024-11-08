package comp3111.examsystem.model;

/**
 * Represents a teacher in the system, containing information about their ID, username, password,
 * name, age, department, and position.
 */
public class Teacher {
    private final int id;
    private final String username;
    private final String password;
    private final String name;
    private final int age;
    private final String department;
    private final String position;

    /**
     * Constructs a Teacher instance with the specified details.
     *
     * @param id         the unique identifier of the teacher
     * @param username   the username of the teacher, used for login
     * @param password   the password of the teacher, used for login
     * @param name       the full name of the teacher
     * @param age        the age of the teacher
     * @param department the department in which the teacher works
     * @param position   the position or title of the teacher within the department
     */
    public Teacher(int id, String username, String password, String name, int age, String department, String position) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.department = department;
        this.position = position;
    }

    /**
     * Returns the unique identifier of the teacher.
     *
     * @return the teacher ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the password of the teacher.
     *
     * @return the teacher's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username of the teacher.
     *
     * @return the teacher's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the full name of the teacher.
     *
     * @return the teacher's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the age of the teacher.
     *
     * @return the teacher's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns the department where the teacher works.
     *
     * @return the teacher's department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Returns the position or title of the teacher within their department.
     *
     * @return the teacher's position
     */
    public String getPosition() {
        return position;
    }
}
