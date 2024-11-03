package comp3111.examsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.HashMap;


public class Main extends Application {
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginUI.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 640, 480);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	// This function receives a hashmap of username and password, and a username and returns the password of the username
	public static String getPassword(HashMap<String, String> userPass, String username) {
		// Check if the username exists in the hashmap
		if (userPass.containsKey(username)) {
			return userPass.get(username);
		}
		return null;
	}

	// This function receives a hashmap of usernames and their grades as values, and a username and returns the average grade of the username
	public static double getAverageGrade(HashMap<String, double[]> userGrade, String username) {
		double[] grades = userGrade.get(username);
		double sum = Arrays.stream(grades).sum();
		if (grades.length == 0) {
			return 0;
		}
		return sum / grades.length;
	}
}
