package org.petoskeypaladins.scouting.server.commands;

public abstract class Command {
	
	private String name;
	private String usage;
	
	public Command() {
		this.name = "";
	}
	
	public abstract void execute(String[] args);
	
	public String getName() {
		return name;
	}
	
	public String getUsage() {
		return usage;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setUsage(String usage) {
		this.usage = usage;
	}
	
}
