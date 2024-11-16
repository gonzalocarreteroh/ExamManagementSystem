package comp3111.examsystem.controller.student;

import comp3111.examsystem.controller.ControllerBase;
import comp3111.examsystem.model.Course;
import comp3111.examsystem.model.DataCollection;
import comp3111.examsystem.model.Grade;
import comp3111.examsystem.model.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class GradeStatisticsController extends ControllerBase {

    @FXML
    private ComboBox<String> courseComboBox;

    @FXML
    private TableView<GradeRow> gradesTable;

    @FXML
    private TableColumn<GradeRow, String> courseColumn;

    @FXML
    private TableColumn<GradeRow, String> examColumn;

    @FXML
    private TableColumn<GradeRow, Integer> scoreColumn;

    @FXML
    private TableColumn<GradeRow, Integer> fullScoreColumn;

    @FXML
    private BarChart<String, Number> scoreChart;

    private ObservableList<GradeRow> gradeData;

    @FXML
    public void initialize() {
        gradeData = FXCollections.observableArrayList();
        gradesTable.setItems(gradeData);

        // Set up table columns
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
        examColumn.setCellValueFactory(new PropertyValueFactory<>("exam"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        fullScoreColumn.setCellValueFactory(new PropertyValueFactory<>("fullScore"));

        // Load course data into ComboBox
        loadCourses();

        // Load grade data into table and chart
        refreshStatistics();
    }

    @FXML
    private void refreshStatistics() {
        gradeData.clear();
        scoreChart.getData().clear();

        DataCollection data = loadData();

        // Populate grades table
        for (Grade grade : data.getGrades().all()) {
            String courseName = data.getCourses().get(grade.getExam(data.getExams()).getCourseId()).getName();
            String examName = grade.getExam(data.getExams()).getName();
            int score = grade.getPoints();
            int fullScore = calculateFullScore(grade.getExam(data.getExams()).getQuestionIds(), data);

            gradeData.add(new GradeRow(courseName, examName, score, fullScore));
        }

        // Populate chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (GradeRow row : gradeData) {
            series.getData().add(new XYChart.Data<>(row.getExam(), row.getScore()));
        }
        scoreChart.getData().add(series);
    }

    @FXML
    private void filterResults() {
        String selectedCourse = courseComboBox.getValue();
        if (selectedCourse == null || selectedCourse.isEmpty()) {
            refreshStatistics();
            return;
        }

        // Filter grade data by course
        List<GradeRow> filtered = new ArrayList<>();
        for (GradeRow row : gradeData) {
            if (row.getCourse().equals(selectedCourse)) {
                filtered.add(row);
            }
        }

        // Update table and chart
        gradesTable.setItems(FXCollections.observableArrayList(filtered));

        scoreChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (GradeRow row : filtered) {
            series.getData().add(new XYChart.Data<>(row.getExam(), row.getScore()));
        }
        scoreChart.getData().add(series);
    }

    @FXML
    private void resetFilters() {
        courseComboBox.getSelectionModel().clearSelection();
        refreshStatistics();
    }

    private void loadCourses() {
        DataCollection data = loadData();
        List<String> courseNames = new ArrayList<>();
        for (Course course : data.getCourses().all()) {
            courseNames.add(course.getName());
        }
        courseComboBox.setItems(FXCollections.observableArrayList(courseNames));
    }

    private int calculateFullScore(int[] questionIds, DataCollection data) {
        int totalPoints = 0;
        for (int id : questionIds) {
            Question question = data.getQuestions().get(id);
            if (question != null) {
                totalPoints += question.getPoints();
            }
        }
        return totalPoints;
    }

    public static class GradeRow {
        private final String course;
        private final String exam;
        private final int score;
        private final int fullScore;

        public GradeRow(String course, String exam, int score, int fullScore) {
            this.course = course;
            this.exam = exam;
            this.score = score;
            this.fullScore = fullScore;
        }

        public String getCourse() {
            return course;
        }

        public String getExam() {
            return exam;
        }

        public int getScore() {
            return score;
        }

        public int getFullScore() {
            return fullScore;
        }
    }
}
