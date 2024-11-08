package comp3111.examsystem.controller.manager;

import comp3111.examsystem.controller.ControllerBase;
import comp3111.examsystem.model.Gender;
import comp3111.examsystem.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentManagementController extends ControllerBase implements Initializable {
    public TextField filterUsername;
    public TextField filterName;
    public TextField filterDepartment;

    public record Row(int id, String username, String name, String age, String gender, String department, String password) {
    }

    public TableColumn<Row, String> columnUsername;
    public TableColumn<Row, String> columnName;
    public TableColumn<Row, String> columnAge;
    public TableColumn<Row, String> columnGender;
    public TableColumn<Row, String> columnDepartment;
    public TableColumn<Row, String> columnPassword;

    @FXML
    private TableView<Row> studentTable;
    private final ObservableList<Row> studentList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadStudents();
        studentTable.setItems(studentList);

        columnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        columnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        columnDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        columnPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        refresh();
    }

    private void loadStudents() {
        Student[] students = loadData().getStudents().all(filterUsername.getText(), filterName.getText(), filterDepartment.getText());
        studentList.clear();
        for (Student student : students) {
            studentList.add(new Row(
                    student.getId(),
                    student.getUsername(),
                    student.getName(),
                    Integer.toString(student.getAge()),
                    student.getGender() == Gender.Male ? "Male" : "Female",
                    student.getDepartment(),
                    student.getPassword()
            ));
        }
    }

    @FXML
    public void refresh() {
    }

    @FXML
    public void reset() {
    }

    @FXML
    public void query() {
    }

    @FXML
    public void add(ActionEvent actionEvent) {
    }

    @FXML
    public void update(ActionEvent actionEvent) {
    }
}
