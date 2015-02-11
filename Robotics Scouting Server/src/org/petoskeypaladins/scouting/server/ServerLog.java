package org.petoskeypaladins.scouting.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.petoskeypaladins.scouting.server.util.Properties;

public class ServerLog {

	private static String INFO = "[INFO][Server]: ";
	private static String WARN = "[WARN][Server]: ";
	private static String ERROR = "[ERROR][Server]: ";
	
	private static File logDir;
	private static File logFile;
	private static String loc = "";
	
	public static void createFile(String date) {
		logDir = new File(Properties.LOG_FILE_PATH);
		logFile = new File(logDir, date + ".log");
		
		if(logFile.exists()) {
			logFile.delete();
		}
		
		if(!logDir.exists()) {
			logDir.mkdirs();
		}
		try {
			logFile.createNewFile();
		} catch (IOException e) {
			logError("Unable to create a log file for the current session!");
		}
		
		loc = logFile.getPath();
		
	}
	
	public static void log(String input, ServerLevel lvl) {
		if(lvl == ServerLevel.INFO) {
			System.out.println(INFO + input);
		} else if(lvl == ServerLevel.WARN) {
			System.out.println(WARN + input);
		} else if(lvl == ServerLevel.ERROR) {
			System.out.println(ERROR + input);
		} else {
			System.out.println(INFO + input);
		}
	}
	
	public static void logInfo(String input) {
		String msg = INFO + input;
		System.out.println(INFO + input);
		storeMsg(msg);
	}
	
	public static void logWarn(String input) {
		String msg = WARN + input;
		System.out.println(WARN + input);
		storeMsg(msg);
	}
	
	public static void logError(String input) {
		String msg = ERROR + input;
		System.out.println(ERROR + input);
		storeMsg(msg);
	}
	
	public static void log(String input) {
		System.out.println(input);
	}
	
	private static void storeMsg(String message) {
		try {
			FileWriter writer = new FileWriter(loc, true);
			BufferedWriter br = new BufferedWriter(writer);
			
			br.write(message);
			br.newLine();
			br.close();
			
		} catch (IOException e) {
			logError("Unable to write to the current file!");
		}
	}
	
}
