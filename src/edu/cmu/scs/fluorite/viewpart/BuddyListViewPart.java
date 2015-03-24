package edu.cmu.scs.fluorite.viewpart;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

import buddylist.chat.Buddy;
import buddylist.database.DatabaseUtils;
import buddylist.database.GetUserInformation;
import buddylist.database.Users;
import buddylist.views.AddAccountWizard;
import edu.cmu.scs.fluorite.plugin.Activator;

public class BuddyListViewPart extends ViewPart {
	private Table table;
	private Action loginAction;
	private Action addBuddyAction;
	private TableViewer tableViewer;
	List<Buddy> myBuddies = new Vector<Buddy>();
	public BuddyListViewPart() {
	}

	@Override
	public void createPartControl(Composite parent) {

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();

		// //Create the composite
		// Composite composite = new Composite(container, SWT.NONE);
		// composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
		// 1, 1));

		// Add TableColumnLayout
		TableColumnLayout layout = new TableColumnLayout();
		parent.setLayout(layout);

		// Instantiate TableViewer
		// TableViewer tableViewer = new TableViewer(parent, SWT.BORDER |
		// SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnUsername = tableViewerColumn.getColumn();
		// Specify width using pixels
		layout.setColumnData(tblclmnUsername, new ColumnPixelData(200, true,
				true));
		tblclmnUsername.setText("Username/Name");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnStatus = tableViewerColumn_1.getColumn();
		// Specify width using weights
		layout.setColumnData(tblclmnStatus, new ColumnWeightData(4,
				ColumnWeightData.MINIMUM_WIDTH, true));
		tblclmnStatus.setText("Difficulty Status");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnWeight4 = tableViewerColumn_2.getColumn();
		// Specify width using weights
		layout.setColumnData(tblclmnWeight4, new ColumnWeightData(4,
				ColumnWeightData.MINIMUM_WIDTH, true));
		tblclmnWeight4.setText("Most Frequent Recent Action");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnWeight6 = tableViewerColumn_3.getColumn();
		// Specify width using weights
		layout.setColumnData(tblclmnWeight6, new ColumnWeightData(6,
				ColumnWeightData.MINIMUM_WIDTH, true));
		tblclmnWeight6.setText("Last File Viewed");
		tableViewer.setLabelProvider(new BuddyListLabelProvider());
		tableViewer.setContentProvider(new BuddyListChatViewContentProvider());
		makeActions(parent);
		contributeToActionBars();
	}

	private void makeActions(final Composite composite) {

		this.loginAction = new Action() {
			public void run() {
				AddAccountWizard wizard = new AddAccountWizard(
						buddylist.chat.AccountType.CEC);
				WizardDialog wizardDialog = new WizardDialog(tableViewer
						.getControl().getShell(), wizard);
				if (wizardDialog.open() == Window.OK) {
					DatabaseUtils.setIsConnected(true);
					DatabaseUtils.setLoggedInUser(wizard.getUsername());
					Vector<Users> buddies = wizard.getBuddies();
					
					for(Users buddy : buddies)
					{
						Buddy b = new Buddy(buddy.getId(), buddy.getUsername(), buddy.getFirstName(), buddy.getLastName());
						myBuddies.add(b);
					}
					
					tableViewer.setInput(myBuddies);
					tableViewer.refresh();
					
					 TimerTask readStatusInformationFromDB  = new GetUserInformation(myBuddies);
					 
					 Timer timer = new Timer();
					 //300000
					 timer.schedule(readStatusInformationFromDB, 0,1000);

				} else {
					DatabaseUtils.setIsConnected(false);
				}
			}
		};

		this.loginAction.setToolTipText("Login");
		this.loginAction.setImageDescriptor(Activator
				.getImageDescriptor("icons/google.png"));

		this.addBuddyAction = new Action() {
			public void run() {
				if (DatabaseUtils.isConnected()) {
					AddAccountWizard wizard = new AddAccountWizard(
							buddylist.chat.AccountType.ADD_BUDDY);
					
					WizardDialog wizardDialog = new WizardDialog(tableViewer
							.getControl().getShell(), wizard);
					if (wizardDialog.open() == Window.OK) {
						Users u = wizard.getUser();
						Buddy b = new Buddy(u.getId(), u.getUsername(), u.getFirstName(), u.getLastName());
						myBuddies.add(b);
						tableViewer.add(b);
						tableViewer.refresh();
					} 
				}
			}
		};
		this.addBuddyAction.setToolTipText("Add Buddy");

	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(this.loginAction);
		manager.add(this.addBuddyAction);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
