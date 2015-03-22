package analyzer.ui;

import java.beans.PropertyChangeListener;

import analyzer.Resettable;
import analyzer.ui.graphics.PlayAndRewindCounter;
import util.annotations.Column;
import util.annotations.Row;
import util.annotations.Visible;
import util.models.PropertyListenerRegistrar;

public interface GeneralizedPlayAndRewindCounter extends PlayAndRewindCounter {
	public void live();

	public void start() ;

	public void end() ;
	public int getNextFeatureIndex() ;
	
	public void setNextFeatureIndex(int newVal) ;
	
	public boolean isPlayBack() ;
	

}
