package comp3111.examsystem.model;

public class Student  {
    private final int id;
    private final String password;
    private final String username;
    private final String name;
    private final int age;
    private final String department;
    private final Gender gender;

    public Student(int id, String password, String username, String name, int age, String department, Gender gender) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.name = name;
        this.age = age;
        this.department = department;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
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
