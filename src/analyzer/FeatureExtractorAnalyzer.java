package analyzer;

import java.util.List;

import difficultyPrediction.Mediator;
import difficultyPrediction.featureExtraction.FeatureExtractionStrategy;
import difficultyPrediction.featureExtraction.FeatureExtractorDetails;
import edu.cmu.scs.fluorite.commands.ICommand;

public class FeatureExtractorAnalyzer {

	Mediator mediator;

	public FeatureExtractorAnalyzer(Mediator mediator) {
		this.mediator = mediator;
	}

	public FeatureExtractionStrategy featureExtractionStrategy;
	private TimeandEventBasedPercentage metrics = new TimeandEventBasedPercentage();

	private static final int NAVIGATION_PERCENTAGE = 0;
	private static final int DEBUG_PERCENTAGE = 1;
	private static final int FOCUS_PERCENTAGE = 2;
	private static final int EDIT_PERCENTAGE = 3;
	private static final int REMOVE_PERCENTAGE = 4;
	private static final int INSERTION_PERCENTAGE = 5;
	private static final int DELETION_PERCENTAGE = 6;
	private static final int EXCEPTIONS_PER_RUNS_PERCENTAGE = 7;

	public void performFeatureExtraction(List<ICommand> actions,
			FeatureExtractorAnalyzer featureExtractorAnalyzer, long startTimeStamp) {
		List<Double> percentages = null;
		percentages = metrics.computeMetrics(actions, startTimeStamp);
		onFeatureHandOff(percentages.get(EDIT_PERCENTAGE),
				percentages.get(DEBUG_PERCENTAGE),
				percentages.get(NAVIGATION_PERCENTAGE),
				percentages.get(FOCUS_PERCENTAGE),
				percentages.get(REMOVE_PERCENTAGE),
				percentages.get(INSERTION_PERCENTAGE),
				percentages.get(DELETION_PERCENTAGE),
				percentages.get(EXCEPTIONS_PER_RUNS_PERCENTAGE), 0, 0, 0, 0, 0,
				0);

		// featureExtractorAnalyzer.onFeatureHandOff(percentages.get(EDIT_PERCENTAGE),
		// percentages.get(DEBUG_PERCENTAGE),
		// percentages.get(NAVIGATION_PERCENTAGE),
		// percentages.get(FOCUS_PERCENTAGE),
		// percentages.get(REMOVE_PERCENTAGE),
		// percentages.get(INSERTION_PERCENTAGE),
		// percentages.get(DELETION_PERCENTAGE),
		// percentages.get(EXCEPTIONS_PER_RUNS_PERCENTAGE),
		// null,null,null,null,null,null);
	}

	public void onFeatureHandOff(double editRatio, double debugRatio,
			double navigationRatio, double focusRatio, double removeRatio,
			double insertionRatio, double deletionRatio,
			double exceptionsPerRun, double insertionTimeRatio,
			double deletionTimeRatio, double debugTimeRatio,
			double navigationTimeRatio, double focusTimeRatio,
			double removeTimeRatio) {
		if (mediator != null) // Any handlers attached to this event?
		{
			FeatureExtractorDetails args = new FeatureExtractorDetails();
			args.editRatio = editRatio;
			args.debugRatio = debugRatio;
			args.navigationRatio = navigationRatio;
			args.focusRatio = focusRatio;
			args.removeRatio = removeRatio;
			args.exceptionsPerRun = exceptionsPerRun;
			args.insertionRatio = insertionRatio;
			args.deletionRatio = deletionRatio;
			args.insertionTimeRatio = insertionTimeRatio;
			args.deletionTimeRatio = deletionTimeRatio;
			args.debugTimeRatio = debugTimeRatio;
			args.navigationTimeRatio = navigationTimeRatio;
			args.focusTimeRatio = focusTimeRatio;
			args.removeTimeRatio = removeTimeRatio;
			args.savedTimeStamp = metrics.getCurrentTimeStamp();
			mediator.featureExtractor_HandOffFeatures(null, args); // Raise
			// the event
		}
	}
}
