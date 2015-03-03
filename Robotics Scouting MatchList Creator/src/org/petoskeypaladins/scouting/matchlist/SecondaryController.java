package org.petoskeypaladins.scouting.matchlist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class SecondaryController implements Initializable {

	@FXML TextField red_1;
	@FXML TextField red_2;
	@FXML TextField red_3;
	@FXML TextField blue_1;
	@FXML TextField blue_2;
	@FXML TextField blue_3;
	
	private File[] matchFiles;
	private FileChooser fileChooser;
	
	String defaultText;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		matchFiles = new File[6];
		fileChooser = new FileChooser();
		
		defaultText = "No File Selected";
		
		red_1.setEditable(false);
		red_2.setEditable(false);
		red_3.setEditable(false);
		blue_1.setEditable(false);
		blue_2.setEditable(false);
		blue_3.setEditable(false);
		
		red_1.setText(defaultText);
		red_2.setText(defaultText);
		red_3.setText(defaultText);
		blue_1.setText(defaultText);
		blue_2.setText(defaultText);
		blue_3.setText(defaultText);
	}

	@FXML void openFileChooser(ActionEvent e) {
		String node = ((Control)e.getSource()).getId();
		if(node != null) {
			if(node.equalsIgnoreCase("br1")) {
				File f = fileChooser.showOpenDialog(MatchListCreator.getStage());
				matchFiles[0] = f;
				red_1.setText(f.getPath());
			} else if(node.equalsIgnoreCase("br2")) {
				File f = fileChooser.showOpenDialog(MatchListCreator.getStage());
				matchFiles[1] = f;
				red_2.setText(f.getPath());
			} else if(node.equalsIgnoreCase("br3")) {
				File f = fileChooser.showOpenDialog(MatchListCreator.getStage());
				matchFiles[2] = f;
				red_3.setText(f.getPath());
			} else if(node.equalsIgnoreCase("bb1")) {
				File f = fileChooser.showOpenDialog(MatchListCreator.getStage());
				matchFiles[3] = f;
				blue_1.setText(f.getPath());
			} else if(node.equalsIgnoreCase("bb2")) {
				File f = fileChooser.showOpenDialog(MatchListCreator.getStage());
				matchFiles[4] = f;
				blue_2.setText(f.getPath());
			} else if(node.equalsIgnoreCase("bb3")) {
				File f = fileChooser.showOpenDialog(MatchListCreator.getStage());
				matchFiles[5] = f;
				blue_3.setText(f.getPath());
			}
		}
	}
	
	@FXML void createMasterlist(ActionEvent e) {
		if(!red_1.getText().equalsIgnoreCase(defaultText) && !red_2.getText().equalsIgnoreCase(defaultText) && !red_3.getText().equalsIgnoreCase(defaultText) && 
				!blue_1.getText().equalsIgnoreCase(defaultText) && !blue_2.getText().equalsIgnoreCase(defaultText) && !blue_3.getText().equalsIgnoreCase(defaultText)) {
			// Create the file here
			StringBuilder data = new StringBuilder();
			String prefix = "";
			for(File f : matchFiles) {
				try {
					BufferedReader br = new BufferedReader(new FileReader(f));
					String line = "";
					while((line = br.readLine()) != null) {
						if(line.equalsIgnoreCase("Red 1")) {
							// Eventually filter through to see if it is Red 1 Blue 1 etc.
						} else if(line.equalsIgnoreCase("Red 2")) {
							
						} else if(line.equalsIgnoreCase("Red 3")) {
							
						} else if(line.equalsIgnoreCase("Blue 1")) {
							
						} else if(line.equalsIgnoreCase("Blue 2")) {
							
						} else if(line.equalsIgnoreCase("Blue 3")) {
							
						}
					}
				} catch (Exception e1) {
				}
			}
		}
	}
	
}
