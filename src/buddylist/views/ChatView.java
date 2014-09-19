package buddylist.views;

import java.util.HashMap;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

import edu.cmu.scs.fluorite.plugin.Activator;

import buddylist.chat.Buddy_1;
import buddylist.chat.ChatViewController;
import buddylist.chat.ChatsView;

public class ChatView extends ViewPart implements ChatsView{


	CTabFolder tabFolder;
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.saffronr.chat4e.views.ChatView";

	ChatViewController chatViewer;
	HashMap<Buddy_1, CTabItem> chatWindows;
	DisposeListener disposeListener = new CloseWindowListener(this);
	Action logModeToggle = null;
	Action about = null;

	/**
	 * The constructor.
	 */
	public ChatView() {
		chatViewer = SWTChatViewController.getInstance();
		chatViewer.setChatView(this);
		chatWindows = new HashMap<Buddy_1, CTabItem>();
	}
	

	public void createPartControl(Composite parent) {
		tabFolder = new CTabFolder(parent, SWT.BORDER);
		IActionBars bars = getViewSite().getActionBars();
		logModeToggle = new Action() {
			@Override
			public void run() {
				if (SWTChatViewController.logmode) {
					logModeToggle.setText("Enable Log Mode");
					logModeToggle.setImageDescriptor(Activator
							.getImageDescriptor("icons/logmode.png"));
				} else {
					logModeToggle.setText("Disable Log Mode");
					logModeToggle.setImageDescriptor(Activator
							.getImageDescriptor("icons/logmode-off.png"));
				}
				SWTChatViewController.logmode = !(SWTChatViewController.logmode);
			}
		};
		logModeToggle.setImageDescriptor(Activator
				.getImageDescriptor("icons/logmode-off.png"));
		logModeToggle.setText("Disable Log Mode");
		logModeToggle.setToolTipText("Toggle Log Mode");
		bars.getMenuManager().add(logModeToggle);
		bars.getToolBarManager().add(logModeToggle);
		about = AboutDialog.getAboutAction(getSite().getShell());
		bars.getToolBarManager().add(about);

	}

	/**
	 * Passing the focus request to the form.
	 */
	public void setFocus() {
		CTabItem item = tabFolder.getSelection();
		if (item == null)
			return;
		item.getControl().setFocus();
	}

	@Override
	public void ensureWindowOpened(Buddy_1 b) {
		if (!chatWindows.containsKey(b)) {
			addChatWindow(b);
		}
	}

	private void addChatWindow(Buddy_1 b) {
		CTabItem item = new CTabItem(tabFolder, SWT.CLOSE);
		item.setText(b.getName());
		item.setControl(new ChatWindow(b, tabFolder, item, SWT.FILL));
		item.addDisposeListener(disposeListener);
		chatWindows.put(b, item);
		tabFolder.setSelection(item);
		tabFolder.setFocus();
	}

	public void closeChatWindow(Buddy_1 b) {
		CTabItem item = chatWindows.get(b);
		if (item == null)
			return;
		item.dispose();
		chatWindows.remove(b);
	}

	@Override
	public void displayChatInConversation(Buddy_1 b, String s) {
		ChatWindow w = (ChatWindow) chatWindows.get(b).getControl();
		// appened messes with the font :-/
		w.conversationText.setText(w.conversationText.getText() + s + w.conversationText.getLineDelimiter());
		w.conversationText.setSelection(w.conversationText.getText().length());
		if (tabFolder.getSelection() != chatWindows.get(b)) {
			w.unread++;
			chatWindows.get(b).setText("[" + w.unread + "] " + b.getName());
		}
		// }
	}

	@Override
	public void disableChat(Buddy_1 b, String error) {
		if (!chatWindows.containsKey(b))
			return;
		ChatWindow w = (ChatWindow) chatWindows.get(b).getControl();
		w.chatText.setEnabled(false);
		w.chatText.setText(error);
		w.chatText.setForeground(new Color(w.chatText.getDisplay(), new RGB(
				160, 160, 160)));
		w.redraw();
	}

	@Override
	public void reenableChat(Buddy_1 b, String notification) {
		if (!chatWindows.containsKey(b))
			return;
		ChatWindow w = (ChatWindow) chatWindows.get(b).getControl();
		w.chatText.setEnabled(true);
		w.chatText.setText(notification);
		w.chatText.setForeground(new Color(w.chatText.getDisplay(), new RGB(0,
				0, 0)));
		w.redraw();
	}

}
