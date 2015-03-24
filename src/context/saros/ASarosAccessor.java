package context.saros;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.picocontainer.annotations.Inject;

import de.fu_berlin.inf.dpp.monitoring.IProgressMonitor;
import de.fu_berlin.inf.dpp.net.xmpp.JID;
import de.fu_berlin.inf.dpp.project.SarosSessionManager;
import de.fu_berlin.inf.dpp.session.ISarosSession;
import de.fu_berlin.inf.dpp.session.ISarosSessionListener;
import de.fu_berlin.inf.dpp.session.User;
import de.fu_berlin.inf.dpp.ui.util.CollaborationUtils;

public class ASarosAccessor implements ISarosSessionListener, SarosAccessor{
	@Inject
	    private static SarosSessionManager sessionManager;
	 // the interface does not export enough
//	    private static ISarosSessionManager sessionManager;

	JID teacherJID;
	List<JID> contacts = new ArrayList();

	public ASarosAccessor() {
		  teacherJID = new JID(TEACHER_ID);
		  contacts.add(teacherJID);
		  Field f;
		try {
			f = CollaborationUtils.class.getDeclaredField("sessionManager");
			  f.setAccessible(true);
			  sessionManager = (SarosSessionManager) f.get(CollaborationUtils.class); //IllegalAccessException
			sessionManager.addSarosSessionListener(this);


		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //NoSuchFieldException
 catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}
	/* (non-Javadoc)
	 * @see context.saros.SarosAccessor#resetIncomingHandler()
	 */
	@Override
	public void resetIncomingHandler() {		
		sessionManager.setNegotiationHandler(new EclipseHelperNegotiationHandler(sessionManager, null));
	}
	/* (non-Javadoc)
	 * @see context.saros.SarosAccessor#shareFixedProjectWithFixedUser()
	 */
	@Override
	public void shareFixedProjectWithFixedUser() {
		  final IProject[] workspaceProjects = ResourcesPlugin.getWorkspace()
		            .getRoot().getProjects();
		 if (workspaceProjects == null || workspaceProjects.length == 0) {
			 System.out.println("No projects to share");
			 return;
		 }
		 List<IResource> resources = Collections.<IResource> singletonList(workspaceProjects[0]);
//		 final List<JID> contacts = SelectionRetrieverFactory
//		            .getSelectionRetriever(JID.class).getSelection();
//		 if (contacts == null || contacts.size() == 0) {
//			 System.out.println("No contacts to share with");
//			 return;
//		 }
//		 JID teacherJID = new JID(TEACHER_ID);
		
		 
		 CollaborationUtils.startSession(
                resources, contacts);
	}
	static SarosAccessor instance;
	public static SarosAccessor getInstance() {
		if (instance == null){
			instance = new ASarosAccessor();			
		}
		return instance;
	}

	/* (non-Javadoc)
	 * @see context.saros.SarosAccessor#postOutgoingInvitationCompleted(de.fu_berlin.inf.dpp.session.ISarosSession, de.fu_berlin.inf.dpp.session.User, de.fu_berlin.inf.dpp.monitoring.IProgressMonitor)
	 */
	@Override
	public void postOutgoingInvitationCompleted(ISarosSession session,
			User user, IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see context.saros.SarosAccessor#sessionStarting(de.fu_berlin.inf.dpp.session.ISarosSession)
	 */
	@Override
	public void sessionStarting(ISarosSession session) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see context.saros.SarosAccessor#sessionStarted(de.fu_berlin.inf.dpp.session.ISarosSession)
	 */
	@Override
	public void sessionStarted(ISarosSession session) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see context.saros.SarosAccessor#sessionEnding(de.fu_berlin.inf.dpp.session.ISarosSession)
	 */
	@Override
	public void sessionEnding(ISarosSession session) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see context.saros.SarosAccessor#sessionEnded(de.fu_berlin.inf.dpp.session.ISarosSession)
	 */
	
	@Override
	public void sessionEnded(ISarosSession session) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see context.saros.SarosAccessor#projectAdded(java.lang.String)
	 */
	
	@Override
	public void projectAdded(String projectID) {
		// TODO Auto-generated method stub
		
	}

}
