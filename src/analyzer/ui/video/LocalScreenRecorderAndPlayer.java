package analyzer.ui.video;

import util.models.PropertyListenerRegisterer;
import context.recording.DisplayBoundsOutputter;

public interface LocalScreenRecorderAndPlayer extends DisplayBoundsOutputter, PropertyListenerRegisterer {
	public void seek(long aTime);
	public void play();
	public void pause();
	public long getWallTime();
	

}
