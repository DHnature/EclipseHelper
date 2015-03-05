package buddylist.views;

import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;

import buddylist.chat.Buddy_1;



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
class CloseWindowListener implements DisposeListener {
	ChatView view;

	public CloseWindowListener(ChatView view) {
		this.view = view;
	}

	@Override
	public void widgetDisposed(DisposeEvent arg0) {

//		Buddy b = ((ChatWindow) ((CTabItem) arg0.getSource()).getControl())
//				.getBuddy();
//		if (b != null)
//			view.chatWindows.remove(b);
	}

}
