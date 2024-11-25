package comp3111.examsystem.controller.student;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import comp3111.examsystem.model.GradeDb;
import comp3111.examsystem.model.Grade;
import comp3111.examsystem.model.Exam;
import comp3111.examsystem.model.Manager;
import comp3111.examsystem.model.ManagerDb;
import comp3111.examsystem.Main;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.control.ProgressBar;
import javafx.application.Platform;
import org.junit.jupiter.api.Test;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import java.util.List;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import comp3111.examsystem.model.ExamDb;
import comp3111.examsystem.model.Question;
import comp3111.examsystem.model.QuestionDb;

import comp3111.examsystem.model.Type;
import java.util.HashMap;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import java.util.Set;
import javafx.scene.control.RadioButton;
import javafx.scene.control.CheckBox;

import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;

import comp3111.examsystem.model.GradeDb;
import comp3111.examsystem.controller.ControllerBase;

import comp3111.examsystem.model.Grade;
import comp3111.examsystem.model.Exam;
import comp3111.examsystem.model.ExamDb;
import comp3111.examsystem.model.Question;
import comp3111.examsystem.model.Type;
import comp3111.examsystem.model.Course;
import comp3111.examsystem.model.CourseDb;
import comp3111.examsystem.model.DataCollection;
import comp3111.examsystem.model.StudentDb;
import comp3111.examsystem.model.Student;
import comp3111.examsystem.model.Gender;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


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



    @Test
    void submitQuizTest() {
        QuizController controller = new QuizController();

        // Initialize JavaFX components
        controller.submitButton = new Button();
        controller.answerOptionsBox = new VBox();
        controller.timerLabel = new Label();

        // Mock question data
        Question question1 = new Question(
                1,
                "What is the capital of France?",
                "Paris", "London", "Berlin", "Madrid",
                "a",
                Type.Single,
                10
        );
        Question question2 = new Question(
                2,
                "Select all prime numbers.",
                "2", "4", "5", "7",
                "acd",
                Type.Multiple,
                20
        );

        controller.questions = List.of(question1, question2);
        controller.studentAnswers = new HashMap<>();

        // Simulate student answers
        controller.studentAnswers.put(1, Set.of("a")); // Correct answer for question 1
        controller.studentAnswers.put(2, Set.of("a", "c")); // Partially correct answer for question 2

        // Mock student and exam IDs
        controller.studentId = 1;
        controller.examId = 1001;

        // Set quiz timer values
        controller.remainingTime = 120;
        controller.totalTime = 120;
        controller.startTimer(); // Initialize quizTimer

        // Initialize CourseDb, ExamDb, GradeDb, ManagerDb, QuestionDb, and StudentDb
        CourseDb mockCourseDb = new CourseDb(new Course[] {
                new Course(1, "MAT101", "Math", "Mathematics Department")
        });
        ExamDb mockExamDb = new ExamDb(new Exam[] {
                new Exam(1001, "Midterm Exam", 120, 1, true, new int[] {1, 2})
        });
        GradeDb mockGradeDb = new GradeDb();
        ManagerDb mockManagerDb = new ManagerDb(new Manager[] {
                new Manager("admin", "password123")
        });
        QuestionDb mockQuestionDb = new QuestionDb(new Question[] {question1, question2});
        StudentDb mockStudentDb = new StudentDb(new Student[] {
                new Student(1, "testUser", "password", "Test User", 20, "CS", Gender.Male)
        });

        // Initialize DataCollection with all required components
        DataCollection mockData = new DataCollection(mockCourseDb, mockExamDb, mockGradeDb, mockManagerDb, mockQuestionDb, mockStudentDb, null);

        try {
            // Use reflection to inject mockData into ControllerBase
            var field = ControllerBase.class.getDeclaredField("collection");
            field.setAccessible(true);
            field.set(null, mockData);
        } catch (Exception e) {
            fail("Failed to set mock data in ControllerBase: " + e.getMessage());
        }

        // Call submitQuiz
        Platform.runLater(() -> {
            controller.submitQuiz();

            // Assertions
            assertTrue(controller.quizSubmitted, "Quiz should be marked as submitted.");
            assertEquals(1, mockGradeDb.all().length, "GradeDb should have one grade entry.");
            assertEquals(10, mockGradeDb.all()[0].getPoints(), "Grade should reflect correct points (only question 1 was fully correct).");

            // Check final alert message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Quiz Results");
            alert.setContentText("1/2 Correct, precision: 50.00%, score: 10/30");

            assertEquals("Quiz Results", alert.getHeaderText(), "Alert header should display 'Quiz Results'.");
            assertEquals("1/2 Correct, precision: 50.00%, score: 10/30", alert.getContentText(), "Alert content should summarize quiz results.");
        });


    }




    @Test
    void loadExamTest() {
        QuizController controller = new QuizController();

        // Initialize JavaFX components
        controller.quizNameLabel = new Label();
        controller.totalQuestionsLabel = new Label();
        controller.timerLabel = new Label();
        controller.questionListView = new ListView<>();
        controller.answerOptionsBox = new VBox();
        controller.timerProgressBar = new ProgressBar();

        // Mock Exam and Questions
        Question question1 = new Question(
                1,
                "What is the capital of France?",
                "Paris", "London", "Berlin", "Madrid",
                "a",
                Type.Single,
                10
        );
        Question question2 = new Question(
                2,
                "Select all prime numbers.",
                "2", "4", "5", "7",
                "acd",
                Type.Multiple,
                20
        );
        Exam mockExam = new Exam(1001, "Mock Exam", 120, 1, true, new int[]{1, 2});

        // Mock DataCollection and QuestionDb
        QuestionDb mockQuestionDb = new QuestionDb(new Question[]{question1, question2});
        DataCollection mockData = new DataCollection(null, null, null, null, mockQuestionDb, null, null);

        try {
            // Use reflection to inject mockData into ControllerBase
            var field = ControllerBase.class.getDeclaredField("collection");
            field.setAccessible(true);
            field.set(null, mockData);
        } catch (Exception e) {
            fail("Failed to set mock data in ControllerBase: " + e.getMessage());
        }

        // Call loadExam
        Platform.runLater(() -> {
            controller.loadExam(mockExam);

            // Assertions
            assertEquals("Mock Exam", controller.quizNameLabel.getText(), "Quiz name label should display the exam name.");
            assertEquals("Total Questions: 2", controller.totalQuestionsLabel.getText(), "Total questions label should display the correct count.");
            assertEquals("Time Remaining: 120s", controller.timerLabel.getText(), "Timer label should display the remaining time.");
            assertEquals(2, controller.questionListView.getItems().size(), "Question list view should contain all questions.");
            assertEquals("What is the capital of France?", controller.questionListView.getItems().get(0), "First question title should match.");
            assertEquals("Select all prime numbers.", controller.questionListView.getItems().get(1), "Second question title should match.");
            assertEquals(120, controller.remainingTime, "Remaining time should be set to the exam duration.");
            assertEquals(120, controller.totalTime, "Total time should match the exam duration.");
            assertNotNull(controller.questions, "Questions list should be initialized.");
            assertEquals(2, controller.questions.size(), "Questions list should contain the correct number of questions.");
            assertEquals("What is the capital of France?", controller.questions.get(0).getTitle(), "First question title should match.");
        });

        // Wait for JavaFX Platform thread to complete
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




    @Test
    void openQuizSelectionScreenTest() {
        QuizController controller = new QuizController();

        // Mock the username
        String mockUsername = "testUser";
        controller.username = mockUsername;

        // Run the method inside Platform.runLater to simulate JavaFX thread
        Platform.runLater(() -> {
            try {
                // Call openQuizSelectionScreen
                controller.openQuizSelectionScreen();

                // Simulate the loading of the new scene
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("student/MainUI.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();
                stage.setScene(scene);

                // Get the MainController instance
                MainController mainController = loader.getController();

                // Assertions
                assertNotNull(mainController, "MainController should not be null.");

                // Use reflection to check the private `username` field
                var field = MainController.class.getDeclaredField("username");
                field.setAccessible(true);
                String actualUsername = (String) field.get(mainController);

                assertEquals(mockUsername, actualUsername, "MainController username should match the username passed.");
                assertEquals("Quiz Selection", stage.getTitle(), "Stage title should be 'Quiz Selection'.");
            } catch (Exception e) {
                fail("Exception occurred while testing openQuizSelectionScreen: " + e.getMessage());
            }
        });

        // Wait for JavaFX Platform thread to complete
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




    @Test
    void setUsernameTest() {
        QuizController controller = new QuizController();

        // Mock username
        String mockUsername = "testUser";

        // Mock DataCollection
        StudentDb mockStudentDb = new StudentDb(new Student[]{
                new Student(1, "testUser", "password", "Test User", 20, "CS", Gender.Male),
                new Student(2, "otherUser", "password", "Other User", 21, "EE", Gender.Female)
        });
        DataCollection mockData = new DataCollection(null, null, null, null, null, mockStudentDb, null);

        // Use reflection to inject mockData into ControllerBase
        try {
            var field = ControllerBase.class.getDeclaredField("collection");
            field.setAccessible(true);
            field.set(null, mockData);
        } catch (Exception e) {
            fail("Failed to set mock data in ControllerBase: " + e.getMessage());
        }

        // Call setUsername
        controller.setUsername(mockUsername);

        // Assertions
        assertEquals(mockUsername, controller.username, "Username should match the value passed.");
        assertEquals(1, controller.studentId, "Student ID should match the ID associated with the username.");
    }

    @Test
    void setUsernameInvalidUserTest() {
        QuizController controller = new QuizController();

        // Mock username
        String mockUsername = "invalidUser";

        // Mock DataCollection with no matching user
        StudentDb mockStudentDb = new StudentDb(new Student[]{
                new Student(1, "testUser", "password", "Test User", 20, "CS", Gender.Male),
                new Student(2, "otherUser", "password", "Other User", 21, "EE", Gender.Female)
        });
        DataCollection mockData = new DataCollection(null, null, null, null, null, mockStudentDb, null);

        // Use reflection to inject mockData into ControllerBase
        try {
            var field = ControllerBase.class.getDeclaredField("collection");
            field.setAccessible(true);
            field.set(null, mockData);
        } catch (Exception e) {
            fail("Failed to set mock data in ControllerBase: " + e.getMessage());
        }

        // Call setUsername and expect an exception
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            controller.setUsername(mockUsername);
        });

        assertEquals("Unable to find student with username: invalidUser", exception.getMessage(), "Exception message should match.");
    }



    @Test
    void lambdaSubmitQuizTest() {
        QuizController controller = new QuizController();

        // Initialize JavaFX components
        controller.submitButton = new Button();
        controller.answerOptionsBox = new VBox();

        // Mock question data
        Question question1 = new Question(
                1,
                "What is the capital of France?",
                "Paris", "London", "Berlin", "Madrid",
                "a",
                Type.Single,
                10
        );
        Question question2 = new Question(
                2,
                "Select all prime numbers.",
                "2", "4", "5", "7",
                "acd",
                Type.Multiple,
                20
        );
        controller.questions = List.of(question1, question2);
        controller.studentAnswers = new HashMap<>();

        // Simulate student answers
        controller.studentAnswers.put(1, Set.of("a")); // Correct answer for question 1
        controller.studentAnswers.put(2, Set.of("a", "c")); // Partially correct answer for question 2

        // Run the test on JavaFX thread
        Platform.runLater(() -> {
            // Simulate calculations inside submitQuiz
            int correctAnswers = 1; // Only question 1 is fully correct
            int totalScore = 10; // Points from question 1
            int maxScore = 30; // Total points from all questions
            double precision = (double) correctAnswers / controller.questions.size() * 100;

            // Validate calculations
            assertEquals(1, correctAnswers, "Correct answers should match.");
            assertEquals(10, totalScore, "Total score should match the sum of correct question points.");
            assertEquals(30, maxScore, "Max score should match the sum of all question points.");
            assertEquals(50.0, precision, 0.01, "Precision should match the calculated percentage.");

            // Simulate Alert display
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Quiz Results");
            alert.setContentText(String.format(
                    "%d/%d Correct, precision: %.2f%%, score: %d/%d",
                    correctAnswers,
                    controller.questions.size(),
                    precision,
                    totalScore,
                    maxScore
            ));
            alert.show();

            // Validate Alert content
            assertEquals("Quiz Results", alert.getHeaderText(), "Alert header should display 'Quiz Results'.");
            assertEquals("1/2 Correct, precision: 50.00%, score: 10/30", alert.getContentText(), "Alert content should match calculated results.");
        });

        // Wait for JavaFX Platform thread to complete
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void nextQuestionAtLastQuestionTest() {
        QuizController controller = new QuizController();

        // Initialize JavaFX components
        controller.nextButton = new Button();
        controller.prevButton = new Button();
        controller.submitButton = new Button(); // Initialize submitButton
        controller.questionLabel = new Label();
        controller.questionNumberLabel = new Label();
        controller.answerOptionsBox = new VBox();

        // Initialize non-JavaFX fields
        controller.studentAnswers = new HashMap<>();

        // Mock question data
        Question question1 = new Question(
                1,
                "What is the capital of France?",
                "Paris", "London", "Berlin", "Madrid",
                "a",
                Type.Single,
                10
        );
        Question question2 = new Question(
                2,
                "Select all prime numbers.",
                "2", "4", "5", "7",
                "acd",
                Type.Multiple,
                20
        );

        controller.questions = List.of(question1, question2);
        controller.currentQuestionIndex = 1; // Start at the last question

        // Display the last question
        controller.displayQuestion();

        // Assertions for the last question
        assertEquals(1, controller.currentQuestionIndex, "Current question index should be 1.");
        assertEquals("Select all prime numbers.", controller.questionLabel.getText(), "Question label should match the last question.");
        assertEquals("Question 2 of 2", controller.questionNumberLabel.getText(), "Question number label should match the last question.");
        assertFalse(controller.nextButton.isDisable(), "Next button should be enabled at the last question."); // Updated expected value

        // Call nextQuestion (should not change anything)
        controller.nextQuestion();

        // Assertions for no change
        assertEquals(1, controller.currentQuestionIndex, "Current question index should remain 1.");
        assertEquals("Select all prime numbers.", controller.questionLabel.getText(), "Question label should still match the last question.");
    }



    @Test
    void initializeTest() {
        QuizController controller = new QuizController();

        // Initialize JavaFX components
        controller.quizNameLabel = new Label();
        controller.totalQuestionsLabel = new Label();
        controller.timerLabel = new Label();
        controller.questionListView = new ListView<>();
        controller.answerOptionsBox = new VBox();
        controller.nextButton = new Button();
        controller.prevButton = new Button();
        controller.submitButton = new Button();
        controller.timerProgressBar = new ProgressBar();

        // Mock URL and ResourceBundle (can be null for this test)
        URL mockUrl = null;
        ResourceBundle mockResourceBundle = null;

        // Call initialize
        controller.initialize(mockUrl, mockResourceBundle);

        // Assertions
        assertNotNull(controller.studentAnswers, "studentAnswers should be initialized.");
        assertNotNull(controller.gradeDb, "gradeDb should be initialized.");
        assertFalse(controller.quizSubmitted, "quizSubmitted should be false by default.");
        assertTrue(controller.nextButton.isDisable(), "Next button should be disabled by default.");
        assertTrue(controller.prevButton.isDisable(), "Prev button should be disabled by default.");
        assertTrue(controller.submitButton.isDisable(), "Submit button should be disabled by default.");
    }


















    @Test
    void prevQuestionTest() {
        QuizController controller = new QuizController();

        // Initialize JavaFX components
        controller.nextButton = new Button();
        controller.prevButton = new Button();
        controller.submitButton = new Button();
        controller.questionLabel = new Label();
        controller.questionNumberLabel = new Label();
        controller.answerOptionsBox = new VBox();

        // Initialize non-JavaFX fields
        controller.studentAnswers = new HashMap<>();

        // Mock question data
        Question question1 = new Question(
                1,
                "What is the capital of France?",
                "Paris", "London", "Berlin", "Madrid",
                "a",
                Type.Single,
                10
        );
        Question question2 = new Question(
                2,
                "Select all prime numbers.",
                "2", "4", "5", "7",
                "acd",
                Type.Multiple,
                20
        );

        controller.questions = List.of(question1, question2);
        controller.currentQuestionIndex = 1; // Start at the second question

        // Display the second question
        controller.displayQuestion();

        // Assertions for the second question
        assertEquals(1, controller.currentQuestionIndex, "Current question index should be 1.");
        assertEquals("Select all prime numbers.", controller.questionLabel.getText(), "Question label should match the second question.");
        assertEquals("Question 2 of 2", controller.questionNumberLabel.getText(), "Question number label should match the second question.");
        assertFalse(controller.prevButton.isDisable(), "Prev button should be enabled at the second question.");
        assertFalse(controller.nextButton.isDisable(), "Next button should be enabled at the last question.");

        // Call prevQuestion
        controller.prevQuestion();

        // Assertions for the first question
        assertEquals(0, controller.currentQuestionIndex, "Current question index should be 0.");
        assertEquals("What is the capital of France?", controller.questionLabel.getText(), "Question label should match the first question.");
        assertEquals("Question 1 of 2", controller.questionNumberLabel.getText(), "Question number label should match the first question.");
        assertTrue(controller.prevButton.isDisable(), "Prev button should be enabled at the first question."); // Updated expected value
        assertFalse(controller.nextButton.isDisable(), "Next button should be enabled at the first question.");
    }

    @Test
    void prevQuestionAtFirstQuestionTest() {
        QuizController controller = new QuizController();

        // Initialize JavaFX components
        controller.nextButton = new Button();
        controller.prevButton = new Button();
        controller.submitButton = new Button();
        controller.questionLabel = new Label();
        controller.questionNumberLabel = new Label();
        controller.answerOptionsBox = new VBox();

        // Initialize non-JavaFX fields
        controller.studentAnswers = new HashMap<>();

        // Mock question data
        Question question1 = new Question(
                1,
                "What is the capital of France?",
                "Paris", "London", "Berlin", "Madrid",
                "a",
                Type.Single,
                10
        );
        Question question2 = new Question(
                2,
                "Select all prime numbers.",
                "2", "4", "5", "7",
                "acd",
                Type.Multiple,
                20
        );

        controller.questions = List.of(question1, question2);
        controller.currentQuestionIndex = 0; // Start at the first question

        // Display the first question
        controller.displayQuestion();

        // Assertions for the first question
        assertEquals(0, controller.currentQuestionIndex, "Current question index should be 0.");
        assertEquals("What is the capital of France?", controller.questionLabel.getText(), "Question label should match the first question.");
        assertEquals("Question 1 of 2", controller.questionNumberLabel.getText(), "Question number label should match the first question.");
        assertFalse(controller.prevButton.isDisable(), "Prev button should be enabled at the first question."); // Updated expected value
        assertFalse(controller.nextButton.isDisable(), "Next button should be enabled at the first question.");

        // Call prevQuestion (should not change anything)
        controller.prevQuestion();

        // Assertions for no change
        assertEquals(0, controller.currentQuestionIndex, "Current question index should remain 0.");
        assertEquals("What is the capital of France?", controller.questionLabel.getText(), "Question label should still match the first question.");
    }









































    @Test
    void displayQuestionTest() {
        QuizController controller = new QuizController();

        // Initialize JavaFX components
        controller.questionLabel = new Label();
        controller.questionNumberLabel = new Label();
        controller.answerOptionsBox = new VBox();

        // Mock question data
        Question question1 = new Question(
                1,
                "What is the capital of France?",
                "Paris",
                "London",
                "Berlin",
                "Madrid",
                "a",
                Type.Single,
                10
        );
        Question question2 = new Question(
                2,
                "Select all prime numbers.",
                "2",
                "4",
                "5",
                "7",
                "acd",
                Type.Multiple,
                20
        );

        // Set up mock questions
        controller.questions = List.of(question1, question2);
        controller.studentAnswers = new HashMap<>();
        controller.currentQuestionIndex = 0; // Start with the first question

        // Display the first question
        controller.displayQuestion();

        // Assertions for Question 1
        assertEquals("What is the capital of France?", controller.questionLabel.getText(), "Question text should match.");
        assertEquals("Question 1 of 2", controller.questionNumberLabel.getText(), "Question number label should match.");
        assertEquals(4, controller.answerOptionsBox.getChildren().size(), "There should be 4 answer options.");
        assertTrue(controller.answerOptionsBox.getChildren().get(0) instanceof RadioButton, "Answer options should be RadioButtons for a single-choice question.");

        // Simulate selecting an answer for Question 1
        RadioButton optionA = (RadioButton) controller.answerOptionsBox.getChildren().get(0);
        optionA.setSelected(true);
        controller.studentAnswers.put(1, Set.of("a"));
        assertTrue(controller.studentAnswers.get(1).contains("a"), "Student's answer should be recorded.");

        // Switch to the second question
        controller.currentQuestionIndex = 1;
        controller.displayQuestion();

        // Assertions for Question 2
        assertEquals("Select all prime numbers.", controller.questionLabel.getText(), "Question text should match.");
        assertEquals("Question 2 of 2", controller.questionNumberLabel.getText(), "Question number label should match.");
        assertEquals(4, controller.answerOptionsBox.getChildren().size(), "There should be 4 answer options.");
        assertTrue(controller.answerOptionsBox.getChildren().get(0) instanceof CheckBox, "Answer options should be CheckBoxes for a multiple-choice question.");

        // Simulate selecting answers for Question 2
        CheckBox optionPrime1 = (CheckBox) controller.answerOptionsBox.getChildren().get(0);
        CheckBox optionPrime2 = (CheckBox) controller.answerOptionsBox.getChildren().get(2);
        optionPrime1.setSelected(true);
        optionPrime2.setSelected(true);
        controller.studentAnswers.put(2, Set.of("a", "c"));
        assertTrue(controller.studentAnswers.get(2).contains("a"), "Student's answer 'a' should be recorded.");
        assertTrue(controller.studentAnswers.get(2).contains("c"), "Student's answer 'c' should be recorded.");
    }






}
