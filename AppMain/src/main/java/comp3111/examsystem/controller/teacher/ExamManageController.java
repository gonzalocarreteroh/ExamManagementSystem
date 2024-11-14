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

    // For left table
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

    // For right table
    public Integer thisIdQuestion = null;
    public String thisQuestionText;
    public String thisType;
    public String thisScore;
    // For centre table
    public Integer thisIdNewQuestion = null;
    public String thisNewQuestionText;
    public String thisNewType;
    public String thisNewScore;


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

        public String getExamName() { return examName; }
        public String getCourseID() { return courseID; }
        public String getExamTime() { return examTime; }
        public String getPublish() { return publish; }
    }
    // For right table
    public static class RowQuestions {
        public int idQuestion;
        public String questionText, type, score;

        public RowQuestions(int idQuestion, String questionText, String type, String score) {
            this.idQuestion = idQuestion;
            this.questionText = questionText;
            this.type = type;
            this.score = score;
        }

        public String getQuestionText() { return questionText; }
        public String getType() { return type; }
        public String getScore() { return score; }
    }
    // For left table
    public TableColumn<Row, String> columnexamName;
    public TableColumn<Row, String> columncourseID;
    public TableColumn<Row, String> columnexamTime;
    public TableColumn<Row, String> columnpublish;
    // For right table
    public TableColumn<RowQuestions, String> columnquestionText;
    public TableColumn<RowQuestions, String> columntype;
    public TableColumn<RowQuestions, String> columnscore;
    // For centre table
    public TableColumn<RowQuestions, String> columnnewquestionText;
    public TableColumn<RowQuestions, String> columnnewtype;
    public TableColumn<RowQuestions, String> columnnewscore;

    // For left table
    @FXML
    private TableView<Row> examTable;
    private final ObservableList<Row> examList = FXCollections.observableArrayList();
    // For right table
    @FXML
    private TableView<RowQuestions> questionTable;
    private final ObservableList<RowQuestions> questionList = FXCollections.observableArrayList();
    // For centre table
    @FXML
    private TableView<RowQuestions> newQuestionTable;
    private final ObservableList<RowQuestions> newQuestionList = FXCollections.observableArrayList();

    // @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        examTable.setItems(examList);
        // For right table
        questionTable.setItems(questionList);
        newQuestionTable.setItems(newQuestionList);

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

        questionTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                thisIdQuestion = newValue.idQuestion;
                thisQuestionText = newValue.getQuestionText();
                thisType = newValue.getType();
                thisScore = newValue.getScore();
            }
        });

        columnexamName.setCellValueFactory(new PropertyValueFactory<>("examName"));
        columncourseID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        columnexamTime.setCellValueFactory(new PropertyValueFactory<>("examTime"));
        columnpublish.setCellValueFactory(new PropertyValueFactory<>("publish"));

        columnquestionText.setCellValueFactory(new PropertyValueFactory<>("questionText"));
        columntype.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnscore.setCellValueFactory(new PropertyValueFactory<>("score"));

        columnnewquestionText.setCellValueFactory(new PropertyValueFactory<>("questionText"));
        columnnewtype.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnnewscore.setCellValueFactory(new PropertyValueFactory<>("score"));

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

    public void loadQuestions() {
        int s;

        try {
            s = Integer.parseInt(filterScore.getText());
        } catch (NumberFormatException e) {
            s = -1;
        }
        if (filterScore.getText().isEmpty()) {
            s = -1;
        }
        Type t = filterType.getValue() == null ? null : filterType.getValue().equals("Single") ? Type.Single : Type.Multiple;
        Question[] questions = loadData().getQuestions().all(filterQuestion.getText(), t, s);
        questionList.clear();

        for (Question question : questions) {
            questionList.add(new ExamManageController.RowQuestions(
                    question.getId(),
                    question.getTitle(),
                    question.getType() == Type.Single ? "Single" : "Multiple",
                    Integer.toString(question.getPoints())
            ));
        }
    }

    @FXML
    public void refresh() {
        loadExams();
        loadQuestions();
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

    @FXML
    public void addLeft() {
        if (thisIdQuestion != null) {
            newQuestionList.add(new RowQuestions(thisIdQuestion, thisQuestionText, thisType, thisScore));
        }
    }

}

