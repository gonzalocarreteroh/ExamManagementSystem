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
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import javafx.application.Platform;

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
import comp3111.examsystem.model.Teacher;
import comp3111.examsystem.model.TeacherDb;


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
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
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




public class RegisterControllerTest {






    // Utility methods for setup and injection

    private DataCollection createMockData() {
        CourseDb mockCourseDb = new CourseDb(new Course[]{});
        ExamDb mockExamDb = new ExamDb(new Exam[]{});
        GradeDb mockGradeDb = new GradeDb(new Grade[]{});
        ManagerDb mockManagerDb = new ManagerDb(new Manager[]{});
        QuestionDb mockQuestionDb = new QuestionDb(new Question[]{});
        StudentDb mockStudentDb = new StudentDb();
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

    private Object getPrivateField(Object instance, String fieldName) {
        try {
            var field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(instance);
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



