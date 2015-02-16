package org.petoskeypaladins.scouting.shared;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class ScoutingForm implements Serializable {

	private transient static final long serialVersionUID = 1L;
	
	private HashMap<String, Object> singleObjectFields;
	private HashMap<String, String> textObjectFields;
	private ArrayList<String> tmp;
	
	private File form;
	
	public ScoutingForm() {
		singleObjectFields = new HashMap<String, Object>();
		textObjectFields = new HashMap<String, String>();
		tmp = new ArrayList<String>();
	}
	
	public ScoutingForm(String path) {
		this.form = new File(path);
		
		singleObjectFields = new HashMap<String, Object>();
		textObjectFields = new HashMap<String, String>();
		tmp = new ArrayList<String>();
		
		try {
			FileReader reader = new FileReader(form);
			BufferedReader br = new BufferedReader(reader);
			
			String line;
			
			try {
				while((line = br.readLine()) != null) {
					// Initialize objects parameter and put all of the objectFields inside of a hashmap
					String[] objects = line.split(": ");
					addObjectField(objects[0], objects[1]);
				}
			} catch (IOException e) {
				System.out.println("API: IO Exception!");

			}
		} catch (FileNotFoundException e) {
			System.out.println("API: Could not read file!");
		}
		
	}
	
	public void addObjectField(String field, Object o) {
		singleObjectFields.put(field.toLowerCase(), o);
	}
	
	public void addObjectFields(String field, String text) {
		textObjectFields.put(field.toLowerCase(), text);
	}
	
	public Object getObjectField(String field) {
		return singleObjectFields.get(field);
	}
	
	public String getTextObjectField(String field) {
		return textObjectFields.get(field);
	}
	
	public ArrayList<String> readAll() {
		Iterator sofi = singleObjectFields.entrySet().iterator();
		Iterator tofi = textObjectFields.entrySet().iterator();
		while(sofi.hasNext()) {
			Entry entry = (Entry) sofi.next();
			tmp.add(entry.getKey() + ": " + entry.getValue());
		}
		while(tofi.hasNext()) {
			Entry entry = (Entry) tofi.next();
			tmp.add(entry.getKey() + ": " + entry.getValue());
		}
		return tmp;
	}
	
	public void remove() {
		singleObjectFields.clear();
		textObjectFields.clear();
		tmp.clear();
	}
	
}
