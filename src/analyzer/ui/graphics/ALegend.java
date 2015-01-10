package analyzer.ui.graphics;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import util.annotations.Column;
import util.annotations.ComponentWidth;
import util.annotations.Row;

public class ALegend implements Legend {

	private ALineGraph lineGraph;
	private AStatusBar statusBar;
	private AWebDisplay webDisplay;
	private ADifficultyTypeDisplay difficultyTypeDisplay;
	private boolean statusBarVisible = true;
	private boolean lineGraphVisible = true;
	private boolean webDisplayVisible = true;
	private boolean difficultyDisplayVisible = true;
	private boolean edit = true;
	private boolean remove = true;
	private boolean insertion = true;
	private boolean deletion = true;
	private boolean debug = true;
	private boolean navigation = true;
	private boolean focus = true;
	private JFrame frame;

	public ALegend(JFrame newFrame,
			ADifficultyTypeDisplay newDifficultyTypeDisplay,
			AStatusBar newStatusBar, AWebDisplay newWebDisplay,
			ALineGraph newLineGraph) {
		frame = newFrame;
		difficultyTypeDisplay = newDifficultyTypeDisplay;
		statusBar = newStatusBar;
		webDisplay = newWebDisplay;
		lineGraph = newLineGraph;

	}

	public void setDiffType(boolean val) {
		difficultyDisplayVisible = val;
		if (difficultyDisplayVisible) {
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 1.0; // request any extra horizontal space
			c.weighty = .1;
			c.gridx = 0;
			c.gridy = 3;
			frame.add(difficultyTypeDisplay, c);
			frame.pack();
			frame.setSize(840, 600);
		} else {
			frame.remove(difficultyTypeDisplay);
			frame.pack();
			frame.setSize(840, 600);
		}
	}

	@Row(1)
	@Column(0)
	@ComponentWidth(100)
	public boolean getDiffType() {
		return difficultyDisplayVisible;
	}

	public void setStatusBar(boolean val) {
		statusBarVisible = val;
		if (statusBarVisible) {
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 1.0; // request any extra horizontal space
			c.weighty = .25;
			c.gridx = 0;
			c.gridy = 4;
			frame.add(statusBar, c);
			frame.pack();
			frame.setSize(840, 600);
		} else {
			frame.remove(statusBar);
			frame.pack();
			frame.setSize(840, 600);
		}
	}

	@Row(1)
	@Column(1)
	@ComponentWidth(100)
	public boolean getStatusBar() {
		return statusBarVisible;
	}

	public void setLineGraph(boolean val) {
		lineGraphVisible = val;
		if (lineGraphVisible) {
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;
			c.weighty = 1.0; // request any extra vertical space
			c.weightx = 1.0; // request any extra horizontal space
			c.gridx = 0;
			c.gridy = 6;
			frame.add(lineGraph, c);
			frame.pack();
			frame.setSize(840, 600);
		} else {
			frame.remove(lineGraph);
			frame.pack();
			frame.setSize(840, 300);
		}
	}

	@Row(1)
	@Column(2)
	@ComponentWidth(100)
	public boolean getWebUse() {
		return webDisplayVisible;
	}

	public void setInsertion(boolean val) {
		insertion = val;
		List<List<Double>> lists = lineGraph.getLists();
		ArrayList<Color> colors = lineGraph.getColors();
		if (!insertion) {
			lists.remove(lists.indexOf(lineGraph.getInsertionList()));
			colors.remove(colors.indexOf(new Color(158, 0, 178)));
			lineGraph.repaint();
		} else {
			lists.add(lineGraph.getInsertionList());
			colors.add(new Color(158, 0, 178));
			lineGraph.repaint();
		}
	}

	@Row(1)
	@Column(3)
	@ComponentWidth(100)
	public boolean getLineGraph() {
		return lineGraphVisible;
	}

	public void setWebUse(boolean val) {
		webDisplayVisible = val;
		if (webDisplayVisible) {
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 1.0; // request any extra horizontal space
			c.weighty = .1;
			c.gridx = 0;
			c.gridy = 5;
			frame.add(webDisplay, c);
			frame.pack();
			frame.setSize(840, 600);
		} else {
			frame.remove(webDisplay);
			frame.pack();
			frame.setSize(840, 600);
		}
	}

	@Row(0)
	@Column(1)
	@ComponentWidth(100)
	public boolean getInsertion() {
		return insertion;
	}

	public void setEdit(boolean val) {
		edit = val;
		List<List<Double>> lists = lineGraph.getLists();
		ArrayList<Color> colors = lineGraph.getColors();
		if (!edit) {
			lists.remove(lists.indexOf(lineGraph.getEditList()));
			colors.remove(colors.indexOf(new Color(199, 21, 133)));
			lineGraph.repaint();
		} else {
			lists.add(lineGraph.getEditList());
			colors.add(new Color(199, 21, 133));
			lineGraph.repaint();
		}
	}
	
	@Row(0)
	@Column(0)
	@ComponentWidth(100)
	public boolean getEdit() {
		return edit;
	}
	
	public void setDeletion(boolean val) {
		deletion = val;
		List<List<Double>> lists = lineGraph.getLists();
		ArrayList<Color> colors = lineGraph.getColors();
		if (!deletion) {
			lists.remove(lists.indexOf(lineGraph.getDeletionList()));
			colors.remove(colors.indexOf(new Color(79, 191, 10)));
			lineGraph.repaint();
		} else {
			lists.add(lineGraph.getDeletionList());
			colors.add(new Color(79, 191, 10));
			lineGraph.repaint();
		}
	}

	@Row(0)
	@Column(2)
	@ComponentWidth(100)
	public boolean getDeletion() {
		return deletion;
	}

	public void setDebug(boolean val) {
		debug = val;
		List<List<Double>> lists = lineGraph.getLists();
		ArrayList<Color> colors = lineGraph.getColors();
		if (!debug) {
			lists.remove(lists.indexOf(lineGraph.getDebugList()));
			colors.remove(colors.indexOf(new Color(63, 0, 178)));
			lineGraph.repaint();
		} else {
			lists.add(lineGraph.getDebugList());
			colors.add(new Color(63, 0, 178));
			lineGraph.repaint();
		}
	}

	@Row(0)
	@Column(3)
	@ComponentWidth(100)
	public boolean getDebug() {
		return debug;
	}

	public void setNavigation(boolean val) {
		navigation = val;
		List<List<Double>> lists = lineGraph.getLists();
		ArrayList<Color> colors = lineGraph.getColors();
		if (!navigation) {
			lists.remove(lists.indexOf(lineGraph.getNavigationList()));
			colors.remove(colors.indexOf(new Color(10, 190, 201)));
			lineGraph.repaint();
		} else {
			lists.add(lineGraph.getNavigationList());
			colors.add(new Color(10, 190, 201));
			lineGraph.repaint();
		}
	}

	@Row(0)
	@Column(4)
	@ComponentWidth(100)
	public boolean getNavigation() {
		return navigation;
	}

	public void setFocus(boolean val) {
		focus = val;
		List<List<Double>> lists = lineGraph.getLists();
		ArrayList<Color> colors = lineGraph.getColors();
		if (!focus) {
			lists.remove(lists.indexOf(lineGraph.getFocusList()));
			colors.remove(colors.indexOf(new Color(201, 24, 10)));
			lineGraph.repaint();
		} else {
			lists.add(lineGraph.getFocusList());
			colors.add(new Color(201, 24, 10));
			lineGraph.repaint();
		}
	}

	@Row(0)
	@Column(5)
	@ComponentWidth(100)
	public boolean getFocus() {
		return focus;
	}

	public void setRemove(boolean val) {
		remove = val;
		List<List<Double>> lists = lineGraph.getLists();
		ArrayList<Color> colors = lineGraph.getColors();
		if (!remove) {
			lists.remove(lists.indexOf(lineGraph.getRemoveList()));
			colors.remove(colors.indexOf(new Color(235, 172, 10)));
			lineGraph.repaint();
		} else {
			lists.add(lineGraph.getRemoveList());
			colors.add(new Color(235, 172, 10));
			lineGraph.repaint();
		}
	}

	@Row(0)
	@Column(6)
	@ComponentWidth(100)
	public boolean getRemove() {
		return remove;
	}

}
