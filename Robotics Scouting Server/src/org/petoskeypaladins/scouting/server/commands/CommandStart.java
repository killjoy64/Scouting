package org.petoskeypaladins.scouting.server.commands;

import org.petoskeypaladins.scouting.server.ServerLog;
import org.petoskeypaladins.scouting.server.ServerThread;

public class CommandStart extends Command {

	public CommandStart() {
		setName("start");
		setUsage("Usage: start <int>");
	}
	
	@Override
	public void execute(String[] args) {
		if(args.length == 2) {
			try {
				Integer port = Integer.parseInt(args[1]);
				
				ServerLog.logInfo("Bob marley is da man, yeah. PORT: " + port);
				new ServerThread(port).start();
			} catch(NumberFormatException e) {
				ServerLog.logError("Argument must be an integer");
			}
		} else {
			ServerLog.logError(getUsage());
		}
	}

}
