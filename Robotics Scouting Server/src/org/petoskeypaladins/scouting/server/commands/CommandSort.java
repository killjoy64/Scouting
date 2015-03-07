package org.petoskeypaladins.scouting.server.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Map.Entry;
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
		
		ConcurrentHashMap<File, String> searches = new ConcurrentHashMap<File, String>();
		ArrayList<String> results = new ArrayList<String>();
		ArrayList<String> headings = new ArrayList<String>();
		StringBuilder dataString = new StringBuilder();
		
		String searchField = "NULL";
			
		for(File file : f.listFiles()) {
			try {
				tempForm = new ScoutingForm(file.getPath());
				
				searchField = args[1].replace("_", " ");	
				// Eventually the goal is to sort every file by a certain field (by the same round_number, or competition)
				
				if(tempForm.getObjectField(searchField) != null) {
					String result = (String) tempForm.getObjectField(searchField);
					if(result.contains(" ")) {
						result = result.replace(" ", "");
					}
					searches.put(file, result);
					if(!results.contains(result)) {
						results.add(result);
					}
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		// End of the file loop
		headings.add(searchField);
		headings.add("file");
		
		for(String r : results) {
			for(Entry<File, String> entry : searches.entrySet()) {
				if(entry.getValue().equalsIgnoreCase(r)) {
					ServerLog.logInfo(entry.getValue() + ": " + entry.getKey().getName());
					dataString.append("<tr><td>" + entry.getValue() + "</td><td>" + entry.getKey().getName() + "</td>");
					searches.remove(entry.getKey(), entry.getValue());
				}
			}
		}
		
		String[] queries = new String[4];
		
		queries[0] = searchField;
		queries[1] = "file";
		queries[2] = "";
		queries[3] = "";
		
		new HTMLDocument("[" + "Testing" + "]" + "see-all", searchField, headings, dataString);		
		} else {
			ServerLog.logError(getUsage());
		}
	}

}
