
package dayton;

import org.jivesoftware.smack.packet.Presence;

public class InstructorPing extends Presence {

	public InstructorPing() {
		super(Presence.Type.subscribe);
	}
	
}