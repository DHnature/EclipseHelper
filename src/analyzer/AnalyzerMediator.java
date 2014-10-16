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
import difficultyPrediction.featureExtraction.ARatioFeatures;
import difficultyPrediction.featureExtraction.RatioBasedFeatureExtractor;
import difficultyPrediction.featureExtraction.RatioFeatures;
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
	public void featureExtractor_HandOffFeatures(RatioBasedFeatureExtractor extractor,
			RatioFeatures details) {

		System.out.println("Insertion ratio:" + details.getInsertionRatio());
		System.out.println("Deletion ratio:" + details.getDeletionRatio());
		System.out.println("Debug ratio:" + details.getDebugRatio());
		System.out.println("Navigation ratio:" + details.getNavigationRatio());
		System.out.println("Focus ratio:" + details.getFocusRatio());
		System.out.println("Remove ratio:" + details.getRemoveRatio());
		System.out.println("features have been computed");
		
		java.util.Date time=new java.util.Date((long)details.getSavedTimeStamp());
		Calendar mydate = Calendar.getInstance();
		mydate.setTimeInMillis(details.getSavedTimeStamp());
		
		//mydate.get(Calendar.HOUR)
		//mydate.get(Calendar.MINUTE)
		//mydate.get(Calendar.SECOND)
		DateTime timestamp = new DateTime(details.getSavedTimeStamp());
		//timestamp.get(timestamp)
		
		System.out.println(timestamp.toString("MM-dd-yyyy H:mm:ss"));
		try
		{
//		    String filename= "/users/jasoncarter/filename.csv";
		    String filename = dataFolder + "ratios.csv";
		    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
		   
		    fw.write(""+ details.getInsertionRatio());
		    fw.write(",");
		    fw.write("" + details.getDeletionRatio());
		    fw.write(",");
		    fw.write("" + details.getDebugRatio());
		    fw.write(",");
		    fw.write("" + details.getNavigationRatio());
		    fw.write(",");
		    fw.write("" + details.getFocusRatio());
		    fw.write(",");
			fw.write("" + details.getRemoveRatio());
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
