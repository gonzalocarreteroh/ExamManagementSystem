package comp3111.examsystem.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class TeacherDb {
    private int lastId;
    private final HashMap<Integer, Teacher> teachers;

    public TeacherDb(Teacher[] teachers) {
        lastId = Arrays.stream(teachers).map(Teacher::getId).max(Comparator.naturalOrder()).orElse(0);

        this.teachers = new HashMap<>();
        for (Teacher c : teachers) {
            this.teachers.put(c.getId(), c);
        }
    }

    public TeacherDb() {
        this(new Teacher[0]);
    }

    public void add(String username, String password, String name, int age, String department, String position) {
        lastId += 1;
        teachers.put(lastId, new Teacher(lastId, username, password, name, age, department, position));
    }

    public void update(Teacher teacher) {
        if (teachers.containsKey(teacher.getId())) {
            teachers.put(teacher.getId(), teacher);
        }
    }

    public Teacher get(int id) {
        return teachers.get(id);
    }

    public void remove(int id) {
        teachers.remove(id);
    }

    public Teacher[] all(String username, String name, String department) {
        return teachers.values().stream().filter(s -> {
            boolean usernameMatch = username.equals("") || s.getUsername().equals(username);
            boolean nameMatch = name.equals("") || s.getName().equals(name);
            boolean departmentMatch = department.equals("") || s.getDepartment().equals(department);
            return usernameMatch && nameMatch && departmentMatch;
        }).toList().toArray(new Teacher[0]);
    }

    public Teacher[] all(String username, String name) {
        return all(username, name, "");
    }

    public Teacher[] all(String username) {
        return all(username, "");
    }

    public Teacher[] all() {
        return all("");
    }
}
