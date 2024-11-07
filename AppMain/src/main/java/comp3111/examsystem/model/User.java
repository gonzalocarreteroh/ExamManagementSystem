package comp3111.examsystem.model;

public abstract class User {
    private final int id;
    private final String password;
    private final String username;

    public User(int id, String password, String username) {
        this.id = id;
        this.password = password;
        this.username = username;
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

    abstract public Teacher getTeacher();
    abstract public Student getStudent();
    abstract public Manager getManager();
}
