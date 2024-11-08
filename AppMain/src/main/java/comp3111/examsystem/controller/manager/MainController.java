package comp3111.examsystem.controller.manager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private VBox mainbox;

    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void openStudentManageUI() {
    }

    @FXML
    public void openTeacherManageUI() {
    }

    @FXML
    public void openCourseManageUI() {
    }

    @FXML
    public void exit() {
        System.exit(0);
    }
}
