package saros;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;

import de.fu_berlin.inf.dpp.negotiation.FileList;
import de.fu_berlin.inf.dpp.negotiation.IncomingProjectNegotiation;
import de.fu_berlin.inf.dpp.negotiation.IncomingSessionNegotiation;
import de.fu_berlin.inf.dpp.negotiation.ProjectNegotiationData;
import de.fu_berlin.inf.dpp.net.xmpp.XMPPConnectionService;
import de.fu_berlin.inf.dpp.project.SarosSessionManager;
import de.fu_berlin.inf.dpp.ui.eventhandler.NegotiationHandler;
import de.fu_berlin.inf.dpp.ui.util.DialogUtils;
import de.fu_berlin.inf.dpp.ui.util.SWTUtils;
import de.fu_berlin.inf.dpp.ui.util.ViewUtils;
import de.fu_berlin.inf.dpp.ui.wizards.AddProjectToSessionWizard;
import de.fu_berlin.inf.dpp.ui.wizards.JoinSessionWizard;
import de.fu_berlin.inf.dpp.ui.wizards.dialogs.WizardDialogAccessable;

public class EclipseHelperNegotiationHandler extends NegotiationHandler{
	 private static final Logger LOG = Logger
		        .getLogger(NegotiationHandler.class);

	public EclipseHelperNegotiationHandler(SarosSessionManager sessionManager,
			XMPPConnectionService connectionService) {
		super(sessionManager, connectionService);
		// TODO Auto-generated constructor stub
	}
	
	private void showIncomingInvitationUI(
	        final IncomingSessionNegotiation process) {

	        SWTUtils.runSafeSWTAsync(LOG, new Runnable() {
	            @Override
	            public void run() {
	                ViewUtils.openSarosView();
	            }
	        });

	        // Fixes #2727848: InvitationDialog is opened in the
	        // background
	        SWTUtils.runSafeSWTAsync(LOG, new Runnable() {
	            @Override
	            public void run() {
	                /**
	                 * @JTourBusStop 8, Invitation Process:
	                 * 
	                 *               (4a) The SessionManager then hands over the
	                 *               control to the NegotiationHandler (this class)
	                 *               which works on a newly started
	                 *               IncomingSessionNegotiation. This handler opens
	                 *               the JoinSessionWizard, a dialog for the user to
	                 *               decide whether to accept the invitation.
	                 */
	                JoinSessionWizard sessionWizard = new JoinSessionWizard(process);

	                final WizardDialogAccessable wizardDialog = new WizardDialogAccessable(
	                    SWTUtils.getShell(), sessionWizard);

	                // TODO Provide help :-)
	                wizardDialog.setHelpAvailable(false);

	                // as we are not interested in the result
	                wizardDialog.setBlockOnOpen(false);

	                DialogUtils.openWindow(wizardDialog);
	            }
	        });
	    }

}
