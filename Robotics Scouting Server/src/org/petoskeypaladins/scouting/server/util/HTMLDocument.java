package org.petoskeypaladins.scouting.server.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.petoskeypaladins.scouting.server.ServerLog;

public class HTMLDocument {

	// For everything we want to fill inside of the HTML file, we will delcare it by doing $TAG_HERE, and simply replace that string with the information
	
	String title;
	
	StringBuilder template;
	String data;
	String tempInfo;
	String query_1, query_2, query_3, query_4;
	
	public HTMLDocument(String title, String[] queries, String data) {
		this.title = title;
		this.data = data;
		this.query_1 = queries[0];
		this.query_2 = queries[1];
		this.query_3 = queries[2];
		this.query_4 = queries[3];
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
								
				String[] lines = template.toString().split("_LINE_");
				tempInfo = "";
								
				tempInfo = template.toString().replace("_LINE_", "")
						.replace("<$DATA>", data)
							.replace("<$QUERY>", "<i>" + query_1 + "</i>")
								.replace("<$QUERY_1>", query_1)
									.replace("<$QUERY_2>", query_2)
										.replace("<$QUERY_3>", query_3)
											.replace("<$QUERY_4>", query_4);
				
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
