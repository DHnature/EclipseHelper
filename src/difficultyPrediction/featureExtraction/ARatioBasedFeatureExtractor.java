package difficultyPrediction.featureExtraction;

import difficultyPrediction.Mediator;

public class ARatioBasedFeatureExtractor {
	
	Mediator mediator;
	
	public ARatioBasedFeatureExtractor(Mediator mediator) {
		this.mediator = mediator;
	}
	
	public FeatureExtractionStrategy featureExtractionStrategy;
	
	public void onFeatureHandOff(double editRatio, double debugRatio, double navigationRatio, double focusRatio,
			double removeRatio) {
		if (mediator != null)                           //Any handlers attached to this event?  
        {
            FeatureExtractorDetails args = new FeatureExtractorDetails();
            args.editRatio = editRatio;
            args.debugRatio = debugRatio;
            args.navigationRatio = navigationRatio;
            args.focusRatio = focusRatio;
            args.removeRatio = removeRatio;
            args.exceptionsPerRun = 0;
            mediator.featureExtractor_HandOffFeatures(this, args);                       //Raise the event  
        }
	}
	
	 public void onFeatureHandOff(double editRatio, double debugRatio, double navigationRatio, double focusRatio, double removeRatio, double exceptionsPerRun)
     {
		 if (mediator != null)                           //Any handlers attached to this event?  
	        {
	            FeatureExtractorDetails args = new FeatureExtractorDetails();
	            args.editRatio = editRatio;
	            args.debugRatio = debugRatio;
	            args.navigationRatio = navigationRatio;
	            args.focusRatio = focusRatio;
	            args.removeRatio = removeRatio;
	            args.exceptionsPerRun = exceptionsPerRun;
	            mediator.featureExtractor_HandOffFeatures(this, args);                       //Raise the event  
	        }
		 
     }
	 
	 
	
}
