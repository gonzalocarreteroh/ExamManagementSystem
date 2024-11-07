package comp3111.examsystem.model;

public class Student  {
    private final int id;
    private final String username;
    private final String password;
    private final String name;
    private final int age;
    private final String department;
    private final Gender gender;

    public Student(int id, String username, String password, String name, int age, String department, Gender gender) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.department = department;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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
