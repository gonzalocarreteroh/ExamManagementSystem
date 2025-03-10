package comp3111.examsystem.controller.manager;

import comp3111.examsystem.Main;
import comp3111.examsystem.controller.ControllerBase;
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
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;

    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void login(ActionEvent e) {
        if (loadData().getManagers().login(usernameTxt.getText(), passwordTxt.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Login successful");
            alert.setContentText("Welcome to the system");
            alert.showAndWait();

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("manager/MainUI.fxml"));

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
}
