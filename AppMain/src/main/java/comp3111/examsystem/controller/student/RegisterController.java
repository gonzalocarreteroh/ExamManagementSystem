package comp3111.examsystem.controller.student;

import comp3111.examsystem.Main;
import comp3111.examsystem.controller.ControllerBase;
import comp3111.examsystem.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController extends ControllerBase implements Initializable {
    @FXML
    private TextField usernameTxtRegister;
    @FXML
    private TextField nameTxtRegister;
    @FXML
    private ChoiceBox<String> genderRegister;
    @FXML
    private TextField ageTxtRegister;
    @FXML
    private TextField departmentTxtRegister;
    @FXML
    private PasswordField passwordTxtRegister;
    @FXML
    private PasswordField passwordConfirmTxtRegister;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Populate gender choices
        if (genderRegister.getItems().isEmpty()) {
            genderRegister.getItems().addAll("FEMALE", "MALE", "OTHER");
        }
    }

    @FXML
    public void storeRegister() {
        if (!passwordTxtRegister.getText().equals(passwordConfirmTxtRegister.getText())) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Passwords do not match. Please try again.");
            return;
        }

        try {
            DataCollection data = loadData();
            Gender genderEnum = Gender.valueOf(genderRegister.getValue());

            // Add student to database
            data.getStudents().add(
                    usernameTxtRegister.getText(),
                    passwordTxtRegister.getText(),
                    nameTxtRegister.getText(),
                    Integer.parseInt(ageTxtRegister.getText()),
                    departmentTxtRegister.getText(),
                    genderEnum
            );

            // Save data back to storage
            storeData(data);

            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Welcome to the system.");
            ((Stage) usernameTxtRegister.getScene().getWindow()).close();
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Invalid gender selection.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "An error occurred. Please try again.");
        }
    }

    private void showAlert(Alert.AlertType type, String header, String content) {
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void close() {
        ((Stage) usernameTxtRegister.getScene().getWindow()).close();
    }
}
