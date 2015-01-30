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
	@Override
    public void handleIncomingSessionNegotiation(
        IncomingSessionNegotiation negotiation) {
        showIncomingInvitationUI(negotiation);
    }
	 @Override
	    public void handleIncomingProjectNegotiation(
	        IncomingProjectNegotiation negotiation) {
	        showIncomingProjectUI(negotiation);
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
	                // as we re automatically accepting, no need to show dialog
	                DialogUtils.openWindow(wizardDialog);
	                sessionWizard.performFinish();	
	                wizardDialog.close();
	                }
	        });
	    }
	private void showIncomingProjectUI(final IncomingProjectNegotiation process) {
        List<ProjectNegotiationData> pInfos = process.getProjectInfos();
        final List<FileList> fileLists = new ArrayList<FileList>(pInfos.size());

        for (ProjectNegotiationData pInfo : pInfos)
            fileLists.add(pInfo.getFileList());

        SWTUtils.runSafeSWTAsync(LOG, new Runnable() {

            @Override
            public void run() {
//                AddProjectToSessionWizard projectWizard = new AddProjectToSessionWizard(
//                    process, process.getPeer(), fileLists);
                EclipseHelperAddProjectToSessionWizard projectWizard = new EclipseHelperAddProjectToSessionWizard(
                        process, process.getPeer(), fileLists);

                final WizardDialogAccessable wizardDialog = new WizardDialogAccessable(
                    SWTUtils.getShell(), projectWizard, SWT.MIN | SWT.MAX,
                    SWT.SYSTEM_MODAL | SWT.APPLICATION_MODAL
                        | SWT.PRIMARY_MODAL);

                /*
                 * IMPORTANT: as the dialog is non modal it MUST NOT block on
                 * open or there is a good chance to crash the whole GUI
                 * 
                 * Scenario: A modal dialog is currently open with
                 * setBlockOnOpen(true) (as most input dialogs are).
                 * 
                 * When we now open this wizard with setBlockOnOpen(true) this
                 * wizard will become the main dispatcher for the SWT Thread. As
                 * this wizard is non modal you cannot close it because you
                 * could not access it. Therefore the modal dialog cannot be
                 * closed as well because it is stuck on the non modal dialog
                 * which currently serves as main dispatcher !
                 */

                wizardDialog.setBlockOnOpen(false);

                wizardDialog.setHelpAvailable(false);
                projectWizard.setWizardDlg(wizardDialog);
                
                DialogUtils.openWindow(wizardDialog);
                projectWizard.performFinish();
                wizardDialog.close();
                
            }
        });
    }


}
