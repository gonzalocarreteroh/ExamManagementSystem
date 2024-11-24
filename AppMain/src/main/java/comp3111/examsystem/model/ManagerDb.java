package comp3111.examsystem.model;

import java.util.*;

/**
 * A database class that manages a collection of Manager objects and handles manager authentication.
 * This class provides methods to access manager data and verify login credentials.
 */
public class ManagerDb {
    /** An array containing all manager objects in the database */
    private final Manager[] managers;

    /**
     * Constructs a new ManagerDb with the specified array of managers.
     *
     * @param managers An array of Manager objects to initialize the database
     */
    public ManagerDb(Manager[] managers) {
        this.managers = managers;
    }

    /**
     * Returns the total number of managers in the database.
     *
     * @return The number of managers stored in the database
     */
    public int size() {
        return managers.length;
    }

    /**
     * Retrieves a manager at the specified index.
     *
     * @param i The index of the manager to retrieve
     * @return The Manager object at the specified index
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     */
    public Manager get(int i) {
        return managers[i];
    }

    /**
     * Verifies manager login credentials against the database.
     * Checks if there exists a manager with matching username and password.
     *
     * @param username The username to verify
     * @param password The password to verify
     * @return true if the credentials match a manager in the database, false otherwise
     */
    public boolean login(String username, String password) {
        return Arrays.stream(managers).anyMatch(m -> {
            String managerUsername = m.getUsername();
            String managerPassword = m.getPassword();
            return managerUsername.equals(username) && managerPassword.equals(password);
        });
    }
}