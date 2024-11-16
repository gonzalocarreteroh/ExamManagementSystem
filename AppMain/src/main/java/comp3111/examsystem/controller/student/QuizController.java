package comp3111.examsystem.controller.student;

import comp3111.examsystem.Main;
import comp3111.examsystem.controller.ControllerBase;
import comp3111.examsystem.model.DataCollection;
import comp3111.examsystem.model.Exam;
import comp3111.examsystem.model.Question;
import comp3111.examsystem.model.GradeDb;
import comp3111.examsystem.model.Grade;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class QuizController extends ControllerBase implements Initializable {
    @FXML
    private Label quizNameLabel;
    @FXML
    private Label totalQuestionsLabel;
    @FXML
    private Label timerLabel;
    @FXML
    private ListView<String> questionListView;
    @FXML
    private Label questionNumberLabel;
    @FXML
    private Label questionLabel;
    @FXML
    private VBox answerOptionsBox;
    @FXML
    private Button nextButton;
    @FXML
    private Button prevButton;
    @FXML
    private Button submitButton;

    private List<Question> questions;
    private int currentQuestionIndex;
    private Map<Integer, Set<String>> studentAnswers; // Stores question ID and selected answers
    private GradeDb gradeDb;
    private Timeline quizTimer;
    private int remainingTime; // Time in seconds
    private int studentId; // Placeholder for the logged-in student's ID
    private int examId; // The ID of the current exam

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentAnswers = new HashMap<>();
        gradeDb = loadData().getGrades();

        nextButton.setDisable(true);
        prevButton.setDisable(true);
        submitButton.setDisable(true);
    }

    public void loadExam(Exam exam) {
        DataCollection data = loadData();

        examId = exam.getId();
        studentId = 3; // Placeholder for the logged-in student ID; replace with actual logic to retrieve logged-in user ID.

        quizNameLabel.setText(exam.getName());
        questions = Arrays.asList(exam.getQuestions(data.getQuestions()));
        totalQuestionsLabel.setText("Total Questions: " + questions.size());

        // Populate question list
        questionListView.getItems().clear();
        questionListView.getItems().addAll(
                questions.stream().map(Question::getTitle).collect(Collectors.toList())
        );
        questionListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                currentQuestionIndex = newVal.intValue();
                displayQuestion();
                updateNavigationButtons();
            }
        });

        // Initialize timer: 15 seconds per question
        remainingTime = questions.size() * 15; // 15 seconds per question
        startTimer();

        currentQuestionIndex = 0;
        displayQuestion();
        updateNavigationButtons();
    }

    private void startTimer() {
        quizTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            remainingTime--;
            timerLabel.setText("Time Remaining: " + remainingTime + "s");

            if (remainingTime <= 0) {
                quizTimer.stop();
                submitQuiz();
            }
        }));
        quizTimer.setCycleCount(Animation.INDEFINITE);
        quizTimer.play();
    }

    private void displayQuestion() {
        Question question = questions.get(currentQuestionIndex);
        questionLabel.setText(question.getTitle());
        questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());

        answerOptionsBox.getChildren().clear(); // Clear previous options

        if (question.isSingle()) {
            // Single-choice question: Use RadioButtons
            ToggleGroup group = new ToggleGroup();

            RadioButton optionA = new RadioButton(question.getA());
            RadioButton optionB = new RadioButton(question.getB());
            RadioButton optionC = new RadioButton(question.getC());
            RadioButton optionD = new RadioButton(question.getD());

            optionA.setToggleGroup(group);
            optionB.setToggleGroup(group);
            optionC.setToggleGroup(group);
            optionD.setToggleGroup(group);

            // Restore previously selected answer if available
            Set<String> selectedAnswers = studentAnswers.getOrDefault(question.getId(), new HashSet<>());
            if (selectedAnswers.contains("a")) optionA.setSelected(true);
            if (selectedAnswers.contains("b")) optionB.setSelected(true);
            if (selectedAnswers.contains("c")) optionC.setSelected(true);
            if (selectedAnswers.contains("d")) optionD.setSelected(true);

            // Store answer on selection
            group.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
                studentAnswers.put(question.getId(), new HashSet<>()); // Clear previous answers
                if (newVal == optionA) studentAnswers.get(question.getId()).add("a");
                if (newVal == optionB) studentAnswers.get(question.getId()).add("b");
                if (newVal == optionC) studentAnswers.get(question.getId()).add("c");
                if (newVal == optionD) studentAnswers.get(question.getId()).add("d");
            });

            answerOptionsBox.getChildren().addAll(optionA, optionB, optionC, optionD);

        } else {
            // Multiple-choice question: Use CheckBoxes
            CheckBox optionA = new CheckBox(question.getA());
            CheckBox optionB = new CheckBox(question.getB());
            CheckBox optionC = new CheckBox(question.getC());
            CheckBox optionD = new CheckBox(question.getD());

            // Restore previously selected answers if available
            Set<String> selectedAnswers = studentAnswers.getOrDefault(question.getId(), new HashSet<>());
            optionA.setSelected(selectedAnswers.contains("a"));
            optionB.setSelected(selectedAnswers.contains("b"));
            optionC.setSelected(selectedAnswers.contains("c"));
            optionD.setSelected(selectedAnswers.contains("d"));

            // Store answers on selection
            optionA.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) studentAnswers.computeIfAbsent(question.getId(), k -> new HashSet<>()).add("a");
                else studentAnswers.getOrDefault(question.getId(), new HashSet<>()).remove("a");
            });
            optionB.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) studentAnswers.computeIfAbsent(question.getId(), k -> new HashSet<>()).add("b");
                else studentAnswers.getOrDefault(question.getId(), new HashSet<>()).remove("b");
            });
            optionC.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) studentAnswers.computeIfAbsent(question.getId(), k -> new HashSet<>()).add("c");
                else studentAnswers.getOrDefault(question.getId(), new HashSet<>()).remove("c");
            });
            optionD.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) studentAnswers.computeIfAbsent(question.getId(), k -> new HashSet<>()).add("d");
                else studentAnswers.getOrDefault(question.getId(), new HashSet<>()).remove("d");
            });

            answerOptionsBox.getChildren().addAll(optionA, optionB, optionC, optionD);
        }
    }

    @FXML
    public void nextQuestion() {
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
            displayQuestion();
            updateNavigationButtons();
        }
    }

    @FXML
    public void prevQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            displayQuestion();
            updateNavigationButtons();
        }
    }

    @FXML
    public void submitQuiz() {
        int correctAnswers = 0;
        int totalScore = 0;
        int maxScore = 0;

        for (Question question : questions) {
            Set<String> correctAnswersSet = new HashSet<>(Arrays.asList(question.getAnswer().split(",")));
            Set<String> studentAnswerSet = studentAnswers.getOrDefault(question.getId(), new HashSet<>());

            maxScore += question.getPoints();

            if (correctAnswersSet.equals(studentAnswerSet)) {
                correctAnswers++;
                totalScore += question.getPoints();
            }
        }

        // Save the grade in the JSON file
        DataCollection data = loadData();
        data.getGrades().add(studentId, examId, totalScore);
        storeData(data);

        double precision = (double) correctAnswers / questions.size() * 100;

        Alert resultAlert = new Alert(Alert.AlertType.INFORMATION);
        resultAlert.setHeaderText("Quiz Results");
        resultAlert.setContentText(String.format("%d/%d Correct, the precision is %.2f%%, the score is %d/%d",
                correctAnswers, questions.size(), precision, totalScore, maxScore));
        resultAlert.showAndWait();

        // Close the quiz window and return to the quiz selection screen
        ((Stage) submitButton.getScene().getWindow()).close();
        openQuizSelectionScreen();
    }

    private void updateNavigationButtons() {
        prevButton.setDisable(currentQuestionIndex == 0);
        nextButton.setDisable(currentQuestionIndex == questions.size() - 1);
        submitButton.setDisable(questions.isEmpty());
    }

    private void openQuizSelectionScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("student/MainUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Quiz Selection");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
