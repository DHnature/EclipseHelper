package buddylist.views;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import buddylist.chat.Buddy_1;
import buddylist.chat.PresenceType;

class BuddyViewFilter extends ViewerFilter {

	static boolean showOfflineFriends = false;
	static String searchPattern = "";

	@Override
	public boolean select(Viewer viewer, Object parent, Object element) {

		if (searchPattern != null && !searchPattern.equals("")) {
			if (element instanceof Buddy_1) {
				return (((Buddy_1) element).getName().toLowerCase().contains(searchPattern.toLowerCase()) || ((Buddy_1) element)
						.getUserName().toLowerCase().contains(searchPattern.toLowerCase())) ? true : false;
			}
		}
		if (!showOfflineFriends)
			if (element instanceof Buddy_1) {
				return ((Buddy_1) element).getPresenceType() == PresenceType.OFFLINE ? false
						: true;
			}
		return true;

	}
}