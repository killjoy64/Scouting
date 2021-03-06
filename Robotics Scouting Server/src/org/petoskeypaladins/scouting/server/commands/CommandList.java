package org.petoskeypaladins.scouting.server.commands;

import java.io.File;
import java.util.ArrayList;

import org.petoskeypaladins.scouting.server.ServerLog;
import org.petoskeypaladins.scouting.server.util.HTMLDocument;
import org.petoskeypaladins.scouting.server.util.Properties;
import org.petoskeypaladins.scouting.shared.ScoutingForm;

public class CommandList extends Command {

	public CommandList(){
		setName("list");
		setUsage("list <field=query> <field=query|field> <can-see-duplicates>");
	}

	@Override
	public void execute(String[] args) {
		// e.g. list spos 3618
		if(args.length >= 3) {						
			File f = new File(Properties.FORM_FILE_PATH);
					
			ScoutingForm tempForm;
			
			ArrayList<String> queries = new ArrayList<String>();
			
			StringBuilder data = new StringBuilder();
			String[] q;
			
			for(File file : f.listFiles()) {
				try {
					tempForm = new ScoutingForm(file.getPath());					
					
					String dataQuery = args[2].replace("_", " ");
					String[] query = args[1].split("=");
					String eQuery = query[0].replace("_", " ");
					
					if(tempForm.getObjectField(eQuery) != null) {
					// We found the query of the first criteria field
					// Eventually want to getObjectField(team_number) from round_number when it equals 5
						if(tempForm.getObjectField(eQuery).equals(query[1].replace("_", " "))) {
							if(args.length >= 4) {
								if(args[3].equalsIgnoreCase("false")) {
									if(queries.contains(tempForm.getObjectField(dataQuery)) == false) {
										ServerLog.logInfo("Result of " + args[2] + " where " + query[0] + " = " + query[1] + ": " + tempForm.getObjectField(dataQuery));								
										queries.add((String) tempForm.getObjectField(dataQuery));
										data.append("<tr><td>" + tempForm.getObjectField(dataQuery) + "</td><td>" + tempForm.getObjectField(query[0].replace("_", " ")) + "</td></tr>");
									}
								} else if(args[3].equalsIgnoreCase("true")) {
									ServerLog.logInfo("Result of " + args[2] + " where " + query[0] + " = " + query[1] + ": " + tempForm.getObjectField(dataQuery));
									data.append("<tr><td>" + tempForm.getObjectField(dataQuery) + "</td><td>" + tempForm.getObjectField(query[0].replace("_", " ")) + "</td></tr>");

								} else {
									// it's not a boolean, it's another case query
									String[] secondQuery = args[2].split("=");
									
									if(tempForm.getObjectField(secondQuery[0].replace("_", " ")).equals(secondQuery[1])) {
										dataQuery = args[3].replace("_", " ");
										if(args.length == 5) {
											if(args[4].equalsIgnoreCase("false")) {
												if(queries.contains(tempForm.getObjectField(dataQuery)) == false) {
													ServerLog.logInfo("Result of " + args[3] + " where " + query[0] + " = " + query[1] + " and where " + secondQuery[0] + " = " + secondQuery[1] + ": " + tempForm.getObjectField(dataQuery));
													queries.add((String) tempForm.getObjectField(dataQuery));
													data.append("<tr><td>" + tempForm.getObjectField(dataQuery) + "</td><td>" + tempForm.getObjectField(query[0].replace("_", " ")) + "</td><td>" + tempForm.getObjectField(secondQuery[0].replace("_", " ")) +  "</td></tr>");
												}
											} else {
												ServerLog.logInfo("Result of " + args[3] + " where " + query[0] + " = " + query[1] + " and where " + secondQuery[0] + " = " + secondQuery[1] + ": " + tempForm.getObjectField(dataQuery));																							
												data.append("<tr><td>" + tempForm.getObjectField(dataQuery) + "</td><td>" + tempForm.getObjectField(query[0].replace("_", " ")) + "</td><td>" + tempForm.getObjectField(secondQuery[0].replace("_", " ")) +  "</td></tr>");
											}
										} else {
											ServerLog.logInfo("Result of " + args[3] + " where " + query[0] + " = " + query[1] + " and where " + secondQuery[0] + " = " + secondQuery[1] + ": " + tempForm.getObjectField(dataQuery));											
											data.append("<tr><td>" + tempForm.getObjectField(dataQuery) + "</td><td>" + tempForm.getObjectField(query[0].replace("_", " ")) + "</td><td>" + tempForm.getObjectField(secondQuery[0].replace("_", " ")) +  "</td></tr>");
										}
									}
								}
							} else {
								ServerLog.logInfo("Result of " + args[2] + " where " + query[0] + " = " + query[1] + ": " + tempForm.getObjectField(dataQuery));
								data.append("<tr><td>" + tempForm.getObjectField(dataQuery) + "</td><td>" + tempForm.getObjectField(query[0].replace("_", " ")) + "</td></tr>");
							}
						}
					}
										
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
			
//			q[0] = args[2].replace("_", " ");
//			q[1] = args[1].split("=")[0].replace("_", " ");
//			if(args.length >= 4) {
//				q[0] = args[3].replace("_", " ");
//				q[1] = args[1].split("=")[0].replace("_", " ");
//				q[2] = args[2].split("=")[0].replace("_", " ");
//				q[3] = "";
//			} else {
//				q[2] = "";
//				q[3] = "";
//			}			
			
			q = new String[args.length];
			
			//new HTMLDocument("[" + "Testing" + "]" + "sort", q, data.toString());

			queries.clear();
		} else {
			ServerLog.logError(getUsage());
		}
	}
}