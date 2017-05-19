package fluorite.model;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import fluorite.commands.EHICommand;

public class Events {
	private List<EHICommand> mCommands;
	private int mSessionID = 0;
	private long mStartTimestamp;

	public Events(List<EHICommand> commands, String id, String name, String desc) {
		this(commands, id, name, desc, 0);
	}

	public Events(List<EHICommand> commands, String id, String name, String desc,
			long startTimestamp) {
		mCommands = new ArrayList<EHICommand>();
		mCommands.addAll(commands);
		mStartTimestamp = startTimestamp;
	}

	public void addCommand(EHICommand command) {
		mCommands.add(command);
	}

	public boolean hasEvents() {
		return mCommands.size() > 0;
	}

	public void dump() {
		for (EHICommand command : mCommands) {
			command.dump();
		}
	}

	public void persist(Document doc, Element macroElement) {
		macroElement.setAttribute("startTimestamp",
				Long.toString(getStartTimestamp()));

		for (EHICommand command : mCommands) {
			try {
				String commandTag = command.getCommandTag();

				Element child = doc.createElement(commandTag);
				command.persist(doc, child);
				macroElement.appendChild(child);
			} catch (Exception e) {
				// TODO improved exception handling.
			}
		}
	}

	public List<EHICommand> getCommands() {
		return mCommands;
	}

	public void copyFrom(Events existingMacro) {
		mCommands.clear();
		mCommands.addAll(existingMacro.mCommands);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Events))
			return false;

		return ((Events) obj).getStartTimestamp() == getStartTimestamp();
	}

	@Override
	public int hashCode() {
		return (int) getStartTimestamp();
	}

	public int getSessionID() {
		return mSessionID;
	}

	public void setSessionID(int sessionID) {
		if (mSessionID == 0)
			mSessionID = sessionID;
		else {
			throw new RuntimeException("Session ID set twice. Macro: "
					+ toString());
		}
	}

	@Override
	public String toString() {
		return "Events, start timestamp: " + Long.toString(getStartTimestamp());
	}

	public long getStartTimestamp() {
		return mStartTimestamp;
	}
}
