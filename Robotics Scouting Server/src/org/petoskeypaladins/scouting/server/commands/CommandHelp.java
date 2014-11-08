package org.petoskeypaladins.scouting.server.commands;

import org.petoskeypaladins.scouting.server.ServerLog;

public class CommandHelp extends Command {

	public CommandHelp() {
		setName("help");
	}

	@Override
	public void execute(String[] args) {
		ServerLog.logInfo("Help Page Eventually Goes Here");
	}

}
