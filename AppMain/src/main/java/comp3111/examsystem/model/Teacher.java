package comp3111.examsystem.model;

public class Teacher {
    private final int id;
    private final String username;
    private final String password;
    private final String name;
    private final int age;
    private final String department;
    private final String position;

    public Teacher(int id, String username, String password, String name, int age, String department, String position) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.department = department;
        this.position = position;
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

    public String getPosition() {
        return position;
    }
}
