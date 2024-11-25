package comp3111.examsystem.controller.teacher;

import comp3111.examsystem.controller.ControllerBase;
import comp3111.examsystem.model.DataCollection;
import comp3111.examsystem.model.Gender;
import comp3111.examsystem.model.Question;
import comp3111.examsystem.model.Type;
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

/**
 * Controller class for managing questions in the teacher's view of the exam system.
 */
public class QuestionManageController extends ControllerBase implements Initializable {
    public TextField filterQuestion;
    public ChoiceBox<String> filterType;
    public TextField filterScore;

    public Integer thisId = null;
    public TextField thisQuestion;
    public TextField thisOptionA;
    public TextField thisOptionB;
    public TextField thisOptionC;
    public TextField thisOptionD;
    public TextField thisAnswer;
    public ChoiceBox<String> thisType;
    public TextField thisScore;

    /**
     * Data class for representing a row in the question table.
     */
    public static class Row {
        public int id;
        public String question, optionA, optionB, optionC, optionD, answer, type, score;

        /**
         * Constructs a new Row object.
         *
         * @param id       the ID of the question
         * @param question the content of the question
         * @param optionA  option A
         * @param optionB  option B
         * @param optionC  option C
         * @param optionD  option D
         * @param answer   the correct answer
         * @param type     the type of the question
         * @param score    the score of the question
         */
        public Row(int id, String question, String optionA, String optionB, String optionC, String optionD, String answer, String type, String score) {
            this.id = id;
            this.question = question;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
            this.answer = answer;
            this.type = type;
            this.score = score;
        }

        public String getQuestion() { return question; }
        public String getOptionA() { return optionA; }
        public String getOptionB() { return optionB; }
        public String getOptionC() { return optionC; }
        public String getOptionD() { return optionD; }
        public String getAnswer() { return answer; }
        public String getType() { return type; }
        public String getScore() { return score; }
    }

    public TableColumn<Row, String> columnQuestion;
    public TableColumn<Row, String> columnOptionA;
    public TableColumn<Row, String> columnOptionB;
    public TableColumn<Row, String> columnOptionC;
    public TableColumn<Row, String> columnOptionD;
    public TableColumn<Row, String> columnAnswer;
    public TableColumn<Row, String> columnType;
    public TableColumn<Row, String> columnScore;

    @FXML
    TableView<Row> questionTable;
    private final ObservableList<Row> questionList = FXCollections.observableArrayList();

    /**
     * Initializes the controller.
     *
     * @param url            the location of the FXML file
     * @param resourceBundle the resources for localization
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questionTable.setItems(questionList);

        questionTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                thisId = newValue.id;
                thisQuestion.setText(newValue.getQuestion());
                thisOptionA.setText(newValue.getOptionA());
                thisOptionB.setText(newValue.getOptionB());
                thisOptionC.setText(newValue.getOptionC());
                thisOptionD.setText(newValue.getOptionD());
                thisAnswer.setText(newValue.getAnswer());
                thisType.setValue(newValue.getType());
                thisScore.setText(newValue.getScore());
            }
        });

        columnQuestion.setCellValueFactory(new PropertyValueFactory<>("question"));
        columnOptionA.setCellValueFactory(new PropertyValueFactory<>("optionA"));
        columnOptionB.setCellValueFactory(new PropertyValueFactory<>("optionB"));
        columnOptionC.setCellValueFactory(new PropertyValueFactory<>("optionC"));
        columnOptionD.setCellValueFactory(new PropertyValueFactory<>("optionD"));
        columnAnswer.setCellValueFactory(new PropertyValueFactory<>("answer"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnScore.setCellValueFactory(new PropertyValueFactory<>("score"));

        // questionTable.setItems(questionList);
        refresh();
    }

    /**
     * Loads questions based on filters and populates the table.
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
            questionList.add(new Row(
                question.getId(),
                question.getTitle(),
                question.getA(),
                question.getB(),
                question.getC(),
                question.getD(),
                question.getAnswer(),
                question.getType() == Type.Single ? "Single" : "Multiple",
                Integer.toString(question.getPoints())
            ));
        }
    }

    /**
     * Refreshes the question table.
     */
    @FXML
    public void refresh() {
        loadQuestions();
    }

    /**
     * Resets all filters and reloads questions.
     */
    @FXML
    public void reset() {
        filterQuestion.clear();
        filterType.setValue(null);
        filterScore.clear();
        loadQuestions();
    }

    /**
     * Filters questions based on the provided inputs.
     */
    @FXML
    public void query() { loadQuestions(); }

    /**
     * Clears the question form fields.
     */
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

    /**
     * Adds a new question to the system.
     */
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

    /**
     * Updates the selected question with new details.
     */
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

    /**
     * Deletes the selected question from the system.
     */
    @FXML
    public void delete() {
        if (thisId != null) {
            DataCollection data = loadData();
            data.getQuestions().remove(thisId);
            storeData(data);

            clearForm();
            loadQuestions();
        }
    }
}
