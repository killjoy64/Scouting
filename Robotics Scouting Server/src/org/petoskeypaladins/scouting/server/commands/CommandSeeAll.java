package org.petoskeypaladins.scouting.server.commands;

import java.io.File;
import java.util.ArrayList;

import org.petoskeypaladins.scouting.server.ServerLog;
import org.petoskeypaladins.scouting.server.util.HTMLDocument;
import org.petoskeypaladins.scouting.server.util.Properties;
import org.petoskeypaladins.scouting.shared.ScoutingForm;

public class CommandSeeAll extends Command {

	public CommandSeeAll() {
		setName("see-all");
		setUsage("see-all <auto|tele|all> <team_number=#> <competition=string>");
	}
	
	@Override
	public void execute(String[] args) {
		if(args.length >= 2) {
			File f = new File(Properties.FORM_FILE_PATH);
			
			String whatToSee = args[1];
			String[] query = args[2].split("=");
			String field = query[0].replace("_", " ");
			String team = query[1];
			ArrayList<String> headings = new ArrayList<String>();
			StringBuilder dataString = new StringBuilder();
			
			ScoutingForm tempForm;
			
			for(File file : f.listFiles()) {
				tempForm = new ScoutingForm(file.getPath());
				
				if(tempForm.getObjectField(field) != null && tempForm.getObjectField("team number") != null) {
					if(whatToSee.equalsIgnoreCase("auto")) {
						if(tempForm.getObjectField(field).equals(team)) {
							dataString.append("<tr>");
							ServerLog.logInfo("Results of autonomous fields for " + "(" +  tempForm.getObjectField("team number") + ")");
							for(String data : tempForm.readAll()) {
								String[] objects = data.split(":");
								if((objects[0].contains("auto") || objects[0].contains("round number") || objects[0].contains("team number") || objects[0].contains("competition")) && !objects[0].equalsIgnoreCase("comments")) {
									ServerLog.logInfo(data);
									if(!headings.contains(objects[0])) {
										headings.add(objects[0]);
									}
									dataString.append("<td>" + objects[1] + "</td>");
								}
							}
							dataString.append("</tr>");						
						}
						
					} else if(whatToSee.equalsIgnoreCase("tele")) {
						if(tempForm.getObjectField(field).equals(team)) {
							ServerLog.logInfo("Results of teleop fields for " + "(" + tempForm.getObjectField("team number") + ")");
							dataString.append("<tr>");						
							for(String data : tempForm.readAll()) {
								String[] objects = data.split(":");
								if(!objects[0].contains("auto") && (!objects[0].equalsIgnoreCase("comments"))) {
									if(!headings.contains(objects[0])) {
										headings.add(objects[0]);
									}									
									ServerLog.logInfo(data);
									dataString.append("<td>" + objects[1] + "</td>");
								}
							}
							dataString.append("</tr>");						
						}
					} else if(whatToSee.equalsIgnoreCase("all")) {
						if(tempForm.getObjectField(field).equals(team)) {
							dataString.append("<tr>");
							ServerLog.logInfo("Results of all fields for " + "(" + tempForm.getObjectField("team number") + ")");
							for(String data : tempForm.readAll()) {
								String[] objects = data.split(":");
								if(!headings.contains(objects[0])) {
									headings.add(objects[0]);
								}								
								ServerLog.logInfo(data);
								dataString.append("<td>" + objects[1] + "</td>");
							}
							dataString.append("</tr>");
						}
					}
				} else {
					ServerLog.logWarn("Invalid field, or invalid data for team numbers");
				}
			}
			
			String newArg = "";
			
			if(whatToSee.equalsIgnoreCase("auto")) {
				newArg = "autonomous";
			} else if(whatToSee.equalsIgnoreCase("tele")) {
				newArg = "teleop";
			} else if(whatToSee.equalsIgnoreCase("all")) {
				newArg = "everything";
			}
			
			new HTMLDocument("[" + "Testing" + "]" + "see-all", newArg + " where " + args[2], headings, dataString);
			
			headings.clear();
			
		} else {
			ServerLog.logError(getUsage());
		}
	}
	
}
