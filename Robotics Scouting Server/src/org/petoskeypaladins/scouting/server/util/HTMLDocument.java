package org.petoskeypaladins.scouting.server.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.petoskeypaladins.scouting.server.ServerLog;

public class HTMLDocument {

	// For everything we want to fill inside of the HTML file, we will delcare it by doing $TAG_HERE, and simply replace that string with the information
	
	String title;
	
	StringBuilder template, headingData, data, dataString;
	String tempInfo;
	String query;
	ArrayList<String> headings;
	
	public HTMLDocument(String title, String query, ArrayList<String> headings, StringBuilder dataString) {
		this.title = title;
		this.query = query;
		this.dataString = dataString;
		this.headings = headings;
		createFile();
	}
	
	private void copyData() {
		File f = new File(Properties.HTML_FILE_PATH, "results_template.html");
		if(!f.exists()) {
			ServerLog.logError("No template found, not creating HTML results file");
		} else {
			
			template = new StringBuilder();
			
			try {
				FileReader reader = new FileReader(f);
				BufferedReader br = new BufferedReader(reader);
				
				String line = "";
				
				while((line = br.readLine()) != null) {
					template.append(line + "_LINE_");
				}
				
				br.close();
				
			} catch (Exception e) {
				ServerLog.logError("Error reading HTML file");			
			}
		}
	}
	
	private void createFile() {
		copyData();
		
		File dir = new File(Properties.HTML_FILE_PATH);
		File f = new File(dir, title + "_results.html");
		
		if(!dir.exists()) {
			dir.mkdirs();
		}
				
		if(!f.exists() || f.exists()) {
			try {
				f.createNewFile();
				
				FileWriter writer = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(writer);
								
				tempInfo = "";
				
				headingData = new StringBuilder();
				data = new StringBuilder();
				for(String s : headings) {
					headingData.append("<td>" + s + "</td>");
				}
				
				tempInfo = template.toString().replace("_LINE_", "")
					.replace("<$QUERY>", query)
						.replace("<$DATA>", dataString.toString())
							.replace("<$HEADERS>", headingData.toString());
				
				bw.write(tempInfo);
				bw.close();
				
			} catch (Exception e) {
				ServerLog.logError("Error creating HTML file");
				e.printStackTrace();
			}
		}
		
	}
	
	public String getTitle() {
		return title;
	}
	
}
