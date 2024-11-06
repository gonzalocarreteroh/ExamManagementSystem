package comp3111.examsystem.model;

import java.util.*;

public class UserDb {
    private final HashMap<String, User> users;

    public UserDb(HashMap<String, User> users) {
        this.users = users;
    }

    public UserDb() {
        this(new HashMap<>());
    }

    public void add(User user) {
        if (!users.containsKey(user.getUsername())) {
            users.put(user.getUsername(), user);
        }
    }

    public void update(User user) {
        if (users.containsKey(user.getUsername())) {
            users.put(user.getUsername(), user);
        }
    }

    public User get(String username) {
        return users.get(username);
    }

    public void remove(String username) {
        users.remove(username);
    }

    public Teacher[] teachers(String username, String name, String department) {
        return users.values().stream().map(User::getTeacher).filter(Objects::nonNull).filter(s -> {
            return s.getUsername().startsWith(username)
                    && s.getName().startsWith(name)
                    && s.getDepartment().startsWith(department);
        }).toList().toArray(new Teacher[0]);
    }

    public Teacher[] teachers() {
        return users.values().stream().map(User::getTeacher).filter(Objects::nonNull).toList().toArray(new Teacher[0]);
    }

    public Student[] students(String username, String name, String department) {
        return users.values().stream().map(User::getStudent).filter(Objects::nonNull).filter(s -> {
            return s.getUsername().startsWith(username)
                    && s.getName().startsWith(name)
                    && s.getDepartment().startsWith(department);
        }).toList().toArray(new Student[0]);
    }

    public Student[] students() {
        return students("", "", "");
    }

    public Manager[] managers(String username) {
        return users.values().stream().map(User::getManager).filter(Objects::nonNull).filter(s -> {
            return s.getUsername().startsWith(username);
        }).toList().toArray(new Manager[0]);
    }

    public Manager[] managers() {
        return managers("");
    }

    public int size() {
        return users.size();
    }
}
