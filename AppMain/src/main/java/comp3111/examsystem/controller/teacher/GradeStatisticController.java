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

        courseCombox.getItems().addAll(Arrays.stream(loadData().getCourses().all()).map(Course::getCode).toList());
        studentCombox.getItems().addAll(Arrays.stream(loadData().getStudents().all()).map(Student::getUsername).toList());
        examCombox.getItems().addAll(Arrays.stream(loadData().getExams().all()).map(Exam::getName).toList());

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
        gradeList.clear();
        gradeList.addAll(
                Arrays.stream(loadData().getCourses().all(courseCombox.getValue()))
                .flatMap(course -> Arrays.stream(loadData().getExams().all(examCombox.getValue(), course.getId()))
                        .flatMap(exam -> Arrays.stream(loadData().getStudents().all(studentCombox.getValue()))
                                .flatMap(student -> Arrays.stream(loadData().getGrades().all(student.getId(), exam.getId()))
                                        .map(grade -> new StatsRow(student.getName(), course.getCode(), exam.getName(), grade.getPoints(), exam.getMaxPoints(loadData().getQuestions()), exam.getDuration()))))).toList()
        );
    }

    private void loadChart() {
        CourseDb courseDb = loadData().getCourses();
        GradeDb gradeDb = loadData().getGrades();
        ExamDb examDb = loadData().getExams();
        StudentDb studentDb = loadData().getStudents();

        var seriesBar = new XYChart.Series<String, Number>();
        seriesBar.getData().addAll(Arrays.stream(courseDb.all()).map(course -> {
            var grades = Arrays.stream(examDb.all(null, course.getId()))
                    .flatMap(exam -> Arrays.stream(gradeDb.all(null, exam.getId())))
                    .toList();

            int gradeSum = grades.stream().map(Grade::getPoints).reduce(0, Integer::sum);
            int avgGrade = (int)(gradeSum / (grades.size() + 0.01));
            return new XYChart.Data<String, Number>(course.getCode(), avgGrade);
        }).toList());
        barChart.getData().add(seriesBar);

        pieChart.getData().addAll(Arrays.stream(studentDb.all()).map(student -> {
            Grade[] grades = gradeDb.all(student.getId(), null);
            int gradeSum = Arrays.stream(grades).map(Grade::getPoints).reduce(0, Integer::sum);
            int avgGrade = (int)(gradeSum / (grades.length + 0.01));

            return new PieChart.Data(student.getName(), avgGrade);
        }).toList());

        var seriesLine = new XYChart.Series<String, Number>();
        seriesLine.getData().addAll(Arrays.stream(examDb.all()).map(exam -> {
            Grade[] grades = gradeDb.all(null, exam.getId());
            int gradeSum = Arrays.stream(grades).map(Grade::getPoints).reduce(0, Integer::sum);
            int avgGrade = (int)(gradeSum / (grades.length + 0.01));

            return new XYChart.Data<String, Number>(exam.getName(), avgGrade);
        }).toList());
        lineChart.getData().add(seriesLine);
    }

    @FXML
    public void reset() {
        studentCombox.setValue(null);
        examCombox.setValue(null);
        courseCombox.setValue(null);
    }
}
