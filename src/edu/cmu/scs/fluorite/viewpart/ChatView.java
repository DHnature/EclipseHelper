package edu.cmu.scs.fluorite.viewpart;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Pattern;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.internal.dialogs.ViewContentProvider;
import org.eclipse.ui.part.ViewPart;

import buddylist.chat.Buddy_1;
import buddylist.database.Chat;
import buddylist.database.DatabaseConnection;
import buddylist.database.DatabaseUtils;
import buddylist.database.Users;
import buddylist.views.AddAccountWizard;
import buddylist.views.ChatWindow;

import edu.cmu.scs.fluorite.commands.PredictionCommand;
import edu.cmu.scs.fluorite.plugin.Activator;
import edu.cmu.scs.fluorite.util.StringUtil;

//import com.gtalk.ui.LoginDialog;

public class ChatView extends ViewPart {

	public ChatView() {
	}

	public static final String ID = "com.gtalk.views.mainView";
	/*     */public Action loginAction;
	/*     */private Action logoutAction;
	/*     */private Action settingsAction;
	/*     */private Action removeAction;
	/*     */private Action clearAction;
	/*     */private Action doubleClickAction;
	/*     */private Action saveAction;
	/*     */private static CTabFolder tabFolder;
	/*     */private static TableViewer viewer;
	/*     */private static StyledText chatText;
	/*     */private static Text sendText;
	/*     */private static Combo statusCombo;
	/*     */private static StyledText historyText;

	static HashMap<String, CTabItem> chatWindows;

	static SendListener keyListener = new SendListener();

	/*     */
	/*     */public void createPartControl(Composite parent) {

		chatWindows = new HashMap<String, CTabItem>();

		/* 72 */SashForm sashForm = new SashForm(parent, 0);
		/*     */
		/* 74 */Composite composite = new Composite(sashForm, 0);
		/*     */
		/* 76 */GridLayout layout = new GridLayout();
		/* 77 */GridData gridData = new GridData();
		/* 78 */gridData.horizontalAlignment = 4;
		/* 79 */gridData.grabExcessHorizontalSpace = true;
		/*     */
		/* 81 */composite.setLayout(layout);
		/*     */
		/* 83 */statusCombo = new Combo(composite, 2060);
		/* 84 */statusCombo.setLayoutData(gridData);
		/*     */
		/* 86 */statusCombo.add(Constants.STATUS_AVAILABLE);
		/* 87 */statusCombo.add(Constants.STATUS_BUSY);
		/* 88 */statusCombo.add(Constants.STATUS_AWAY);
		statusCombo.add(Constants.STATUS_INVISIBLE);

		statusCombo.select(0);

		statusCombo.addSelectionListener(new SelectionListener() {
			/*     */public void widgetSelected(SelectionEvent e) {
				// /* 95 */ IPreferenceStore store =
				// GTalkPlugin.getDefault().getPreferenceStore();
				// /* 96 */
				// Util.setStatus(GTalkChatView.statusCombo.getSelectionIndex(),
				// store.getString(Constants.STATUS_MSG));
				// /* 97 */ store.setValue(Constants.STATUS,
				// GTalkChatView.statusCombo.getSelectionIndex());
				/*     */}

			/*     */
			/*     */public void widgetDefaultSelected(SelectionEvent e)
			/*     */{
				/*     */}
			/*     */
		});

		/* 77 */gridData = new GridData();
		/* 78 */gridData.horizontalAlignment = 4;
		/* 79 */gridData.grabExcessHorizontalSpace = true;
		/* 104 */final Text searchText = new Text(composite, 2176);
		/* 105 */searchText.setLayoutData(gridData);
		// searchText.setText(GTalkPlugin.getString("MainChatView.findFriend"));
		/*     */
		/* 108 */// final ViewFilter filter = new ViewFilter();
		/*     */
		/* 110 */searchText.addFocusListener(new FocusListener() {
			/*     */public void focusLost(FocusEvent e) {
				// /* 112 */
				// searchText.setText(GTalkPlugin.getString("MainChatView.findFriend"));
				// /* 113 */ filter.setSearchText("");
				// /* 114 */ GTalkChatView.viewer.refresh();
				/*     */}

			/*     */
			/*     */public void focusGained(FocusEvent e) {
				/* 118 */searchText.setText("");
				/*     */}
			/*     */
		});
		/* 122 */searchText.addKeyListener(new KeyListener() {
			/*     */public void keyReleased(KeyEvent e) {
				// /* 124 */ filter.setSearchText(searchText.getText());
				// /* 125 */ GTalkChatView.viewer.refresh();
				/*     */}

			/*     */
			/*     */public void keyPressed(KeyEvent e)
			/*     */{
				/*     */}
			/*     */
		});
		/* 132 */gridData = new GridData();
		/* 133 */gridData.horizontalAlignment = 4;
		/* 134 */gridData.verticalAlignment = 4;
		/* 135 */gridData.grabExcessHorizontalSpace = true;
		/* 136 */gridData.grabExcessVerticalSpace = true;

		/* 138 */Table table = new Table(composite, 68354);
		/* 139 */table.setLayoutData(gridData);
		/*     */
		/* 141 */Font font = new Font(table.getDisplay(),
				Constants.FONT_ARIAL_9);
		/* 142 */table.setFont(font);

		/* 144 */tabFolder = new CTabFolder(sashForm, 2048);
		/*     */
		/* 146 */tabFolder.setSimple(false);
		/* 147 */tabFolder.setUnselectedCloseVisible(false);

		tabFolder.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				CTabItem item = (CTabItem) e.item;
				Font font = new Font(item.getDisplay(), Constants.FONT_ARIAL_9);
				item.setFont(font);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}

		});
		/* 160 */sashForm.setWeights(new int[] { 25, 75 });
		/*     */
		viewer = new TableViewer(table);
		viewer.setContentProvider(new ChatViewContentProvider());
		viewer.setLabelProvider(new ChatViewLabelProvider());
		/* 165 */// viewer.setSorter(new ViewNameSorter());
		/* 166 */viewer.setInput(getViewSite());
		/*     */
		/* 168 */// viewer.addFilter(filter);

		makeActions(parent);
		/* 171 */hookDoubleClickAction();
		/* 172 */contributeToActionBars();
		// GTalkPlugin.getString("MainChatView.firstTab")
		createDefaultTabItem("dashboard");
		/*     */}

	/*     */
	/*     */private void contributeToActionBars() {
		/* 177 */IActionBars bars = getViewSite().getActionBars();
		/* 178 */fillLocalToolBar(bars.getToolBarManager());
		/*     */}

	/*     */
	/*     */private void fillLocalToolBar(IToolBarManager manager) {
		// /* 182 */ manager.add(this.removeAction);
		// /* 183 */ manager.add(this.clearAction);
		// /* 184 */ manager.add(new Separator());
		// /* 185 */ manager.add(this.saveAction);
		// /* 186 */ manager.add(new Separator());
		// /* 187 */ manager.add(this.settingsAction);
		// /* 188 */ manager.add(new Separator());
		/* 189 */manager.add(this.loginAction);
		// /* 190 */ manager.add(this.logoutAction);
		// /* 191 */ manager.add(new Separator());
		/*     */}

	/*     */
	/*     */private void makeActions(final Composite composite)
	/*     */{
		/* 196 */this.removeAction = new Action() {
			/*     */public void run() {
				/* 198 */// GTalkChatView.this.closeAllTabs();
				/*     */}
			/*     */
		};
		// /* 202 */
		// this.removeAction.setToolTipText(GTalkPlugin.getString("MainChatView.removeTabs"));
		// /* 203 */ GTalkPlugin.getDefault();
		// this.removeAction.setImageDescriptor(GTalkPlugin.getImageDescriptor("IMG_ELCL_REMOVEALL"));
		/*     */
		/* 205 */this.clearAction = new Action() {
			/*     */public void run() {

				// /* 207 */ if (ChatView.tabFolder.getSelection() != null)
				// /* 208 */ if
				// (ChatView.tabFolder.getSelection().getText().equals(ChatView.getString("MainChatView.firstTab")))
				// /* 209 */ ChatView.historyText.setText("");
				// /* */ else
				// /* 211 */
				// ((StyledText)ChatView.getChatTextArea(ChatView.tabFolder.getSelection().getText(),
				// 1)).setText("");
				/*     */}
			/*     */
		};
		// /* 217 */
		// this.clearAction.setToolTipText(GTalkPlugin.getString("MainChatView.clearText"));
		// /* 218 */ GTalkPlugin.getDefault();
		// this.clearAction.setImageDescriptor(GTalkPlugin.getImageDescriptor("IMG_ETOOL_CLEAR"));
		/*     */
		/* 220 */this.saveAction = new Action() {
			/*     */public void run() {
				/* 222 */if (ChatView.tabFolder.getSelection() != null) {
					/* 223 */String chatText = ((StyledText) ChatView
							.getChatTextArea(ChatView.tabFolder.getSelection()
									.getText(), 1)).getText();
					/*     */
					/* 225 */if (chatText.length() <= 0) {
						/* 226 */MessageDialog.openInformation(
								ChatView.tabFolder.getShell(), "Save",
								"There is nothing to save in "
										+ ChatView.tabFolder.getSelection()
												.getText() + " chat window.");
						/* 227 */return;
						/*     */}
					/*     */
					/* 230 */DateFormat formatter = new SimpleDateFormat(
							"dd-MMM-yyyy-hh-mm");
					/* 231 */FileDialog fd = new FileDialog(
							ChatView.tabFolder.getShell(), 8192);
					/* 232 */fd.setText("Save");
					/* 233 */fd.setFilterPath("C:/");
					/* 234 */fd.setFilterExtensions(new String[] { "*.txt" });
					/* 235 */fd.setFileName(ChatView.tabFolder.getSelection()
							.getText()
							+ "-"
							+ formatter.format(new Date())
							+ ".txt");
					/*     */
					/* 237 */String selected = fd.open();
					/* 238 */if (selected != null)
						/*     */try {
							/* 240 */FileWriter fstream = new FileWriter(
									selected);
							/* 241 */BufferedWriter out = new BufferedWriter(
									fstream);
							/* 242 */out.write(chatText);
							/* 243 */out.close();
							/*     */} catch (IOException e) {
							/* 245 */e.printStackTrace();
							/*     */}
					/*     */}
				/*     */}
			/*     */
		};
		// /* 252 */
		// this.saveAction.setToolTipText(GTalkPlugin.getString("MainChatView.saveText"));
		// /* 253 */ GTalkPlugin.getDefault();
		// this.saveAction.setImageDescriptor(GTalkPlugin.getImageDescriptor("IMG_ETOOL_SAVE_EDIT"));
		/*     */
		// /* 255 */ final LoginDialog ld = new
		// LoginDialog(composite.getShell());
		/* 256 */this.loginAction = new Action() {
			public void run() {

				AddAccountWizard wizard = new AddAccountWizard(
						buddylist.chat.AccountType.CEC);
				WizardDialog wizardDialog = new WizardDialog(viewer
						.getControl().getShell(), wizard);
				if (wizardDialog.open() == Window.OK) {

					DatabaseUtils.setIsConnected(true);
					DatabaseUtils.setLoggedInUser(wizard.getUsername());
					viewer.refresh();

					// here is where i need to create a thread that checks the
					// database for messages from other users
					Runnable getChatMessages = new Runnable() {

						@Override
						public void run() {
							if (DatabaseUtils.isConnected()) {
								final Vector<Chat> messages = DatabaseConnection
										.getUserMessages(DatabaseUtils
												.getLoggedInUser());

								PlatformUI.getWorkbench().getDisplay()
										.asyncExec(new Runnable() {
											@Override
											public void run() {
												for (int i = 0; i < messages
														.size(); i++) {
													
													
													CTabFolder tabFolder = ChatView.getTabFolder();
													CTabItem[] tabItems = tabFolder.getItems();
													boolean isTabOpen = false;
													for(int tabItemIndex = 0; tabItemIndex < tabItems.length; tabItemIndex++)
													{
														if(tabItems[tabItemIndex].getText().equalsIgnoreCase(messages.get(i)
																.getFromUser()))
														{
															isTabOpen = true;
															StyledText chatText = (StyledText) ChatView
																	.getChatTextArea(tabFolder.getSelection().getText(), 1);
														}
													}
													
													//if the tab is not open
													if(!isTabOpen)
													{
														createTabItem(messages.get(i)
																.getFromUser());
														StyledText chatText = (StyledText) ChatView
																.getChatTextArea(messages.get(i)
																		.getFromUser(), 1);
													}
													//determine if their is a tab open for the current user
													//if there is a tab open for the current user
														//get the chat area and add text
													//else
														//create a tab for the user
														//get the chat area and add text
													
													//notify the user that they received a chat message, if this is the first message
													// addToHistory(messages.get(i).getFromUser(),);
													StyledText chatText = (StyledText) getChatTextArea(
															messages.get(i)
																	.getFromUser(),
															1);
													chatText.append(messages
															.get(i).getText());
												}
											}
										});

							}

						}

					};
					Thread logThread = new Thread(getChatMessages);
					logThread.start();
				} else {
					DatabaseUtils.setIsConnected(false);
				}
				ChatView.this.updateTitle();
			}

		};

		this.loginAction.setToolTipText("Login");
		// /* 263 */
		// this.loginAction.setToolTipText(GTalkPlugin.getString("MainChatView.loginDesc"));
		// /* 264 */ GTalkPlugin.getDefault();
		this.loginAction.setImageDescriptor(Activator
				.getImageDescriptor("icons/login.jpeg"));
		// /* */
		/* 266 */this.logoutAction = new Action()
		/*     */{
			/*     */public void run() {
				/* 269 */Util.disconnect();
				ChatView.this.loginAction.setEnabled(true);
				ChatView.this.closeAllTabs();
				ChatView.viewer.refresh();
				ChatView.this.setTitle("");
				ChatView.this.loginAction.setEnabled(true);
				// GTalkChatView.this.closeAllTabs();
				// GTalkChatView.viewer.refresh();
				// GTalkChatView.this.setTitle("");
				/*     */}
			/*     */
		};

		// /* 277 */
		// this.logoutAction.setToolTipText(GTalkPlugin.getString("MainChatView.logoutDesc"));
		// /* 278 */ GTalkPlugin.getDefault();
		// this.logoutAction.setImageDescriptor(GTalkPlugin.getImageDescriptor(Constants.ICON_DISCONNECT));
		/*     */
		/* 280 */this.doubleClickAction = new Action() {
			public void run() {
				ISelection selection = ChatView.viewer.getSelection();
				Object obj = ((IStructuredSelection) selection)
						.getFirstElement();

				String currentUser = DatabaseUtils.getLoggedInUser();

				String fullName = obj.toString();
				String userName = "";

				userName = StringUtil.removeUserName(fullName);

				if (userName.equals(""))
					return;
				else {

					if (userName.equalsIgnoreCase(currentUser)) {
						return;
					}
					if (userName.equalsIgnoreCase(Constants.ROOT_FOLDER)) {
						return;
					}
					createTabItem(obj.toString());
				}
			}
		};
		/* 297 */this.settingsAction = new Action() {
			/*     */public void run() {
				/* 299 */Shell shell = composite.getShell();
				/* 300 */PreferenceDialog dialog = PreferencesUtil
						.createPreferenceDialogOn(shell,
								Constants.PREFERENCES_PAGE_ID, null, null);
				/* 301 */dialog.open();
				/*     */}
			/*     */
		};
		// /* 305 */
		// this.settingsAction.setToolTipText(GTalkPlugin.getString("MainChatView.prefDesc"));
		// /* 306 */ GTalkPlugin.getDefault();
		// this.settingsAction.setImageDescriptor(GTalkPlugin.getImageDescriptor(Constants.ICON_SETTINGS));
		/*     */}

	/*     */
	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ChatView.this.doubleClickAction.run();
			}
		});
	}

	public static void refreshViewer() {
		viewer.refresh();
	}

	/*     */
	/*     */public void setFocus() {
		/*     */}

	/*     */
	/*     */public static Image getImage(String title) {
		/* 325 */TableItem[] tabItems = viewer.getTable().getItems();
		/* 326 */Image img = null;
		/*     */
		/* 328 */for (TableItem tabItem : tabItems) {
			/* 329 */String text = tabItem.getText();
			/* 330 */int len = text.indexOf(Constants.CHAR_OPEN_ROUND_BRACKET);
			/*     */
			/* 332 */if (len != -1) {
				/* 333 */text = text.substring(0, len);
				/*     */}
			/* 335 */if (text.equals(title))
				/* 336 */img = tabItem.getImage();
			/*     */}
		/* 338 */return img;
		/*     */}

	public void displayChatInConversation(String title, String s) {
		// ChatWindow w = (ChatWindow) chatWindows.get(b).getControl();
		// appened messes with the font :-/
		// w.conversationText.setText(w.conversationText.getText() + s +
		// w.conversationText.getLineDelimiter());
		// w.conversationText.setSelection(w.conversationText.getText().length());
		// if (tabFolder.getSelection() != chatWindows.get(b)) {
		// w.unread++;
		// chatWindows.get(b).setText("[" + w.unread + "] " + b.getName());
		// }
		// }
	}

	private static void createTabItem(String title) {
		Color color = new Color(tabFolder.getDisplay(),
				Constants.COLOR_R_SENDER, Constants.COLOR_G_SENDER,
				Constants.COLOR_B_SENDER);

		TableItem[] tabItems = viewer.getTable().getItems();
		Image img = null;

		for (TableItem tabItem : tabItems) {
			if (tabItem.getText().equals(title)) {
				img = tabItem.getImage();
			}
		}
		CTabItem[] items = tabFolder.getItems();

		int len = title.indexOf(Constants.CHAR_OPEN_ROUND_BRACKET);

		if (len != -1) {
			// title = title.substring(0, len);
			for (CTabItem item : items) {
				if (item.getText().equals(title)) {
					item.setImage(img);
					tabFolder.setSelection(item);
					return;
				}
			}

			CTabItem tabItem = new CTabItem(tabFolder, 0);
			tabItem.setText(title);

			if (img != null) {
				tabItem.setImage(img);
			}
			Font font = new Font(tabItem.getDisplay(), Constants.FONT_ARIAL_9);
			tabItem.setFont(font);

			SashForm sashForm = new SashForm(tabFolder, 512);
			tabItem.setControl(sashForm);
			tabItem.setShowClose(true);

			chatText = new StyledText(sashForm, 2634);

			chatText.setBackground(color);

			sendText = new Text(sashForm, 2048);
			sendText.setEditable(true);
			sendText.setFocus();
			sendText.addKeyListener(keyListener);
			sashForm.setWeights(new int[] { 80, 20 });

			chatText.setFont(font);
			sendText.setFont(font);

			tabFolder.setSelection(tabItem);
			// chatWindows.put(title, tabFolder);
		}
	}

	/*     */
	/*     */private static void createDefaultTabItem(String title) {
		/* 402 */CTabItem tabItem = new CTabItem(tabFolder, 0);
		/* 403 */tabItem.setText(title);
		/*     */
		/* 405 */Color color = new Color(tabFolder.getDisplay(),
				Constants.COLOR_R_SENDER, Constants.COLOR_G_SENDER,
				Constants.COLOR_B_SENDER);
		/*     */
		/* 407 */Font font = new Font(tabItem.getDisplay(),
				Constants.FONT_ARIAL_9);
		/* 408 */tabItem.setFont(font);
		/*     */
		/* 410 */historyText = new StyledText(tabFolder, 578);
		/* 411 */historyText.setBackground(color);
		/* 412 */historyText.setEditable(false);
		/*     */
		/* 414 */historyText.setFont(font);
		/*     */
		/* 416 */tabItem.setControl(historyText);
		/* 417 */tabItem.setShowClose(false);
		/*     */
		/* 419 */tabFolder.setSelection(tabItem);
		/*     */}

	/*     */
	/*     */public static Control getChatTextArea(String title, int controlType)
	/*     */{
		CTabItem[] items = tabFolder.getItems();
		for (CTabItem item : items) {
			/* 433 */if (item.getText().equals(title)) {
				// /* 434 */ if
				// (title.equals(GTalkPlugin.getString("MainChatView.firstTab")))
				// {
				// /* 435 */ return item.getControl();
				// /* */ }
				/* 437 */SashForm sf = (SashForm) item.getControl();
				/* 438 */Control[] controls = sf.getTabList();
				/* 439 */for (Control control : controls) {
					/* 440 */if (((control instanceof StyledText))
							&& (controlType == 1))
						/* 441 */return control;
					/* 442 */if (((control instanceof Text))
							&& (controlType == 2))
						/* 443 */return control;
					/*     */}
				/*     */}
			/*     */}
		/* 447 */createTabItem(title);
		/* 448 */return getChatTextArea(title, controlType);
		/*     */}

	/*     */
	/*     */public void closeAllTabs()
	/*     */{
		/* 455 */CTabItem[] items = tabFolder.getItems();
		/* 456 */for (int i = 1; i < items.length; i++) {
			/* 457 */CTabItem item = items[i];
			/* 458 */item.dispose();
			/*     */}
		/*     */}

	/*     */
	/*     */public void updateTitle()
	/*     */{
		/* 467 */if (!Util.isConnected()) {
			/* 468 */return;
			/*     */}
		/* 470 */IPreferenceStore store = null;// GTalkPlugin.getDefault().getPreferenceStore();
		/*     */
		/* 472 */StringBuffer title = new StringBuffer();
		/* 473 */title.append("Logged in as: " + Util.getCurrentUser());
		/*     */
		/* 475 */if (store.getString(Constants.STATUS_MSG).length() != 0) {
			/* 476 */title.append(" | Status Message: "
					+ store.getString(Constants.STATUS_MSG));
			/*     */}
		/* 478 */if (store.getBoolean(Constants.OFFLINE_FRIENDS))
			/* 479 */title.append(" | Showing offline friends");
		/*     */else {
			/* 481 */title.append(" | Hiding offline friends ");
			/*     */}
		/* 483 */if (store.getBoolean(Constants.AUTOREPLY))
			/* 484 */title.append(" | Auto reply enabled ("
					+ store.getString(Constants.AUTOREPLY_MSG) + ")");
		/*     */else {
			/* 486 */title.append(" | Auto reply disabled");
			/*     */}
		/* 488 */setTitle(title.toString());
		/*     */}

	/*     */
	/*     */public static CTabItem getTabItem(String title)
	/*     */{
		/* 499 */CTabItem[] items = tabFolder.getItems();
		/*     */
		/* 501 */for (CTabItem item : items) {
			/* 502 */if (item.getText().equals(title)) {
				// /* 503 */ item.setImage(getImage(title));
				/* 504 */return item;
				/*     */}
			/*     */}
		/* 507 */createTabItem(title);
		/* 508 */return getTabItem(title);
		/*     */}

	/*     */
	/*     */public static CTabItem getTabItem(String title, boolean create)
	/*     */{
		/* 520 */if (create) {
			/* 521 */return getTabItem(title);
			/*     */}
		/* 523 */CTabItem[] items = tabFolder.getItems();
		/*     */
		/* 525 */for (CTabItem item : items) {
			/* 526 */if (item.getText().equals(title)) {
				/* 527 */return item;
				/*     */}
			/*     */}
		/* 530 */return null;
		/*     */}

	/*     */
	/*     */public static void updateCombo(int status)
	/*     */{
		/* 552 */statusCombo.select(status);
		/*     */}

	/*     */
	/*     */public static void addToHistory(String user, String message) {
		/* 556 */String time = DateFormat.getTimeInstance(2).format(new Date());
		/*     */
		/* 558 */historyText.append(Constants.CHAR_OPEN_BRACKET + time
				+ Constants.CHAR_CLOSE_BRACKET + Constants.COLON);
		/*     */
		/* 560 */int start = historyText.getText().length();
		/*     */
		/* 562 */historyText.append(user + " ");
		/* 563 */historyText.append(message);
		/* 564 */historyText.append(Constants.NEW_LINE);
		/*     */
		/* 566 */historyText.setStyleRange(Util.getStyleRange(start,
				user.length()));
		/*     */
		/* 570 */int top = historyText.getTopIndex();
		/* 571 */int bottom = historyText.getBottomMargin();
		/*     */
		/* 573 */if (start < top + 1)
			/* 574 */historyText.setTopIndex(start - 1 > 0 ? start - 1 : 0);
		/* 575 */else if (historyText.getText().length() > bottom - 1)
			/* 576 */historyText.setTopIndex(top + start - bottom + 1);
		/*     */}

	/*     */
	/*     */public static CTabFolder getTabFolder()
	/*     */{
		/* 582 */return tabFolder;
		/*     */}

}
