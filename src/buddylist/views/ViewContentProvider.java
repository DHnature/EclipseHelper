package buddylist.views;


import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import buddylist.chat.Viewable;

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
class ViewContentProvider implements IStructuredContentProvider,
		ITreeContentProvider {

	private Viewable invisibleRoot;
	Object viewSite;

	public ViewContentProvider(Object viewSite) {
		this.viewSite = viewSite;
	}

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {

	}

	public void dispose() {
	}

	public void setRoot(Viewable root) {
		this.invisibleRoot = root;
	}

	public Viewable getRoot() {
		return this.invisibleRoot;
	}

	public Object[] getElements(Object parent) {
		if (parent.equals(viewSite)) {
			return getChildren(invisibleRoot);
		}
		return getChildren(parent);
	}

	public Object getParent(Object child) {
		return ((Viewable) child).getParent();
	}

	public Object[] getChildren(Object parent) {
		return ((Viewable) parent).getChildren();
	}

	public boolean hasChildren(Object parent) {
		Object[] chioldren = ((Viewable) parent).getChildren();
		return (chioldren != null && chioldren.length > 0) ? true : false;
	}
}
