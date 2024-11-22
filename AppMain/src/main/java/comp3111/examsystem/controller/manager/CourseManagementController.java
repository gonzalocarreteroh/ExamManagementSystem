package comp3111.examsystem.controller.manager;

import comp3111.examsystem.controller.ControllerBase;
import comp3111.examsystem.model.Course;
import comp3111.examsystem.model.DataCollection;
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

public class CourseManagementController extends ControllerBase implements Initializable {
    public TextField filterCode;
    public TextField filterName;
    public TextField filterDepartment;

    public Integer thisId = null;
    public TextField thisCode;
    public TextField thisName;
    public TextField thisDepartment;

    public static class Row {
        public int id;
        public String code, name, department;

        public Row(int id, String code, String name, String department) {
            this.id = id;
            this.code = code;
            this.name = name;
            this.department = department;
        }

        public String getCode() { return code; }
        public String getName() { return name; }
        public String getDepartment() { return department; }
    }

    public TableColumn<Row, String> columnCode;
    public TableColumn<Row, String> columnName;
    public TableColumn<Row, String> columnDepartment;

    @FXML
    private TableView<Row> courseTable;
    private final ObservableList<Row> courseList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        courseTable.setItems(courseList);

        courseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                thisId = newValue.id;
                thisCode.setText(newValue.getCode());
                thisName.setText(newValue.getName());
                thisDepartment.setText(newValue.getDepartment());
            }
        });

        columnCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));

        refresh();
    }

    private void loadCourses() {
        Course[] courses = loadData().getCourses().all(filterCode.getText(), filterName.getText(), filterDepartment.getText());
        courseList.clear();
        courseList.addAll(Arrays.stream(courses).map(course -> new Row(
                course.getId(),
                course.getCode(),
                course.getName(),
                course.getDepartment()
        )).toList());
    }

    @FXML
    public void refresh() {
        loadCourses();
    }

    @FXML
    public void reset() {
        filterCode.clear();
        filterName.clear();
        filterDepartment.clear();
        loadCourses();
    }

    @FXML
    public void query() {
        loadCourses();
    }

    private void clearForm() {
        thisId = null;
        thisCode.clear();
        thisName.clear();
        thisDepartment.clear();
    }

    @FXML
    public void add() {
        DataCollection data = loadData();
        data.getCourses().add(
                thisCode.getText(),
                thisName.getText(),
                thisDepartment.getText()
        );
        storeData(data);

        clearForm();
        loadCourses();
    }

    @FXML
    public void update() {
        if (thisId == null) {
            return;
        }

        DataCollection data = loadData();
        data.getCourses().update(
                new Course(
                        thisId,
                        thisCode.getText(),
                        thisName.getText(),
                        thisDepartment.getText()
                )
        );
        storeData(data);

        loadCourses();
    }

    public void delete() {
        var data = loadData();
        data.getCourses().remove(thisId);
        storeData(data);

        clearForm();
        loadCourses();
    }
}
