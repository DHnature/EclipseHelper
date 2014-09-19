package buddylist.views;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;



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
class ViewLabelProvider extends LabelProvider {

	public ViewLabelProvider() {

	}

	public String getText(Object obj) {
		return obj.toString();
	}

	public Image getImage(Object obj) {

		return null;
		// String imageKey = ISharedImages.IMG_DEC_FIELD_ERROR;
		// if (obj instanceof Buddy) {
		// Buddy b = (Buddy) obj;
		// switch (b.getPresenceType()) {
		// case BUSY:
		// imageKey = ISharedImages.IMG_TOOL_DELETE;
		// break;
		// case OFFLINE:
		// imageKey = ISharedImages.IMG_ELCL_REMOVE_DISABLED;
		// break;
		// case AVAILABLE:
		// imageKey = ISharedImages.IMG_TOOL_NEW_WIZARD;
		// break;
		// case AWAY:
		// imageKey = ISharedImages.IMG_ELCL_STOP;
		// break;
		// default:
		// break;
		// }
		// }
		// if (obj instanceof Account)
		// imageKey = ISharedImages.IMG_DEC_FIELD_ERROR;
		// return
		// PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}
}