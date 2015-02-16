package org.petoskeypaladins.scouting.server.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.petoskeypaladins.scouting.server.ServerLog;
import org.petoskeypaladins.scouting.server.util.Properties;
import org.petoskeypaladins.scouting.shared.ScoutingForm;

public class CommandList extends Command {

	public CommandList(){
		setName("list");
		setUsage("list <field=query> <field> <can-see-duplicates>");
	}

	@Override
	public void execute(String[] args) {
		// e.g. list spos 3618
		if(args.length == 3) {						
			File f = new File(Properties.FORM_FILE_PATH);
					
			ArrayList<File> formFiles = new ArrayList<File>();
			ScoutingForm tempForm;
			
			ArrayList<String> queries = new ArrayList<String>();
			
			for(File file : f.listFiles()) {
				formFiles.add(file);
				try {
					tempForm = new ScoutingForm(file.getPath());					
					
					String dataQuery = args[2].replace("_", " ");
					String[] query = args[1].split("=");
					String eQuery = query[0].replace("_", " ");
					
					if(tempForm.getObjectField(eQuery) != null) {
					// We found the query of the first criteria field
					// Eventually want to getObjectField(team_number) from round_number when it equals 5
						if(tempForm.getObjectField(eQuery).equals(query[1].replace("_", " "))) {
							if(args.length == 4) {
								if(args[3].equalsIgnoreCase("true")) {
									if(!queries.contains(dataQuery)) {
										ServerLog.logInfo("Result of " + args[2] + " where " + query[0] + " = " + query[1] + ": " + tempForm.getObjectField(dataQuery));								
										queries.add(dataQuery);
									}
								}
							} else {
								ServerLog.logInfo("Result of " + args[2] + " where " + query[0] + " = " + query[1] + ": " + tempForm.getObjectField(dataQuery));
							}
						}
					}
					
				} catch (Exception e) {;				
				}
			}	
		}
	}
}