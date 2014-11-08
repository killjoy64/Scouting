package org.petoskeypaladins.scouting.server;

import java.util.ArrayList;

import org.petoskeypaladins.scouting.server.commands.Command;
import org.petoskeypaladins.scouting.server.commands.CommandHelp;
import org.petoskeypaladins.scouting.server.commands.CommandStart;

public class CommandHandler {

	public ArrayList<Command> commands;
	
	private CommandHelp help;
	private CommandStart start;
	private boolean found = false;

	
	public CommandHandler() {
		commands = new ArrayList<Command>();
		
		help = new CommandHelp();
		start = new CommandStart();
		
		register();
	}
	
	private void register() {
		commands.add(help);
		commands.add(start);
	}
	
	public void execute(String input, String[] args) {
		for(Command c : commands) {
			if(c.getName().equalsIgnoreCase(input)) {
				c.execute(args);
				found = true;
			}
		}
		if(!found) {
			ServerLog.logError("Command '" + input + "' not found");
			found = false;
		}
	}
	
}
