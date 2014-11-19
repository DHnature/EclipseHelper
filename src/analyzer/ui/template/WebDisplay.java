package analyzer.ui.template;

import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.util.List;

public interface WebDisplay extends MouseListener, PropertyChangeListener,
		RatioFeaturesListener {
	public void setData(List<List<WebLink>> newWebsiteList);

	public void paint(Graphics g);

}
