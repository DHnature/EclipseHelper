package saros;

import de.fu_berlin.inf.dpp.net.xmpp.XMPPConnectionService;
import de.fu_berlin.inf.dpp.project.SarosSessionManager;
import de.fu_berlin.inf.dpp.ui.eventhandler.NegotiationHandler;

public class EclipseHelperNegotiationHandler extends NegotiationHandler{

	public EclipseHelperNegotiationHandler(SarosSessionManager sessionManager,
			XMPPConnectionService connectionService) {
		super(sessionManager, connectionService);
		// TODO Auto-generated constructor stub
	}

}
