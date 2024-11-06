package comp3111.examsystem.model;

public abstract class User {
    private final String password;
    private final String username;

    public User(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    abstract public Teacher getTeacher();
    abstract public Student getStudent();
    abstract public Manager getManager();
}
