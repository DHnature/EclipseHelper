package buddylist.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;

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
public class ExpandTreeOnDoubleClick extends Action {

	TreeViewer viewer;

	public ExpandTreeOnDoubleClick(TreeViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void run() {
		TreeSelection s = (TreeSelection) viewer.getSelection();
		viewer.setExpandedState(s.getFirstElement(),
				!viewer.getExpandedState(s.getFirstElement()));
		viewer.refresh();
		super.run();
	}
}
