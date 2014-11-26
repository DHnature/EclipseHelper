package difficultyPrediction;

import edu.cmu.scs.fluorite.model.EventRecorder;
import analyzer.ui.graphics.LineGraphComposer;
import bus.uigen.ObjectEditor;

public class PredictorConfigurer {
	// add listeners here also
	public static void configure() {
		EventRecorder.setAsyncFireEvent(false);
 		LineGraphComposer.composeUI();
		ObjectEditor.edit(APredictionParameters.getInstance());
 	}

}
