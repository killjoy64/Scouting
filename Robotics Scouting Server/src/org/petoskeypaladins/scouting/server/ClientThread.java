package org.petoskeypaladins.scouting.server;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.net.Socket;

import org.petoskeypaladins.scouting.server.util.Properties;
import org.petoskeypaladins.scouting.shared.ScoutingForm;

public class ClientThread implements Runnable {

	private Socket client;
	
	public ClientThread(Socket s) {
		client = s;
	}
	
	@Override
	public void run() {
		try {
			ObjectInputStream input = new ObjectInputStream(client.getInputStream());
				
			ScoutingForm form = (ScoutingForm) input.readObject();
			
			String competition 	= (String) form.getObjectField("Competition");
			String team			= (String) form.getObjectField("Team Number");
			String round		= (String) form.getObjectField("Round Number");
			
			File formDir = new File(Properties.FORM_FILE_PATH);
			
			if(!formDir.exists()) {
				formDir.mkdir();
			}
			
			File formFile = new File(formDir, competition + "-" + round + "-" + team + ".form");
			
			if(formFile.exists()) {
				formFile.delete();
			} else {
				formFile.createNewFile();
			}
			// Put all of the form fields into the file
			
			FileWriter formIO = new FileWriter(formFile, true);
			BufferedWriter bfIO = new BufferedWriter(formIO);
			
			for(String field : form.readAll()) {
				bfIO.write(field + " \n");
			}
			
			bfIO.close();
			
			ServerLog.logInfo("Created form file " + formFile.getName());
			
			DataOutputStream output = new DataOutputStream(client.getOutputStream());
			
			output.writeUTF("Thanks for the data, closing connection");
			
			ServerLog.logInfo("Client " + client.getLocalAddress().getHostName() + " has disconnected");
			
			client.close();			
			} catch (Exception e) {
			}
	}
	
	public Socket getClient() {
		return client;
	}
	
}
