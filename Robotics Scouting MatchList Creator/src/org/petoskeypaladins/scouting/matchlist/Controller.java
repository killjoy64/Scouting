package org.petoskeypaladins.scouting.matchlist;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller implements Initializable {

	@FXML ComboBox<String> robotID;
	@FXML TextField teamID;
	@FXML TextField matchID;
	@FXML Button addMatch;
	@FXML Button submit;
	@FXML Button clearAll;
	
	private FileChooser fileChooser;
	
	private ObservableList<String> robotIDs;
	
	private HashMap<String, String> matchList;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		fileChooser = new FileChooser();
		matchList = new HashMap<String, String>();
		
		robotIDs = FXCollections.observableArrayList();
		
		robotIDs.add("Red 1");
		robotIDs.add("Red 2");
		robotIDs.add("Red 3");
		robotIDs.add("Blue 1");
		robotIDs.add("Blue 2");
		robotIDs.add("Blue 3");
		
		robotID.setItems(robotIDs);
		
		teamID.setText("xxyy");
		matchID.setText("1");
	}

	@FXML void addMatch(ActionEvent e) {
		if(robotID.getSelectionModel().getSelectedItem() != null) {
			try {
				if(matchID.getText().matches("\\d+") && teamID.getText().matches("\\d+")) {
					int match = Integer.parseInt(matchID.getText());
					
					int team = Integer.parseInt(teamID.getText());
					teamID.setText("xxyy");
					teamID.requestFocus();
					matchList.put(match + "", team + "");
					
					match++;
					matchID.setText(match + "");
				}
			} catch(NumberFormatException ex) {
				
			}
		} else {
		}
	}
	
	@FXML void createFile(ActionEvent e) {
		fileChooser.setTitle("Save a Scouting Form");
		
		File data = fileChooser.showSaveDialog(MatchListCreator.getStage());
		
		FileWriter writer;
		try {
			writer = new FileWriter(data);
			BufferedWriter br = new BufferedWriter(writer);
			br.append(robotID.getSelectionModel().getSelectedItem());
			br.newLine();
		
			for(Entry<String, String> set : matchList.entrySet()) {
				
				if(data != null) {
					// Eventually save all of the data fields
					br.append(set.getKey() + "," + set.getValue());
					br.newLine();
				}
			}
			br.close();
		} catch (Exception e1) {
		}
	}
	
	@FXML void clearData(ActionEvent e) {
		matchList.clear();
		matchID.setText("1");
		teamID.setText("xxyy");
	}
	
	@FXML void showMasterlistDialog(ActionEvent e) {
		Stage stage = new Stage();
		stage.setTitle("Create Master FRC Matchlist");
		Pane pane = null;
		MatchListCreator.setCurentStage(stage);
		try {
			pane = FXMLLoader.load(getClass().getResource("xml/CreateMasterlist.fxml"));
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.show();			
		} catch (IOException e1) {
		}
	}
	
}
