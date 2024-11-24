package comp3111.examsystem.controller.teacher;

import comp3111.examsystem.Main;
import comp3111.examsystem.controller.ControllerBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the teacher login interface.
 * Handles user authentication and navigation to other parts of the application.
 */
public class LoginController extends ControllerBase implements Initializable {
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;

    /**
     * Initializes the login controller. Currently, no specific setup is required.
     *
     * @param location  the location used to resolve relative paths for the root object, or null if unknown.
     * @param resources the resources used to localize the root object, or null if not specified.
     */
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Handles the login action. Authenticates the user with the provided credentials
     * and navigates to the main teacher interface if successful. Shows an alert dialog
     * if the login fails.
     *
     * @param e the action event triggered by the login button.
     */
    @FXML
    public void login(ActionEvent e) {
        if (loadData().getTeachers().login(usernameTxt.getText(), passwordTxt.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Login successful");
            alert.setContentText("Welcome to the system");
            alert.showAndWait();

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("teacher/MainUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Hi " + usernameTxt.getText() + ", Welcome to HKUST Examination System");
            try {
                stage.setScene(new Scene(fxmlLoader.load()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            stage.show();
            ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Login failed");
            alert.setContentText("Password or username incorrect");
            alert.showAndWait();
        }
    }

    /**
     * Handles the register action. Opens the teacher registration interface in a new window.
     */
    @FXML
    public void register() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("teacher/RegisterUI.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Teacher Register");
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();

        // ((Stage) usernameTxt.getScene().getWindow()).close();
    }
}
