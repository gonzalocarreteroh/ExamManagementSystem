package comp3111.examsystem.model;

public class Manager extends User {
    public Manager(int id, String password, String username) {
        super(id, password, username);
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
