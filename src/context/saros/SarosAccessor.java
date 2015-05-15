package context.saros;

import org.eclipse.ui.IPartListener;

import de.fu_berlin.inf.dpp.monitoring.IProgressMonitor;
import de.fu_berlin.inf.dpp.session.ISarosSession;
import de.fu_berlin.inf.dpp.session.ISarosSessionListener;
import de.fu_berlin.inf.dpp.session.User;
import de.fu_berlin.inf.dpp.ui.views.SarosView;

public interface SarosAccessor extends ISarosSessionListener, IPartListener {

	public static final String TEACHER_ID = "pd@saros-con.imp.fu-berlin.de";

	/**
	 *invoked by teacher after Saros has been started, maybe everyone can execute it
	 *or some configuration parameter can decide who the teacher is
	 */
	public abstract void resetIncomingHandler();

	/**
	 *invoked in response to a message from the teacher session to share a project	 
	 */
	public abstract void shareFixedProjectWithFixedUser();

	public abstract void postOutgoingInvitationCompleted(ISarosSession session,
			User user, IProgressMonitor monitor);

	public abstract void sessionStarting(ISarosSession session);

	public abstract void sessionStarted(ISarosSession session);

	public abstract void sessionEnding(ISarosSession session);

	public abstract void sessionEnded(ISarosSession session);

	public abstract void projectAdded(String projectID);

	SarosView getSarosView();

	void setSarosView(SarosView sarosView);

}