package analyzer.ui;

import util.annotations.Column;
import util.annotations.ComponentWidth;
import util.annotations.Row;
import util.annotations.Visible;
import analyzer.ParticipantTimeLine;
import analyzer.extension.LiveAnalyzerProcessorFactory;
import analyzer.ui.graphics.APlayAndRewindCounter;
import analyzer.ui.graphics.ARatioFileReader;
import analyzer.ui.graphics.RatioFileReader;
import bus.uigen.ObjectEditor;

public class AGeneralizedPlayAndRewindCounter extends APlayAndRewindCounter implements GeneralizedPlayAndRewindCounter {

    boolean playBack;
    int nextFeatureIndex;
//    ParticipantTimeLine liveParticipantTimeLine;
    long absoluteStartTime;
    @Override
	public long getAbsoluteStartTime() {
		return absoluteStartTime;
	}

	public AGeneralizedPlayAndRewindCounter(RatioFileReader reader) {
		super(reader);
		absoluteStartTime = System.currentTimeMillis();	
//		liveParticipantTimeLine = LiveAnalyzerProcessorFactory.getSingleton().getParticipantTimeLine();
		
	}

	public AGeneralizedPlayAndRewindCounter() {
		this (new ARatioFileReader());
	}
	@Override
	@Row(0)
	@Column(0)
	@ComponentWidth(100)
	public void back() {
		playBack = true;
		super.back();		
	}
	@Override
	@Row(0)
	@Column(4)
	@ComponentWidth(100)
	public void forward() {
		playBack = true;
		super.forward();		
	}

	@Row(1)
	@Column(2)
	@ComponentWidth(100)
	public void live() {		
		end(); //play back all past events
		playBack = false;
	}
	@Row(1)
	@Column(0)
	@ComponentWidth(100)
	public void start() {
		playBack = true;
		setCurrentFeatureIndex(0);
	}
	@Row(1)
	@Column(1)
	@ComponentWidth(100)
	public void end() {
		playBack = true;
		setCurrentFeatureIndex(nextFeatureIndex - 1);
	}
	int previousDifficulty;
	public boolean prePreviousDifficulty() {
		previousDifficulty = LiveAnalyzerProcessorFactory.getSingleton().getParticipantTimeLine().getDifficultyPredictionBefore(getCurrentTime());
		return previousDifficulty >= 0;
	}
	@Row(1)
	@Column(3)
	@ComponentWidth(100)
	public void previousDifficulty() {
		if (!prePreviousDifficulty())
			return;
		playBack = true;
		setCurrentFeatureIndex(previousDifficulty);		
	}
	int nextDifficulty;
	public boolean preNextDifficulty() {
		nextDifficulty = LiveAnalyzerProcessorFactory.getSingleton().getParticipantTimeLine().getDifficultyPredictionAfter(getCurrentTime());
		return nextDifficulty >= 0;
	}
	@Row(1)
	@Column(4)
	@ComponentWidth(100)
	public void nextDifficulty() {
		if (!preNextDifficulty())
			return;
		playBack = true;
		setCurrentFeatureIndex(nextDifficulty);		
	}
	
	@Visible(false)
	public int getNextFeatureIndex() {
		return nextFeatureIndex;
	}
	
	public void setNextFeatureIndex(int newVal) {
		nextFeatureIndex = newVal;
		if (!isPlayBack())
		setCurrentFeatureIndex(nextFeatureIndex -1);
	}
	
	public boolean isPlayBack() {
		return playBack;
	}
	
//	void propagatePre() {
//		//propertyChangeSupport().firePropertyChange("this", null, this);
//    }
	
//	
	
	public static void main (String[] args) {
		ObjectEditor.edit(new AGeneralizedPlayAndRewindCounter());
	}

	@Override
	@Visible(false)
	public long getCurrentWallTime() {
		if (getCurrentTime() >= LiveAnalyzerProcessorFactory.getSingleton().getParticipantTimeLine().getTimeStampList().size())
			return 0;
		return LiveAnalyzerProcessorFactory.getSingleton().getParticipantTimeLine().getTimeStampList().get(getCurrentTime());
	}
}
