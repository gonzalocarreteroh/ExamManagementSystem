package comp3111.examsystem.controller.manager;

import comp3111.examsystem.controller.ControllerBase;
import comp3111.examsystem.model.DataCollection;
import comp3111.examsystem.model.Gender;
import comp3111.examsystem.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
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

    public Integer thisId = null;
    public TextField thisUsername;
    public TextField thisName;
    public TextField thisAge;
    public ChoiceBox<String> thisGender;
    public TextField thisDepartment;
    public TextField thisPassword;

    public static class Row {
        public int id;
        public String username, name, age, gender, department, password;

        public Row(int id, String username, String name, String age, String gender, String department, String password) {
            this.id = id;
            this.username = username;
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.department = department;
            this.password = password;
        }

        public String getUsername() { return username; }
        public String getName() { return name; }
        public String getAge() { return age; }
        public String getGender() { return gender; }
        public String getDepartment() { return department; }
        public String getPassword() { return password; }
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
        studentTable.setItems(studentList);

        studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                thisId = newValue.id;
                thisUsername.setText(newValue.getUsername());
                thisName.setText(newValue.getName());
                thisAge.setText(newValue.getAge());
                thisGender.setValue(newValue.getGender());
                thisDepartment.setText(newValue.getDepartment());
                thisPassword.setText(newValue.getPassword());
            }
        });

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
        loadStudents();
    }

    @FXML
    public void reset() {
        filterUsername.clear();
        filterName.clear();
        filterDepartment.clear();
        loadStudents();
    }

    @FXML
    public void query() {
        loadStudents();
    }

    private void clearForm() {
        thisId = null;
        thisUsername.clear();
        thisPassword.clear();
        thisName.clear();
        thisAge.clear();
        thisDepartment.clear();
        thisGender.setValue(null);
    }

    @FXML
    public void add() {
        DataCollection data = loadData();
        data.getStudents().add(
                thisUsername.getText(),
                thisPassword.getText(),
                thisName.getText(),
                Integer.parseInt(thisAge.getText()),
                thisDepartment.getText(),
                thisGender.getValue().equals("Female") ? Gender.Female : Gender.Male
        );
        storeData(data);

        clearForm();
        loadStudents();
    }

    @FXML
    public void update() {
        if (thisId == null) {
            return;
        }

        DataCollection data = loadData();
        data.getStudents().update(
                new Student(
                        thisId,
                        thisUsername.getText(),
                        thisPassword.getText(),
                        thisName.getText(),
                        Integer.parseInt(thisAge.getText()),
                        thisDepartment.getText(),
                        thisGender.getValue().equals("Female") ? Gender.Female : Gender.Male
                )
        );

        storeData(data);
        loadStudents();
    }

    public void delete() {
        if (thisId != null) {
            var data = loadData();
            data.getStudents().remove(thisId);
            storeData(data);

            clearForm();
            loadStudents();
        }
    }
}
