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

    public void add(String username, String password, String name, int age, String department, Gender gender) {
        lastId += 1;
        students.put(lastId, new Student(lastId, username, password, name, age, department, gender));
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

    public Student[] filter(String username, String name, String department) {
        return students.values().stream().filter(s -> {
            boolean usernameMatch = username == "" || s.getUsername().startsWith(username);
            boolean nameMatch = name == "" || s.getName().startsWith(name);
            boolean departmentMatch = department == "" || s.getDepartment().startsWith(department);
            return usernameMatch && nameMatch && departmentMatch;
        }).toList().toArray(new Student[0]);
    }

    public Student[] all() {
        return filter("", "", "");
    }

    public int size() {
        return students.size();
    }
}
