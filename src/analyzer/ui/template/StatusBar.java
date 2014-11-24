package analyzer.ui.template;

import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.util.List;

public interface StatusBar extends MouseListener, PropertyChangeListener,
		DuriRatioFeaturesListener {

	public void setData(List<Integer> newPredictedList,
			List<Integer> newActualList);

	public void paint(Graphics g);

}
