package comp3111.examsystem.model;

public class Course {
    private final int id;
    private final String code;
    private final String name;
    private final String department;

    public Course(int id, String code, String name, String department) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }
}
