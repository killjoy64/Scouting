package org.petoskeypaladins.scouting.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

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
	
	@FXML private ComboBox<String> autoDriveZone;
	@FXML private ComboBox<String> autoToteStacks;
	@FXML private ComboBox<String> autoContainerSets;
	@FXML private ComboBox<String> autoToteSets;
	
	@FXML private ComboBox<String> teleLoading;
	@FXML private ComboBox<String> teleCanNoodle;
	@FXML private ComboBox<String> teleNoodleRate;
	@FXML private ComboBox<String> teleCoopStack;
	
	@FXML private ComboBox<String> teleToteManipulation;
	@FXML private ComboBox<String> teleContainerManipulation;
	
	@FXML private ComboBox<String> teleDriver;
	@FXML private ComboBox<String> teleAlliance;
	
	@FXML private TextField stackNumber;
	@FXML private TextField stackAmount;
	
	@FXML private TextField textIP;
	
	@FXML private RadioMenuItem r1Alliance;
	@FXML private RadioMenuItem r2Alliance;
	@FXML private RadioMenuItem r3Alliance;
	@FXML private RadioMenuItem b1Alliance;
	@FXML private RadioMenuItem b2Alliance;
	@FXML private RadioMenuItem b3Alliance;
	
	private ToggleGroup selectedAllianceGroup;
	
	private FileChooser fileChooser;
	
	private ObservableList<String> twoOptions;
	private ObservableList<String> rateOptions;
	private ObservableList<String> setOptions;
	private ObservableList<String> loadOptions;
	
	private HashMap<String, String> toteStacks;
	
	private HashMap<String, String> matchList;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		fileChooser = new FileChooser();
		selectedAllianceGroup = new ToggleGroup();
		toteStacks = new HashMap<String, String>();
		matchList = new HashMap<String, String>();
		
		assignGroups();
		
		twoOptions = FXCollections.observableArrayList();
		rateOptions = FXCollections.observableArrayList();
		setOptions = FXCollections.observableArrayList();
		loadOptions = FXCollections.observableArrayList();
		
		twoOptions.add("Yes");
		twoOptions.add("No");
		twoOptions.add("N/A");

		rateOptions.add("0");
		rateOptions.add("1");
		rateOptions.add("2");
		rateOptions.add("3");
		rateOptions.add("4");
		rateOptions.add("5");
		rateOptions.add("N/A");
		
		setOptions.add("0");
		setOptions.add("1");
		setOptions.add("2");
		setOptions.add("3");
		setOptions.add("N/A");
		
		loadOptions.add("Human Station");
		loadOptions.add("Landfill");
		loadOptions.add("N/A");
		
		numberTeam.setText(null);
		numberRound.setText(null);
		textCompetition.setText(null);
		textComments.setText("Insert additional comments here");
		console.setEditable(false);
		autoDriveZone.setItems(twoOptions);
		autoToteStacks.setItems(twoOptions);
		autoContainerSets.setItems(setOptions);
		autoToteSets.setItems(setOptions);		
		teleLoading.setItems(loadOptions);
		teleCanNoodle.setItems(twoOptions);
		teleNoodleRate.setItems(rateOptions);
		teleCoopStack.setItems(twoOptions);
		teleDriver.setItems(rateOptions);
		teleAlliance.setItems(rateOptions);
		teleToteManipulation.setItems(rateOptions);
		teleContainerManipulation.setItems(rateOptions);
		stackNumber.setText("1");
		stackNumber.setEditable(false);
		stackAmount.setText("0");
		
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
	public void addStack(ActionEvent e) {
		try {
				if(stackAmount.getText() != null && !stackAmount.getText().equalsIgnoreCase("0")) {
					int tN = Integer.parseInt(stackNumber.getText());
					tN++;
					toteStacks.put(stackNumber.getText(), stackAmount.getText());					
					stackNumber.setText(tN + "");
					stackAmount.setText("");
					
				}
		} catch (Exception ex){
		}
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
				reader.close();
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
		Stage stage = new Stage();
		stage.setTitle("About the 2015 Scouting Program");
		Pane pane = null;
		ScoutingClient.setStage(stage);
		try {
			pane = FXMLLoader.load(getClass().getResource("xml/About.fxml"));
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();			
		} catch (IOException e1) {
		}
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
		if(autoDriveZone.getSelectionModel().getSelectedItem() != null
				&& autoContainerSets.getSelectionModel().getSelectedItem() != null
					&& autoToteSets.getSelectionModel().getSelectedItem() != null
						&& autoToteStacks.getSelectionModel().getSelectedItem() != null
							&& teleLoading.getSelectionModel().getSelectedItem() != null
								&& teleCanNoodle.getSelectionModel().getSelectedItem() != null
									&& teleNoodleRate.getSelectionModel().getSelectedItem() != null
										&& teleCoopStack.getSelectionModel().getSelectedItem() != null
											&& teleToteManipulation.getSelectionModel().getSelectedItem() != null
												&& teleContainerManipulation.getSelectionModel().getSelectedItem() != null
													&& teleDriver.getSelectionModel().getSelectedItem() != null
														&& teleAlliance.getSelectionModel().getSelectedItem() != null
															&& numberTeam.getText() != null
																&& numberRound.getText() != null
																	&& textCompetition.getText() != null) {
			if(!matchList.isEmpty()) {
				// Send all data blah-blah blah
				
				// Start to count up here
				try {
					int round = Integer.parseInt(numberRound.getText());
					round++;
					if(matchList.containsKey(round + "")) {
						numberRound.setText(round + "");
						numberTeam.setText(matchList.get(round + ""));
					}
				} catch(Exception ex) {
					numberTeam.setText(null);
				}
			} else {
				try {
					int round = Integer.parseInt(numberRound.getText());
					round++;
					numberRound.setText(round + "");	
				} catch(Exception ex) {
					numberTeam.setText(null);
				}
			}
			//Send all the data
			//handle(e);
			
			JOptionPane.showConfirmDialog(null, "Successfully validated data and sent the form!", "Gratz m8 you did it", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
			
			// Reset all the fields
			autoDriveZone.getSelectionModel().clearSelection();
			autoContainerSets.getSelectionModel().clearSelection();
			autoToteStacks.getSelectionModel().clearSelection();
			autoToteSets.getSelectionModel().clearSelection();
			teleLoading.getSelectionModel().clearSelection();
			teleCanNoodle.getSelectionModel().clearSelection();
			teleCoopStack.getSelectionModel().clearSelection();
			teleNoodleRate.getSelectionModel().clearSelection();
			teleToteManipulation.getSelectionModel().clearSelection();
			teleContainerManipulation.getSelectionModel().clearSelection();
			teleDriver.getSelectionModel().clearSelection();
			teleAlliance.getSelectionModel().clearSelection();
			stackNumber.setText("1");
			stackAmount.setText(null);
			toteStacks.clear();
		} else {
			JOptionPane.showConfirmDialog(null, "Please fill out the entire form!", "Are you serious, bro?", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
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
	   	form.addObjectField("Auto Drive Zone", autoDriveZone.getSelectionModel().getSelectedItem());
	   	form.addObjectField("Auto Completed Tote Stack", autoToteStacks.getSelectionModel().getSelectedItem());
	   	form.addObjectField("Auto Containers", autoContainerSets.getSelectionModel().getSelectedItem());
	   	form.addObjectField("Auto Totes", autoToteSets.getSelectionModel().getSelectedItem());
	   	form.addObjectField("Loading Method", teleLoading.getSelectionModel().getSelectedItem());
	   	form.addObjectField("Noodle Functionality", teleCanNoodle.getSelectionModel().getSelectedItem());
	   	form.addObjectField("Noodle Effectiveness", teleNoodleRate.getSelectionModel().getSelectedItem());
	   	form.addObjectField("Coopertition Stack", teleCoopStack.getSelectionModel().getSelectedItem());
	   	form.addObjectField("Tote Manipulation", teleToteManipulation.getSelectionModel().getSelectedItem());
	   	form.addObjectField("Container Manipulation", teleContainerManipulation.getSelectionModel().getSelectedItem());
	   	form.addObjectField("Driver Quality", teleDriver.getSelectionModel().getSelectedItem());
	   	form.addObjectField("Alliance Cooperation", teleAlliance.getSelectionModel().getSelectedItem());
	   	
	   	if(!toteStacks.isEmpty()) {
	   		StringBuilder stacks = new StringBuilder();
	   		int size = 0;
	   		for(Entry<String, String> entry : toteStacks.entrySet()) {
	   			if(size > 0) {
	   				stacks.append("," + entry.getKey() + "-" + entry.getValue());
	   			} else {
	   				stacks.append(entry.getKey() + "-" + entry.getValue());	   				
	   			}
	   			size++;
	   		}
	   		form.addObjectField("Tote Stacks", stacks.toString());
	   	}
	   	
	}
	
}
