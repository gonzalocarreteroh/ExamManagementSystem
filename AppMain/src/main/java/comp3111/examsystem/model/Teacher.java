package comp3111.examsystem.model;

public class Teacher extends User {
    private final String name;
    private final int age;
    private final String department;
    private final String position;

    public Teacher(int id, String password, String username, String name, int age, String department, String position) {
        super(id, password, username);
        this.name = name;
        this.age = age;
        this.department = department;
        this.position = position;
    }

    @Override
    public Teacher getTeacher() {
        return this;
    }

    @Override
    public Student getStudent() {
        return null;
    }

    @Override
    public Manager getManager() {
        return null;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }
}
