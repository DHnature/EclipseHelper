package analyzer.ui.graphics;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import util.annotations.Column;
import util.annotations.ComponentWidth;
import util.annotations.Row;
import bus.uigen.ObjectEditor;

public class APlayAndRewindCounter implements PlayAndRewindCounter {

	private PropertyChangeSupport propertyChangeSupport;
	private boolean running = false;
	private int start = 0;
	private int currentTime = 0;
	private int displaySize = 10;// default
	private RatioFileReader ratioFileReader;
	private int counter = 0;

	public APlayAndRewindCounter(RatioFileReader reader) {
		ratioFileReader = reader;
		ratioFileReader.addPropertyChangeListener(this);
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	// It would be nice to have preconditions that disabled buttons that should
	// not be pressed, but something in object editor is causing it not to work
	// properly so for now I just disable within the methods.

	// public boolean preBack() {
	// if (running) {
	// return false;
	// } else {
	// return true;
	// }
	// }
	//
	// public boolean preRewind() {
	// if (running) {
	// return false;
	// } else {
	// return true;
	// }
	// }
	//
	// public boolean preForward() {
	// if (running) {
	// return false;
	// } else {
	// return true;
	// }
	// }
	//
	// public boolean prePause() {
	// if (!running) {
	// return false;
	// } else {
	// return true;
	// }
	// }
	//
	// public boolean prePlay() {
	// if (running) {
	// return false;
	// } else {
	// return true;
	// }
	// }

	@Row(0)
	@Column(0)
	@ComponentWidth(100)
	public void back() {
		setCurrentTime(currentTime - 1);
		setStart(start - 1);
	}

	@Row(0)
	@Column(1)
	@ComponentWidth(100)
	public void rewind() {
		if (!running) {
			running = true;
			new Thread(new Runnable() {
				public void run() {
					while (running) {
						back();
						if (!running) {
							return;
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
	}

	@Row(0)
	@Column(2)
	@ComponentWidth(100)
	public void pause() {
		running = false;
	}

	@Row(0)
	@Column(3)
	@ComponentWidth(100)
	public void play() {
		if (!running) {
			running = true;
			new Thread(new Runnable() {
				public void run() {
					while (running) {
						forward();
						if (!running) {
							return;
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
	}

	@Row(0)
	@Column(4)
	@ComponentWidth(100)
	public void forward() {
		setCurrentTime(currentTime + 1);
		setStart(start + 1);
	}

	@Row(0)
	@Column(5)
	@ComponentWidth(100)
	public void live() {
		System.out.println(counter);
		setCurrentTime(counter-1);
		setStart(counter-1);
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return start + displaySize;
	}

	public void setStart(int newValue) {
		int oldStart = start;
		start = newValue;
		propertyChangeSupport.firePropertyChange("start", oldStart, newValue);
	}

	@Row(0)
	@Column(7)
	@ComponentWidth(50)
	public int getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(int newVal) {
		int oldTime = currentTime;
		currentTime = newVal;
		propertyChangeSupport
				.firePropertyChange("currentTime", oldTime, newVal);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener aListener) {
		propertyChangeSupport.addPropertyChangeListener(aListener);
	}

	public static void main(String[] args) {
		RatioFileReader reader = new ARatioFileReader();
		ObjectEditor.edit(new APlayAndRewindCounter(reader));
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equalsIgnoreCase("newRatioFeatures")) {
			counter = counter + 1;
		}
	}

	@Row(0)
	@Column(6)
	@ComponentWidth(50)
	public int getSize() {
		return displaySize;
	}

	public void setSize(int size) {
		int oldSize = displaySize;
		displaySize = size;
		propertyChangeSupport.firePropertyChange("size", oldSize, displaySize);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		System.err.println("Reset not implemented");

	}

}
