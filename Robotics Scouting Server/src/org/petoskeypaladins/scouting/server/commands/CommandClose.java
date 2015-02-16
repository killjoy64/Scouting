package org.petoskeypaladins.scouting.server.commands;

import org.petoskeypaladins.scouting.server.ScoutingServer;
import org.petoskeypaladins.scouting.server.ServerLog;

public class CommandClose extends Command {

	public CommandClose() {
		setName("close");
	}
	
	@Override
	public void execute(String[] args) {
		ServerLog.logInfo("Closed server on port " + ScoutingServer.getServerThread().getServer().getLocalPort());
		ScoutingServer.getServerThread().close();
	}

}
