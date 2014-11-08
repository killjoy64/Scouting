package org.petoskeypaladins.scouting.server;

public class ServerLog {

	private static String INFO = "[INFO][Server]: ";
	private static String WARN = "[WARN][Server]: ";
	private static String ERROR = "[ERROR][Server]: ";
	
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
		System.out.println(INFO + input);
	}
	
	public static void logWarn(String input) {
		System.out.println(WARN + input);
	}
	
	public static void logError(String input) {
		System.out.println(ERROR + input);
	}
	
	public static void log(String input) {
		System.out.println(input);
	}
	
}
