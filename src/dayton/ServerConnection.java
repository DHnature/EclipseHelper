
package dayton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

public class ServerConnection implements RosterListener, PacketListener {
	
	private static ServerConnection serverConnection;
	
	private XMPPConnection connection;
	private Roster roster;
	private List<ContactChangeListener> contactListeners = new ArrayList<ContactChangeListener>();
	
	private HashMap<String,String> helpRequests = new HashMap<String,String>();
	
	public static ServerConnection getServerConnection() {
		if(serverConnection == null) {
			serverConnection = new ServerConnection();
		}
		return serverConnection;
	}
	
	public ServerConnection() {
		connect();
	}
	
	public void connect() {
		//new Thread(new Runnable() {
			//public void run() {
		System.out.println("Logging in!");
		//LogIntoXMPP.login();
				try {
					AccountSettings account = AccountSettings.getAccountSettings();
					if(!account.getUsername().equals("")) {
						ConnectionConfiguration config = new ConnectionConfiguration("talk.google.com",5222,"gmail.com");
						connection = new XMPPConnection(config);
						connection.connect();
						System.out.println("Connected");
						SASLAuthentication.supportSASLMechanism("PLAIN", 0);
						//connection.login("daytonellwangertest@gmail.com","ellwangertest");
						connection.login(account.getUsername(),account.getPassword());
						System.out.println("Login successful");
						//ConnectionConfiguration config = new ConnectionConfiguration(account.getServer(),
						//account.getPort(),account.getServiceName());
						/*connection = new XMPPTCPConnection(config);
						connection.connect();
						System.out.println("Connected");
						connection.login("daytonellwangertest","ellwangertest");
						//connection.login(account.getUsername(),account.getPassword());
						System.out.println("Login successful");*/
						roster = connection.getRoster();
						//roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
						roster.addRosterListener(this);
						connection.addPacketListener(this,null);
						//LogIntoXMPP.ECFLogin();
						//LogIntoXMPP.login();
						//pingInstructor();
						updateStatus("Connected");
					}
				} catch (Exception ex) {ex.printStackTrace(); System.out.println("Error connecting to server");}
			//}
		//}).start();
		updateContactListeners();
	}
	
	public void disconnect() {
		try {
			connection.disconnect();
		} catch (Exception ex) {}
		updateContactListeners();
	}
	
	public boolean isConnected() {
		return (connection == null) ? false : connection.isConnected();
	}
	
	public void requestVideo(String user) {
		Message videoRequest = new Message(user);
		videoRequest.setSubject("videorequest");
		try {
			connection.sendPacket(videoRequest);
		} catch (Exception ex) {System.out.println("Error sending message");}	
	}
	
	public void updateStatus(String status) {
		Presence statusUpdate = new Presence(Presence.Type.available);
		statusUpdate.setStatus(status);
		try {
			connection.sendPacket(statusUpdate);
		} catch (Exception ex) {System.out.println("Error sending update");}
	}
	
	public void pingInstructor() throws Exception {
		AccountSettings account = AccountSettings.getAccountSettings();
		if(!account.getInstructorUsername().equals("")) {
			System.out.println("Pinging instructor");
			InstructorPing ping = new InstructorPing();
			ping.setTo(account.getInstructorUsername());
			ping.setFrom(account.getUsername());
			connection.sendPacket(ping);
		}
	}
	
	public void addListener(ContactChangeListener listener) {
		contactListeners.add(listener);
		listener.contactsChanged(getContacts());
	}
	
	private void updateContactListeners() {
		List<Contact> contacts = getContacts();
		for(ContactChangeListener listener : contactListeners) {
			listener.contactsChanged(contacts);
		}
	}
	
	public List<Contact> getContacts() {
		List<Contact> contacts = new ArrayList<Contact>();
		contacts.add(new Contact("Server",isConnected() ? "Connected" : "Disconnected"));
		if(roster == null) return contacts;
		try {
			roster.reload();
		} catch (Exception ex) {System.out.println("Error loading roster");}
		for (RosterEntry entry : roster.getEntries()) {
			try {
				Presence presence = roster.getPresence(entry.getName());
				contacts.add(new Contact(presence.getFrom(),
						presence.isAvailable() ? presence.getStatus() : "Offline"));
			} catch (Exception ex) {System.out.println("Error getting presence");}
		}
		return contacts;
	}
	
	public void entriesAdded(Collection<String> arg0) {}
	public void entriesDeleted(Collection<String> arg0) {}
	public void entriesUpdated(Collection<String> arg0) {}

	@Override
	public void presenceChanged(Presence presence) {
		System.out.println("Presence Changed");
		updateContactListeners();
	}

	@Override
	public void processPacket(Packet packet) {
		/*if(packet instanceof Presence) {
			Presence presence = (Presence) packet;
			if(presence.getType() == Presence.Type.subscribe &&
				presence.getFrom().equals(AccountSettings.getAccountSettings().getInstructorUsername())) {
				System.out.println("Instructor Subscription Request");
				Presence subscribed = new Presence(Presence.Type.subscribed);
				subscribed.setTo(presence.getFrom());
				subscribed.setFrom(AccountSettings.getAccountSettings().getUsername());
				try {
					connection.sendPacket(subscribed);
				} catch (Exception ex) {System.out.println("Error sending subscribed");}
			} else if (presence.getType() == Presence.Type.subscribe){
				//if(packet instanceof InstructorPing) {
				//gotPing((Presence) packet);
				//}
			}
		}*/
		System.out.println("Received packet");
		if(packet instanceof Message) {
			System.out.println("Received message");
			String subject = ((Message) packet).getSubject();
			if(subject.equals("videorequest")) {
				try {
					VideoSender.sendVideo();
				} catch (Exception ex) {ex.printStackTrace();};
				Message reply = new Message(packet.getFrom());
				reply.setSubject("videouploaded");
				try {
					connection.sendPacket(reply);
				} catch (Exception ex) {System.out.println("Error sending message");}
			} else if (subject.equals("videouploaded")) {
				try {
					VideoReceiver.receiveVideo();
				} catch (Exception ex) {ex.printStackTrace();};
			} else if (subject.equals("helprequest")) {
				try {
					processHelpRequest((Message) packet);
				} catch (Exception ex) {ex.printStackTrace();}
			}
		}
	}	
	
	private void processHelpRequest(Message request) {
		helpRequests.put(request.getFrom(),request.getBody());
	}
	
	public void getHelpRequest(String user) {
		String request = helpRequests.get(user);
		if(request != null) {
			String[] pieces = request.split("[]");
			for(String s : pieces) {
				System.out.println(s);
			}
		}
	}
	
	public void sendHelpRequest(String[] helpQuery) {
		Message helpMessage = new Message(AccountSettings.getAccountSettings().getInstructorUsername());
		helpMessage.setSubject("helprequest");
		String messageBody = "";
		String seperator = "[]";
		for(String s : helpQuery) {
			messageBody += s;
			messageBody += seperator;
		}
		helpMessage.setBody(messageBody);
		try {
			connection.sendPacket(helpMessage);
		} catch (Exception ex) {System.out.println("Error sending message");}
	}
	
	private void gotPing(Presence ping) {
		System.out.println("Ping");
		if(roster.getEntry(ping.getFrom()) == null) {
			try {
				roster.createEntry(ping.getFrom(),ping.getFrom(),null);
			} catch (Exception ex) {System.out.println("Error adding contact"); ex.printStackTrace();}
			Presence subscribe = new Presence(Presence.Type.subscribe);
	        subscribe.setTo(ping.getFrom());
	        subscribe.setFrom(ping.getTo());
	        try {
	        	connection.sendPacket(subscribe);
	        	System.out.println("Sent subscription request to " + subscribe.getTo());
	        } catch (Exception ex) {System.out.println("Error sending subscribe");}
		}
	}
}