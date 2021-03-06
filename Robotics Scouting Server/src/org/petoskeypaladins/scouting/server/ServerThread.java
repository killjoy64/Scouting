package org.petoskeypaladins.scouting.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {

	private ServerSocket server;
	
	private ArrayList<Socket> users;
	
	public ServerThread(int port) {
		try {
			server = new ServerSocket(port);
			users = new ArrayList<Socket>();
			
			ScoutingServer.setServerThread(this);
			
			ServerLog.logInfo("Successfully started Scouting Server on port " + port);
			ServerLog.logInfo("Scouting Server is now accepting users");
		} catch (IOException e) {
			ServerLog.logError("Couldn't start the server socket, contact Kyle immediately!");
		}
	}
	
	@Override
	public void run() {
		while(true) {
			if(!server.isClosed()) {
				try {		
					
							Socket user = server.accept();
						ServerLog.logInfo("Client " + user.getLocalAddress().getHostName() + " has connected");
						
						users.add(user);
						
						//ScoutingServer.setUserSocketList((Socket[]) users.toArray()); 
						
						(new Thread(new ClientThread(user))).start();
				} catch(IOException e) {
					ServerLog.logError("Error receiving messages from client, contact Kyle Immediately!");
				}
			}
		}
	}
	
	public ServerSocket getServer() {
		return server;
	}
	
	public void close() {
		try {
			System.out.println("Closed connection");
			for(Socket u : users) {
				u.close();
			}
			server.close();
			System.out.println(server.isClosed());
			this.join();
		} catch (Exception e) {
		}
	}
	
}
