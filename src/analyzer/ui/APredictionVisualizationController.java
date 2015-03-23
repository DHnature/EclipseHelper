package analyzer.ui;

import difficultyPrediction.APredictionParameters;
import analyzer.ui.balloons.ABalloonCreator;
import analyzer.ui.graphics.LineGraphComposer;
import analyzer.ui.text.AMultiLevelAggregator;
import analyzer.ui.video.ALocalScreenRecorderAndPlayer;
import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;

public class APredictionVisualizationController implements PredictionVisualizationController {
	
	protected GeneralizedPlayAndRewindCounter player;
	
	public APredictionVisualizationController() {
		player = PlayerFactory.getSingleton();
	}

	@Override
	public void lineGraph() {
		LineGraphComposer.composeUI();
	}

	@Override
	public void multilevelAggregator() {
		AMultiLevelAggregator.createUI();		
	}

	@Override
	public void localScreenRecorderAndPlayer() {
 		ALocalScreenRecorderAndPlayer.createUI();		
	}

	@Override
	public void predictionParameters() {
		APredictionParameters.createUI();		
	}

	@Override
	public void balloonCreator() { 		
 		ABalloonCreator.createUI();	
	}

	@Override
	public GeneralizedPlayAndRewindCounter getPlayer() {
		// TODO Auto-generated method stub
		return player;
	}
	
	public static void createUI() {
		OEFrame oeFrame = ObjectEditor.edit(PredictionVisualizationControllerFactory.getSingleton());
		oeFrame.setSize(800, 150);
	}
	
	public static void main(String[] args) {
		ObjectEditor.edit(new APredictionVisualizationController());
	}
	
}
