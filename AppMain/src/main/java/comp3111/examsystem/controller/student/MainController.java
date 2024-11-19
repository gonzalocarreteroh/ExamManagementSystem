package comp3111.examsystem.controller.student;

import comp3111.examsystem.Main;
import comp3111.examsystem.controller.ControllerBase;
import comp3111.examsystem.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class MainController extends ControllerBase implements Initializable {
    @FXML
    private ComboBox<String> examComboBox;

    private ExamDb examDb;
    private String username; // Store the logged-in username

    public void setUsername(String username) {
        this.username = username;
        loadAvailableExams();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialization moved to loadAvailableExams()
    }

    private void loadAvailableExams() {
        DataCollection data = loadData();
        examDb = data.getExams();

        // Get the student ID associated with the username
        int studentId = Arrays.stream(data.getStudents().all())
                .filter(student -> student.getUsername().equals(username))
                .findFirst()
                .map(Student::getId)
                .orElse(-1);

        if (studentId == -1) {
            throw new IllegalStateException("Unable to find student with username: " + username);
        }

        // Get the exams that the student has already taken
        Set<Integer> takenExamIds = Arrays.stream(data.getGrades().all())
                .filter(grade -> grade.getStudentId() == studentId)
                .map(Grade::getExamId)
                .collect(Collectors.toSet());

        // Now, only include exams that are not in takenExamIds
        examComboBox.getItems().clear();
        Arrays.stream(examDb.all())
                .filter(exam -> !takenExamIds.contains(exam.getId()))
                .forEach(exam -> examComboBox.getItems().add(exam.getName()));

        if (examComboBox.getItems().isEmpty()) {
            // Optionally, show a message that the student has completed all exams
            showAlert(Alert.AlertType.INFORMATION, "No Available Exams", "You have completed all available exams.");
        }
    }

    @FXML
    public void openQuizScreen(ActionEvent e) {
        String selectedExamName = examComboBox.getValue();
        if (selectedExamName == null) {
            showAlert(Alert.AlertType.WARNING, "No Quiz Selected", "Please select a quiz from the list.");
            return;
        }

        Exam selectedExam = Arrays.stream(examDb.all())
                .filter(exam -> exam.getName().equals(selectedExamName))
                .findFirst()
                .orElse(null);

        if (selectedExam == null) {
            showAlert(Alert.AlertType.ERROR, "Quiz Not Found", "The selected quiz could not be found.");
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("student/QuizUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            // Pass the username to the QuizController
            QuizController quizController = fxmlLoader.getController();
            quizController.setUsername(username);
            quizController.loadExam(selectedExam);

            Stage stage = new Stage();
            stage.setTitle("Quiz: " + selectedExamName);
            stage.setScene(scene);
            stage.show();

            // Close the current window
            ((Stage) examComboBox.getScene().getWindow()).close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void openGradeStatistic() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("student/GradeStatisticsUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            // Pass the username to GradeStatisticsController
            GradeStatisticsController controller = fxmlLoader.getController();
            controller.setUsername(username);

            Stage stage = new Stage();
            stage.setTitle("Grade Statistics");
            stage.setScene(scene);
            stage.show();

            // Removed the line that closes the current window
            // ((Stage) examComboBox.getScene().getWindow()).close();

        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load Grade Statistics.");
        }
    }

    @FXML
    public void exit() {
        storeData(loadData());
        System.exit(0);
    }

    private void showAlert(Alert.AlertType type, String header, String content) {
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
