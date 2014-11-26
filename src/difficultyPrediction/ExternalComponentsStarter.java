package difficultyPrediction;

import analyzer.ui.graphics.LineGraphComposer;
import bus.uigen.ObjectEditor;

public class ExternalComponentsStarter {
	// add listeners here also
	public static void startExternalComponents() {
 		LineGraphComposer.composeUI();
		ObjectEditor.edit(APredictionParameters.getInstance());
 	}

}
