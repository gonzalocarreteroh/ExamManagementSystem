package comp3111.examsystem.model;

/**
 * The `Student` class represents a student in the exam system.
 * It encapsulates the basic information about a student, including their ID, username, password, name, age, department, and gender.
 */
public class Student {
    /**
     * The unique identifier of the student.
     */
    private final int id;

    /**
     * The username of the student.
     */
    private final String username;

    /**
     * The password of the student.
     */
    private final String password;

    /**
     * The name of the student.
     */
    private final String name;

    /**
     * The age of the student.
     */
    private final int age;

    /**
     * The department the student is enrolled in.
     */
    private final String department;

    /**
     * The gender of the student.
     */
    private final Gender gender;

    /**
     * Constructs a new `Student` object with the given parameters.
     *
     * @param id         the unique identifier of the student
     * @param username   the username of the student
     * @param password   the password of the student
     * @param name       the name of the student
     * @param age        the age of the student
     * @param department the department the student is enrolled in
     * @param gender     the gender of the student
     */
    public Student(int id, String username, String password, String name, int age, String department, Gender gender) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.department = department;
        this.gender = gender;
    }

    /**
     * Returns the unique identifier of the student.
     *
     * @return the unique identifier of the student
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the username of the student.
     *
     * @return the username of the student
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the student.
     *
     * @return the password of the student
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the name of the student.
     *
     * @return the name of the student
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the age of the student.
     *
     * @return the age of the student
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns the department the student is enrolled in.
     *
     * @return the department the student is enrolled in
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Returns the gender of the student.
     *
     * @return the gender of the student
     */
    public Gender getGender() {
        return gender;
    }
}