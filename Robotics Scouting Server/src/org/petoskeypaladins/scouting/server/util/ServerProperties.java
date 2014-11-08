package org.petoskeypaladins.scouting.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import org.petoskeypaladins.scouting.server.ServerLog;

public class ServerProperties {

	private Properties properties;
	private File file;
	private File sourceFile;
	
	public ServerProperties(String path) {
		properties = new Properties();
		file = new File(path);
		sourceFile = new File(getClass().getResource("/server.properties").getFile());
	}

	public void check() {
		if (file.exists()) {
			ServerLog.logInfo("Found server.properties file");
			try {
				properties.load(new FileInputStream(file));
			} catch (Exception ex) {
				ServerLog.logError("Unable to load server.properties file");
			}
		} else {
			ServerLog.logInfo("Creating server.properties file");
			try {
				file.createNewFile();
				
				properties.load(new FileInputStream(file));
			} catch (IOException e) {
				ServerLog.logError("Couldn't create server.properties file, contact an administrator");
			}
		}
		
	}

	public Properties getPropFile() {
		return properties;
	}

}
