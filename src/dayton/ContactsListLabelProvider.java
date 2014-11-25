
package dayton;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class ContactsListLabelProvider extends LabelProvider implements ITableLabelProvider{

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Contact contact = (Contact) element;
		String result = "";
		switch(columnIndex){
		case 0:
			result = contact.getName();
			break;
		case 1:
			result = contact.getStatus();
			break;
		default:
			result = "";
		}
		return result;
	}
}