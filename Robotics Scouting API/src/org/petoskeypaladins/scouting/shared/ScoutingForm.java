package org.petoskeypaladins.scouting.shared;

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
	
	public ScoutingForm() {
		singleObjectFields = new HashMap<String, Object>();
		textObjectFields = new HashMap<String, String>();
		tmp = new ArrayList<String>();
	}
	
	public void addObjectField(String field, Object o) {
		singleObjectFields.put(field, o);
	}
	
	public void addObjectFields(String field, String text) {
		textObjectFields.put(field, text);
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
