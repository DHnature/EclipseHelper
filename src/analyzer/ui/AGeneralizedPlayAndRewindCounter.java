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
}
