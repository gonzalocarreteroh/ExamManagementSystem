package comp3111.examsystem.model;

import java.util.*;

public class UserDb {
    private int lastId;
    private final HashMap<Integer, User> users;

    public UserDb(HashMap<Integer, User> users) {
        lastId = users.keySet().stream().max(Comparator.naturalOrder()).orElse(0);
        this.users = users;
    }

    public UserDb() {
        this(new HashMap<>());
    }

    public void addManager(String password, String username) {
        lastId += 1;
        users.put(lastId, new Manager(lastId, password, username));
    }

    public void addStudent(String password, String username, String name, int age, String department, Gender gender) {
        lastId += 1;
        users.put(lastId, new Student(lastId, password, username, name, age, department, gender));
    }

    public void addTeacher(String password, String username, String name, int age, String department, String position) {
        lastId += 1;
        users.put(lastId, new Teacher(lastId, password, username, name, age, department, position));
    }

    public void update(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        }
    }

    public User get(int id) {
        return users.get(id);
    }

    public void remove(int id) {
        users.remove(id);
    }

    public Teacher[] teachers(String username, String name, String department) {
        return users.values().stream().map(User::getTeacher).filter(Objects::nonNull).filter(s -> {
            return s.getUsername().startsWith(username)
                    && s.getName().startsWith(name)
                    && s.getDepartment().startsWith(department);
        }).toList().toArray(new Teacher[0]);
    }

    public Teacher[] teachers() {
        return teachers("", "", "");
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
