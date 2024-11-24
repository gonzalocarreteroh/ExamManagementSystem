package comp3111.examsystem.controller.teacher;

import comp3111.examsystem.Main;
import comp3111.examsystem.controller.ControllerBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the main interface of the teacher module.
 * Provides navigation to all the management and statistical tools.
 */
public class MainController extends ControllerBase implements Initializable {
    @FXML
    private VBox mainbox;

    /**
     * Initializes the controller. Currently, no specific setup is required.
     *
     * @param location  the location used to resolve relative paths for the root object, or null if unknown.
     * @param resources the resources used to localize the root object, or null if not specified.
     */
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Opens the "Question Bank Management" interface in a new window.
     * Loads the FXML file associated with the question management UI.
     */
    @FXML
    public void openQuestionManageUI() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("teacher/QuestionManageUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Question Bank Management");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the "Exam Management" interface in a new window.
     * Loads the FXML file associated with the exam management UI.
     */
    @FXML
    public void openExamManageUI() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("teacher/ExamManageUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Exam Management");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the "Grade Statistics" interface in a new window.
     * Loads the FXML file associated with the grade statistics UI.
     */
    @FXML
    public void openGradeStatistic() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("teacher/GradeStatistic.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Grade Statistics");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Exits the application.
     * Terminates the program with a system exit call.
     */
    @FXML
    public void exit() {
        System.exit(0);
    }
}
