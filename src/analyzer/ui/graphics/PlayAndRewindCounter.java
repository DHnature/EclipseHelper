package analyzer.ui.graphics;

import java.beans.PropertyChangeListener;

import util.models.PropertyListenerRegistrar;
import analyzer.Resettable;

public interface PlayAndRewindCounter extends PropertyListenerRegistrar,
		PropertyChangeListener, Resettable {
	public void back();

	void setStart(int newValue);

	public void play();

	public void rewind();

	public void pause();

	public void forward();

	public int getStart();

	public int getEnd();

	public int getSize();

	public void setSize(int size);

	public int getCurrentTime();

	public void setCurrentFeatureIndex(int newVal);

//	public boolean preBack();
//
//	public boolean preRewind();
//
//	public boolean preForward();
//
//	public boolean prePause();
//
//	public boolean prePlay();

}
