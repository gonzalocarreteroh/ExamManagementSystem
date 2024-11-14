package comp3111.examsystem.controller.teacher;

import comp3111.examsystem.controller.ControllerBase;
import comp3111.examsystem.model.*;
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

public class ExamManageController extends ControllerBase implements Initializable {
    public TextField filterExamName;
    public ChoiceBox<String> filterCourseID;
    public ChoiceBox<String> filterPublish;

    public TextField filterQuestion;
    public ChoiceBox<String> filterType;
    public TextField filterScore;

    /*
    public TextField filterQuestion;
    public ChoiceBox<String> filterType;
    public TextField filterScore;
    */

    public Integer thisId = null;
    /*
    public TextField thisQuestion;
    public TextField thisOptionA;
    public TextField thisOptionB;
    public TextField thisOptionC;
    public TextField thisOptionD;
    public TextField thisAnswer;
    public ChoiceBox<String> thisType;
    public TextField thisScore;
    */

    public static class Row {
        public int id;
        public String examName, courseID, examTime, publish;

        public Row(int id, String examName, String courseID, String examTime, String publish) {
            this.id = id;
            this.examName = examName;
            this.courseID = courseID;
            this.examTime = examTime;
            this.publish = publish;
        }

        public String getexamName() { return examName; }
        public String getcourseID() { return courseID; }
        public String getexamTime() { return examTime; }
        public String getpublish() { return publish; }
    }

    public TableColumn<Row, String> columnexamName;
    public TableColumn<Row, String> columncourseID;
    public TableColumn<Row, String> columnexamTime;
    public TableColumn<Row, String> columnpublish;

    @FXML
    private TableView<Row> examTable;
    private final ObservableList<Row> examList = FXCollections.observableArrayList();

    // @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        examTable.setItems(examList);

        // Add all course codes to the filterCourseID choice box
        Course[] courses = loadData().getCourses().all();
        ObservableList<String> courseCodes = FXCollections.observableArrayList();
        for (Course course : courses) {
            courseCodes.add(course.getCode());
        }
        filterCourseID.setItems(courseCodes);


        examTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                thisId = newValue.id;
                /*
                thisQuestion.setText(newValue.getQuestion());
                thisOptionA.setText(newValue.getOptionA());
                thisOptionB.setText(newValue.getOptionB());
                thisOptionC.setText(newValue.getOptionC());
                thisOptionD.setText(newValue.getOptionD());
                thisAnswer.setText(newValue.getAnswer());
                thisType.setValue(newValue.getType());
                thisScore.setText(newValue.getScore());
                */
            }
        });

        columnexamName.setCellValueFactory(new PropertyValueFactory<>("examName"));
        columncourseID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        columnexamTime.setCellValueFactory(new PropertyValueFactory<>("examTime"));
        columnpublish.setCellValueFactory(new PropertyValueFactory<>("publish"));

        // questionTable.setItems(questionList);
        refresh();
    }

    public void loadExams() {
        // Get the id of the course from the course name
        Integer course_id;
        if (filterCourseID.getValue() == null) {
            course_id = null;
        } else {
            String course_code = filterCourseID.getValue();
            Course[] courses = loadData().getCourses().all(course_code);
            course_id = courses[0].getId();
        }
        // boolean published = filterPublish.getValue().equals("yes");
        Boolean published;
        if (filterPublish.getValue() == null) {
            published = null;
        } else {
            published = filterPublish.getValue().equals("yes");
        }
        Exam[] exams = loadData().getExams().all(filterExamName.getText(), course_id, published);
        examList.clear();

        for (Exam exam : exams) {
            String course_name = loadData().getCourses().get(exam.getCourseId()).getName();
            examList.add(new Row(
                    exam.getId(),
                    exam.getName(),
                    course_name,
                    Integer.toString(exam.getDuration()),
                    exam.getPublished() ? "yes" : "no"
            ));
        }
    }

    @FXML
    public void refresh() {
        loadExams();
    }

    @FXML
    public void reset_exams() {
        filterExamName.clear();
        filterCourseID.setValue(null);
        filterPublish.setValue(null);
        loadExams();
    }

    @FXML
    public void query_exams() { loadExams(); }

    /*
    private void clearForm() {
        thisId = null;
        thisQuestion.clear();
        thisOptionA.clear();
        thisOptionB.clear();
        thisOptionC.clear();
        thisOptionD.clear();
        thisAnswer.clear();
        thisType.setValue(null);
        thisScore.clear();
    }

    @FXML
    public void add() {
        DataCollection data = loadData();
        data.getQuestions().add(
                thisQuestion.getText(),
                thisOptionA.getText(),
                thisOptionB.getText(),
                thisOptionC.getText(),
                thisOptionD.getText(),
                thisAnswer.getText(),
                thisType.getValue().equals("Single") ? Type.Single : Type.Multiple,
                Integer.parseInt(thisScore.getText())
        );
        storeData(data);

        clearForm();
        loadQuestions();
    }

    @FXML
    public void update() {
        if (thisId == null) {
            return;
        }

        DataCollection data = loadData();
        data.getQuestions().update(
                new Question(
                        thisId,
                        thisQuestion.getText(),
                        thisOptionA.getText(),
                        thisOptionB.getText(),
                        thisOptionC.getText(),
                        thisOptionD.getText(),
                        thisAnswer.getText(),
                        thisType.getValue().equals("Single") ? Type.Single : Type.Multiple,
                        Integer.parseInt(thisScore.getText())
                )
        );
        storeData(data);

        clearForm();
        loadQuestions();
    }

    */
    @FXML
    public void delete() {
        if (thisId != null) {
            DataCollection data = loadData();
            data.getExams().remove(thisId);
            storeData(data);

            // clearForm();
            loadExams();
        }
    }

}

