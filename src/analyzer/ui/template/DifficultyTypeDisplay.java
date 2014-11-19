package analyzer.ui.template;

import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.util.List;

public interface DifficultyTypeDisplay extends MouseListener,
		PropertyChangeListener, RatioFeaturesListener {
	public void setData(List<String> newDifficultyTypeList);

	public void paint(Graphics g);
}
