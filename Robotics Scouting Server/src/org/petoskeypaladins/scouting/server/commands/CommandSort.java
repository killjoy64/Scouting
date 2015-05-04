package org.petoskeypaladins.scouting.server.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.petoskeypaladins.scouting.server.ServerLog;
import org.petoskeypaladins.scouting.server.util.HTMLDocument;
import org.petoskeypaladins.scouting.server.util.Properties;
import org.petoskeypaladins.scouting.shared.ScoutingForm;

public class CommandSort extends Command {

	public CommandSort() {
		setName("sort");
		setUsage("sort <field>");
	}
	
	@Override
	public void execute(String[] args) {
		if(args.length == 2) {
		File f = new File(Properties.FORM_FILE_PATH);
		
		ScoutingForm tempForm;
		
		ConcurrentHashMap<String, ScoutingForm> dataResults = new ConcurrentHashMap<String, ScoutingForm>();
		//ArrayList<String> results = new ArrayList<String>();
		ArrayList<String> headings = new ArrayList<String>();
		StringBuilder dataString = new StringBuilder();
		
		String whatToSearchFor = "NULL";
			
		for(File file : f.listFiles()) {
			try {
				tempForm = new ScoutingForm(file.getPath());
				
				whatToSearchFor = args[1].replace("_", " ");	
				// Eventually the goal is to sort every file by a certain field (by the same round_number, or competition)
				
				if(tempForm.getObjectField(whatToSearchFor) != null) {
//					String result = (String) tempForm.getObjectField(whatToSearchFor);
//					if(result.contains(" ")) {
//						result = result.replace(" ", "");
//					}
//					searches.put(file, result);
//					if(!results.contains(result)) {
//						results.add(result);
//					}
					for(String data : tempForm.readAll()) {
						String[] objects = data.split(":");
						if(objects[1].contains(" ")) {
							objects[1] = objects[1].replace(" ", "");
						}
						if(objects[0].equalsIgnoreCase(whatToSearchFor)) {
							dataResults.put(objects[1], tempForm);
						}
					}
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		// End of the file loop
		headings.add(whatToSearchFor);
		
//		for(String r : results) {
//			for(Entry<File, String> entry : searches.entrySet()) {
//				if(entry.getValue().equalsIgnoreCase(r)) {
//					ServerLog.logInfo(entry.getValue() + ": " + entry.getKey().getName());
//					dataString.append("<tr><td>" + entry.getValue() + "</td><td>" + entry.getKey().getName() + "</td>");
//					searches.remove(entry.getKey(), entry.getValue());
//				}
//			}
//		}
		
		try {
			TreeSet<Integer> keys = new TreeSet<Integer>();
			
			for(String key : dataResults.keySet()) {
				int team = Integer.parseInt(key);
				keys.add(team);
			}
			
			for(int i : keys) {
				ScoutingForm resultForm = dataResults.get(i + "");
				dataString.append("<tr>");
				dataString.append("<td>" + resultForm.getObjectField(whatToSearchFor) + "</td>");
				int c = 0;
				for(String s : resultForm.readAll()) {
					c++;
					System.out.println(c);
					String[] objects = s.split(": ");
					if(!objects[0].equalsIgnoreCase(whatToSearchFor)) {
						if(!headings.contains(objects[0])) {
							headings.add(objects[0]);
						} else {
							dataString.append("<td>" + objects[1] + "</td>");
						}
					}
						
				}
				dataString.append("</tr>");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		new HTMLDocument("[" + "Testing" + "]" + "sort", whatToSearchFor, headings, dataString);		
		} else {
			ServerLog.logError(getUsage());
		}
	}

}
