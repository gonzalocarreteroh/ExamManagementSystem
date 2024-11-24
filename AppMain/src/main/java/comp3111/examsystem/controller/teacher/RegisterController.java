package comp3111.examsystem.controller.teacher;

import comp3111.examsystem.Main;
import comp3111.examsystem.controller.ControllerBase;
import comp3111.examsystem.model.DataCollection;
import comp3111.examsystem.model.TeacherDb;
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
 * Controller class for the teacher registration interface.
 * Handles the creation of new teacher accounts.
 */
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
    private ChoiceBox<String> positionRegister;
    @FXML
    private TextField departmentTxtRegister;
    @FXML
    private PasswordField passwordTxtRegister;
    @FXML
    private PasswordField passwordConfirmTxtRegister;

    /**
     * Initializes the register controller. Currently, no specific setup is required.
     *
     * @param location  the location used to resolve relative paths for the root object, or null if unknown.
     * @param resources the resources used to localize the root object, or null if not specified.
     */
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Stores the registration information for a new teacher.
     * Validates that the entered passwords match and adds the teacher to the database.
     * If successful, shows a success alert and closes the registration window.
     * If passwords do not match, shows a failure alert.
     */
    @FXML
    public void storeRegister() {
        if (passwordTxtRegister.getText().equals(passwordConfirmTxtRegister.getText())) {
            DataCollection data = loadData();
            data.getTeachers().add(
                    usernameTxtRegister.getText(),
                    passwordTxtRegister.getText(),
                    nameTxtRegister.getText(),
                    Integer.parseInt(ageTxtRegister.getText()),
                    departmentTxtRegister.getText(),
                    positionRegister.getValue()
            );
            storeData(data);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Register successful");
            alert.setContentText("Welcome to the system");
            alert.showAndWait();
            ((Stage) usernameTxtRegister.getScene().getWindow()).close();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Register failed");
            alert.setContentText("Password not match");
            alert.showAndWait();
        }
    }

    /**
     * Closes the registration window.
     */
    @FXML
    public void close() {
        ((Stage) usernameTxtRegister.getScene().getWindow()).close();
    }
}
