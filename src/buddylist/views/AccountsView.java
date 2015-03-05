package buddylist.views;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.jivesoftware.smack.XMPPException;

import edu.cmu.scs.fluorite.plugin.Activator;

import buddylist.chat.Account;
import buddylist.chat.AccountsManager;
import buddylist.chat.Buddy_1;
import buddylist.chat.ChatViewController;

public class AccountsView extends ViewPart implements buddylist.chat.AccountsView  {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "buddylist.views.AccountsView";
	private TreeViewer viewer;
	private Action addFacebookAccount;
	private Action addGoogleAccount;
	private Action deleteAccount;
	private Action takeOffline;
	public static Action about;
	private Action expandTree;
	private Action startChat;
	ViewContentProvider contentProvider;
	private Text searchBox;
	ChatViewController chatvieweController = SWTChatViewController
			.getInstance();
	private Action takeOnline;
	private Action toogleOfflineFriends;

	/**
	 * The constructor.
	 */
	public AccountsView() {
		chatvieweController.setAccountsview(this);
	}

	private void initialize() {

		contentProvider = new ViewContentProvider(getViewSite());
		contentProvider.setRoot(AccountsManager.getInstance());
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {

		parent.setLayout(new GridLayout(1, true));
		searchBox = new Text(parent, SWT.SEARCH | SWT.ICON_CANCEL
				| SWT.ICON_SEARCH);
		searchBox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.V_SCROLL);
		viewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		viewer.setFilters(new ViewerFilter[] { new BuddyViewFilter() });
		initialize();
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(getViewSite());
		// // Create the help context id for the viewer's control
		// PlatformUI.getWorkbench().getHelpSystem()
		// .setHelp(viewer.getControl(), "com.saffronr.chat4e.viewer");
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				AccountsView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		//manager.add(addFacebookAccount);
		//manager.add(new Separator());
		manager.add(addGoogleAccount);
	}

	private void fillContextMenu(IMenuManager manager) {

		if (viewer.getSelection().isEmpty()) {
			return;
		}
		if (viewer.getSelection() instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) viewer
					.getSelection();
			Object object = selection.getFirstElement();
			if (object instanceof Account) {
				Account a = (Account) object;
				if (a.isOnline())
					manager.add(takeOffline);
				else
					manager.add(takeOnline);
				manager.add(deleteAccount);
			}
		}

		// manager.add(new Separator());
		// drillDownAdapter.addNavigationActions(manager);
		// // Other plug-ins can contribute there actions here
		// manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		//manager.add(toogleOfflineFriends);
		//manager.add(addFacebookAccount);
		manager.add(addGoogleAccount);
		//manager.add(about);

		// drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		addFacebookAccount = new Action() {
			@Override
			public void run() {

				AddAccountWizard wizard = new AddAccountWizard(
						buddylist.chat.AccountType.ADD_BUDDY);
				WizardDialog wizardDialog = new WizardDialog(viewer
						.getControl().getShell(), wizard);
				if (wizardDialog.open() == Window.OK) {
					viewer.refresh();
				}
			}
		};
		addFacebookAccount.setText("Add a facebook account");
		addFacebookAccount.setToolTipText("Add a fecbook account");
		System.out.println(new File(".").getAbsolutePath() + "<<<<<< PWD");
//		.setImageDescriptor(Activator
//				.getImageDescriptor("icons/facebook-blue.png"));
		
		
		
		addGoogleAccount = new Action() {
			@Override
			public void run() {

				AddAccountWizard wizard = new AddAccountWizard(
						buddylist.chat.AccountType.CEC);
				WizardDialog wizardDialog = new WizardDialog(viewer
						.getControl().getShell(), wizard);
				if (wizardDialog.open() == Window.OK) {
					viewer.refresh();
				}
			}
		};
		addGoogleAccount.setText("Sign in with CEC username");
		addGoogleAccount.setToolTipText("Sign in with CEC username");
//		addGoogleAccount.setImageDescriptor(Activator
//				.getImageDescriptor("icons/google-red.png"));
//		addGoogleAccount.setHoverImageDescriptor(Activator
//				.getImageDescriptor("icons/google.png"));

		deleteAccount = new Action() {
			@Override
			public void run() {
				Account a = ((Account) ((TreeSelection) viewer.getSelection())
						.getFirstElement());
				a.offline();
				a.releaseBuddies();
				AccountsManager.getInstance().deleteAccount(a);
			}
		};
		deleteAccount.setText("Delete this account");
		deleteAccount.setToolTipText("Delete this account");
		deleteAccount.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_ETOOL_DELETE));

		takeOffline = new Action() {
			@Override
			public void run() {
				Account a = ((Account) ((TreeSelection) viewer.getSelection())
						.getFirstElement());
				a.offline();
				ChatView cv = (ChatView) PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.findView("com.saffronr.chat4e.views.ChatView");
				if (cv != null) {
					for (Buddy_1 b : a.getBuddies().values()) {
						cv.closeChatWindow(b);
					}
					cv.tabFolder.redraw();
				}
				a.releaseBuddies();
				viewer.refresh();

			}
		};

		takeOffline.setText("Sign out");
		takeOffline.setToolTipText("Sign out");
		takeOffline.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_ELCL_SYNCED_DISABLED));

		takeOnline = new Action() {
			@Override
			public void run() {
				Account a = ((Account) ((TreeSelection) viewer.getSelection())
						.getFirstElement());
				try {
					a.online();
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				viewer.refresh();
			}
		};
		takeOnline.setText("Sign in");
		takeOnline.setToolTipText("Sign in");
		takeOnline.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_ELCL_SYNCED));

		expandTree = new ExpandTreeOnDoubleClick(viewer);
		startChat = new Action() {
			@Override
			public void run() {

				// Initialize the ChatView, if it has not been opened yet.!
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage()
							.showView("buddylist.views.ChatView");
				} catch (PartInitException e) {
					e.printStackTrace();
				}

				Buddy_1 b = (Buddy_1) ((TreeSelection) (viewer.getSelection()))
						.getFirstElement();
				chatvieweController.sendChat(b, null);
			}
		};
		
		toogleOfflineFriends = new Action() {
			@Override
			public void run() {
				boolean oldValue = BuddyViewFilter.showOfflineFriends;
				BuddyViewFilter.showOfflineFriends = !oldValue;
				if (oldValue)
					toogleOfflineFriends
							.setImageDescriptor(Activator
									.getImageDescriptor("icons/show-offline.png"));
				else
					toogleOfflineFriends.setImageDescriptor(Activator
							.getImageDescriptor("icons/do-not-show-offline.png"));

				viewer.refresh();
			}
		};
		if (!BuddyViewFilter.showOfflineFriends)
			toogleOfflineFriends.setImageDescriptor(Activator
					.getImageDescriptor("icons/show-offline.png"));
		else
			toogleOfflineFriends.setImageDescriptor(Activator
					.getImageDescriptor("icons/do-not-show-offline.png"));
		toogleOfflineFriends.setText("Toggle Visibility of Offline Friends");
		toogleOfflineFriends
				.setToolTipText("Toggle Visibility of Offline Friends");
		about = AboutDialog.getAboutAction(getSite().getShell());

		searchBox.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {

			}

			@Override
			public void focusGained(FocusEvent arg0) {
				searchBox.setSelection(0, searchBox.getText().length());
				searchBox.redraw();
			}
		});
		
		searchBox.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				BuddyViewFilter.searchPattern = searchBox.getText();
				viewer.refresh(true);
			}
		});

	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				if (((TreeSelection) event.getSelection()).getFirstElement() instanceof Account)
					expandTree.run();
				else
					startChat.run();
			}
		});
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	@Override
	public void refresh() {
		viewer.refresh(true);
	}

}
