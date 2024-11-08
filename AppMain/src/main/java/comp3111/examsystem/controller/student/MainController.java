package comp3111.examsystem.controller.student;

import comp3111.examsystem.controller.ControllerBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends ControllerBase implements Initializable {
    @FXML
    ComboBox<String> examCombox;

    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void openExamUI() {
    }

    @FXML
    public void openGradeStatistic() {
    }

    @FXML
    public void exit() {
        System.exit(0);
    }
}
