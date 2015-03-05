package dayton;

/*import org.eclipse.ecf.core.*;
import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.core.identity.IDFactory;
import org.eclipse.ecf.core.security.ConnectContextFactory;
import org.eclipse.ecf.core.security.IConnectContext;
import org.eclipse.ecf.ui.actions.AsynchContainerConnectAction;
import org.eclipse.ecf.ui.util.PasswordCacheHelper;
import org.eclipse.ecf.core.ContainerFactory;
import org.eclipse.ecf.provider.xmpp.XMPPContainer;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;*/

public class LogIntoXMPP {

	//static IContainer container;
	//static XMPPConnection connection;
	
	public static void login() {
		/*if(ServerConnection.getServerConnection().isConnected()) {
			ECFLogin();
		}*/ 
		ServerConnection.getServerConnection();
		//ECFLogin();
	}
	
	public static void ECFLogin() {
		/*AccountSettings account = AccountSettings.getAccountSettings();
		
		final String connectID = account.getUsername();
		final String password = account.getPassword();	
		
		System.out.println(connectID + " : " + password);
		
		try {
			container = ContainerFactory.getDefault().createContainer("ecf.xmpp.smack");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		IConnectContext connectContext = ConnectContextFactory.createPasswordConnectContext(password);
		ID targetID = IDFactory.getDefault().createID(container.getConnectNamespace(), connectID);

		new AsynchContainerConnectAction(container, targetID, connectContext,
				null, new Runnable() {
			public void run() {
				final PasswordCacheHelper pwStorage = new PasswordCacheHelper(connectID);
				pwStorage.savePassword(password);
			}
		}).run();*/
		
		/*connection = ((XMPPContainer) container).getXMPPConnection();
		
		Presence statusUpdate = new Presence(Presence.Type.available);
		statusUpdate.setStatus("CONNECTED!");
		try {
			System.out.println("HERE");
			connection.sendPacket(statusUpdate);
		} catch (Exception ex) {System.out.println("Error sending update"); ex.printStackTrace();}*/
	}
}