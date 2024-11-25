package comp3111.examsystem.controller.student;

import comp3111.examsystem.controller.ControllerBase;
import comp3111.examsystem.model.Course;
import comp3111.examsystem.model.DataCollection;
import comp3111.examsystem.model.Exam;
import comp3111.examsystem.model.Grade;
import comp3111.examsystem.model.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.*;

public class GradeStatisticsController extends ControllerBase {

    @FXML
    ComboBox<String> courseComboBox;

    @FXML
    TableView<GradeRow> gradesTable;

    @FXML
    private TableColumn<GradeRow, String> courseColumn;

    @FXML
    private TableColumn<GradeRow, String> examColumn;

    @FXML
    private TableColumn<GradeRow, Integer> scoreColumn;

    @FXML
    private TableColumn<GradeRow, Integer> fullScoreColumn;

    @FXML
    private TableColumn<GradeRow, Integer> timeColumn;

    @FXML
    BarChart<String, Number> scoreChart;

    @FXML
    Label feedbackLabel; // Label for summary feedback

    ObservableList<GradeRow> allGradeData;
    ObservableList<GradeRow> gradeData;
    private String username;

    public void setUsername(String username) {
        this.username = username;
        autoReset();
    }

    @FXML
    public void initialize() {
        allGradeData = FXCollections.observableArrayList();
        gradeData = FXCollections.observableArrayList();
        gradesTable.setItems(gradeData);

        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
        examColumn.setCellValueFactory(new PropertyValueFactory<>("exam"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        fullScoreColumn.setCellValueFactory(new PropertyValueFactory<>("fullScore"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
    }

    @FXML
    void refreshStatistics() {
        allGradeData.clear();
        gradeData.clear();

        DataCollection data = loadData();
        int studentId = getStudentIdFromUsername(data);

        if (studentId == -1) return;

        int totalScore = 0;
        int highestScore = 0;
        int totalExams = 0;

        for (Grade grade : data.getGrades().all()) {
            if (grade.getStudentId() == studentId) {
                Exam exam = grade.getExam(data.getExams());
                String courseName = data.getCourses().get(exam.getCourseId()).getName();
                String examName = exam.getName();
                int score = grade.getPoints();
                int fullScore = calculateFullScore(exam.getQuestionIds(), data);
                int time = exam.getDuration();

                GradeRow row = new GradeRow(courseName, examName, score, fullScore, time);
                allGradeData.add(row);

                totalScore += score;
                highestScore = Math.max(highestScore, score);
                totalExams++;
            }
        }

        gradeData.setAll(allGradeData);
        loadCourses();
        updateChart(gradeData);
        updateFeedback(totalScore, highestScore, totalExams);
    }

    void updateFeedback(int totalScore, int highestScore, int totalExams) {
        if (totalExams == 0) {
            feedbackLabel.setText("No completed exams yet. Start now to see your progress!");
            return;
        }

        int averageScore = totalScore / totalExams;
        feedbackLabel.setText(
                String.format(
                        "Completed Exams: %d | Highest Score: %d%nKeep up the great work!",
                        totalExams, highestScore, averageScore
                )
        );
    }

    @FXML
    void filterResults() {
        String selectedCourse = courseComboBox.getValue();
        if (selectedCourse == null || selectedCourse.isEmpty() || selectedCourse.equals("Pick Course")) {
            refreshStatistics();
            return;
        }

        List<GradeRow> filtered = new ArrayList<>();
        for (GradeRow row : allGradeData) {
            if (row.getCourse().equals(selectedCourse)) {
                filtered.add(row);
            }
        }

        gradeData.setAll(filtered);
        updateChart(gradeData);
    }

    @FXML
    void resetFilters() {
        courseComboBox.getSelectionModel().clearSelection();
        courseComboBox.setPromptText("Pick Course");
        gradeData.setAll(allGradeData);
        updateChart(gradeData);
    }

    void loadCourses() {
        Set<String> courseNames = new LinkedHashSet<>();
        for (GradeRow row : allGradeData) {
            courseNames.add(row.getCourse());
        }
        List<String> courseList = new ArrayList<>(courseNames);
        courseComboBox.setItems(FXCollections.observableArrayList(courseList));
        courseComboBox.setPromptText("Pick Course");
    }

    void updateChart(List<GradeRow> dataToDisplay) {
        scoreChart.getData().clear();

        // Disable legend
        scoreChart.setLegendVisible(false);

        Set<String> categoriesSet = new LinkedHashSet<>();
        for (GradeRow row : dataToDisplay) {
            String modifiedLabel = insertLineBreaks(row.getExam());
            categoriesSet.add(modifiedLabel);
        }
        List<String> categories = new ArrayList<>(categoriesSet);

        CategoryAxis xAxis = (CategoryAxis) scoreChart.getXAxis();
        xAxis.setAutoRanging(false);
        xAxis.getCategories().clear();
        xAxis.getCategories().addAll(categories);

        Map<String, String> courseColorMap = new HashMap<>();
        String[] colors = {"#FF0000", "#00FF00", "#0000FF", "#FFA500", "#800080", "#FFFF00", "#00FFFF"};
        int colorIndex = 0;

        for (GradeRow row : dataToDisplay) {
            String courseName = row.getCourse();
            if (!courseColorMap.containsKey(courseName)) {
                courseColorMap.put(courseName, colors[colorIndex % colors.length]);
                colorIndex++;
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (GradeRow row : dataToDisplay) {
            String modifiedLabel = insertLineBreaks(row.getExam());
            XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(modifiedLabel, row.getScore());
            series.getData().add(dataPoint);

            String courseColor = courseColorMap.get(row.getCourse());
            dataPoint.nodeProperty().addListener((obs, oldNode, newNode) -> {
                if (newNode != null) {
                    newNode.setStyle("-fx-bar-fill: " + courseColor + ";");
                }
            });
        }

        scoreChart.getData().add(series);
        xAxis.setTickLabelRotation(0);
        xAxis.setTickLabelGap(10);
        scoreChart.setCategoryGap(50);
        scoreChart.setBarGap(0);
    }

    String insertLineBreaks(String label) {
        String[] words = label.split("\\s+");
        StringBuilder modifiedLabel = new StringBuilder();
        int maxCharsPerLine = 10;
        int currentLineLength = 0;

        for (String word : words) {
            if (currentLineLength + word.length() > maxCharsPerLine) {
                modifiedLabel.append("\n");
                currentLineLength = 0;
            } else if (modifiedLabel.length() > 0) {
                modifiedLabel.append(" ");
            }
            modifiedLabel.append(word);
            currentLineLength += word.length() + 1;
        }

        return modifiedLabel.toString();
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

    int getStudentIdFromUsername(DataCollection data) {
        for (var student : data.getStudents().all()) {
            if (student.getUsername().equals(username)) {
                return student.getId();
            }
        }
        return -1;
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
        private final int time;

        public GradeRow(String course, String exam, int score, int fullScore, int time) {
            this.course = course;
            this.exam = exam;
            this.score = score;
            this.fullScore = fullScore;
            this.time = time;
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

        public int getTime() {
            return time;
        }
    }
}
