package comp3111.examsystem.controller.manager;

import comp3111.examsystem.controller.ControllerBase;
import comp3111.examsystem.model.DataCollection;
import comp3111.examsystem.model.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class TeacherManagementController extends ControllerBase implements Initializable {
    public TextField filterUsername;
    public TextField filterName;
    public TextField filterDepartment;

    public Integer thisId = null;
    public TextField thisUsername;
    public TextField thisName;
    public TextField thisAge;
    public TextField thisPosition;
    public TextField thisDepartment;
    public TextField thisPassword;

    public static class Row {
        public int id;
        public String username, name, age, position, department, password;

        public Row(int id, String username, String name, String age, String position, String department, String password) {
            this.id = id;
            this.username = username;
            this.name = name;
            this.age = age;
            this.position = position;
            this.department = department;
            this.password = password;
        }

        public String getUsername() { return username; }
        public String getName() { return name; }
        public String getAge() { return age; }
        public String getPosition() { return position; }
        public String getDepartment() { return department; }
        public String getPassword() { return password; }
    }

    public TableColumn<Row, String> columnUsername;
    public TableColumn<Row, String> columnName;
    public TableColumn<Row, String> columnAge;
    public TableColumn<Row, String> columnPosition;
    public TableColumn<Row, String> columnDepartment;
    public TableColumn<Row, String> columnPassword;

    @FXML
    private TableView<Row> teacherTable;
    private final ObservableList<Row> teacherList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        teacherTable.setItems(teacherList);

        teacherTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                thisId = newValue.id;
                thisUsername.setText(newValue.getUsername());
                thisName.setText(newValue.getName());
                thisAge.setText(newValue.getAge());
                thisPosition.setText(newValue.getPosition());
                thisDepartment.setText(newValue.getDepartment());
                thisPassword.setText(newValue.getPassword());
            }
        });

        columnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        columnPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        columnDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        columnPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        refresh();
    }

    @FXML
    public void reset() {
        thisId = null;
        thisUsername.clear();
        thisPassword.clear();
        thisName.clear();
        thisAge.clear();
        thisDepartment.clear();
        thisPosition.clear();
        refresh();
    }

    @FXML
    public void refresh() {
        Teacher[] teachers = loadData().getTeachers().all(filterUsername.getText(), filterName.getText(), filterDepartment.getText());
        teacherList.clear();
        teacherList.addAll(Arrays.stream(teachers).map(teacher ->
                new Row(teacher.getId(), teacher.getUsername(), teacher.getName(),
                        Integer.toString(teacher.getAge()), teacher.getPosition(),
                        teacher.getDepartment(), teacher.getPassword())).toList());
    }

    @FXML
    public void add() {
        DataCollection data = loadData();
        data.getTeachers().add(thisUsername.getText(), thisPassword.getText(),
                thisName.getText(), Integer.parseInt(thisAge.getText()),
                thisDepartment.getText(), thisPosition.getText());
        storeData(data);
        reset();
    }

    @FXML
    public void update() {
        if (thisId == null) {
            return;
        }

        DataCollection data = loadData();
        data.getTeachers().update(new Teacher(
                thisId, thisUsername.getText(), thisPassword.getText(),
                thisName.getText(), Integer.parseInt(thisAge.getText()),
                thisDepartment.getText(), thisPosition.getText()
        ));
        storeData(data);
        refresh();
    }

    public void delete() {
        var data = loadData();
        data.getTeachers().remove(thisId);
        storeData(data);
        reset();
    }
}
