package edu.cmu.scs.fluorite.viewpart;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import buddylist.chat.Buddy;

public class BuddyListLabelProvider extends LabelProvider implements ITableLabelProvider{

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Buddy b = (Buddy) element;
		String result = "";
		switch(columnIndex){
		case 0:
			result = b.getName();
			break;
		case 1:
			result = b.getStatus();
			break;
		case 2:
			result = b.getMostRecentAction();
			break;
		case 3:
			result = b.getFilename();
			break;
		default:
			//should not reach here
			result = "";
		}
		return result;
	}

}
