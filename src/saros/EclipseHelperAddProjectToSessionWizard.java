package saros;

import java.lang.reflect.Field;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabItem;
import org.picocontainer.annotations.Inject;

import de.fu_berlin.inf.dpp.negotiation.FileList;
import de.fu_berlin.inf.dpp.negotiation.IncomingProjectNegotiation;
import de.fu_berlin.inf.dpp.net.IConnectionManager;
import de.fu_berlin.inf.dpp.net.xmpp.JID;
import de.fu_berlin.inf.dpp.preferences.PreferenceUtils;
import de.fu_berlin.inf.dpp.project.SarosSessionManager;
import de.fu_berlin.inf.dpp.session.ISarosSession;
import de.fu_berlin.inf.dpp.session.ISarosSessionManager;
import de.fu_berlin.inf.dpp.ui.widgets.wizard.ProjectOptionComposite;
import de.fu_berlin.inf.dpp.ui.wizards.AddProjectToSessionWizard;
import de.fu_berlin.inf.dpp.ui.wizards.pages.EnterProjectNamePage;

public class EclipseHelperAddProjectToSessionWizard extends AddProjectToSessionWizard {
//	 @Inject
//	    private static SarosSessionManager sessionManager;
	IncomingProjectNegotiation process;
	JID peer;
	List<FileList> fileLists;	
	Field sessionManagerField;
	Field  connectionManagerField;
	Field namePageField;
	Field preferenceUtilsField;
	EclipseHelperEnterProjectNamePage myNamePage;
	public EclipseHelperAddProjectToSessionWizard(
			IncomingProjectNegotiation aProcess, JID aPeer,
			List<FileList> aFileLists) {
		super(aProcess, aPeer, aFileLists);
		process = aProcess;
		peer = aPeer;
		fileLists = aFileLists;
		try {
			sessionManagerField = AddProjectToSessionWizard.class.getDeclaredField("sessionManager");
			sessionManagerField.setAccessible(true);
			connectionManagerField = AddProjectToSessionWizard.class.getDeclaredField("connectionManager");
			connectionManagerField.setAccessible(true);
			namePageField = AddProjectToSessionWizard.class.getDeclaredField("namePage");
			namePageField.setAccessible(true);
			preferenceUtilsField = AddProjectToSessionWizard.class.getDeclaredField("preferenceUtils");
			preferenceUtilsField.setAccessible(true);

		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}
	
	 @Override
	    public void addPages() {
//		 super.addPages();
		 
		 try {
			ISarosSessionManager sessionManager = (ISarosSessionManager) sessionManagerField.get(this);
			 IConnectionManager connectionManager = (IConnectionManager) connectionManagerField.get(this);
			
			myNamePage = (EclipseHelperEnterProjectNamePage) namePageField.get(this);

		 
	        ISarosSession session = sessionManager.getSarosSession();
	        PreferenceUtils preferenceUtils = (PreferenceUtils) preferenceUtilsField.get(this);
	        if (session == null)
	            return;

	        myNamePage = new EclipseHelperEnterProjectNamePage(session, connectionManager,
	            preferenceUtils, fileLists, peer, process.getProjectNames());
	        namePageField.set(this, myNamePage);

	        addPage(myNamePage);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	 }
	 
//	 public void setLocalProjectName () {
//		 String aPeerName = peer.getName();
//		 
//		 for (final FileList fileList : fileLists) {
//
//	            final String projectID = fileList.getProjectID();
//
//	            TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
//	            tabItem.setText(remoteProjectNames.get(projectID));
//
//	            ProjectOptionComposite tabComposite = new ProjectOptionComposite(
//	                tabFolder, projectID);
//
//	            tabItem.setControl(tabComposite);
//
//	            projectOptionComposites.put(projectID, tabComposite);
//	        }
//	 }

}
