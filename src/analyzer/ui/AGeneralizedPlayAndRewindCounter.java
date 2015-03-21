package analyzer.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import util.annotations.Column;
import util.annotations.ComponentWidth;
import util.annotations.Row;
import analyzer.ui.graphics.APlayAndRewindCounter;
import analyzer.ui.graphics.ARatioFileReader;
import analyzer.ui.graphics.PlayAndRewindCounter;
import analyzer.ui.graphics.RatioFileReader;
import bus.uigen.ObjectEditor;

public class AGeneralizedPlayAndRewindCounter extends APlayAndRewindCounter implements GeneralizedPlayAndRewindCounter {



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
	
	public static void main (String[] args) {
		ObjectEditor.edit(new AGeneralizedPlayAndRewindCounter());
	}
}
