package analyzer.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import util.annotations.Column;
import util.annotations.ComponentWidth;
import util.annotations.Row;
import util.annotations.Visible;
import analyzer.ui.graphics.APlayAndRewindCounter;
import analyzer.ui.graphics.ARatioFileReader;
import analyzer.ui.graphics.PlayAndRewindCounter;
import analyzer.ui.graphics.RatioFileReader;
import bus.uigen.ObjectEditor;

public class AGeneralizedPlayAndRewindCounter extends APlayAndRewindCounter implements GeneralizedPlayAndRewindCounter {

    boolean playBack;
    int nextFeatureIndex;

	public AGeneralizedPlayAndRewindCounter(RatioFileReader reader) {
		super(reader);
	}

	public AGeneralizedPlayAndRewindCounter() {
		super (new ARatioFileReader());
	}
	@Override
	@Row(0)
	@Column(0)
	public void back() {
		playBack = true;
		super.back();		
	}
//	@Override
//	@Row(0)
//	@Column(1)
//	public void forward() {
//		super.forward();		
//	}
//	
//	@Override
//	@Row(0)
//	@Column(1)
//	public void forward() {
//		super.forward();		
//	}
	@Row(1)
	@Column(2)
	public void live() {		
		end(); //play back all past events
		playBack = false;
	}
	@Row(1)
	@Column(0)
	public void start() {
		setCurrentTime(0);
	}
	@Row(1)
	@Column(1)
	public void end() {
		setCurrentTime(nextFeatureIndex - 1);
	}
	@Visible(false)
	public int getNextFeatureIndex() {
		return nextFeatureIndex;
	}
	
	public void setNextFeatureIndex(int newVal) {
		nextFeatureIndex = newVal;
		setCurrentTime(nextFeatureIndex -1);
	}
	
	public boolean isPlayBack() {
		return playBack;
	}
	
//	
	
	public static void main (String[] args) {
		ObjectEditor.edit(new AGeneralizedPlayAndRewindCounter());
	}
}
