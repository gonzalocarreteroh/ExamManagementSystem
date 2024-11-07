package comp3111.examsystem.model;

public class Exam {
    private final int id;
    private final String name;
    private final int duration;
    private final int courseId;
    private final boolean published;
    private final int[] questionIds;

    public Exam(int id, String name, int duration, int courseId, boolean published, int[] questionIds) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.courseId = courseId;
        this.published = published;
        this.questionIds = questionIds;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public Course getCourse(CourseDb db) {
        return db.get(courseId);
    }

    public boolean getPublished() {
        return published;
    }

    public Question[] getQuestions(QuestionDb db) {
        Question[] questions = new Question[questionIds.length];
        for (int i = 0; i < questions.length; ++i) {
            questions[i] = db.get(questionIds[i]);
        }
        return questions;
    }
}
