package comp3111.examsystem.model;

import java.util.Comparator;
import java.util.HashMap;

public class CourseDb {
    private int lastId;
    private final HashMap<Integer, Course> courses;

    public CourseDb(HashMap<Integer, Course> courses) {
        lastId = courses.keySet().stream().max(Comparator.naturalOrder()).orElse(0);
        this.courses = courses;
    }

    public CourseDb() {
        this(new HashMap<>());
    }

    public void add(String code, String name, String department) {
        lastId += 1;
        courses.put(lastId, new Course(lastId, code, name, department));
    }

    public void update(int id, String code, String name, String department) {
        if (courses.containsKey(id)) {
            courses.put(id, new Course(id, code, name, department));
        }
    }

    public Course get(int id) {
        return courses.get(id);
    }

    public void remove(int id) {
        courses.remove(id);
    }

    public Course[] all(String code, String name, String department) {
        return courses.values().stream().filter(s -> {
            return s.getCode().startsWith(code)
                    && s.getName().startsWith(name)
                    && s.getDepartment().startsWith(department);
        }).toList().toArray(new Course[0]);
    }

    public Course[] all(String code, String name) {
        return all(code, name, "");
    }

    public Course[] all(String code) {
        return all(code, "");
    }

    public Course[] all() {
        return all("");
    }
}
