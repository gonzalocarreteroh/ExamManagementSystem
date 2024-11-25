package comp3111.examsystem.controller.student;

import comp3111.examsystem.Main;
import comp3111.examsystem.controller.ControllerBase;
import comp3111.examsystem.model.DataCollection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends ControllerBase implements Initializable {
    @FXML
    TextField usernameTxt;
    @FXML
    PasswordField passwordTxt;

    DataCollection data; // Store a reference to DataCollection for shared access

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = loadData(); // Load existing data, including StudentDb
    }

    @FXML
    public void login(ActionEvent e) {
        boolean isAuthenticated = data.getStudents().login(usernameTxt.getText(), passwordTxt.getText());

        if (isAuthenticated) {
            // Show a success alert with an OK button
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Login Successful");
            alert.setContentText("Click OK to proceed.");
            alert.showAndWait();

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("student/MainUI.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                // Pass the username to the MainController
                MainController mainController = fxmlLoader.getController();
                mainController.setUsername(usernameTxt.getText());

                Stage stage = new Stage();
                stage.setTitle("Hi " + usernameTxt.getText() + ", Welcome to HKUST Examination System");
                stage.setScene(scene);
                stage.show();

                // Close the login window
                ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Login failed");
            alert.setContentText("Invalid username or password. Please try again.");
            alert.showAndWait();
        }
    }

    @FXML
    public void register() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("student/RegisterUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Student Register");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
