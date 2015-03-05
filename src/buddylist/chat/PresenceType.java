package buddylist.chat;

//import org.jivesoftware.smack.packet.Presence;

/**
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * @author Rajgopal Vaithiyanathan
 * 
 */
public enum PresenceType {

	AVAILABLE {
		public String getSymbol() {
			return "+";
		}
	},
	AWAY {
		public String getSymbol() {
			return "?";
		}
	},
	BUSY {
		public String getSymbol() {
			return "X";
		}
	},
	OFFLINE {
		public String getSymbol() {
			return "-";
		}
	};
	public abstract String getSymbol();

//	public static PresenceType getPresenceTypeFromPresence(Presence p) {
//		if (p.getMode() == null) {
//			if (p.isAvailable())
//				return AVAILABLE;
//			else
//				return OFFLINE;
//		} else {
//			switch (p.getMode()) {
//			case available:
//				return AVAILABLE;
//			case away:
//				return AWAY;
//			case dnd:
//				return BUSY;
//			default:
//				return p.isAvailable() ? AVAILABLE : OFFLINE;
//			}
//		}
//	}
}
