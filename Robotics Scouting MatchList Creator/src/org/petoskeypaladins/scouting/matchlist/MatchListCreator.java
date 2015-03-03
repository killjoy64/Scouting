package org.petoskeypaladins.scouting.matchlist;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MatchListCreator extends Application {

	private static Stage application;
	
	@Override
	public void start(Stage stage) throws Exception {
		try {
			MatchListCreator.application = stage;
			
			Parent root = FXMLLoader.load(getClass().getResource("xml/Application.fxml"));
			
			Scene scene = new Scene(root);
			
			application.setScene(scene);
			application.setTitle("FRC Matchlist Creator - v0.5");
			application.setResizable(false);
			application.show();
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Stage getStage() {
		return application;
	}
	
	public static void setCurentStage(Stage stage) {
		MatchListCreator.application = stage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
