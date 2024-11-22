package comp3111.examsystem.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class CourseDb {
    private int lastId;
    private final HashMap<Integer, Course> courses;

    public CourseDb(Course[] courses) {
        lastId = Arrays.stream(courses).map(Course::getId).max(Comparator.naturalOrder()).orElse(0);

        this.courses = new HashMap<>();
        for (Course c : courses) {
            int courseId = c.getId();
            this.courses.put(courseId, c);
        }
    }

    public CourseDb() {
        this(new Course[0]);
    }

    public void add(String code, String name, String department) {
        lastId += 1;
        Course course = new Course(lastId, code, name, department);
        courses.put(lastId, course);
    }

    public void update(Course course) {
        int courseId = course.getId();
        if (courses.containsKey(courseId)) {
            Course cclone = new Course(courseId, course.getCode(), course.getName(), course.getDepartment());
            courses.put(courseId, cclone);
        }
    }

    public Course get(int id) {
        return courses.get(id);
    }

    public void remove(Integer id) {
        courses.remove(id);
    }

    public Course[] all(String code, String name, String department) {
        return courses.values().stream().filter(s -> {
            return (code == null || code.isEmpty() || s.getCode().equals(code))
                    && (name.isEmpty() || s.getName().startsWith(name))
                    && (department.isEmpty() || s.getDepartment().startsWith(department));
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
