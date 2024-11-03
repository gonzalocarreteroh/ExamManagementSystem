package comp3111.examsystem;

public class Grades {
    private int total, maximal;

    public Grades() {
        total = 0;
        maximal = 0;
    }

    public void add(int total, int maximal) {
        this.total += total;
        this.maximal += maximal;
    }

    public int getAverage() {
        return 100 * total / maximal;
    }

    public String getGrade() {
        int avg = getAverage();
        if (avg >= 90) {
            return "A";
        } else if (avg >= 80) {
            return "B";
        } else if (avg >= 65) {
            return "C";
        } else if (avg >= 50) {
            return "D";
        } else {
            return "F";
        }
    }
}
