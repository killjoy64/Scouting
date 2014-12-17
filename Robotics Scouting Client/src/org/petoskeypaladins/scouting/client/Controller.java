package org.petoskeypaladins.scouting.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import org.petoskeypaladins.scouting.shared.ScoutingForm;

public class Controller implements Initializable {

	@FXML private MenuItem connect;
	@FXML private MenuItem fileOpen;
	@FXML private MenuItem fileSave;
	@FXML private MenuItem fileNew;
	@FXML private MenuItem helpAbout;
	@FXML private TextArea console;
	
	@FXML private TextField numberTeam;
	@FXML private TextField numberRound;
	@FXML private TextField textCompetition;
	@FXML private TextArea textComments;
	
	@FXML private TextField textIP;
	
	@FXML private RadioButton leftPos;
	@FXML private RadioButton rightPos;
	@FXML private RadioButton midPos;
	@FXML private RadioButton goalPos;
	
	@FXML private RadioButton yesHot;
	@FXML private RadioButton noHot;
	@FXML private RadioButton idkHot;
	
	@FXML private RadioButton highGoal;
	@FXML private RadioButton lowGoal;
	@FXML private RadioButton noGoal;
	
	private ToggleGroup positionGroup;
	private ToggleGroup hotgoalGroup;
	private ToggleGroup goalpointGroup;
	
	private FileChooser fileChooser;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		fileChooser = new FileChooser();
		
		positionGroup = new ToggleGroup();
		hotgoalGroup = new ToggleGroup();
		goalpointGroup = new ToggleGroup();
		
		assignGroups();
		
		numberTeam.setText("3618");
		numberRound.setText("2");
		textCompetition.setText("UNKNOWN");
		textComments.setText("Insert additional comments here");
		console.setEditable(false);
		sendMessage("Successfully initialized Client controller");		
	}
	
	public void sendMessage(String message) {
		console.appendText("[Client]: " + message + " \n");
	}
	
	public void receiveMessage(String message) {
		console.appendText("[Server]: " + message + " \n");
	}
	
	public void assignGroups() {
		leftPos.setToggleGroup(positionGroup);
		rightPos.setToggleGroup(positionGroup);
		midPos.setToggleGroup(positionGroup);
		goalPos.setToggleGroup(positionGroup);
		goalPos.setSelected(true);
		goalPos.requestFocus();
		
		yesHot.setToggleGroup(hotgoalGroup);
		noHot.setToggleGroup(hotgoalGroup);
		idkHot.setToggleGroup(hotgoalGroup);
		noHot.setSelected(true);
		noHot.requestFocus();
		
		highGoal.setToggleGroup(goalpointGroup);
		lowGoal.setToggleGroup(goalpointGroup);
		noGoal.setToggleGroup(goalpointGroup);
		noGoal.setSelected(true);
		noGoal.requestFocus();
	}
	
	@FXML 
	public void openFile(ActionEvent e) {
		fileChooser.setTitle("Open a Scouting Form");
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Scouting Forms", "*.form")
		);
		File data = fileChooser.showOpenDialog(ScoutingClient.getStage());
		
		if(data != null) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(data));
				String line = null;
				while((line = reader.readLine()) != null) {
					if(line.contains(":")) {
						// If it is NOT a multi-line comment
						// ..... TODO - Finish field lining for opening files, not needed now.
					}
				}
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	@FXML
	public void saveFile(ActionEvent e) {
		fileChooser.setTitle("Save a Scouting Form");
		
		File data = fileChooser.showSaveDialog(ScoutingClient.getStage());
		
		if(data != null) {
			// Eventually save all of the data fields
		}
		
	}
	
	@FXML
	public void resetFields(ActionEvent e) {
		numberTeam.setText("");
		numberRound.setText("");
		textCompetition.setText("");
		textComments.setText("");
		
		noGoal.setSelected(true);
		noGoal.requestFocus();
		noHot.setSelected(true);
		noHot.requestFocus();
		goalPos.setSelected(true);
		goalPos.requestFocus();
	}
	
	@FXML 
	public void showAbout(ActionEvent e) {
		// Ugh... TODO - Create new XML file for the about window
	}
	
	@FXML
	public void handle(ActionEvent e) {
       sendMessage("Attempting to connect to server...");
       
       try {
    	   	@SuppressWarnings("resource")
			Socket client = new Socket(textIP.getText(), 3618);
    	   
    	   	sendMessage("Successfully connected to server");
   	   	
    	   	ScoutingForm form = new ScoutingForm();
    	   	    	   	
    	   	collectFields(form);
    	   	
    	   	ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
    	   	
    	   	output.writeObject(form);
    	   	
    	   	sendMessage("Sent Data");
    	   	
    	   	InputStream inFromServer = client.getInputStream();
    	   	DataInputStream in = new DataInputStream(inFromServer);
     
    	   	//while(in.readUTF() != ".disconnect") {
    	   	receiveMessage(in.readUTF());
    	   	//}
           
    	   	//client.close();
           
       } catch (Exception e1) {
       		sendMessage("Falied to connect to Server");
       }
    }
	
	public void collectFields(ScoutingForm form) {
		form.addObjectField("Competition", textCompetition.getText());
	   	form.addObjectField("Team Number", numberTeam.getText());
	   	form.addObjectField("Round Number", numberRound.getText());
	   	form.addObjectField("Comments", textComments.getText());
	   	form.addObjectField("Starting Position", ((RadioButton)positionGroup.getSelectedToggle()).getText());
	   	form.addObjectField("Hot Goal Detection", ((RadioButton)hotgoalGroup.getSelectedToggle()).getText());
	   	form.addObjectField("Autonomous Goal", ((RadioButton)goalpointGroup.getSelectedToggle()).getText());
	}
	
}
