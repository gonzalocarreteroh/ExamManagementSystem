package comp3111.examsystem.controller.teacher;

import comp3111.examsystem.controller.ControllerBase;
import comp3111.examsystem.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class GradeStatisticController extends ControllerBase implements Initializable {
    public static class StatsRow {
        public final String studentName, courseNum, examName;
        public final int score, fullScore, timeSpend;

        public StatsRow(String studentName, String courseNum, String examName, int score, int fullScore, int timeSpend) {
            this.studentName = studentName;
            this.courseNum = courseNum;
            this.examName = examName;
            this.score = score;
            this.fullScore = fullScore;
            this.timeSpend = timeSpend;
        }

        public String getStudentName() {
            return studentName;
        }
        public String getCourseNum() {
            return courseNum;
        }
        public String getExamName() {
            return examName;
        }
        public String getScore() {
            return Integer.toString(score);
        }
        public String getFullScore() {
            return Integer.toString(fullScore);
        }
        public String getTimeSpend() {
            return Integer.toString(timeSpend);
        }
    }

    @FXML
    private ChoiceBox<String> courseCombox;
    @FXML
    private ChoiceBox<String> examCombox;
    @FXML
    private ChoiceBox<String> studentCombox;
    @FXML
    private TableView<StatsRow> gradeTable;
    @FXML
    private TableColumn<StatsRow, String> studentColumn;
    @FXML
    private TableColumn<StatsRow, String> courseColumn;
    @FXML
    private TableColumn<StatsRow, String> examColumn;
    @FXML
    private TableColumn<StatsRow, String> scoreColumn;
    @FXML
    private TableColumn<StatsRow, String> fullScoreColumn;
    @FXML
    private TableColumn<StatsRow, String> timeSpendColumn;
    @FXML
    BarChart<String, Number> barChart;
    @FXML
    CategoryAxis categoryAxisBar;
    @FXML
    NumberAxis numberAxisBar;
    @FXML
    LineChart<String, Number> lineChart;
    @FXML
    CategoryAxis categoryAxisLine;
    @FXML
    NumberAxis numberAxisLine;
    @FXML
    PieChart pieChart;

    private final ObservableList<StatsRow> gradeList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        barChart.setLegendVisible(false);
        categoryAxisBar.setLabel("Course");
        numberAxisBar.setLabel("Avg. Score");
        pieChart.setLegendVisible(false);
        pieChart.setTitle("Student Scores");
        lineChart.setLegendVisible(false);
        categoryAxisLine.setLabel("Exam");
        numberAxisLine.setLabel("Avg. Score");
        gradeTable.setItems(gradeList);

        for (Course course: loadData().getCourses().all()) {
            courseCombox.getItems().add(course.getCode());
        }

        for (Student student: loadData().getStudents().all()) {
            studentCombox.getItems().add(student.getUsername());
        }

        for (Exam exam: loadData().getExams().all()) {
            examCombox.getItems().add(exam.getName());
        }

        studentColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("courseNum"));
        examColumn.setCellValueFactory(new PropertyValueFactory<>("examName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        fullScoreColumn.setCellValueFactory(new PropertyValueFactory<>("fullScore"));
        timeSpendColumn.setCellValueFactory(new PropertyValueFactory<>("timeSpend"));

        refresh();
        loadChart();
    }

    @FXML
    public void refresh() {
        String courseCode = courseCombox.getValue();
        String studentUsername = studentCombox.getValue();
        String examName = examCombox.getValue();

        CourseDb courseDb = loadData().getCourses();
        GradeDb gradeDb = loadData().getGrades();
        ExamDb examDb = loadData().getExams();
        QuestionDb questionDb = loadData().getQuestions();
        StudentDb studentDb = loadData().getStudents();

        gradeList.clear();
        for (Course course: courseDb.all(courseCode)) {
            for (Exam exam: examDb.all(examName, course.getId())) {
                for (Student student: studentDb.all(studentUsername)) {
                    for (Grade grade: gradeDb.all(student.getId(), exam.getId())) {
                        gradeList.add(new StatsRow(student.getName(), course.getCode(), exam.getName(), grade.getPoints(), exam.getMaxPoints(questionDb), exam.getDuration()));
                    }
                }
            }
        }
    }

    private void loadChart() {
        CourseDb courseDb = loadData().getCourses();
        GradeDb gradeDb = loadData().getGrades();
        ExamDb examDb = loadData().getExams();
        QuestionDb questionDb = loadData().getQuestions();
        StudentDb studentDb = loadData().getStudents();

        XYChart.Series<String, Number> seriesBar = new XYChart.Series<>();
        seriesBar.getData().clear();
        barChart.getData().clear();

        for (Course course: courseDb.all()) {
            int gradeSum = 0, gradeCount = 0;
            for (Exam exam: examDb.all(null, course.getId())) {
                Grade[] grades = gradeDb.all(null, exam.getId());
                gradeSum += Arrays.stream(grades).map(Grade::getPoints).reduce(0, Integer::sum);
                gradeCount += grades.length;
            }

            int avgGrade = gradeCount == 0 ? 0 : gradeSum / gradeCount;
            seriesBar.getData().add(new XYChart.Data<>(course.getCode(), avgGrade));
        }
        barChart.getData().add(seriesBar);

        pieChart.getData().clear();
        for (Student student: studentDb.all()) {
            Grade[] grades = gradeDb.all(student.getId(), null);

            int gradeSum = Arrays.stream(grades).map(Grade::getPoints).reduce(0, Integer::sum);
            int avgGrade = grades.length == 0 ? 0 : gradeSum / grades.length;

            pieChart.getData().add(new PieChart.Data(student.getName(), avgGrade));
        }

        XYChart.Series<String, Number> seriesLine = new XYChart.Series<>();
        seriesLine.getData().clear();
        lineChart.getData().clear();
        for (Exam exam: examDb.all()) {
            Grade[] grades = gradeDb.all(null, exam.getId());

            int gradeSum = Arrays.stream(grades).map(Grade::getPoints).reduce(0, Integer::sum);
            int avgGrade = grades.length == 0 ? 0 : gradeSum / grades.length;

            seriesLine.getData().add(new XYChart.Data<>(exam.getName(), avgGrade));
        }
        lineChart.getData().add(seriesLine);

    }

    @FXML
    public void reset() {
        studentCombox.setValue(null);
        examCombox.setValue(null);
        courseCombox.setValue(null);
    }
}
