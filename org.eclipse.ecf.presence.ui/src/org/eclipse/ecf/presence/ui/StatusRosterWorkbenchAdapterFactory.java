package org.eclipse.ecf.presence.ui;

import org.eclipse.ecf.presence.roster.IRosterEntry;

public class StatusRosterWorkbenchAdapterFactory extends RosterWorkbenchAdapterFactory {

	//Override
	protected String getRosterEntryLabel(IRosterEntry entry) {
		return entry.getName() + " - " + entry.getPresence().getStatus();
	}

}