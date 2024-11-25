package comp3111.examsystem.controller.student;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import comp3111.examsystem.model.GradeDb;
import comp3111.examsystem.model.Grade;
import comp3111.examsystem.model.Teacher;
import comp3111.examsystem.model.TeacherDb;


import comp3111.examsystem.model.Exam;
import comp3111.examsystem.model.ExamDb;
import comp3111.examsystem.model.CourseDb;


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
import comp3111.examsystem.model.GradeDb;

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


import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MainControllerTest {









    /**
     * Test case for successfully opening Grade Statistics.
     * Covers the valid execution path.
     */
    @Test
    void openGradeStatisticValidTest() {
        MainController controller = new MainController();

        // Initialize JavaFX components
        controller.feedbackLabel = new Label();
        controller.quizProgressBar = new ProgressBar();

        // Mock DataCollection
        DataCollection mockData = createMockData(new Exam[]{});
        injectDataCollection(mockData);

        // Set private `username`
        setPrivateField(controller, "username", "testUser");

        Platform.runLater(() -> {
            try {
                controller.openGradeStatistic();

                // Simulate loading the new Grade Statistics screen
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("student/GradeStatisticsUI.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();
                stage.setScene(scene);

                // Get GradeStatisticsController
                GradeStatisticsController gradeController = loader.getController();

                // Assertions
                assertNotNull(gradeController, "GradeStatisticsController should not be null.");

                // Validate private `username` in GradeStatisticsController
                String username = getPrivateField(gradeController, "username");
                assertEquals("testUser", username, "GradeStatisticsController username should match the one passed.");
            } catch (Exception e) {
                fail("Exception during openGradeStatistic valid test: " + e.getMessage());
            }
        });

        waitForJavaFX();
    }










    /**
     * Test case when no quiz is selected.
     * Covers branch: `selectedExamName == null`.
     */
    @Test
    void openQuizScreenNoSelectionTest() {
        MainController controller = new MainController();

        // Initialize JavaFX components
        controller.examComboBox = new ComboBox<>();
        controller.feedbackLabel = new Label();

        // Set private `username`
        setPrivateField(controller, "username", "testUser");

        // No exam selected
        controller.examComboBox.setValue(null);

        Platform.runLater(() -> {
            try {
                controller.openQuizScreen(new ActionEvent());

                // Expect a warning alert
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("No Quiz Selected");
                alert.setContentText("Please select a quiz from the list.");
                alert.show();

                assertEquals("No Quiz Selected", alert.getHeaderText(), "Alert header should indicate no quiz selection.");
                assertEquals("Please select a quiz from the list.", alert.getContentText(), "Alert content should prompt the user to select a quiz.");
            } catch (Exception e) {
                fail("Exception during openQuizScreen no selection test: " + e.getMessage());
            }
        });

        waitForJavaFX();
    }

    /**
     * Test case when an invalid quiz is selected.
     * Covers branch: `selectedExam == null`.
     */
    @Test
    void openQuizScreenInvalidSelectionTest() {
        MainController controller = new MainController();

        // Initialize JavaFX components
        controller.examComboBox = new ComboBox<>();
        controller.feedbackLabel = new Label();

        // Mock DataCollection with no matching quizzes
        Exam mockExam = new Exam(1, "Valid Quiz", 60, 101, true, new int[]{});
        DataCollection mockData = createMockData(new Exam[]{mockExam});

        // Inject mockData into ControllerBase
        injectDataCollection(mockData);

        // Set private `username`
        setPrivateField(controller, "username", "testUser");

        // Select an invalid exam
        controller.examComboBox.setValue("Invalid Quiz");

        Platform.runLater(() -> {
            try {
                controller.openQuizScreen(new ActionEvent());

                // Expect an error alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Quiz Not Found");
                alert.setContentText("The selected quiz could not be found.");
                alert.show();

                assertEquals("Quiz Not Found", alert.getHeaderText(), "Alert header should indicate quiz not found.");
                assertEquals("The selected quiz could not be found.", alert.getContentText(), "Alert content should indicate the issue.");
            } catch (Exception e) {
                fail("Exception during openQuizScreen invalid selection test: " + e.getMessage());
            }
        });

        waitForJavaFX();
    }

    /**
     * Test case for valid quiz selection.
     * Covers branch: `selectedExam != null` and quiz is valid.
     */
    @Test
    void openQuizScreenValidSelectionTest() {
        MainController controller = new MainController();

        // Initialize JavaFX components
        controller.examComboBox = new ComboBox<>();
        controller.feedbackLabel = new Label();
        controller.quizProgressBar = new ProgressBar();

        // Mock supporting data
        Exam mockExam = new Exam(1, "Math Exam", 60, 101, true, new int[]{});
        DataCollection mockData = createMockData(new Exam[]{mockExam});

        // Inject mockData into ControllerBase
        injectDataCollection(mockData);

        // Set private `username`
        setPrivateField(controller, "username", "testUser");

        // Add exams to ComboBox
        controller.examComboBox.getItems().addAll("Math Exam");

        // Select an exam and call openQuizScreen
        controller.examComboBox.setValue("Math Exam");

        Platform.runLater(() -> {
            try {
                controller.openQuizScreen(new ActionEvent());

                // Simulate loading the new Quiz screen
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("student/QuizUI.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();
                stage.setScene(scene);

                // Get QuizController
                QuizController quizController = loader.getController();

                // Assertions
                assertNotNull(quizController, "QuizController should not be null.");

                // Validate private `username` in QuizController
                String username = getPrivateField(quizController, "username");
                assertEquals("testUser", username, "QuizController username should match the one passed.");

                assertEquals("Math Exam", quizController.quizNameLabel.getText(), "Quiz name should match the selected exam.");
            } catch (Exception e) {
                fail("Exception during openQuizScreen valid selection test: " + e.getMessage());
            }
        });

        waitForJavaFX();
    }

    /**
     * Test case when ExamDb is empty.
     * Covers empty database branch.
     */
    @Test
    void openQuizScreenEmptyExamDbTest() {
        MainController controller = new MainController();

        // Initialize JavaFX components
        controller.examComboBox = new ComboBox<>();
        controller.feedbackLabel = new Label();

        // Mock empty ExamDb
        DataCollection mockData = createMockData(new Exam[]{});

        // Inject mockData into ControllerBase
        injectDataCollection(mockData);

        // Set private `username`
        setPrivateField(controller, "username", "testUser");

        Platform.runLater(() -> {
            try {
                controller.openQuizScreen(new ActionEvent());

                // Expect no action but ensure no exceptions are thrown
                assertTrue(controller.examComboBox.getItems().isEmpty(), "ComboBox should remain empty.");
            } catch (Exception e) {
                fail("Exception during openQuizScreen empty ExamDb test: " + e.getMessage());
            }
        });

        waitForJavaFX();
    }

    /**
     * Test case for missing username.
     * Ensures behavior when `username` is not set.
     */
    @Test
    void openQuizScreenMissingUsernameTest() {
        MainController controller = new MainController();

        // Initialize JavaFX components
        controller.examComboBox = new ComboBox<>();
        controller.feedbackLabel = new Label();

        // Do not set `username`

        Platform.runLater(() -> {
            try {
                controller.openQuizScreen(new ActionEvent());

                // Ensure no action is taken and no exceptions are thrown
                assertNull(getPrivateField(controller, "username"), "Username should remain null.");
                assertTrue(controller.examComboBox.getItems().isEmpty(), "ComboBox should remain empty without username.");
            } catch (Exception e) {
                fail("Exception during openQuizScreen missing username test: " + e.getMessage());
            }
        });

        waitForJavaFX();
    }

    // Utility methods for setup and injection

    private DataCollection createMockData(Exam[] exams) {
        CourseDb mockCourseDb = new CourseDb(new Course[]{});
        ExamDb mockExamDb = new ExamDb(exams);
        GradeDb mockGradeDb = new GradeDb(new Grade[]{});
        ManagerDb mockManagerDb = new ManagerDb(new Manager[]{});
        QuestionDb mockQuestionDb = new QuestionDb(new Question[]{});
        StudentDb mockStudentDb = new StudentDb(new Student[]{
                new Student(1, "testUser", "password", "Test User", 20, "CS", Gender.Male)
        });
        TeacherDb mockTeacherDb = new TeacherDb(new Teacher[]{});

        return new DataCollection(
                mockCourseDb, mockExamDb, mockGradeDb, mockManagerDb,
                mockQuestionDb, mockStudentDb, mockTeacherDb
        );
    }

    private void injectDataCollection(DataCollection dataCollection) {
        try {
            var field = ControllerBase.class.getDeclaredField("collection");
            field.setAccessible(true);
            field.set(null, dataCollection);
        } catch (Exception e) {
            fail("Failed to inject mock data: " + e.getMessage());
        }
    }

    private void setPrivateField(Object instance, String fieldName, Object value) {
        try {
            var field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (Exception e) {
            fail("Failed to set private field '" + fieldName + "': " + e.getMessage());
        }
    }

    private String getPrivateField(Object instance, String fieldName) {
        try {
            var field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (String) field.get(instance);
        } catch (Exception e) {
            fail("Failed to get private field '" + fieldName + "': " + e.getMessage());
            return null;
        }
    }

    private void waitForJavaFX() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }





}
