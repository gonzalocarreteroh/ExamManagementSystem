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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis; // Import NumberAxis
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.*;

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

    private ObservableList<GradeRow> allGradeData;
    private ObservableList<GradeRow> gradeData;
    private String username; // Store the logged-in username

    public void setUsername(String username) {
        this.username = username;
        autoReset(); // Automatically refresh data when username is set
    }

    @FXML
    public void initialize() {
        allGradeData = FXCollections.observableArrayList();
        gradeData = FXCollections.observableArrayList();
        gradesTable.setItems(gradeData);

        // Set up table columns
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
        examColumn.setCellValueFactory(new PropertyValueFactory<>("exam"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        fullScoreColumn.setCellValueFactory(new PropertyValueFactory<>("fullScore"));

        // Courses will be loaded in refreshStatistics()
    }

    @FXML
    private void refreshStatistics() {
        allGradeData.clear();
        gradeData.clear();

        DataCollection data = loadData();
        int studentId = getStudentIdFromUsername(data);

        if (studentId == -1) return; // No matching user, do nothing

        // Populate grades
        for (Grade grade : data.getGrades().all()) {
            if (grade.getStudentId() == studentId) {
                String courseName = data.getCourses().get(grade.getExam(data.getExams()).getCourseId()).getName();
                String examName = grade.getExam(data.getExams()).getName();
                int score = grade.getPoints();
                int fullScore = calculateFullScore(grade.getExam(data.getExams()).getQuestionIds(), data);

                GradeRow row = new GradeRow(courseName, examName, score, fullScore);
                allGradeData.add(row);
            }
        }

        gradeData.setAll(allGradeData);

        // Reload courses into ComboBox
        loadCourses();

        // Update the chart
        updateChart(gradeData);
    }

    @FXML
    private void filterResults() {
        String selectedCourse = courseComboBox.getValue();
        if (selectedCourse == null || selectedCourse.isEmpty() || selectedCourse.equals("Pick Course")) {
            refreshStatistics();
            return;
        }

        // Filter grade data by course
        List<GradeRow> filtered = new ArrayList<>();
        for (GradeRow row : allGradeData) {
            if (row.getCourse().equals(selectedCourse)) {
                filtered.add(row);
            }
        }

        gradeData.setAll(filtered);

        // Update the chart
        updateChart(gradeData);
    }

    @FXML
    private void resetFilters() {
        courseComboBox.getSelectionModel().clearSelection();
        courseComboBox.setPromptText("Pick Course"); // Reset the ComboBox prompt text
        gradeData.setAll(allGradeData); // Reset table to show all grades
        updateChart(gradeData); // Update the chart
    }

    private void loadCourses() {
        Set<String> courseNames = new LinkedHashSet<>();
        for (GradeRow row : allGradeData) {
            courseNames.add(row.getCourse());
        }
        List<String> courseList = new ArrayList<>(courseNames);
        courseComboBox.setItems(FXCollections.observableArrayList(courseList));
        courseComboBox.setPromptText("Pick Course"); // Set default prompt text
    }

    private void updateChart(List<GradeRow> dataToDisplay) {
        scoreChart.getData().clear();

        // Prepare categories
        Set<String> categoriesSet = new LinkedHashSet<>();
        for (GradeRow row : dataToDisplay) {
            categoriesSet.add(row.getExam());
        }
        List<String> categories = new ArrayList<>(categoriesSet);

        // Update x-axis categories
        CategoryAxis xAxis = (CategoryAxis) scoreChart.getXAxis();
        xAxis.setAutoRanging(false);
        xAxis.getCategories().clear();
        xAxis.getCategories().addAll(categories);

        // Populate chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (GradeRow row : dataToDisplay) {
            series.getData().add(new XYChart.Data<>(row.getExam(), row.getScore()));
        }
        scoreChart.getData().add(series);

        // Adjust chart properties to center labels
        xAxis.setTickLabelRotation(0);
        xAxis.setTickLabelGap(10); // Adjust as needed
        scoreChart.setCategoryGap(20); // Adjust as needed
        scoreChart.setBarGap(5);       // Adjust as needed
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

    private int getStudentIdFromUsername(DataCollection data) {
        for (var student : data.getStudents().all()) {
            if (student.getUsername().equals(username)) {
                return student.getId();
            }
        }
        return -1; // Return -1 if no matching username
    }

    private void autoReset() {
        refreshStatistics();
        courseComboBox.setPromptText("Pick Course");
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
