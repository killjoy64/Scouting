package org.petoskeypaladins.scouting.server;

import java.util.Scanner;

import org.petoskeypaladins.scouting.server.util.Properties;
import org.petoskeypaladins.scouting.server.util.ServerProperties;

public class ScoutingServer implements Runnable {
	
	private CommandHandler cmdHandler;
	private Thread scouting;
	private ServerProperties serverProp;
	
	public static void main(String[] args) {
		ScoutingServer server = new ScoutingServer();
		
		server.start();
	}
			
	public ScoutingServer() {
		scouting = new Thread(this, "Scouting Server");
		// Eventually use threading for loading files and waiting ect.
		cmdHandler = new CommandHandler();
		serverProp = new ServerProperties("server.properties");
	}
	
	public void start() {
		scouting.start();
	}
	
	public void loadProperties() {
		serverProp.check();
		
		Properties.FORM_FILE_PATH = serverProp.getPropFile().getProperty("form-file-path");		
	}
	
	public void acceptInput(String cmd, String[] args) {
		// Eventually analyze input here and redirect to do whatever function is necessary
		if(cmd.equalsIgnoreCase("stop")) {
			ServerLog.logInfo("Shutting Down Server");
			System.exit(0);
		} else if(cmd.equalsIgnoreCase("help")) {
			cmdHandler.execute("help", args);
		} else if(cmd.equalsIgnoreCase("start")) {
			cmdHandler.execute("start", args);
		} else if(cmd.equalsIgnoreCase("avg")) {
			cmdHandler.execute("avg", args);
		} else if(cmd.equalsIgnoreCase("list")) {
			cmdHandler.execute("list", args);
		}
	}

	@Override
	public void run() {
		ServerLog.logInfo("Robitcs Scouting Server v1.0");
		
		loadProperties();
		
		ServerLog.logInfo("Type 'help' for a list of commands");
		
		Scanner scanner = new Scanner(System.in);
		
		String cmd = "";
		String[] args;
		
		while(scanner.hasNextLine()) {
			cmd = scanner.nextLine();
			args = cmd.split("\\s+");
			cmd = args[0];
			acceptInput(cmd, args);
		}
	}

}
