
package dayton;

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

public class AccountSettingsWizard extends Wizard {

	private String username;
	private String password;
	private String instructorUsername;
	private String server;
	private String serviceName;
	private int port;
	
	private AccountSettingsPage page;

	public AccountSettingsWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		page = new AccountSettingsPage();
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		this.password = page.getPassword();
		this.username = page.getUserName();
		this.instructorUsername = page.getInstructorUsername();
		this.server = page.getServer();
		this.serviceName = page.getServiceName();
		this.port = page.getPort();
		page.getControl().redraw();
		return true;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public String getInstructorUsername() {
		return instructorUsername;
	}
	
	public String getServer() {
		return server;
	}
	
	public String getServiceName() {
		return serviceName;
	}
	
	public int getPort() {
		return port;
	}

	class AccountSettingsPage extends WizardPage {
		
		private Text username;
		private Text password;
		private Text instructorUsername;
		private Text server;
		private Text serviceName;
		private Text port;
		private Composite container;

		protected AccountSettingsPage() {
			super("Account Settings");
			setTitle("Account Settings");
			setDescription("Account Settings");
		}

		@Override
		public void createControl(Composite parent) {
			AccountSettings accountSettings = AccountSettings.getAccountSettings();
			
			container = new Composite(parent,SWT.NULL);
			GridLayout layout = new GridLayout();
			container.setLayout(layout);
			layout.numColumns = 2;

			Label usernameLabel = new Label(container,SWT.NULL);
			usernameLabel.setText("Username:");
			username = new Text(container,SWT.BORDER | SWT.SINGLE);
			username.setText(accountSettings.getUsername());

			Label passwordLabel = new Label(container, SWT.NULL);
			passwordLabel.setText("Password:");
			password = new Text(container, SWT.BORDER | SWT.PASSWORD | SWT.SINGLE);
			password.setText(accountSettings.getPassword());
			
			Label instructorLabel = new Label(container, SWT.NULL);
			instructorLabel.setText("Instructor Username:");
			instructorUsername = new Text(container, SWT.BORDER | SWT.SINGLE);
			instructorUsername.setText(accountSettings.getInstructorUsername());
			
			Label serverLabel = new Label(container, SWT.NULL);
			serverLabel.setText("Server Address:");
			server = new Text(container, SWT.BORDER | SWT.SINGLE);
			server.setText(accountSettings.getServer());
			
			Label serviceLabel = new Label(container, SWT.NULL);
			serviceLabel.setText("Service Name:");
			serviceName = new Text(container, SWT.BORDER | SWT.SINGLE);
			serviceName.setText(accountSettings.getServiceName());
			
			Label portLabel = new Label(container, SWT.NULL);
			portLabel.setText("Port:");
			port = new Text(container, SWT.BORDER | SWT.SINGLE);
			port.setText("" + accountSettings.getPort());

			KeyListener wizardKeyListener = new KeyListener() {
				public void keyPressed(KeyEvent e) {}
				public void keyReleased(KeyEvent e) {
					if (!username.getText().isEmpty()) {
						setPageComplete(true);
					}
				}
			};

			username.addKeyListener(wizardKeyListener);
			password.addKeyListener(wizardKeyListener);
			instructorUsername.addKeyListener(wizardKeyListener);
			server.addKeyListener(wizardKeyListener);
			serviceName.addKeyListener(wizardKeyListener);
			port.addKeyListener(wizardKeyListener);

			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			username.setLayoutData(gd);
			password.setLayoutData(gd);
			instructorUsername.setLayoutData(gd);
			server.setLayoutData(gd);
			serviceName.setLayoutData(gd);
			port.setLayoutData(gd);

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
		
		public String getInstructorUsername() {
			return instructorUsername.getText();
		}
		
		public String getServer() {
			return server.getText();
		}
		
		public String getServiceName() {
			return serviceName.getText();
		}
		
		public int getPort() {
			try {
				return Integer.parseInt(port.getText());
			} catch (Exception ex) {return 0;}
		}
	}
}