package org.petoskeypaladins.scouting.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import javax.swing.JOptionPane;

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
	
	@FXML private RadioMenuItem r1Alliance;
	@FXML private RadioMenuItem r2Alliance;
	@FXML private RadioMenuItem r3Alliance;
	@FXML private RadioMenuItem b1Alliance;
	@FXML private RadioMenuItem b2Alliance;
	@FXML private RadioMenuItem b3Alliance;
	
	private ToggleGroup selectedAllianceGroup;
	
	private FileChooser fileChooser;
	
	private ObservableList<String> autoCycle;
	
	private HashMap<String, String> matchList;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		fileChooser = new FileChooser();
		selectedAllianceGroup = new ToggleGroup();
		matchList = new HashMap<String, String>();
		
		assignGroups();
		
		autoCycle = FXCollections.observableArrayList();
		
		autoCycle.add("Missed");
		autoCycle.add("Scored");
		autoCycle.add("Nope");
		
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
		r1Alliance.setToggleGroup(selectedAllianceGroup);
		r2Alliance.setToggleGroup(selectedAllianceGroup);
		r3Alliance.setToggleGroup(selectedAllianceGroup);
		b1Alliance.setToggleGroup(selectedAllianceGroup);
		b2Alliance.setToggleGroup(selectedAllianceGroup);
		b3Alliance.setToggleGroup(selectedAllianceGroup);
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
	}
	
	@FXML 
	public void showAbout(ActionEvent e) {
		// Ugh... TODO - Create new XML file for the about window
	}
	
	@FXML
	public void importList(ActionEvent e) {
		if(selectedAllianceGroup.getSelectedToggle() != null) {
			File list = fileChooser.showOpenDialog(ScoutingClient.getStage());
			
			if(list != null) {
				matchList.clear();
				try {
					BufferedReader br = new BufferedReader(new FileReader(list));
					String line = "";
					
					String alliance = ((RadioMenuItem)selectedAllianceGroup.getSelectedToggle()).getText();
					
					boolean canContinue = false;
					while((line = br.readLine()) != null) {
						if(!canContinue) {
							if(line.contains(alliance)) {
								canContinue = true;
							}
						} else {
							try {
								String[] matches = line.split(":")[1].split(",");
								matchList.put(matches[0], matches[1]);
							} catch(IndexOutOfBoundsException a) {
								break;
							}
						}
					}
					br.close();
					
					if(matchList.containsKey("1")) {
						numberRound.setText("1");
						numberTeam.setText(matchList.get("1"));
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		} else {
			JOptionPane.showConfirmDialog(null, "Please select an alliance under Teams!", "Are you serious, bro?", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	@FXML
	public void submitData(ActionEvent e) {
		if(!matchList.isEmpty()) {
			// Send all data blah-blah blah
			
			// Start to count up here
			try {
				int round = Integer.parseInt(numberRound.getText());
				round++;
				System.out.println(round);
				if(matchList.containsKey(round + "")) {
					numberRound.setText(round + "");
					numberTeam.setText(matchList.get(round + ""));
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
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
     
    	   	receiveMessage(in.readUTF());           
           
       } catch (Exception e1) {
       		sendMessage("Falied to connect to Server");
       }
    }
	
	public void collectFields(ScoutingForm form) {
		form.addObjectField("Competition", textCompetition.getText());
	   	form.addObjectField("Team Number", numberTeam.getText());
	   	form.addObjectField("Round Number", numberRound.getText());
	}
	
}
