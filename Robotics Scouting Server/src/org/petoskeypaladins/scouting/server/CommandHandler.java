package org.petoskeypaladins.scouting.server;

import java.util.ArrayList;

import org.petoskeypaladins.scouting.server.commands.Command;
import org.petoskeypaladins.scouting.server.commands.CommandClose;
import org.petoskeypaladins.scouting.server.commands.CommandHelp;
import org.petoskeypaladins.scouting.server.commands.CommandList;
import org.petoskeypaladins.scouting.server.commands.CommandSort;
import org.petoskeypaladins.scouting.server.commands.CommandStart;

public class CommandHandler {

	public ArrayList<Command> commands;
	
	private CommandHelp help;
	private CommandStart start;
	private CommandList list;
	private CommandClose close;
	private CommandSort sort;
	
	private boolean found = false;

	
	public CommandHandler() {
		commands = new ArrayList<Command>();
		
		help = new CommandHelp();
		start = new CommandStart();
		list = new CommandList();
		close = new CommandClose();
		sort = new CommandSort();
		
		register();
	}
	
	private void register() {
		commands.add(help);
		commands.add(start);
		commands.add(list);
		commands.add(close);
		commands.add(sort);
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
