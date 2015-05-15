package buddylist.chat;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.packet.Presence;

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
public class Buddy_1 implements Viewable, Comparable<Buddy_1> {

	Chat c;
	Presence p;
	RosterEntry entry;
	Account a;

	private String name;
	private PresenceType presenceType;
	private String status;

	public Buddy_1(Account account) {
		a = account;
	}

	public Chat getChat() {
		return c;
	}

	public void setChat(Chat c) {
		this.c = c;
	}

	public String getName() {
		if (name == null) {
			if (entry.getUser().indexOf('/') != -1)
				return entry.getUser().substring(0,
						entry.getUser().indexOf('/'));
			return entry.getUser();
		}
		return name;

	}

	/**
	 * @return
	 */
	public String getUserName() {
		return entry.getUser();
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPresenceType(PresenceType presenceType) {
		if (presenceType == null) {
			System.out.println("Why God Why");
		}
		this.presenceType = presenceType;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		String ret = "[" + presenceType.name() + "] " + getName();
		if (a.getAccountType() == AccountType.CEC)
			ret += " (" + getUserName() + ")";
		// if (status != null) {
		// ret += " ";
		// ret += (status.length() > 50) ? status.substring(0, 47) + "..."
		// : status;
		// }
		return ret;
	}

	public Account getAccount() {
		return a;
	}

	@Override
	public Viewable[] getChildren() {
		return null;
	}

	@Override
	public Viewable getParent() {
		return a;
	}

	public PresenceType getPresenceType() {
		return presenceType;
	}

	@Override
	public int compareTo(Buddy_1 o) {
		Integer i = getNumberForPresenceType(presenceType);
		Integer j = getNumberForPresenceType(o.presenceType);
		int compare = i.compareTo(j);
		return (compare == 0) ? getName().toLowerCase().compareTo(
				o.getName().toLowerCase()) : compare;
	}

	private Integer getNumberForPresenceType(PresenceType p) {
		switch (p) {
		case AVAILABLE:
			return 1;
		case BUSY:
			return 2;
		case AWAY:
			return 3;
		case OFFLINE:
			return 4;
		}
		return null;
	}

	public void destrory() {
		a = null;
		c = null;
		p = null;
		entry = null;
		presenceType = null;
		name = null;
		status = null;
	}

}
