
package dayton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class AccountSettings implements Serializable {

	private static final String SAVE_DIRECTORY = "C:/Users/Dayton Ellwanger/Desktop/account_settings";
	private static final String SAVE_FILE_NAME = "account_settings";
	private static String saveLocation = SAVE_DIRECTORY + "/" + SAVE_FILE_NAME;
	
	private static AccountSettings accountSettings;
	
	private String username;
	private String password;
	private String instructorUsername;
	private String server;
	private String serviceName;
	private int port;
	
	public static AccountSettings getAccountSettings() {
		if(accountSettings == null) {
			if((accountSettings = loadAccountSettings()) == null) {
				accountSettings = new AccountSettings();
				accountSettings.updateSettings("","","","","",0);
			}
		}
		return accountSettings;
	}
	
	public void updateSettings(String username, String password,
			String instructorUsername, String server, String serviceName, int port) {
		this.username = username;
		this.password = password;
		this.instructorUsername = instructorUsername;
		this.server = server;
		this.serviceName = serviceName;
		this.port = port;
		if(!username.equals("")) {
			saveAccountSettings();
			ServerConnection serverConnection = ServerConnection.getServerConnection();
			serverConnection.disconnect();
			serverConnection.connect();
		}
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getInstructorUsername() {
		return instructorUsername;
	}
	
	public String getServer() {
		return server;
	}
	
	public String getServiceName() {
		return serviceName;
	}
	
	public int getPort() {
		return port;
	}
	
	private static AccountSettings loadAccountSettings() {
		try {
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(saveLocation));
			AccountSettings settings = (AccountSettings) input.readObject();
			input.close();
			return settings;
		} catch (Exception ex) {return null;}
	}
	
	private static void saveAccountSettings() {
		try {
			File saveDirectoryFile = new File(SAVE_DIRECTORY);
			if(!saveDirectoryFile.exists()) {
				saveDirectoryFile.mkdir();
			}
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(saveLocation));
			output.writeObject(accountSettings);
			output.close();
		} catch (Exception ex) {ex.printStackTrace();}
	}
	
}