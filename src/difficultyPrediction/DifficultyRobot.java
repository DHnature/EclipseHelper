package difficultyPrediction;


import java.sql.Date;
import java.util.Calendar;

//import main.Server;







import trace.difficultyPrediction.NewPredictionEvent;
import trace.difficultyPrediction.NewExtractedFeatures;
import trace.difficultyPrediction.NewPrediction;
import trace.difficultyPrediction.PredictionValueToStatus;
import trace.difficultyPrediction.StatusAggregationStarted;
import util.trace.Tracer;
//import database.Status;
import difficultyPrediction.eventAggregation.ADisjointDiscreteChunks;
import difficultyPrediction.eventAggregation.AnEventAggregator;
import difficultyPrediction.eventAggregation.AnEventAggregatorDetails;
import difficultyPrediction.featureExtraction.ExtractRatiosBasedOnNumberOfEvents;
import difficultyPrediction.featureExtraction.ARatioBasedFeatureExtractor;
import difficultyPrediction.featureExtraction.FeatureExtractorDetails;
import difficultyPrediction.predictionManagement.DecisionTreeModel;
import difficultyPrediction.predictionManagement.APredictionManager;
import difficultyPrediction.predictionManagement.APredictionManagerDetails;
import difficultyPrediction.statusManager.StatusAggregationDiscreteChunks;
import difficultyPrediction.statusManager.StatusManager;
import difficultyPrediction.statusManager.StatusManagerDetails;
import edu.cmu.scs.fluorite.commands.ICommand;
import edu.cmu.scs.fluorite.commands.PredictionCommand;
import edu.cmu.scs.fluorite.commands.PredictionCommand.PredictionType;
import edu.cmu.scs.fluorite.model.EventRecorder;

public class DifficultyRobot implements Mediator {
	//Server server;
	String id = "";
	AnEventAggregator eventAggregator;
	ARatioBasedFeatureExtractor featureExtractor;
	APredictionManager predictionManager;
	StatusManager statusManager;
	private StatusInformation statusInformation;
	
	public DifficultyRobot(String id) {
		this.id = id;
		eventAggregator = new AnEventAggregator(this);
		eventAggregator.eventAggregationStrategy = new ADisjointDiscreteChunks();
		
		featureExtractor = new ARatioBasedFeatureExtractor(this);
		featureExtractor.featureExtractionStrategy = new ExtractRatiosBasedOnNumberOfEvents();
		
		predictionManager = new APredictionManager(this);
		predictionManager.predictionStrategy = new DecisionTreeModel(predictionManager);
		
		statusManager = new StatusManager(this);
		statusManager.strategy = new StatusAggregationDiscreteChunks(statusManager);
		
		//this.server=server;
	}
	
	
	//Aggregate events using aggregator class
	public void processEvent(ICommand e) {
		NewPredictionEvent.newCase(this);
//		Tracer.info(this, "difficultyRobot.processEvent");

			eventAggregator.eventAggregationStrategy.performAggregation(e, eventAggregator);
	}
	
	//Takes aggregated events and uses a separate thread to compute features
	public void eventAggregator_HandOffEvents(AnEventAggregator aggregator,
			AnEventAggregatorDetails details) {

		Tracer.info(this,"difficultyRobot.handoffevents");
		this.featureExtractor.featureExtractionStrategy.performFeatureExtraction(details.actions, featureExtractor);

		
	}


	@Override
	public void featureExtractor_HandOffFeatures(ARatioBasedFeatureExtractor extractor,
			FeatureExtractorDetails details) {
		Tracer.info(this, "difficultyRobot.featureExtractor");
		statusInformation = new StatusInformation();
		statusInformation.setEditRatio(details.editRatio);
		statusInformation.setDebugRatio(details.debugRatio);
		statusInformation.setNavigationRatio(details.navigationRatio);
		statusInformation.setRemoveRatio(details.removeRatio);
		statusInformation.setFocusRatio(details.focusRatio);
		NewExtractedFeatures.newCase(statusInformation, this);

		this.predictionManager.predictionStrategy.predictSituation(details.editRatio, details.debugRatio, details.navigationRatio, details.focusRatio, details.removeRatio);
//		NewPrediction.newCase(this);

	}


	@Override
	public void predictionManager_HandOffPrediction(APredictionManager manager,
			APredictionManagerDetails details) {
		StatusAggregationStarted.newCase(this);
		Tracer.info(this, "difficultyRobot.handOffPrediction");
		statusInformation.predictedClass = "Prediction";
		statusInformation.prediction = details.predictionValue;
		statusInformation.timeStamp = new Date(Calendar.getInstance().getTimeInMillis());
		statusInformation.statusKind = StatusKind.PREDICTION_MADE;
		statusInformation.userId = this.id;
		statusInformation.userName = this.id;
		this.statusManager.strategy.aggregateStatuses(details.predictionValue);
	}


	@Override
	public void statusManager_HandOffStatus(StatusManager manager,
			StatusManagerDetails details) {
		Tracer.info(this, "difficultyRobot.handOffStatus");
		 StatusPrediction statusPrediction = new StatusPrediction();
         statusPrediction.timeStamp = new Date(Calendar.getInstance().getTimeInMillis());
         statusPrediction.prediction = details.predictionValue;
         statusPrediction.userId = this.id;
         statusPrediction.userName = this.id;
         statusPrediction.statusKind = StatusKind.PREDICTION_MADE;
       
         saveToLog(details);
         
         
         //I'm not sure I need this
//         DifficultyPredictionEventArgs eventArgs = new DifficultyPredictionEventArgs();
//         eventArgs.predictionValue = details.predictionValue;
//         eventArgs.id = this.id;
//         server.robot_handOffResultsFromDifficultyRobot(this, eventArgs);
	}
	
	
	public void saveToLog(StatusManagerDetails prediction)
    {
		Tracer.info(this, "Saving to log:" + prediction);
		PredictionValueToStatus.newCase(this);
		 PredictionType predictionType = PredictionType.MakingProgress;
		 if(prediction.predictionValue.equals("NO"))
         {
        	 predictionType = PredictionType.MakingProgress;
         }
         if(prediction.predictionValue.equals("TIE"))
         {
        	 predictionType = PredictionType.Indeterminate;
         }
         if(prediction.predictionValue.equals("YES"))
         {
        	 predictionType = PredictionType.HavingDifficulty;
         }
    
         PredictionCommand predictionCommand = new PredictionCommand(predictionType);
         EventRecorder.getInstance().recordCommand(predictionCommand);
    }
	
}
