package analyzer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import difficultyPrediction.Mediator;
import difficultyPrediction.eventAggregation.EventAggregator;
import difficultyPrediction.eventAggregation.EventAggregatorDetails;
import difficultyPrediction.featureExtraction.ExtractRatiosBasedOnNumberOfEventsAndBasedOnTime;
import difficultyPrediction.featureExtraction.FeatureExtractor;
import difficultyPrediction.featureExtraction.FeatureExtractorDetails;
import difficultyPrediction.predictionManagement.PredictionManager;
import difficultyPrediction.predictionManagement.PredictionManagerDetails;
import difficultyPrediction.statusManager.StatusManager;
import difficultyPrediction.statusManager.StatusManagerDetails;

public class AnalyzerMediator implements Mediator {
	FeatureExtractorAnalyzer featureExtractorAnalyzer;
	File file;
	String participantId;
	public AnalyzerMediator(String participantId) {
		featureExtractorAnalyzer = new FeatureExtractorAnalyzer(this);
		featureExtractorAnalyzer.featureExtractionStrategy = new ExtractRatiosBasedOnNumberOfEventsAndBasedOnTime();
//		 file = new File("/users/jasoncarter/filename.txt");
		 file = new File(MainConsoleUI.PARTICIPANT_INFORMATION_DIRECTORY + "filename.txt");
		 if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}

	@Override
	public void eventAggregator_HandOffEvents(EventAggregator aggregator,
			EventAggregatorDetails details) {
		System.out.println("events have been aggregated");

		this.featureExtractorAnalyzer.performFeatureExtraction(details.actions,
				featureExtractorAnalyzer, details.startTimeStamp);

	}

	@Override
	public void featureExtractor_HandOffFeatures(FeatureExtractor extractor,
			FeatureExtractorDetails details) {

		System.out.println("Insertion ratio:" + details.insertionRatio);
		System.out.println("Deletion ratio:" + details.deletionRatio);
		System.out.println("Debug ratio:" + details.debugRatio);
		System.out.println("Navigation ratio:" + details.navigationRatio);
		System.out.println("Focus ratio:" + details.focusRatio);
		System.out.println("Remove ratio:" + details.removeRatio);
		System.out.println("features have been computed");
		
		java.util.Date time=new java.util.Date((long)details.savedTimeStamp);
		Calendar mydate = Calendar.getInstance();
		mydate.setTimeInMillis(details.savedTimeStamp);
		
		//mydate.get(Calendar.HOUR)
		//mydate.get(Calendar.MINUTE)
		//mydate.get(Calendar.SECOND)
		DateTime timestamp = new DateTime(details.savedTimeStamp);
		//timestamp.get(timestamp)
		
		System.out.println(timestamp.toString("MM-dd-yyyy H:mm:ss"));
		try
		{
//		    String filename= "/users/jasoncarter/filename.csv";
		    String filename = MainConsoleUI.PARTICIPANT_INFORMATION_DIRECTORY + "filename.csv";
		    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
		   
		    fw.write(""+ details.insertionRatio);
		    fw.write(",");
		    fw.write("" + details.deletionRatio);
		    fw.write(",");
		    fw.write("" + details.debugRatio);
		    fw.write(",");
		    fw.write("" + details.navigationRatio);
		    fw.write(",");
		    fw.write("" + details.focusRatio);
		    fw.write(",");
			fw.write("" + details.removeRatio);
			fw.write(",");
			fw.write("" + timestamp.toString("MM-dd-yyyy H:mm:ss"));
			fw.write("\n");
		    fw.close();
		}
		catch(IOException ioe)
		{
		    System.err.println("IOException: " + ioe.getMessage());
		}
		
		

	}

	@Override
	public void predictionManager_HandOffPrediction(PredictionManager manager,
			PredictionManagerDetails details) {
		// not used

	}

	@Override
	public void statusManager_HandOffStatus(StatusManager manager,
			StatusManagerDetails details) {
		// not used

	}

}
