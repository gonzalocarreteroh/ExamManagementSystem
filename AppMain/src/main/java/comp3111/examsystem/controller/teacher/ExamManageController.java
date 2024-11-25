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
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Controller class for managing exams and questions in the teacher module.
 * Provides functionalities for filtering, querying, and updating exam and question data,
 * as well as managing relationships between exams and questions.
 */
public class ExamManageController extends ControllerBase implements Initializable {
    public TextField filterExamName;
    public ChoiceBox<String> filterCourseID;
    public ChoiceBox<String> filterPublish;

    public TextField filterQuestion;
    public ChoiceBox<String> filterType;
    public TextField filterScore;

    public TextField parameterExamName;
    public TextField parameterExamTime;
    public ChoiceBox<String> parameterCourseID;
    public ChoiceBox<String> parameterPublish;

    // For left table
    public Integer thisId = null;

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

    /**
     * Represents a row in the exam table.
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

        public String getExamName() { return examName; }
        public String getCourseID() { return courseID; }
        public String getExamTime() { return examTime; }
        public String getPublish() { return publish; }
    }

    // For right and centre tables
    /**
     * Represents a row in the question table.
     */
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
    TableView<Row> examTable;
    ObservableList<Row> examList = FXCollections.observableArrayList();
    // For right table
    @FXML
    private TableView<RowQuestions> questionTable;
    private final ObservableList<RowQuestions> questionList = FXCollections.observableArrayList();
    // For centre table
    @FXML
    private TableView<RowQuestions> newQuestionTable;
    ObservableList<RowQuestions> newQuestionList = FXCollections.observableArrayList();


    /**
     * Initializes the controller. Configures table bindings and populates choice boxes.
     *
     * @param url            The location of the FXML resource.
     * @param resourceBundle The resource bundle for localization.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        examTable.setItems(examList);
        // For right table
        questionTable.setItems(questionList);
        // For centre table
        newQuestionTable.setItems(newQuestionList);

        // Add all course codes to the filterCourseID and parameterCourseID choice boxes
        Course[] courses = loadData().getCourses().all();
        ObservableList<String> courseCodes = FXCollections.observableArrayList();
        for (Course course : courses) {
            courseCodes.add(course.getCode());
        }
        filterCourseID.setItems(courseCodes);
        parameterCourseID.setItems(courseCodes);


        examTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                thisId = newValue.id;
                parameterExamName.setText(newValue.getExamName());
                parameterCourseID.setValue(newValue.getCourseID());
                parameterExamTime.setText(newValue.getExamTime());
                parameterPublish.setValue(newValue.getPublish());
                loadNewQuestions();
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

        newQuestionTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                thisIdNewQuestion = newValue.idQuestion;
                thisNewQuestionText = newValue.getQuestionText();
                thisNewType = newValue.getType();
                thisNewScore = newValue.getScore();
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

    /**
     * Loads exams into the table based on filter criteria.
     */
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
            String course_name = loadData().getCourses().get(exam.getCourseId()).getCode();
            examList.add(new Row(
                    exam.getId(),
                    exam.getName(),
                    course_name,
                    Integer.toString(exam.getDuration()),
                    exam.getPublished() ? "yes" : "no"
            ));
        }
    }

    /**
     * Loads questions into the table based on filter criteria.
     */
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

    /**
     * Loads questions associated with the selected exam.
     */
    public void loadNewQuestions() {
        if (thisId != null) {
            Exam exam = loadData().getExams().get(thisId);
            int[] questionIds = exam.getQuestionIds();
            Question[] questions = new Question[questionIds.length];
            for (int i = 0; i < questions.length; ++i) {
                questions[i] = loadData().getQuestions().get(questionIds[i]);
            }
            newQuestionList.clear();

            for (Question question : questions) {
                newQuestionList.add(new ExamManageController.RowQuestions(
                        question.getId(),
                        question.getTitle(),
                        question.getType() == Type.Single ? "Single" : "Multiple",
                        Integer.toString(question.getPoints())
                ));
            }
        }
    }

    /**
     * Refreshes the data in all tables.
     */
    @FXML
    public void refresh() {
        loadExams();
        loadQuestions();
    }

    /**
     * Resets filters for the exams table and reloads data.
     */
    @FXML
    public void reset_exams() {
        filterExamName.clear();
        filterCourseID.setValue(null);
        filterPublish.setValue(null);
        loadExams();
    }

    @FXML
    public void query_exams() { loadExams(); }

    /**
     * Resets filters for the questions table and reloads data.
     */
    @FXML
    public void reset_questions() {
        filterQuestion.clear();
        filterType.setValue(null);
        filterScore.clear();
        loadQuestions();
    }

    @FXML
    public void query_questions() { loadQuestions(); }

    /**
     * Deletes the currently selected exam.
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

    /**
     * Adds the selected question to the new question list.
     */
    @FXML
    public void addLeft() {
        if (thisIdQuestion != null) {
            newQuestionList.add(new RowQuestions(thisIdQuestion, thisQuestionText, thisType, thisScore));
        }
    }

    /**
     * Deletes the selected question from the new question list.
     */
    @FXML
    public void deleteLeft() {
        if (thisIdNewQuestion != null) {
            RowQuestions rowToDelete = null;
            for (RowQuestions row : newQuestionList) {
                if (row.idQuestion == thisIdNewQuestion) {
                    rowToDelete = row;
                    break;
                }
            }

            if (rowToDelete != null) {
                newQuestionList.remove(rowToDelete);
            }
        }
    }

    /**
     * Adds a new exam with the current input parameters.
     */
    @FXML
    public void add_exam() {
        DataCollection data = loadData();
        // Add all the questions in the newQuestionList to the exam
        data.getExams().add(
                parameterExamName.getText(),
                Integer.parseInt(parameterExamTime.getText()),
                Arrays.stream(data.getCourses().all()).filter(c -> c.getCode().equals(parameterCourseID.getValue())).findFirst().get().getId(),
                parameterPublish.getValue().equals("yes"),
                newQuestionList.stream().mapToInt(r -> r.idQuestion).toArray()
        );
        storeData(data);
        loadExams();
    }

    /**
     * Updates the currently selected exam with the current input parameters.
     */
    @FXML
    public void update_exam() {
        ExamDb db = loadData().getExams();
        db.update(new Exam(
                thisId,
                parameterExamName.getText(),
                Integer.parseInt(parameterExamTime.getText()),
                Arrays.stream(loadData().getCourses().all()).filter(c -> c.getCode().equals(parameterCourseID.getValue())).findFirst().get().getId(),
                parameterPublish.getValue().equals("yes"),
                newQuestionList.stream().mapToInt(r -> r.idQuestion).toArray()
        ));
        storeData(loadData());
        loadExams();
    }

}

