package org.petoskeypaladins.scouting.server;

import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.petoskeypaladins.scouting.server.util.Properties;
import org.petoskeypaladins.scouting.server.util.ServerProperties;

public class ScoutingServer implements Runnable {
	
	private CommandHandler cmdHandler;
	private Thread scouting;
	private ServerProperties serverProp;
	
	private static ServerThread server;
	private static Socket[] userSockets;
	
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
		Properties.LOG_FILE_PATH = serverProp.getPropFile().getProperty("log-file-path");
		Properties.TEMP_FILE_PATH = serverProp.getPropFile().getProperty("tmp-file-path");		
		
		if(Properties.FORM_FILE_PATH == "" || Properties.FORM_FILE_PATH == null) {
			Properties.FORM_FILE_PATH = "C:/Users/Scouting/Data";
		}
		
		if(Properties.LOG_FILE_PATH == "" || Properties.LOG_FILE_PATH == null) {
			Properties.LOG_FILE_PATH = "C:/Users/Scouting/Logs";
		}
		
		if(Properties.TEMP_FILE_PATH == "" || Properties.TEMP_FILE_PATH == null) {
			Properties.TEMP_FILE_PATH = "C:/Users/Scouting/tmp";
		}
		
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
		} else if(cmd.equalsIgnoreCase("close")) {
			cmdHandler.execute("close", args);
		} else if(cmd.equalsIgnoreCase("sort")) {
			cmdHandler.execute("sort", args);
		} else {
			ServerLog.logError("Command not found");
		}
	}

	@Override
	public void run() {
		loadProperties();
		
		DateFormat dateFormat = new SimpleDateFormat("[yyyy_MM_dd]-[HH.mm.ss]");
		Date date = new Date();
				
		ServerLog.createFile(dateFormat.format(date) + "");
		ServerLog.logInfo("Robotics Scouting Server v1.0");
		ServerLog.logInfo("Type 'help' for a list of commands");
		
		Scanner scanner = new Scanner(System.in);
		
		String line = "";
		String cmd = "";
		String[] args;
		
		while(scanner.hasNextLine()) {
			line = scanner.nextLine();
			args = line.split("\\s+");
			cmd = args[0];
			ServerLog.store("[CMD][Server]: \'" + line + "\'");
			acceptInput(cmd, args);
		}
	}
	
	public static void setServerThread(ServerThread server) {
		ScoutingServer.server = server;
	}
	
	public static ServerThread getServerThread() {
		return server;
	}

	public static void setUserSocketList(Socket[] users) {
		ScoutingServer.userSockets = users;
	}
	
	public static Socket[] getUserSockets() {
		return userSockets;
	}
	
}
