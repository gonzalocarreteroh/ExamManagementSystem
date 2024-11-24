package comp3111.examsystem.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * A database for managing teacher records. Provides methods to add, update,
 * retrieve, and remove teachers, as well as to authenticate and filter teacher records.
 */
public class TeacherDb {
    private int lastId; // The ID of the last added teacher
    private final HashMap<Integer, Teacher> teachers; // A map of teacher IDs to teacher objects

    /**
     * Constructs a new TeacherDb with the specified array of teachers.
     * The database initializes with the provided teachers, and the highest ID among them
     * is used as the starting point for assigning new IDs.
     *
     * @param teachers an array of {@link Teacher} objects to initialize the database with
     */
    public TeacherDb(Teacher[] teachers) {
        lastId = Arrays.stream(teachers).map(Teacher::getId).max(Comparator.naturalOrder()).orElse(0);

        this.teachers = new HashMap<>();
        for (Teacher c : teachers) {
            this.teachers.put(c.getId(), c);
        }
    }

    /**
     * Constructs a new, empty TeacherDb.
     */
    public TeacherDb() {
        this(new Teacher[0]);
    }

    /**
     * Adds a new teacher to the database with the specified details.
     *
     * @param username   the username of the teacher
     * @param password   the password of the teacher
     * @param name       the name of the teacher
     * @param age        the age of the teacher
     * @param department the department of the teacher
     * @param position   the position of the teacher
     */
    public void add(String username, String password, String name, int age, String department, String position) {
        lastId += 1;
        teachers.put(lastId, new Teacher(lastId, username, password, name, age, department, position));
    }

    /**
     * Updates an existing teacher's details in the database.
     *
     * @param teacher the {@link Teacher} object containing the updated details
     */
    public void update(Teacher teacher) {
        if (teachers.containsKey(teacher.getId())) {
            teachers.put(teacher.getId(), teacher);
        }
    }

    /**
     * Retrieves a teacher by their ID.
     *
     * @param id the ID of the teacher to retrieve
     * @return the {@link Teacher} object, or {@code null} if no teacher with the specified ID exists
     */
    public Teacher get(int id) {
        return teachers.get(id);
    }

    /**
     * Removes a teacher from the database by their ID.
     *
     * @param id the ID of the teacher to remove
     */
    public void remove(Integer id) {
        teachers.remove(id);
    }

    /**
     * Authenticates a teacher using their username and password.
     *
     * @param username the username of the teacher
     * @param password the password of the teacher
     * @return {@code true} if the username and password match a teacher in the database; {@code false} otherwise
     */
    public boolean login(String username, String password) {
        return teachers.values().stream().anyMatch(t -> t.getUsername().equals(username) && t.getPassword().equals(password));
    }

    /**
     * Retrieves all teachers matching the specified filters.
     *
     * @param username   the username to filter by (empty string for no filter)
     * @param name       the name to filter by (empty string for no filter)
     * @param department the department to filter by (empty string for no filter)
     * @return an array of {@link Teacher} objects matching the filters
     */
    public Teacher[] all(String username, String name, String department) {
        return teachers.values().stream().filter(s -> {
            boolean usernameMatch = username.equals("") || s.getUsername().equals(username);
            boolean nameMatch = name.equals("") || s.getName().equals(name);
            boolean departmentMatch = department.equals("") || s.getDepartment().equals(department);
            return usernameMatch && nameMatch && departmentMatch;
        }).toList().toArray(new Teacher[0]);
    }

    /**
     * Retrieves all teachers matching the specified username and name.
     *
     * @param username the username to filter by (empty string for no filter)
     * @param name     the name to filter by (empty string for no filter)
     * @return an array of {@link Teacher} objects matching the filters
     */
    public Teacher[] all(String username, String name) {
        return all(username, name, "");
    }

    /**
     * Retrieves all teachers matching the specified username.
     *
     * @param username the username to filter by (empty string for no filter)
     * @return an array of {@link Teacher} objects matching the filter
     */
    public Teacher[] all(String username) {
        return all(username, "");
    }

    /**
     * Retrieves all teachers in the database.
     *
     * @return an array of all {@link Teacher} objects
     */
    public Teacher[] all() {
        return all("");
    }
}
