package comp3111.examsystem.model;

import java.util.Comparator;
import java.util.HashMap;

public class StudentDb {
    private int lastId;
    private final HashMap<Integer, Student> students;

    public StudentDb(HashMap<Integer, Student> students) {
        lastId = students.keySet().stream().max(Comparator.naturalOrder()).orElse(0);
        this.students = students;
    }

    public StudentDb() {
        this(new HashMap<>());
    }

    public void add(String password, String username, String name, int age, String department, Gender gender) {
        lastId += 1;
        students.put(lastId, new Student(lastId, password, username, name, age, department, gender));
    }

    public void update(Student student) {
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
        }
    }

    public Student get(int id) {
        return students.get(id);
    }

    public void remove(int id) {
        students.remove(id);
    }

    public Student[] list(String username, String name, String department) {
        return students.values().stream().filter(s -> {
            return s.getUsername().startsWith(username)
                    && s.getName().startsWith(name)
                    && s.getDepartment().startsWith(department);
        }).toList().toArray(new Student[0]);
    }

    public Student[] list() {
        return list("", "", "");
    }

    public int size() {
        return students.size();
    }
}
