package analyzer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import difficultyPrediction.Mediator;
import difficultyPrediction.eventAggregation.AnEventAggregator;
import difficultyPrediction.eventAggregation.AnEventAggregatorDetails;
import difficultyPrediction.featureExtraction.ExtractRatiosBasedOnNumberOfEventsAndBasedOnTime;
import difficultyPrediction.featureExtraction.ARatioBasedFeatureExtractor;
import difficultyPrediction.featureExtraction.AFeatureExtractorDetails;
import difficultyPrediction.predictionManagement.APredictionManager;
import difficultyPrediction.predictionManagement.APredictionManagerDetails;
import difficultyPrediction.statusManager.StatusManager;
import difficultyPrediction.statusManager.StatusManagerDetails;

public class AnalyzerMediator implements Mediator {
	FeatureExtractorAnalyzer featureExtractorAnalyzer;
	File file;
	String participantId;
	String dataFolder;
	public AnalyzerMediator(String aSpreadsheetFolder, String aParticipantId) {
		participantId = aParticipantId;
		dataFolder = aSpreadsheetFolder;
		featureExtractorAnalyzer = new FeatureExtractorAnalyzer(this);
		featureExtractorAnalyzer.featureExtractionStrategy = new ExtractRatiosBasedOnNumberOfEventsAndBasedOnTime();
//		 file = new File("/users/jasoncarter/filename.txt");
//		 file = new File(MainConsoleUI.PARTICIPANT_INFORMATION_DIRECTORY + "filename.txt");
		 file = new File(dataFolder+ "filename.txt");

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
	public void eventAggregator_HandOffEvents(AnEventAggregator aggregator,
			AnEventAggregatorDetails details) {
		System.out.println("events have been aggregated");

		this.featureExtractorAnalyzer.performFeatureExtraction(details.actions,
				featureExtractorAnalyzer, details.startTimeStamp);

	}

	@Override
	public void featureExtractor_HandOffFeatures(ARatioBasedFeatureExtractor extractor,
			AFeatureExtractorDetails details) {

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
		    String filename = dataFolder + "ratios.csv";
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
	public void predictionManager_HandOffPrediction(APredictionManager manager,
			APredictionManagerDetails details) {
		// not used

	}

	@Override
	public void statusManager_HandOffStatus(StatusManager manager,
			StatusManagerDetails details) {
		// not used

	}

}
