package comp3111.examsystem.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class StudentDb {
    private int lastId;
    private final HashMap<Integer, Student> students;

    public StudentDb(Student[] students) {
        lastId = Arrays.stream(students).map(Student::getId).max(Comparator.naturalOrder()).orElse(0);

        this.students = new HashMap<>();
        for (Student c : students) {
            this.students.put(c.getId(), c);
        }
    }

    public StudentDb() {
        this(new Student[0]);
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

    public boolean login(String username, String password) {
        return students.values().stream().anyMatch(s -> s.getUsername().equals(username) && s.getPassword().equals(password));
    }

    public Student[] all(String username, String name, String department) {
        return students.values().stream().filter(s -> {
            boolean usernameMatch = username == null || username.isEmpty() || s.getUsername().equals(username);
            boolean nameMatch = name == null || name.isEmpty() || s.getName().equals(name);
            boolean departmentMatch = department == null || department.isEmpty() || s.getDepartment().equals(department);
            return usernameMatch && nameMatch && departmentMatch;
        }).toArray(Student[]::new);
    }

    public Student[] all(String username, String name) {
        return all(username, name, "");
    }

    public Student[] all(String username) {
        return all(username, "");
    }

    public Student[] all() {
        return all("");
    }
}
