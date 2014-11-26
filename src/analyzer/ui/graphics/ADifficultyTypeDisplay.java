package analyzer.ui.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class ADifficultyTypeDisplay extends JPanel implements
		DifficultyTypeDisplay {

	private static final long serialVersionUID = -5413630047109983241L;
	private List<String> difficultyTypeList = new ArrayList<String>();
	private PlayAndRewindCounter counter;
	private RatioFileReader ratioFileReader;
	private int X_BORDER_GAP = 60;
	private int Y_BORDER_GAP = 10;

	public ADifficultyTypeDisplay(PlayAndRewindCounter aCounter,
			RatioFileReader aRatioFileReader) {
		setBackground(Color.LIGHT_GRAY);
		addMouseListener(this);
		counter = aCounter;
		counter.addPropertyChangeListener(this);
		ratioFileReader = aRatioFileReader;
		ratioFileReader.addPropertyChangeListener(this);
	}

	public void paint(Graphics g) {
		super.paint(g); // clears the window
		Graphics2D g2 = (Graphics2D) g;
		int xPos = 0;
		for (int i = counter.getStart(); i < counter.getEnd() - 1; i++) {
			if (i < difficultyTypeList.size() - 1 && i >= 0) {
				if (difficultyTypeList.get(i) != null) {
					int x = (xPos * (getWidth() - X_BORDER_GAP * 2) / (10 - 1) + X_BORDER_GAP);
					int y = Y_BORDER_GAP;
					g2.setColor(new Color(63, 0, 178));
					g2.setFont(new Font("default", Font.BOLD, 14));
					g2.drawString(difficultyTypeList.get(i), x, y);
				}
				xPos++;
			} else {
				break;
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equalsIgnoreCase("newRatioFeatures")) {
			newRatios((DuriRatioFeatures) evt.getNewValue());
			repaint();
		} else if (evt.getPropertyName().equalsIgnoreCase("start")) {
			repaint();
		}

	}

	@Override
	public void newRatios(DuriRatioFeatures ratioFeatures) {
		difficultyTypeList.add(ratioFeatures.getDifficultyType());

	}

	@Override
	public void setData(List<String> newDifficultyTypeList) {
		difficultyTypeList = newDifficultyTypeList;
		repaint();

	}

}
