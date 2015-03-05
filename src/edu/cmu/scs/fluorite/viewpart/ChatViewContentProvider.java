package edu.cmu.scs.fluorite.viewpart;

// import com.gtalk.GTalkPlugin;

import edu.cmu.scs.fluorite.viewpart.Constants;
import edu.cmu.scs.fluorite.viewpart.Util;

/*    */
import java.util.Collection;
import java.util.Vector;
/*    */
import java.util.Iterator;

/*    */
import org.eclipse.jface.preference.IPreferenceStore;
/*    */
import org.eclipse.jface.viewers.IStructuredContentProvider;
/*    */
import org.eclipse.jface.viewers.Viewer;
/*    */
import org.jivesoftware.smack.RosterEntry;
/*    */
import org.jivesoftware.smack.packet.Presence;
/*    */
import org.jivesoftware.smack.util.StringUtils;

import buddylist.database.DatabaseConnection;
import buddylist.database.DatabaseUtils;
import buddylist.database.Users;

/*    */
/*    */public class ChatViewContentProvider
/*    */implements IStructuredContentProvider
/*    */{
	/*    */public void dispose()
	/*    */{
		/*    */}

	/*    */
	/*    */public void inputChanged(Viewer viewer, Object oldInput,
			Object newInput)
	/*    */{
		/*    */}

	/*    */
	/*    */public Object[] getElements(Object inputElement)
	/*    */{

		if (DatabaseUtils.isConnected()) {
			Vector<Users> users = DatabaseConnection.getAllUsers();
			String[] name = new String[users.size() - 1];
			int i = 0;

			if (DatabaseUtils.getLoggedInUser().equals("joayers")) {
				name = new String[1];
				name[0] = "Jason Carter" + Constants.CHAR_OPEN_ROUND_BRACKET + "jasocart)";

			} else if (DatabaseUtils.getLoggedInUser().equals("jasocart")) {
				name = new String[users.size() - 1];
				for (Users user : users) {
					if (user.getUsername().equals(
							DatabaseUtils.getLoggedInUser())) {
					}
					else
					{
						name[i] = user.getFirstName() + " "
								+ user.getLastName() + Constants.CHAR_OPEN_ROUND_BRACKET + user.getUsername() + ")";
						i++;
					}
				}
			} else {
				name = new String[users.size() - 2];
				for (Users user : users) {

					if ((user.getUsername().equals(DatabaseUtils
							.getLoggedInUser()))
							|| (user.getUsername().equals("joayers"))) {

					} else {
						name[i] = user.getFirstName() + " "
								+ user.getLastName() + Constants. CHAR_OPEN_ROUND_BRACKET + user.getUsername() + ")";
						i++;
					}
				}
			}

			return name;
		}
		return new Object[0];

		// Vector<Employee> myVector = new Vector<Employee>();
		// for (Employee person : myVector)
		// {
		// System.out.println(person.getName());
		// }
		// if (Util.isConnected()) {
		// IPreferenceStore store = null; //
		// GTalkPlugin.getDefault().getPreferenceStore();
		//
		// /* 29 */Collection buddies = Util.getBuddies(store
		// .getBoolean(Constants.OFFLINE_FRIENDS));
		// /* 30 */String[] s = new String[buddies.size()];
		// /* */
		// /* 32 */Iterator itr = buddies.iterator();
		// /* 33 */int i = 0;
		// /* 34 */while (itr.hasNext()) {
		// /* 35 */RosterEntry re = (RosterEntry) itr.next();
		// /* 36 */String name = re.getName();
		// /* */
		// /* 38 */if (name == null) {
		// /* 39 */name = StringUtils.parseName(re.getUser());
		// /* */}
		// /* 41 */Presence p = Util.getRoster().getPresence(re.getUser());
		// /* */
		// /* 43 */if ((p != null) && (p.getStatus() != null)
		// && (p.getStatus() != "")) {
		// /* 44 */name = name + Constants.CHAR_OPEN_ROUND_BRACKET
		// + p.getStatus()
		// + Constants.CHAR_CLOSE_ROUND_BRACKET;
		// /* */}
		// /* 46 */if (store.getBoolean(Constants.SHOW_CHAT_CLIENT)) {
		// /* 47 */name = name
		// + Util.getChatClient(StringUtils.parseResource(p
		// .getFrom()));
		// /* */}
		// /* 49 */s[(i++)] = name;
		// /* */}
		// /* */
		// /* 52 */return s;
		// /* */}
		// /* 54 */return new Object[0];
		/*    */}
	/*    */
}
