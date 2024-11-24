package comp3111.examsystem.controller.student;

import comp3111.examsystem.controller.ControllerBase;
import comp3111.examsystem.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController extends ControllerBase {
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

    @FXML
    public void initialize() {
        // Populate gender choices
        if (genderRegister.getItems().isEmpty()) {
            genderRegister.getItems().addAll("FEMALE", "MALE");
        }
    }

    @FXML
    public void storeRegister() {
        // Validate password match
        if (!passwordTxtRegister.getText().equals(passwordConfirmTxtRegister.getText())) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Passwords do not match. Please try again.");
            return;
        }

        // Validate gender selection
        String selectedGender = genderRegister.getValue();
        if (selectedGender == null || selectedGender.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Please select a valid gender.");
            return;
        }

        try {
            // Parse age
            int age = Integer.parseInt(ageTxtRegister.getText());

            // Convert gender selection to enum
            Gender genderEnum = Gender.valueOf(selectedGender);

            // Load data and add student to database
            DataCollection data = loadData();
            data.getStudents().add(
                    usernameTxtRegister.getText(),
                    passwordTxtRegister.getText(),
                    nameTxtRegister.getText(),
                    age,
                    departmentTxtRegister.getText(),
                    genderEnum
            );

            // Save data back to storage
            storeData(data);

            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Welcome to the system.");
            ((Stage) usernameTxtRegister.getScene().getWindow()).close();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Invalid age. Please enter a valid number.");
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
