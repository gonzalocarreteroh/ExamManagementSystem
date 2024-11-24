package comp3111.examsystem.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * A database for managing student records in the exam system.
 * Provides functionality for adding, updating, removing, and retrieving students,
 * as well as authentication and searching.
 */
public class StudentDb {
    private int lastId; // Tracks the last assigned student ID.
    private final HashMap<Integer, Student> students; // Stores students by their ID.

    /**
     * Initializes the database with an array of existing students.
     * @param students An array of existing {@link Student} objects to populate the database.
     */
    public StudentDb(Student[] students) {
        lastId = Arrays.stream(students).map(Student::getId).max(Comparator.naturalOrder()).orElse(0);
        this.students = new HashMap<>();
        for (Student c : students) {
            this.students.put(c.getId(), c);
        }
    }

    /**
     * Initializes an empty student database.
     */
    public StudentDb() {
        this(new Student[0]);
    }

    /**
     * Adds a new student to the database.
     * @param username   The username of the student.
     * @param password   The password of the student.
     * @param name       The full name of the student.
     * @param age        The age of the student.
     * @param department The department the student belongs to.
     * @param gender     The gender of the student.
     */
    public void add(String username, String password, String name, int age, String department, Gender gender) {
        lastId += 1;
        students.put(lastId, new Student(lastId, username, password, name, age, department, gender));
    }

    /**
     * Updates an existing student record in the database.
     * @param student The {@link Student} object containing updated information.
     */
    public void update(Student student) {
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
        }
    }

    /**
     * Retrieves a student by their ID.
     * @param id The ID of the student to retrieve.
     * @return The {@link Student} object, or {@code null} if not found.
     */
    public Student get(int id) {
        return students.get(id);
    }

    /**
     * Removes a student from the database by their ID.
     * @param id The ID of the student to remove.
     */
    public void remove(Integer id) {
        students.remove(id);
    }

    /**
     * Authenticates a student based on their username and password.
     * @param username The username of the student.
     * @param password The password of the student.
     * @return {@code true} if the credentials match a student in the database; {@code false} otherwise.
     */
    public boolean login(String username, String password) {
        return students.values().stream()
                .anyMatch(s -> s.getUsername().equals(username) && s.getPassword().equals(password));
    }

    /**
     * Retrieves all students that match the given filters.
     * @param username   The username filter (can be {@code null} or empty for no filtering).
     * @param name       The name filter (can be {@code null} or empty for no filtering).
     * @param department The department filter (can be {@code null} or empty for no filtering).
     * @return An array of {@link Student} objects matching the filters.
     */
    public Student[] all(String username, String name, String department) {
        return students.values().stream().filter(s -> {
            boolean usernameMatch = username == null || username.isEmpty() || s.getUsername().equals(username);
            boolean nameMatch = name == null || name.isEmpty() || s.getName().equals(name);
            boolean departmentMatch = department == null || department.isEmpty() || s.getDepartment().equals(department);
            return usernameMatch && nameMatch && departmentMatch;
        }).toArray(Student[]::new);
    }

    /**
     * Retrieves all students that match the given username and name filters.
     * @param username The username filter (can be {@code null} or empty for no filtering).
     * @param name     The name filter (can be {@code null} or empty for no filtering).
     * @return An array of {@link Student} objects matching the filters.
     */
    public Student[] all(String username, String name) {
        return all(username, name, "");
    }

    /**
     * Retrieves all students that match the given username filter.
     * @param username The username filter (can be {@code null} or empty for no filtering).
     * @return An array of {@link Student} objects matching the filter.
     */
    public Student[] all(String username) {
        return all(username, "");
    }

    /**
     * Retrieves all students in the database.
     * @return An array of all {@link Student} objects.
     */
    public Student[] all() {
        return all("");
    }
}
