package buddylist.views;

import java.util.Vector;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
//import chat.Activator;
import buddylist.chat.AccountType;

import buddylist.database.DatabaseConnection;
import buddylist.database.DatabaseUtils;
import buddylist.database.Users;
//import org.jivesoftware.smack.XMPPException;

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
public class AddAccountWizard extends Wizard {

	String username, password;
	AccountType type;
	private Vector<Users> buddies = new Vector<Users>();
	public Vector<Users> getBuddies() {
		return buddies;
	}

	private Users user = new Users();
	public Users getUser()
	{
		return user;
	}
	class AddAccountPage extends WizardPage {
		private Text username, password;
		private Composite container;
		private AccountType type;
		private Label hint, image, error;

		protected AddAccountPage(AccountType accountType) {
			super("Add");

			type = accountType;
			if (accountType == AccountType.CEC) {
				this.type = accountType;
				setTitle("Add CEC Account");
				setDescription("Add CEC Account");
			}

			if (accountType == AccountType.ADD_BUDDY) {
				this.type = accountType;
				setTitle("Add Buddy");
				setDescription("Add Buddy");
			}
		}

		private void message(String s) {

			hint.setText(s);
			hint.setSize(600, 40);
		}

		@Override
		public void createControl(Composite parent) {
			container = new Composite(parent, SWT.NULL);
			GridLayout layout = new GridLayout();
			container.setLayout(layout);
			layout.numColumns = 2;

			Label label1 = new Label(container, SWT.NULL);
			label1.setText("Enter your CEC username:");

			username = new Text(container, SWT.BORDER | SWT.SINGLE);
			username.setText("");

			// Label label2 = new Label(container, SWT.NULL);
			// label2.setText("Enter the " + type.name() + " password:");

			// password = new Text(container, SWT.BORDER | SWT.PASSWORD
			// | SWT.SINGLE);
			// password.setText("");

			new Label(container, SWT.NULL);
			new Label(container, SWT.NULL);
			new Label(container, SWT.NULL);
			hint = new Label(container, SWT.NULL);
			new Label(container, SWT.NULL);
			new Label(container, SWT.NULL);
			new Label(container, SWT.NULL);

			image = new Label(container, SWT.NULL);

			// if (type == AccountType.Facebook) {
			// hint.setText("* To find your facebook username, follow this");
			// image.setImage(Activator.getImageDescriptor(
			// "icons/fb-username.png").createImage());
			// image.setSize(630, 230);
			//
			// } else {
			// hint.setText("* your entire email address is your user name");
			// image.setImage(Activator.getImageDescriptor(
			// "icons/google-username.png").createImage());
			// image.setSize(630, 230);
			// }

			// hint.setText("Your CEC username");
			// image.setImage(Activator.getImageDescriptor(
			// "icons/google-username.png").createImage());
			// image.setSize(630, 230);

			KeyListener usernamePasswordKeyListener = new KeyListener() {
				@Override
				public void keyPressed(KeyEvent e) {
				}

				@Override
				public void keyReleased(KeyEvent e) {
					if (!username.getText().isEmpty()) {
						setPageComplete(true);
					}
				}
			};

			username.addKeyListener(usernamePasswordKeyListener);
			// password.addKeyListener(usernamePasswordKeyListener);

			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			username.setLayoutData(gd);
			// password.setLayoutData(gd);

			// Required to avoid an error in the system
			setControl(container);
			setPageComplete(false);
		}

		public String getUserName() {
			return username.getText();
		}

		public String getPassword() {
			return password.getText();
		}

	

	}

	AddAccountPage page;

	public AddAccountWizard(AccountType type) {
		super();
		this.type = type;
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		page = new AddAccountPage(type);
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		// this.password = page.getPassword();
		this.username = page.getUserName();

		page.message("Trying to login...");
		page.getControl().redraw();

		if (this.type == AccountType.CEC) {
			Vector<Users> users = DatabaseConnection
					.getUserInformation(this.username);
			

			if (users.size() == 0)
				return false;

			if (users.size() == 1) {
				this.buddies = DatabaseConnection.getUsersBuddies(this.username);
				return true;
			}
		}

		if (this.type == AccountType.ADD_BUDDY) {
			Vector<Users> users = DatabaseConnection
					.getUserInformation(this.username);

			if (users.size() == 0)
				return false;

			if (users.size() == 1) {
				Vector<Users> u = DatabaseConnection.getUserInformation(this.username);
				this.user = u.get(0);
				DatabaseConnection.insertBuddy(DatabaseUtils.getLoggedInUser(), this.user);
				//insert user as the logged in user's buddy
				return true;
			}
		}

		return false;

		// here is where i connect to the database and check their username
		// try {
		// AccountsManager.getInstance().addAccount(type, username, password);
		// } catch (Exception e) {
		// if (e.getMessage().contains("Could not connect to")) {
		// page.message("Could not connect to the " + type.name()
		// + " server. Check your network settings");
		// page.getControl().redraw();
		// } else if (e.getMessage().contains("SASL authentication")) {
		// page.message("Unable to authenticate. Check your username and password.");
		// page.getControl().redraw();
		// }
		// return false;
		// }
		// return true;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public AccountType getType() {
		return type;
	}

}
