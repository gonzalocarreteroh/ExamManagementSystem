package comp3111.examsystem.controller.student;

import javafx.application.Platform;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
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

class GradeStatisticsControllerTest {

    @BeforeAll
    static void setupJavaFX() {
        // Initialize JavaFX runtime
        Platform.startup(() -> {});
    }

    /**
     * Helper method to invoke private methods using reflection.
     */
    private String invokeInsertLineBreaks(GradeStatisticsController controller, String input) throws Exception {
        Method method = GradeStatisticsController.class.getDeclaredMethod("insertLineBreaks", String.class);
        method.setAccessible(true);
        return (String) method.invoke(controller, input);
    }

    @Test
    void insertLineBreaksTest() throws Exception {
        GradeStatisticsController controller = new GradeStatisticsController();
        String input = "This is a very long label for testing purposes";
        String expected = "This is a\nvery long\nlabel for\ntesting\npurposes";
        String result = invokeInsertLineBreaks(controller, input);
        assertEquals(expected, result);
    }

    @Test
    void insertLineBreaksNoBreakTest() throws Exception {
        GradeStatisticsController controller = new GradeStatisticsController();
        String input = "Short";
        String expected = "Short";
        String result = invokeInsertLineBreaks(controller, input);
        assertEquals(expected, result);
    }

    @Test
    void insertLineBreaksExactFitTest() throws Exception {
        GradeStatisticsController controller = new GradeStatisticsController();
        String input = "12345 12345"; // Two words, each exactly 5 characters (total 10 with a space)
        String expected = "12345\n12345"; // Should break after the first word
        String result = invokeInsertLineBreaks(controller, input);
        assertEquals(expected, result);
    }

    @Test
    void insertLineBreaksEmptyStringTest() throws Exception {
        GradeStatisticsController controller = new GradeStatisticsController();
        String input = "";
        String expected = ""; // An empty string should remain empty
        String result = invokeInsertLineBreaks(controller, input);
        assertEquals(expected, result);
    }

    @Test
    void insertLineBreaksMultipleShortWordsTest() throws Exception {
        GradeStatisticsController controller = new GradeStatisticsController();
        String input = "One two three";
        String expected = "One two\nthree"; // Break occurs after "two"
        String result = invokeInsertLineBreaks(controller, input);
        assertEquals(expected, result);
    }

    @Test
    void insertLineBreaksFirstWordExactFitTest() throws Exception {
        GradeStatisticsController controller = new GradeStatisticsController();
        String input = "1234567890 abc"; // First word is exactly 10 characters
        String expected = "1234567890\nabc"; // Should break before "abc"
        String result = invokeInsertLineBreaks(controller, input);
        assertEquals(expected, result);
    }

    @Test
    void updateFeedbackTest() {
        // Initialize the controller
        GradeStatisticsController controller = new GradeStatisticsController();

        // Run JavaFX initialization on the JavaFX thread
        Platform.runLater(() -> {
            controller.feedbackLabel = new Label(); // Manually instantiate the Label

            // Test case: No completed exams
            controller.updateFeedback(0, 0, 0);
            assertEquals("No completed exams yet. Start now to see your progress!",
                    controller.feedbackLabel.getText(), "Feedback should indicate no exams.");

            // Test case: Valid scores and exams
            controller.updateFeedback(250, 90, 5);
            assertEquals("Completed Exams: 5 | Highest Score: 90\nKeep up the great work!",
                    controller.feedbackLabel.getText(), "Feedback should summarize results.");
        });

        // Wait for JavaFX thread to complete
        try {
            Thread.sleep(100); // Allow time for JavaFX thread
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    void updateChartTest() {
        // Initialize controller
        GradeStatisticsController controller = new GradeStatisticsController();

        // Initialize data
        List<GradeStatisticsController.GradeRow> data = new ArrayList<>();
        data.add(new GradeStatisticsController.GradeRow("Math", "Midterm", 85, 100, 60));
        data.add(new GradeStatisticsController.GradeRow("Science", "Final", 90, 100, 90));

        // Initialize BarChart
        controller.scoreChart = new BarChart<>(new CategoryAxis(), new NumberAxis());

        // Update chart
        controller.updateChart(data);

        // Verify chart data
        assertEquals(1, controller.scoreChart.getData().size(), "Chart should have one data series.");
        assertEquals(2, controller.scoreChart.getData().get(0).getData().size(),
                "Chart should have two data points.");
    }






}
