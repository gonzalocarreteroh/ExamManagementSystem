package comp3111.examsystem.model;

public class Manager extends User {
    public Manager(String password, String username) {
        super(password, username);
    }

    @Override
    public Teacher getTeacher() {
        return null;
    }

    @Override
    public Student getStudent() {
        return null;
    }

    @Override
    public Manager getManager() {
        return this;
    }
}
