package comp3111.examsystem.model;

import java.util.Comparator;
import java.util.HashMap;

public class TeacherDb {
    private int lastId;
    private final HashMap<Integer, Teacher> teachers;

    public TeacherDb(HashMap<Integer, Teacher> teachers) {
        lastId = teachers.keySet().stream().max(Comparator.naturalOrder()).orElse(0);
        this.teachers = teachers;
    }

    public TeacherDb() {
        this(new HashMap<>());
    }

    public void add(String password, String username, String name, int age, String department, String position) {
        lastId += 1;
        teachers.put(lastId, new Teacher(lastId, password, username, name, age, department, position));
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

    public Teacher[] filter(String username, String name, String department) {
        return teachers.values().stream().filter(s -> {
            return s.getUsername().startsWith(username)
                    && s.getName().startsWith(name)
                    && s.getDepartment().startsWith(department);
        }).toList().toArray(new Teacher[0]);
    }

    public Teacher[] all() {
        return filter("", "", "");
    }

    public int size() {
        return teachers.size();
    }
}
