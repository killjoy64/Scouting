package org.petoskeypaladins.scouting.server.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.petoskeypaladins.scouting.server.ServerLog;
import org.petoskeypaladins.scouting.server.util.Properties;

public class CommandList extends Command {

	public CommandList(){
		setName("list");
		setUsage("list <field> <team#>");
	}

	@Override
	public void execute(String[] args) {
		// e.g. list spos 3618
		if(args.length == 3) {
			String field = args[1];
			String team = args[2];
						
			File f = new File(Properties.FORM_FILE_PATH);
			
			for(File file : f.listFiles()) {
				if(file.isFile()) {
					// it goes competition-round-team
					String[] types = file.getName().replace(".form", "").split("-");
					if(types[2].equalsIgnoreCase(team)) {
						
						try {
							FileReader reader = new FileReader(file);
							BufferedReader br = new BufferedReader(reader);
							
							try {
								String line;
								while((line = br.readLine()) != null) {
									String[] objectFields = line.split(": ");
									if(args[1].equalsIgnoreCase("spos")) {
										if(objectFields[0].equalsIgnoreCase("Starting Position")) {
											ServerLog.log("Round " + types[1] + ": " + objectFields[1]);
										}
									}
								}
							} catch (IOException e) {
								ServerLog.logError("Could not read files");							
							}
						} catch (FileNotFoundException e) {
							ServerLog.logError("Could not read files");
						}
						
					}
				}
			}
			
		} else {
			ServerLog.logError(getUsage());
		}
	}
	
}
