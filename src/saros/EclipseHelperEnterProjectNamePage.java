package saros;

import java.util.List;
import java.util.Map;

import de.fu_berlin.inf.dpp.negotiation.FileList;
import de.fu_berlin.inf.dpp.net.IConnectionManager;
import de.fu_berlin.inf.dpp.net.xmpp.JID;
import de.fu_berlin.inf.dpp.preferences.PreferenceUtils;
import de.fu_berlin.inf.dpp.session.ISarosSession;
import de.fu_berlin.inf.dpp.ui.wizards.pages.EnterProjectNamePage;

public class EclipseHelperEnterProjectNamePage extends EnterProjectNamePage {

	public EclipseHelperEnterProjectNamePage(ISarosSession session,
			IConnectionManager connectionManager,
			PreferenceUtils preferenceUtils, List<FileList> fileLists,
			JID peer, Map<String, String> remoteProjectNames) {
		super(session, connectionManager, preferenceUtils, fileLists, peer,
				remoteProjectNames);
		// TODO Auto-generated constructor stub
	}

}
