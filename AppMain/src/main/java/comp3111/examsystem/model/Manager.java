package comp3111.examsystem.model;

/**
 * Represents a manager in the exam system, with a username and password.
 */
public class Manager {
    private final String username;
    private final String password;

    /**
     * Constructs a new Manager object with the given username and password.
     *
     * @param username the username of the manager
     * @param password the password of the manager
     */
    public Manager(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the username of the manager.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the manager.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}