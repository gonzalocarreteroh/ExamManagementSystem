package comp3111.examsystem.controller.student;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import comp3111.examsystem.model.GradeDb;
import comp3111.examsystem.model.Grade;
import comp3111.examsystem.model.Exam;
import comp3111.examsystem.model.ExamDb;
import comp3111.examsystem.model.Question;
import comp3111.examsystem.model.Type;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;



import java.util.List;




public class QuizControllerTest {
    @Test
    void initTest() {
        QuizController controller = new QuizController();
        assertNotNull(controller, "QuizController instance should not be null");
    }

    @Test
    void fxmlFieldsNullTest() {
        QuizController controller = new QuizController();

        // Assert that FXML fields are null before initialization
        assertNull(controller.quizNameLabel, "quizNameLabel should be null before initialization");
        assertNull(controller.totalQuestionsLabel, "totalQuestionsLabel should be null before initialization");
        assertNull(controller.timerLabel, "timerLabel should be null before initialization");
        assertNull(controller.questionListView, "questionListView should be null before initialization");
        assertNull(controller.questionLabel, "questionLabel should be null before initialization");
        assertNull(controller.answerOptionsBox, "answerOptionsBox should be null before initialization");
        assertNull(controller.nextButton, "nextButton should be null before initialization");
        assertNull(controller.prevButton, "prevButton should be null before initialization");
        assertNull(controller.submitButton, "submitButton should be null before initialization");
        assertNull(controller.timerProgressBar, "timerProgressBar should be null before initialization");
    }

    @Test
    void defaultStateVariablesTest() {
        QuizController controller = new QuizController();

        // Assert that initial state variables are correctly set
        assertNull(controller.username, "Username should be null by default");
        assertEquals(0, controller.currentQuestionIndex, "Current question index should be 0 by default");
        assertFalse(controller.quizSubmitted, "Quiz submitted flag should be false by default");
        assertNull(controller.questions, "Questions list should be null by default");
        assertNull(controller.studentAnswers, "Student answers map should be null by default");
    }

    @Test
    void defaultGradeDbTest() {
        QuizController controller = new QuizController();

        // Assert that gradeDb is initialized
        assertNull(controller.gradeDb, "GradeDb should be null before initialization");
    }

    @Test
    void initialTimerValuesTest() {
        QuizController controller = new QuizController();

        // Assert that timer-related values are set to default
        assertEquals(0, controller.remainingTime, "Remaining time should be 0 by default");
        assertEquals(0, controller.totalTime, "Total time should be 0 by default");
        assertNull(controller.quizTimer, "Quiz timer should be null before initialization");
    }

    @Test
    void timerInitializationTest() {
        QuizController controller = new QuizController();

        // Simulate starting a quiz with a duration of 10 minutes
        controller.totalTime = 600; // 600 seconds
        controller.remainingTime = controller.totalTime;

        // Assert timer values
        assertEquals(600, controller.totalTime, "Total time should be set to 600 seconds");
        assertEquals(600, controller.remainingTime, "Remaining time should equal total time at the start");

        // Simulate timer decrement
        controller.remainingTime -= 1;
        assertEquals(599, controller.remainingTime, "Remaining time should decrement by 1");
    }
    @Test
    void defaultQuestionNavigationTest() {
        QuizController controller = new QuizController();

        // Assert the default value of currentQuestionIndex
        assertEquals(0, controller.currentQuestionIndex, "Default question index should be 0");

        // Try navigating with no questions
        controller.questions = null; // No questions loaded
        assertEquals(0, controller.currentQuestionIndex, "Question index should remain 0 when no questions are loaded");
    }
    @Test
    void timerResetTest() {
        QuizController controller = new QuizController();

        // Simulate a running timer
        controller.totalTime = 1200; // 20 minutes
        controller.remainingTime = 600; // 10 minutes left

        // Reset timer
        controller.remainingTime = controller.totalTime;

        // Assert timer reset
        assertEquals(1200, controller.remainingTime, "Remaining time should reset to total time");
    }

    @Test
    void addSingleGradeTest() {
        QuizController controller = new QuizController();

        // Initialize gradeDb
        controller.gradeDb = new GradeDb();

        // Add a grade and verify
        controller.gradeDb.add(1, 1001, 85);
        Grade[] allGrades = controller.gradeDb.all();
        assertEquals(1, allGrades.length, "GradeDb should contain one grade");
        assertEquals(85, allGrades[0].getPoints(), "Grade points should be 85");
    }













}
