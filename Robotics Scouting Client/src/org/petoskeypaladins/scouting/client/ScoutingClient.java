package org.petoskeypaladins.scouting.client;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScoutingClient extends Application {

	private static Stage appStage;
	
	public static String TITLE = "Scouting Program 2015 - Recycle Rush";
	public static String VERSION = "(v1.11 - BETA)";
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			ScoutingClient.appStage = primaryStage;
			
			Parent root = FXMLLoader.load(getClass().getResource("xml/Application.fxml"));
			
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle(TITLE + " " + VERSION);
			primaryStage.setResizable(false);
			primaryStage.show();
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void setStage(Stage stage) {
		ScoutingClient.appStage = stage;
	}
	
	public static Stage getStage() {
		return appStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
