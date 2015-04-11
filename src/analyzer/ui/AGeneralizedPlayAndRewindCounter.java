package analyzer.ui;

import util.annotations.Column;
import util.annotations.ComponentWidth;
import util.annotations.Row;
import util.annotations.Visible;
import analyzer.ParticipantTimeLine;
import analyzer.extension.AnalyzerProcessorFactory;
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
	int previousPredictedDifficulty;
	public boolean prePreviousPredictedDifficulty() {
		previousPredictedDifficulty = AnalyzerProcessorFactory.getSingleton().getParticipantTimeLine().getDifficultyPredictionBefore(getCurrentTime());
		return previousPredictedDifficulty >= 0;
	}
	@Row(1)
	@Column(3)
	@ComponentWidth(150)
	public void previousPredictedDifficulty() {
		if (!prePreviousPredictedDifficulty())
			return;
		playBack = true;
		setCurrentFeatureIndex(previousPredictedDifficulty);		
	}
	int nextPredictedDifficulty;
	public boolean preNextPredictedDifficulty() {
		nextPredictedDifficulty = AnalyzerProcessorFactory.getSingleton().getParticipantTimeLine().getDifficultyPredictionAfter(getCurrentTime());
		return nextPredictedDifficulty >= 0;
	}
	@Row(1)
	@Column(4)
	@ComponentWidth(150)
	public void nextPredictedDifficulty() {
		if (!preNextPredictedDifficulty())
			return;
		playBack = true;
		setCurrentFeatureIndex(nextPredictedDifficulty);		
	}
	
	int previousActualDifficulty;
	public boolean prePreviousActualDifficulty() {
		previousActualDifficulty = AnalyzerProcessorFactory.getSingleton().
				getParticipantTimeLine().
				getActualDifficultyBefore(getCurrentTime());
		return previousActualDifficulty >= 0;
	}
	@Row(1)
	@Column(5)
	@ComponentWidth(150)
	public void previousActualDifficulty() {
		if (!prePreviousActualDifficulty())
			return;
		playBack = true;
		setCurrentFeatureIndex(previousActualDifficulty);		
	}
	int nextActualDifficulty;
	public boolean preNextActualDifficulty() {
		nextActualDifficulty = AnalyzerProcessorFactory.getSingleton().getParticipantTimeLine().getActualDifficultyAfter(getCurrentTime());
		return nextActualDifficulty >= 0;
	}
	@Row(1)
	@Column(6)
	@ComponentWidth(150)
	public void nextActualDifficulty() {
		if (!preNextActualDifficulty())
			return;
		playBack = true;
		setCurrentFeatureIndex(nextActualDifficulty);		
	}
	
/////
	int previousBarrier;
	public boolean prePreviousBarrier() {
		previousBarrier = AnalyzerProcessorFactory.getSingleton().
				getParticipantTimeLine().
				getBarrierBefore(getCurrentTime());
		return previousBarrier >= 0;
	}
	@Row(2)
	@Column(0)
	@ComponentWidth(150)
	public void previousBarrier() {
		if (!prePreviousBarrier())
			return;
		playBack = true;
		setCurrentFeatureIndex(previousBarrier);		
	}
	int nextBarrier;
	public boolean preNextBarrier() {
		nextBarrier = AnalyzerProcessorFactory.getSingleton().getParticipantTimeLine().getBarrierAfter(getCurrentTime());
		return nextBarrier >= 0;
	}
	@Row(2)
	@Column(1)
	@ComponentWidth(150)
	public void nextBarrier() {
		if (!preNextBarrier())
			return;
		playBack = true;
		setCurrentFeatureIndex(nextBarrier);		
	}
	
	
////	
	int previousWebLinks;
	public boolean prePreviousWebLinks() {
		previousWebLinks = AnalyzerProcessorFactory.getSingleton().
				getParticipantTimeLine().
				getWebLinksBefore(getCurrentTime());
		return previousWebLinks >= 0;
	}
	@Row(2)
	@Column(2)
	@ComponentWidth(150)
	@Override
	public void previousWebLinks() {
		if (!prePreviousWebLinks())
			return;
		playBack = true;
		setCurrentFeatureIndex(previousWebLinks);		
	}
	int nextWebLinks;
	@Override
	public boolean preNextWebLinks() {
		nextWebLinks = AnalyzerProcessorFactory.getSingleton().getParticipantTimeLine().getWebLinksAfter(getCurrentTime());
		return nextWebLinks >= 0;
	}
	@Row(2)
	@Column(3)
	@ComponentWidth(150)
	@Override
	public void nextWebLinks() {
		if (!preNextWebLinks())
			return;
		playBack = true;
		setCurrentFeatureIndex(nextWebLinks);		
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
		if (getCurrentTime() >= AnalyzerProcessorFactory.getSingleton().getParticipantTimeLine().getTimeStampList().size())
			return 0;
		return AnalyzerProcessorFactory.getSingleton().getParticipantTimeLine().getTimeStampList().get(getCurrentTime());
	}
}
