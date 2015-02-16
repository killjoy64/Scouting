package org.petoskeypaladins.scouting.server.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.petoskeypaladins.scouting.server.ServerLog;
import org.petoskeypaladins.scouting.server.util.Properties;

public class CommandList extends Command {

	public CommandList(){
		setName("list");
		setUsage("list <field=query> <field>");
	}

	@Override
	public void execute(String[] args) {
		// e.g. list spos 3618
		if(args.length == 3) {						
			File f = new File(Properties.FORM_FILE_PATH);
			
			ArrayList<String> queries = new ArrayList<String>();
			
			String queryResult = "NULL";			
			// Loop through ALL the files in the FORM_FILE_PATH
			for(File file : f.listFiles()) {
				if(file.isFile()) {
					// Fields for fileParam[] array
					// [0] = Competition
					// [1] = Round
					// [2] = Team
					/*
					 * Right now it works by asking for the team#, then by searching for the arg[1] based on team number
					 * What it can't do RIGHT NOW: 
					 * 	Compare Fields: round_number competition == 
					 * 
					 * Maybe it should search by round_number=3618 - searches for round_number where 3618 is in
					 * 
					 * list round_number=5 team_number - and it gives the team numbers that were in 5
					 * list competitoin=tc team_number - and it gives the team numbers that were in tc
					 * 
					 * 1 = field=query
					 * 2 = field to search for
					 */
					String[] fileParam = file.getName().replace(".form", "").split("-");
					
					String[] query = args[1].split("=");
					
					try {
						FileReader reader = new FileReader(file);
						BufferedReader br = new BufferedReader(reader);
						
						try {
							String line;
							while((line = br.readLine()) != null) {
								String[] info = line.split(": ");
								String firstField = info[0]; // This is the obectField
								String secondField = info[1];// This is the result of the obJectField
								
								// If the field has a space, then replace it with an underscore
								if(info[0].contains(" ")) {
									firstField = info[0].replace(" ", "_");
								}
								
								int curIndex = 0;
								
								// Check if the second field equals the first field in the file
								if(args[2].equalsIgnoreCase(firstField)) {
									queryResult = secondField;
									//if(args[2].equalsIgnoreCase("team_number") || args[2].equalsIgnoreCase("competition")) {
									//e.g. if queries already contains 3618, set it to NULL and don't print it out
										if(queries.contains(queryResult)) {
											if(queries.indexOf(queryResult) == -1) {
												queryResult = "NULL";
											}
										} else {
											queries.add(queryResult);
										}
									//}
								}
								
								// Now search for team_number where round_number=5
									if(query[0].equalsIgnoreCase(firstField)) {		// checks if round_number equals the round_number field in the file so we don't get team_number = 5
										if(query[1].equalsIgnoreCase(secondField)) {	// checks if the query equals the result of the first field
											// How do I get the team number?
											if(!queryResult.equalsIgnoreCase("NULL")) {
												ServerLog.logInfo("Result of " + args[2] + " where " + query[0] + " = " + query[1] + ": " + queryResult);
											}
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
			
			queries.clear();
			
		} else {
			ServerLog.logError(getUsage());
		}
	}
	
}
