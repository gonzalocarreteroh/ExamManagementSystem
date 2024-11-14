package comp3111.examsystem.controller.student;

import comp3111.examsystem.Main;
import comp3111.examsystem.controller.ControllerBase;
import comp3111.examsystem.model.DataCollection;
import comp3111.examsystem.model.Gender;
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

    public void initialize(URL location, ResourceBundle resources) {
        // Match ChoiceBox values exactly with the Gender enum values to avoid case sensitivity issues
        if (genderRegister.getItems().isEmpty()) {
            genderRegister.getItems().addAll("FEMALE", "MALE", "OTHER");
        }
    }

    @FXML
    public void storeRegister() {
        if (passwordTxtRegister.getText().equals(passwordConfirmTxtRegister.getText())) {
            try {
                DataCollection data = loadData();
                // No need to convert case as ChoiceBox values now match enum values
                Gender genderEnum = Gender.valueOf(genderRegister.getValue());

                data.getStudents().add(
                        usernameTxtRegister.getText(),
                        passwordTxtRegister.getText(),
                        nameTxtRegister.getText(),
                        Integer.parseInt(ageTxtRegister.getText()),
                        departmentTxtRegister.getText(),
                        genderEnum
                );

                storeData(data);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Registration successful");
                alert.setContentText("Welcome to the system");
                alert.showAndWait();
                ((Stage) usernameTxtRegister.getScene().getWindow()).close();

            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Registration failed");
                alert.setContentText("Invalid gender selection. Please select a valid option.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Registration failed");
            alert.setContentText("Passwords do not match. Please try again.");
            alert.showAndWait();
        }
    }

    @FXML
    public void close() {
        ((Stage) usernameTxtRegister.getScene().getWindow()).close();
    }
}
