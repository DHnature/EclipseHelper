package analyzer.ui.video;

import util.models.PropertyListenerRegistrar;
import context.recording.DisplayBoundsOutputter;

public interface LocalScreenRecorderAndPlayer extends DisplayBoundsOutputter, PropertyListenerRegistrar {
	public void seek(long aTime);
	public void play();
	public void pause();
	public long getWallTime();
	

}
