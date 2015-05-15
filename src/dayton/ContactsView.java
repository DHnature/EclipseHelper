
package dayton;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

public class ContactsView extends ViewPart implements ContactChangeListener {

	private Table table;
	private Action accountSettingsAction;
	private TableViewer tableViewer;
	List<Contact> contacts = new ArrayList<Contact>();

	@Override
	public void createPartControl(Composite parent) {
		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		TableColumnLayout layout = new TableColumnLayout();
		parent.setLayout(layout);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableViewerColumn tableViewerColumn_0 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnUsername = tableViewerColumn_0.getColumn();
		// Specify width using pixels
		layout.setColumnData(tblclmnUsername, new ColumnPixelData(200, true,
				true));
		tblclmnUsername.setText("Name");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnStatus = tableViewerColumn_1.getColumn();
		// Specify width using weights
		layout.setColumnData(tblclmnStatus, new ColumnWeightData(4,
				ColumnWeightData.MINIMUM_WIDTH, true));
		tblclmnStatus.setText("Difficulty Status");

		tableViewer.setLabelProvider(new ContactsListLabelProvider());
		tableViewer.setContentProvider(new ContactContentProvider());
		makeAccountSettingsAction(parent);
		getViewSite().getActionBars().getToolBarManager().add(accountSettingsAction);
		ServerConnection.getServerConnection().addListener(this);
	}


	private void makeAccountSettingsAction(final Composite composite) {
		this.accountSettingsAction = new Action() {
			public void run() {
				AccountSettingsWizard wizard = new AccountSettingsWizard();
				WizardDialog wizardDialog = new WizardDialog(tableViewer
						.getControl().getShell(), wizard);
				if (wizardDialog.open() == Window.OK) {
					AccountSettings.getAccountSettings().updateSettings(wizard.getUsername(),wizard.getPassword(),
							wizard.getInstructorUsername(),wizard.getServer(),wizard.getServiceName(),wizard.getPort());
				}
			}
		};
		accountSettingsAction.setToolTipText("Account Settings");
	}

	@Override
	public void setFocus() {}


	@Override
	public void contactsChanged(List<Contact> contacts) {
		Display.getDefault().asyncExec(new UpdateTable(contacts));
	}
	
	class UpdateTable implements Runnable {
		List<Contact> contacts;
		public UpdateTable(List<Contact> contacts) {
			this.contacts = contacts;
		}
		public void run() {
			tableViewer.setInput(contacts);
			tableViewer.refresh();
		}
	}
}