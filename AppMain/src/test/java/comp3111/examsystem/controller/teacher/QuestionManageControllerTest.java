package comp3111.examsystem.controller.teacher;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionManageControllerTest {

    private static QuestionManageController controller;

    @BeforeAll
    static void setup() {
        // Platform.startup(() -> {}); // Initialize JavaFX Toolkit
        controller = new QuestionManageController();
        controller.filterQuestion = new TextField();
        controller.filterType = new ChoiceBox<>();
        controller.filterScore = new TextField();
        controller.questionTable = new TableView<>();
    }

    @Test
    void testLoadQuestions() {
        Platform.runLater(() -> {
            // Mock filters
            controller.filterQuestion.setText("Midterm");
            controller.filterType.setValue("Single");
            controller.filterScore.setText("10");

            // Load questions
            controller.loadQuestions();

            ObservableList<QuestionManageController.Row> questionList = controller.questionTable.getItems();
            assertEquals(2, questionList.size());

            // Validate first question
            QuestionManageController.Row firstQuestion = questionList.get(0);
            assertEquals("What is Java?", firstQuestion.getQuestion());
            assertEquals("Programming language", firstQuestion.getOptionA());
            assertEquals("Coffee", firstQuestion.getOptionB());
            assertEquals("Island", firstQuestion.getOptionC());
            assertEquals("None", firstQuestion.getOptionD());
            assertEquals("Programming language", firstQuestion.getAnswer());
            assertEquals("Single", firstQuestion.getType());
            assertEquals("10", firstQuestion.getScore());
        });
    }

    @Test
    void testAddQuestion() {
        Platform.runLater(() -> {
            // Mock new question form
            controller.thisQuestion = new TextField("What is Python?");
            controller.thisOptionA = new TextField("Programming language");
            controller.thisOptionB = new TextField("Snake");
            controller.thisOptionC = new TextField("Game");
            controller.thisOptionD = new TextField("None");
            controller.thisAnswer = new TextField("Programming language");
            controller.thisType = new ChoiceBox<>();
            controller.thisType.setValue("Single");
            controller.thisScore = new TextField("15");

            // Add question
            controller.add();

            // Validate added question
            ObservableList<QuestionManageController.Row> questionList = controller.questionTable.getItems();
            assertEquals(1, questionList.size());
            QuestionManageController.Row newQuestion = questionList.get(0);
            assertEquals("What is Python?", newQuestion.getQuestion());
            assertEquals("15", newQuestion.getScore());
        });
    }

    @Test
    void testUpdateQuestion() {
        Platform.runLater(() -> {
            // Mock existing question
            controller.thisId = 1;
            controller.thisQuestion = new TextField("What is Java?");
            controller.thisOptionA = new TextField("Programming language");
            controller.thisOptionB = new TextField("Coffee");
            controller.thisOptionC = new TextField("Island");
            controller.thisOptionD = new TextField("None");
            controller.thisAnswer = new TextField("Programming language");
            controller.thisType = new ChoiceBox<>();
            controller.thisType.setValue("Single");
            controller.thisScore = new TextField("10");

            // Update question
            controller.thisQuestion.setText("What is updated Java?");
            controller.update();

            // Validate updated question
            ObservableList<QuestionManageController.Row> questionList = controller.questionTable.getItems();
            assertEquals(1, questionList.size());
            QuestionManageController.Row updatedQuestion = questionList.get(0);
            assertEquals("What is updated Java?", updatedQuestion.getQuestion());
        });
    }

    @Test
    void testDeleteQuestion() {
        Platform.runLater(() -> {
            // Mock existing question
            controller.thisId = 1;
            controller.loadQuestions();

            // Delete question
            controller.delete();

            // Validate deletion
            ObservableList<QuestionManageController.Row> questionList = controller.questionTable.getItems();
            assertEquals(0, questionList.size());
        });
    }

    @Test
    void testResetFilters() {
        Platform.runLater(() -> {
            // Set filters
            controller.filterQuestion.setText("Sample");
            controller.filterType.setValue("Single");
            controller.filterScore.setText("10");

            // Reset filters
            controller.reset();

            assertEquals("", controller.filterQuestion.getText());
            assertNull(controller.filterType.getValue());
            assertEquals("", controller.filterScore.getText());
        });
    }
}

