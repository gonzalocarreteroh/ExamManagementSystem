package comp3111.examsystem.model;

public class Question {
    private final String title;
    private final String a, b, c, d;
    private final String answer;
    private final int points;

    public Question(String title, String a, String b, String c, String d, String answer, int points) {
        this.title = title;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.answer = answer;
        this.points = points;
    }

    public String getTitle() {
        return title;
    }

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public String getC() {
        return c;
    }

    public String getD() {
        return d;
    }

    public String getAnswer() {
        return answer;
    }

    public int getPoints() {
        return points;
    }

    public boolean isSingle() {
        return answer.length() == 1;
    }

    public boolean isMultiple() {
        return !isSingle();
    }
}
