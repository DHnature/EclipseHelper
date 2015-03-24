package analyzer.ui.video;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import util.annotations.Visible;
import analyzer.ui.GeneralizedPlayAndRewindCounter;
import analyzer.ui.PlayerFactory;
import analyzer.ui.graphics.PlayAndRewindCounter;
import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import context.recording.ADisplayBoundsPiper;

public class ALocalScreenRecorderAndPlayer extends ADisplayBoundsPiper implements LocalScreenRecorderAndPlayer{
	
	protected GeneralizedPlayAndRewindCounter player;
	protected boolean connected;
	protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	public ALocalScreenRecorderAndPlayer() {
		player = PlayerFactory.getSingleton();
		player.addPropertyChangeListener(this);
	}
	@Override
	public void connectToDisplayAndRecorder() {
//		super.connectToDisplayAndRecorder();
		connected = true;
		propertyChangeSupport.firePropertyChange("connected", null, true);
	
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	@Visible(false)
	public PlayAndRewindCounter getPlayer() {
		return player;
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (!isConnected()) return;
		if (player.isPlayBack() && evt.getPropertyName().equalsIgnoreCase("currentTime")) {
			seek (player.getCurrentWallTime());		
		}
	}
	@Override
	public void seek(long aTime) {
		propertyChangeSupport.firePropertyChange("wallTime", null, player.getCurrentWallTime());

		
	}
	@Override
	public void play() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public long getWallTime() {
		// TODO Auto-generated method stub
		return player.getCurrentWallTime();
	}
	@Override
	public void addPropertyChangeListener(PropertyChangeListener arg0) {
		propertyChangeSupport.addPropertyChangeListener(arg0);
	}
	public static void createUI() {
		
		OEFrame oeFrame = ObjectEditor.edit(LocalScreenRecorderAndPlayerFactory.getSingleton());
		oeFrame.setSize(250, 150);
	}

}
