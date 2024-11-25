package comp3111.examsystem.controller.teacher;

import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import comp3111.examsystem.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Arrays;
import javafx.scene.control.ChoiceBox;
import javafx.application.Platform;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class ExamManageControllerTest {
    private ExamManageController controller;

    @BeforeAll
    public static void setUpAll() {
        Platform.startup(() -> {});
    }
    @BeforeEach
    public void setUp() {

        controller = new ExamManageController();

        // Initialize UI components
        controller.filterCourseID = new ChoiceBox<>();
        controller.filterExamName = new TextField();
        controller.filterPublish = new ChoiceBox<>();
        controller.examTable = new TableView<>();
        controller.examList = FXCollections.observableArrayList();
        controller.newQuestionList = FXCollections.observableArrayList();

        // Add columns to examTable (mock structure)
        TableColumn<ExamManageController.Row, String> columnExamName = new TableColumn<>("Exam Name");
        TableColumn<ExamManageController.Row, String> columnCourseID = new TableColumn<>("Course ID");
        controller.examTable.getColumns().addAll(columnExamName, columnCourseID);

        // Mock data setup
        CourseDb courseDb = new CourseDb();
        ExamDb examDb = new ExamDb();
        DataCollection mockData = new DataCollection(courseDb, examDb, null, null, null, null, null);
    }

    @Test
    void testLoadExams() {
        Platform.runLater(() -> {
            controller.filterCourseID = new ChoiceBox<>();
            controller.filterExamName = new TextField();
            controller.filterPublish = new ChoiceBox<>();
            controller.loadExams();

            ObservableList<ExamManageController.Row> examList = controller.examList;
            assertEquals(2, examList.size());

            ExamManageController.Row firstExam = examList.get(0);
            assertEquals("Midterm", firstExam.getExamName());
            assertEquals("COMP1011", firstExam.getCourseID());
            assertEquals("60", firstExam.getExamTime());
            assertEquals("yes", firstExam.getPublish());
        });
    }

    @Test
    void testDeleteExam() {
        Platform.runLater(() -> {
            // Simulate selecting the first exam for deletion
            controller.examTable = new TableView<>();
            ObservableList<ExamManageController.Row> data = controller.examList;
            controller.examTable.setItems(data);
            controller.examTable.getSelectionModel().select(0);

            controller.delete();

            ObservableList<ExamManageController.Row> examList = controller.examList;
            assertEquals(1, examList.size()); // Assuming 2 exams existed initially

            ExamManageController.Row remainingExam = examList.get(0);
            assertNotEquals("Midterm", remainingExam.getExamName()); // "Midterm" should have been deleted
        });
    }

    @Test
    void testFilterExams() {
        Platform.runLater(() -> {
            controller.filterCourseID = new ChoiceBox<>();
            controller.filterCourseID.getItems().addAll("COMP1011", "COMP2021");
            controller.filterCourseID.setValue("COMP1011");

            controller.filterExamName = new TextField("Midterm");
            controller.filterPublish = new ChoiceBox<>();
            controller.filterPublish.getItems().addAll("yes", "no");
            controller.filterPublish.setValue("yes");

            controller.query_exams();

            ObservableList<ExamManageController.Row> filteredList = controller.examList;
            assertEquals(1, filteredList.size());

            ExamManageController.Row filteredExam = filteredList.get(0);
            assertEquals("Midterm", filteredExam.getExamName());
            assertEquals("COMP1011", filteredExam.getCourseID());
            assertEquals("60", filteredExam.getExamTime());
            assertEquals("yes", filteredExam.getPublish());
        });
    }

    @Test
    void testEditExam() {
        Platform.runLater(() -> {
            controller.examTable = new TableView<>();
            ObservableList<ExamManageController.Row> data = controller.examList;
            controller.examTable.setItems(data);

            // Simulate selecting the first exam
            controller.examTable.getSelectionModel().select(0);

            controller.parameterExamName = new TextField("Edited Midterm");
            controller.parameterCourseID = new ChoiceBox<>();
            controller.parameterExamTime = new TextField("75");
            controller.parameterPublish = new ChoiceBox<>();
            controller.parameterPublish.setValue("no");

            controller.update_exam();

            ObservableList<ExamManageController.Row> examList = controller.examList;
            ExamManageController.Row editedExam = examList.get(0);

            assertEquals("Edited Midterm", editedExam.getExamName());
            assertEquals("COMP1012", editedExam.getCourseID());
            assertEquals("75", editedExam.getExamTime());
            assertEquals("no", editedExam.getPublish());
        });
    }

}
