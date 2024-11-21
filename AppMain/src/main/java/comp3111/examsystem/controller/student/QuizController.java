package comp3111.examsystem.controller.student;

import comp3111.examsystem.Main;
import comp3111.examsystem.controller.ControllerBase;
import comp3111.examsystem.model.DataCollection;
import comp3111.examsystem.model.Exam;
import comp3111.examsystem.model.Question;
import comp3111.examsystem.model.GradeDb;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;
import javafx.scene.control.Alert.AlertType;

import java.net.URL;
import java.util.*;

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
    @FXML
    private ProgressBar timerProgressBar;

    private String username;
    private List<Question> questions;
    private int currentQuestionIndex;
    private Map<Integer, Set<String>> studentAnswers;
    private GradeDb gradeDb;
    private Timeline quizTimer;
    private int remainingTime;
    private int totalTime; // Total time for the quiz
    private int studentId;
    private int examId;
    private boolean quizSubmitted;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentAnswers = new HashMap<>();
        gradeDb = loadData().getGrades();
        quizSubmitted = false;

        nextButton.setDisable(true);
        prevButton.setDisable(true);
        submitButton.setDisable(true);

        playSound("start_quiz.mp3"); // Play sound when the quiz starts
    }

    public void setUsername(String username) {
        this.username = username;

        // Get the student ID associated with the username
        DataCollection data = loadData();
        studentId = Arrays.stream(data.getStudents().all())
                .filter(student -> student.getUsername().equals(username))
                .findFirst()
                .map(student -> student.getId())
                .orElse(-1);

        if (studentId == -1) {
            throw new IllegalStateException("Unable to find student with username: " + username);
        }
    }

    public void loadExam(Exam exam) {
        DataCollection data = loadData();

        examId = exam.getId();

        quizNameLabel.setText(exam.getName());
        questions = Arrays.asList(exam.getQuestions(data.getQuestions()));
        totalQuestionsLabel.setText("Total Questions: " + questions.size());

        // Populate question list
        questionListView.getItems().clear();
        questionListView.getItems().addAll(
                questions.stream().map(Question::getTitle).toList()
        );
        questionListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                currentQuestionIndex = newVal.intValue();
                displayQuestion();
                updateNavigationButtons();
            }
        });

        remainingTime = exam.getDuration();
        totalTime = remainingTime;
        timerLabel.setText("Time Remaining: " + remainingTime + "s");
        startTimer();

        currentQuestionIndex = 0;
        displayQuestion();
        updateNavigationButtons();
    }

    private void startTimer() {
        quizTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (remainingTime > 0) {
                remainingTime--;
                timerLabel.setText("Time Remaining: " + remainingTime + "s");
                timerProgressBar.setProgress((double) remainingTime / totalTime); // Update progress bar
            } else {
                quizTimer.stop();
                submitQuiz(); // Trigger quiz submission when the timer ends
            }
        }));
        quizTimer.setCycleCount(Animation.INDEFINITE);
        quizTimer.play();
    }

    private void displayQuestion() {
        Question question = questions.get(currentQuestionIndex);
        questionLabel.setText(question.getTitle());
        questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());

        answerOptionsBox.getChildren().clear();

        if (question.isSingle()) {
            ToggleGroup group = new ToggleGroup();

            RadioButton optionA = new RadioButton(question.getA());
            RadioButton optionB = new RadioButton(question.getB());
            RadioButton optionC = new RadioButton(question.getC());
            RadioButton optionD = new RadioButton(question.getD());

            optionA.setToggleGroup(group);
            optionB.setToggleGroup(group);
            optionC.setToggleGroup(group);
            optionD.setToggleGroup(group);

            Set<String> selectedAnswers = studentAnswers.getOrDefault(question.getId(), new HashSet<>());
            if (selectedAnswers.contains("a")) optionA.setSelected(true);
            if (selectedAnswers.contains("b")) optionB.setSelected(true);
            if (selectedAnswers.contains("c")) optionC.setSelected(true);
            if (selectedAnswers.contains("d")) optionD.setSelected(true);

            group.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
                studentAnswers.put(question.getId(), new HashSet<>());
                if (newVal == optionA) studentAnswers.get(question.getId()).add("a");
                if (newVal == optionB) studentAnswers.get(question.getId()).add("b");
                if (newVal == optionC) studentAnswers.get(question.getId()).add("c");
                if (newVal == optionD) studentAnswers.get(question.getId()).add("d");
            });

            answerOptionsBox.getChildren().addAll(optionA, optionB, optionC, optionD);

        } else {
            CheckBox optionA = new CheckBox(question.getA());
            CheckBox optionB = new CheckBox(question.getB());
            CheckBox optionC = new CheckBox(question.getC());
            CheckBox optionD = new CheckBox(question.getD());

            Set<String> selectedAnswers = studentAnswers.getOrDefault(question.getId(), new HashSet<>());
            optionA.setSelected(selectedAnswers.contains("a"));
            optionB.setSelected(selectedAnswers.contains("b"));
            optionC.setSelected(selectedAnswers.contains("c"));
            optionD.setSelected(selectedAnswers.contains("d"));

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
        playSound("button_click.mp3");
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
            displayQuestion();
            updateNavigationButtons();
        }
    }

    @FXML
    public void prevQuestion() {
        playSound("button_click.mp3");
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            displayQuestion();
            updateNavigationButtons();
        }
    }

    @FXML
    public void submitQuiz() {
        if (quizSubmitted) return;

        quizSubmitted = true;
        quizTimer.stop(); // Stop the timer when the quiz is submitted

        int correctAnswers = 0;
        int totalScore = 0;
        int maxScore = 0;

        for (Question question : questions) {
            // Convert new "answer":"ABD" format into lowercase set: ["a", "b", "d"]
            Set<String> correctAnswersSet = new HashSet<>();
            for (char c : question.getAnswer().toCharArray()) {
                correctAnswersSet.add(String.valueOf(c).toLowerCase());
            }

            Set<String> studentAnswerSet = studentAnswers.getOrDefault(question.getId(), new HashSet<>());

            maxScore += question.getPoints();

            if (correctAnswersSet.equals(studentAnswerSet)) {
                correctAnswers++;
                totalScore += question.getPoints();
            }
        }

        final int finalCorrectAnswers = correctAnswers;
        final int finalTotalScore = totalScore;
        final int finalMaxScore = maxScore;
        final double precision = (double) finalCorrectAnswers / questions.size() * 100;

        // Store the grade data
        DataCollection data = loadData();
        data.getGrades().add(studentId, examId, finalTotalScore);
        storeData(data);

        playSound("submit_success.mp3");

        Platform.runLater(() -> {
            Alert resultAlert = new Alert(AlertType.INFORMATION);
            resultAlert.setHeaderText("Quiz Results");
            resultAlert.setContentText(String.format("%d/%d Correct, precision: %.2f%%, score: %d/%d",
                    finalCorrectAnswers, questions.size(), precision, finalTotalScore, finalMaxScore));
            resultAlert.showAndWait();
            ((Stage) submitButton.getScene().getWindow()).close();
            openQuizSelectionScreen();
        });
    }

    private void playSound(String fileName) {
        AudioClip sound = new AudioClip(getClass().getResource("/sounds/" + fileName).toString());
        sound.play();
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

            MainController mainController = fxmlLoader.getController();
            mainController.setUsername(username);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
