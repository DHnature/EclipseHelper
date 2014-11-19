package analyzer.ui.template;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.util.List;

public interface LineGraph extends MouseListener, PropertyChangeListener,
		RatioFeaturesListener {
	public void paint(Graphics g);

	public void drawDataPoints(Graphics g2, Color color, List<Double> ratios);

	public void connectTheDots(Graphics g2, Color color, List<Double> ratios);

	public void setData(List<Double> newInsertionList,
			List<Double> newDeletionList, List<Double> newDebugList,
			List<Double> newNavigationList, List<Double> newFocusList,
			List<Double> newRemoveList, List<Long> newTimeStampList);

}
