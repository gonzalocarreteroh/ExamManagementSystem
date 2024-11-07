package comp3111.examsystem.model;

import java.util.*;

public class ManagerDb {
    private final Manager[] managers;

    public ManagerDb(Manager[] managers) {
        this.managers = managers;
    }

    public int size() {
        return managers.length;
    }

    public Manager get(int i) {
        return managers[i];
    }

    public boolean login(String username, String password) {
        return Arrays.stream(managers).anyMatch(m -> m.getUsername().equals(username) && m.getPassword().equals(password));
    }
}
