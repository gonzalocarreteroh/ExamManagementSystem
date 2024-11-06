package comp3111.examsystem.model;

public class Student extends User {
    private final String name;
    private final int age;
    private final String department;
    private final Gender gender;

    public Student(String password, String username, String name, int age, String department, Gender gender) {
        super(password, username);
        this.name = name;
        this.age = age;
        this.department = department;
        this.gender = gender;
    }

    @Override
    public Teacher getTeacher() {
        return null;
    }

    @Override
    public Student getStudent() {
        return this;
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

    public Gender getGender() {
        return gender;
    }
}
