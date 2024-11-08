module comp3111.examsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens comp3111.examsystem to javafx.fxml;
    exports comp3111.examsystem;
    opens comp3111.examsystem.controller to javafx.fxml;
    exports comp3111.examsystem.controller;
    exports comp3111.examsystem.model;
    opens comp3111.examsystem.model to javafx.fxml;
    exports comp3111.examsystem.controller.manager;
    opens comp3111.examsystem.controller.manager to javafx.fxml;
    exports comp3111.examsystem.controller.student;
    opens comp3111.examsystem.controller.student to javafx.fxml;
    exports comp3111.examsystem.controller.teacher;
    opens comp3111.examsystem.controller.teacher to javafx.fxml;
}