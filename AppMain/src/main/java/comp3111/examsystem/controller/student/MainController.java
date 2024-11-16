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
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainController extends ControllerBase implements Initializable {
    @FXML
    private ComboBox<String> examComboBox;

    private ExamDb examDb;
    private String username; // Store the logged-in username

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataCollection data = loadData();
        examDb = data.getExams();

        Arrays.stream(examDb.all()).forEach(exam -> examComboBox.getItems().add(exam.getName()));
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

            ((Stage) examComboBox.getScene().getWindow()).close();
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
