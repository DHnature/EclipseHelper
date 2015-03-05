package buddylist.views;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;


import buddylist.chat.Account;
import buddylist.chat.AccountType;
import buddylist.chat.Buddy_1;
import buddylist.chat.ChatViewController;
import buddylist.chat.PresenceType;

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
public class SWTChatViewController extends buddylist.chat.ChatViewController {

	static ChatViewController chatViewerImpl;
	static SimpleDateFormat longDate = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	static SimpleDateFormat timeOnly = new SimpleDateFormat("hh:mm a");
	public static boolean logmode = true;

	public String reverseWords(String s) {
		String[] splits = s.split(" ");
		String rWord = "";
		for (int i = splits.length - 1; i >= 0; i--) {
			rWord += splits[i];
			if (i != 0)
				rWord += " ";
		}
		return rWord;

	}

	private String getFormattedTextForConversation(Buddy_1 b, String message,
			boolean send) {
		String s = "";
		if (logmode) {
			s = longDate.format(new Date())
					+ " ["
					+ (send ? b.getAccount().getAccountName().toUpperCase() : b
							.getName().toUpperCase()) + "] ";
			if (b.getAccount().getAccountType() == AccountType.ADD_BUDDY) {
				s += "com.facebook."
						+ (send ? "me"
								: reverseWords(b.getName().toLowerCase())
										.replace(' ', '.')) + ": ";
			} else {
				s += "com.google."
						+ (send ? "me" : b.getUserName().substring(0,
								b.getUserName().indexOf('@'))) + ": ";
			}
			s += message;
		} else {
			String shortName = (send ? "me"
					: (b.getName().indexOf(' ') == -1) ? b.getName() : b
							.getName().substring(0, b.getName().indexOf(' ')));
			s = shortName + " " + timeOnly.format(new Date()) + ": " + message;
		}
		return s;
	}

	public static ChatViewController getInstance() {
		if (chatViewerImpl == null) {
			chatViewerImpl = new SWTChatViewController();
		}
		return chatViewerImpl;
	}

	private SWTChatViewController() {

	}

	@Override
	public void recieveChat(final Buddy_1 b, final String s) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (s == null)
					return;
				try {
					if (PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage()
							.findView("buddylist.views.ChatView") == null)
						PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.getActivePage()
								.showView("buddylist.views.ChatView");
				} catch (PartInitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				chatView.ensureWindowOpened(b);
				String message = getFormattedTextForConversation(b, s, false);
				// chatView.ensureWindowOpened(b);
				chatView.displayChatInConversation(b, message);
				// PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				// .getActivePage().hideView((IViewPart) chatView);
			};
		});

	}

	@Override
	public void sendChat(final Buddy_1 b, final String s) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				String message;
				String myname = "";
				if (myname.indexOf('/') != -1)
					myname = myname.substring(0, myname.indexOf('/'));
				chatView.ensureWindowOpened(b);
				if (s == null)
					return;
				try {
					message = s;
				//	b.getAccount().sendChat(b, s);

					message = getFormattedTextForConversation(b, s, true);
				} catch (Exception e) {
					message = getFormattedTextForConversation(b, s, true)
							+ " -- cannot be sent due to network issue";
				}
				chatView.displayChatInConversation(b, message);
			};
		});

	}

	@Override
	public void changePresence(Account a, Buddy_1 b, PresenceType presence,
			String status) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				IViewPart part = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.findView("com.saffronr.chat4e.views.AccountsView");
				if (part != null) {
					accountsview.refresh();
				}
			}
		});
	}

	@Override
	public void addBuddy(Account a, Buddy_1 b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeBuddy(Account a, Buddy_1 b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBuddy(Account a, Buddy_1 b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void listBuddies(Account a) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayBuddy(Account a, Buddy_1 b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disableChat(final Account a, final String error) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				IViewPart part = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.findView("com.saffronr.chat4e.views.ChatView");
				if (part == null)
					return;
				for (Buddy_1 b : a.getBuddies().values()) {
					chatView.disableChat(b, error);
				}
				// PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				// .getActivePage().hideView((IViewPart) chatView);
			};
		});

	}

	@Override
	public void enableChat(final Account a, final String notification) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				IViewPart part = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.findView("com.saffronr.chat4e.views.ChatView");
				if (part == null)
					return;
				for (Buddy_1 b : a.getBuddies().values()) {
					chatView.reenableChat(b, notification);
				}
			};
		});

	}

}
