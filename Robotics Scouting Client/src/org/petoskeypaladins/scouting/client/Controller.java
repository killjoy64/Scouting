package org.petoskeypaladins.scouting.client;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.petoskeypaladins.scouting.shared.ScoutingForm;

public class Controller implements Initializable {

	@FXML private MenuItem connect;
	@FXML private TextArea console;
	@FXML private TextField numberTeam;
	@FXML private TextField numberRound;
	@FXML private TextField textCompetition;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		numberTeam.setText("3618");
		numberRound.setText("2");
		textCompetition.setText("UNKNOWN");
		console.setEditable(false);
		sendMessage("Successfully initialized Client controller");		
	}
	
	public void sendMessage(String message) {
		console.appendText("[Client]: " + message + " \n");
	}
	
	public void receiveMessage(String message) {
		console.appendText("[Server]: " + message + " \n");
	}
	
	@FXML
	public void handle(ActionEvent e) {
       sendMessage("Attempting to connect to server...");
       
       try {
    	   	Socket client = new Socket("127.0.0.1", 3618);
    	   
    	   	sendMessage("Successfully connected to server");
   	   	
    	   	ScoutingForm form = new ScoutingForm();
    	   	
    	   	form.addObjectField("Competition", textCompetition.getText());
    	   	form.addObjectField("Team Number", numberTeam.getText());
    	   	form.addObjectField("Round Number", numberRound.getText());
    	   	    	   	
    	   	ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
    	   	
    	   	output.writeObject(form);
    	   	
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
	
}
