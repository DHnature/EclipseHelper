package buddylist.chat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import buddylist.views.SWTChatViewController;

/**
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * @author Rajgopal Vaithiyanathan
 * 
 */
public class Account implements RosterListener, MessageListener,
		ChatManagerListener, Viewable, ConnectionListener {

	private XMPPConnection connection;
	Roster roster;

	private AccountType accountType;
	private String username, password;
	private HashMap<String, Buddy_1> buddies;
	private List<Buddy_1> buddyList;
	ChatViewController viewer;
	private boolean online = false;
	private boolean connected = false;
	private String accountName;
	private String thisUser;

	public String getUserName() {
		return thisUser;
	}

	static {
		SASLAuthentication.registerSASLMechanism("DIGEST-MD5",
				MySASLDigestMD5Mechanism.class);
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public String getAccountName() {
		return accountName;
	}

	public HashMap<String, Buddy_1> getBuddies() {
		return buddies;
	}

	public Account(AccountType accountType, String username, String password,
			ChatViewController viewer) throws XMPPException,
			InterruptedException {
		this.accountType = accountType;
		this.username = username;
		this.password = password;
		this.viewer = viewer;
		this.accountName = username;
		this.buddyList = new ArrayList<Buddy_1>();

		online();
	}

	private static String deleteResourceString(String s) {
		if (s != null && s.indexOf('/') != -1)
			return s.substring(0, s.indexOf('/'));
		else
			return s;
	}

	@Override
	public void entriesAdded(Collection<String> arg0) {
		System.out.println(arg0);
	}

	@Override
	public void entriesDeleted(Collection<String> arg0) {
		System.out.println(arg0);
	}

	@Override
	public void entriesUpdated(Collection<String> arg0) {
		System.out.println(arg0);
	}

	@Override
	public void presenceChanged(Presence arg0) {
//		buddies.get(deleteResourceString(deleteResourceString(arg0.getFrom())))
//				.setPresenceType(PresenceType.getPresenceTypeFromPresence(arg0));
//		buddies.get(deleteResourceString(deleteResourceString(arg0.getFrom())))
//				.setStatus(arg0.getStatus());
//		viewer.changePresence(this,
//				buddies.get(deleteResourceString(arg0.getFrom())),
//				PresenceType.getPresenceTypeFromPresence(arg0),
//				arg0.getStatus());
	}

	@Override
	public void chatCreated(Chat chat, boolean createdLocally) {
		chat.addMessageListener(this);
	}

	@Override
	public String toString() {
		return accountType.name() + " " + accountName;
	}

	@Override
	public Viewable[] getChildren() {
		if (!online)
			return null;
		Collections.sort(buddyList);
		return buddyList.toArray(new Buddy_1[buddyList.size()]);
	}

	@Override
	public Viewable getParent() {
		return AccountsManager.getInstance();
	}

	public void offline() {
		try {
			connection.disconnect();
		} catch (Exception ex) {}
		online = false;
	}

	public void releaseBuddies() {
		if (online)
			return;

		for (Buddy_1 b : buddyList) {
			b.destrory();
		}
		buddies.clear();
		buddyList.clear();
		// viewer = null;
	}

	public void online() throws XMPPException, InterruptedException {
		ConnectionConfiguration config;

		if (accountType == AccountType.ADD_BUDDY) {
			config = new ConnectionConfiguration("chat.facebook.com", 5222);
		} else if (accountType == AccountType.CEC) {
			config = new ConnectionConfiguration("talk.google.com", 5222,
					"gmail.com");
		} else {
			throw new UnsupportedOperationException();
		}

		SASLAuthentication.registerSASLMechanism("DIGEST-MD5",
				MySASLDigestMD5Mechanism.class);

		//config.setSASLAuthenticationEnabled(true);
		config.setRosterLoadedAtLogin(true);
		
		connection = new XMPPTCPConnection(config);
		try {
			connection.connect(); /* Connect to the XMPP server  */
			connection.login(username, password);
			connection.addConnectionListener(this);
		} catch (Exception xe) {
			try {
			connection.disconnect();
			} catch (Exception ex) {}
			//throw xe;
		}
		thisUser = connection.getUser();
		Thread.sleep(5000);
		roster = connection.getRoster();
		roster.addRosterListener(this);
		buddies = new HashMap<String, Buddy_1>();

		for (RosterEntry entry : roster.getEntries()) {
			Buddy_1 b = new Buddy_1(this);
			b.entry = entry;
			b.p = roster.getPresence(entry.getUser());
			b.setName(entry.getName());
//			b.setPresenceType(PresenceType.getPresenceTypeFromPresence(roster
//					.getPresence(entry.getUser())));
			b.setStatus(roster.getPresence(entry.getUser()).getStatus());
			buddies.put(entry.getUser(), b);
			buddyList.add(b);
			// if (!entry.getUser().equals(
			// deleteResourceString(roster.getPresence(entry.getUser())
			// .getFrom())))
			// System.out.println("ALERRRRRTTTTTT");

		}
		System.out.println("init complete.");
		ChatManager.getInstanceFor(connection).addChatListener(this);
		online = true;
		connected = true;
	}

	public boolean isOnline() {
		return online;
	}

	public void sendChat(Buddy_1 b, String s) throws XMPPException {
		if (b.getChat() == null) {
			b.setChat(ChatManager.getInstanceFor(connection).createChat(b.getUserName(),
					this));
		}
		try {
			b.getChat().sendMessage(s);
		} catch (Exception ex) {}
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		Buddy_1 b = buddies.get(deleteResourceString(chat.getParticipant()));
		if (b.getChat() != chat) {
			b.setChat(chat);
		}
		viewer.recieveChat(b, message.getBody());
	}

	@Override
	public void connectionClosed() {
	
	}

	@Override
	public void connectionClosedOnError(Exception e) {

	}

	@Override
	public void reconnectingIn(int seconds) {
		if(seconds!=0)
		SWTChatViewController.getInstance().disableChat(this,
				"Network not available. Reconnecting in " + seconds + " seconds");
		else
		SWTChatViewController.getInstance().disableChat(this,
				"Reconnecting... Please wait...");
	}

	@Override
	public void reconnectionSuccessful() {
			SWTChatViewController.getInstance().enableChat(this, "");
	}

	@Override
	public void reconnectionFailed(Exception e) {
		SWTChatViewController.getInstance().disableChat(this,
				"Reconnection failed. " + e.getMessage());

	}

	@Override
	public void authenticated(XMPPConnection arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connected(XMPPConnection arg0) {
		// TODO Auto-generated method stub
		
	}

}
