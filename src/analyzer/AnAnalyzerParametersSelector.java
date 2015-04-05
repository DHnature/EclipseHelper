package analyzer;

import java.util.List;

import util.annotations.Column;
import util.annotations.Explanation;
import util.annotations.Row;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.misc.Common;
import util.models.ADynamicEnum;
import util.models.DynamicEnum;
import bus.uigen.ObjectEditor;
import difficultyPrediction.APredictionParameters;
import difficultyPrediction.DifficultyPredictionSettings;
import difficultyPrediction.PredictionParameters;
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
// there should really be a has-a relationship between the two
public class AnAnalyzerParametersSelector /*extends APredictionParameters*/  {
	static PredictionParameters instance;

	DynamicEnum<String> participants;
	Analyzer analyzer;
	boolean visualizePrediction;
//	PredictionParameters predictionParameters;
	
	
	public AnAnalyzerParametersSelector(List<String> aParticipants) {
		participants = new ADynamicEnum(aParticipants);
		
	}
	public AnAnalyzerParametersSelector(String[] aParticipants) {
		participants = new ADynamicEnum(Common.arrayToArrayList(aParticipants));
	}
	public AnAnalyzerParametersSelector(Analyzer anAnalyzer) {
		participants = new ADynamicEnum();
		analyzer = anAnalyzer;
	}
	@Row(0)
	public PredictionParameters getPredictionParameters() {
		return APredictionParameters.getInstance();
	}

	@Row(1)
	@Column(0)
	public DynamicEnum<String> getParticipants() {
		return participants;
	}

	public void setParticipants(DynamicEnum<String> aNewVal) {
		participants = aNewVal;
	}
	@Row(1)
	@Column(1)
	public boolean isVisualizePrediction() {
		return visualizePrediction;
	}
//	@Row(1)
	@Row(2)
	@Column(0)
	@Explanation("Load the names of the participants in the selected folder")
	public void loadDirectory() {
		if (analyzer != null)
			analyzer.loadDirectory();
	}
	public boolean preLoadLogs() {
		if (analyzer != null)
		return analyzer.preLoadLogs();
		else return false;
	}
	public boolean preLoadDirectory() {
		if (analyzer != null)
		return analyzer.preLoadDirectory();
		else return false;
	}
//	@Row(1)
	@Row(2)
	@Column(1)
	@Explanation("Loads the logs of a specific participant or all based on the participant selection")
    public void loadLogs() {
		if (analyzer != null)
			analyzer.loadLogs();
		
	}
//	@Row(1)
	@Row(2)
	@Column(2)
	public boolean isNewOutputFiles() {
		
		
		return DifficultyPredictionSettings.isNewRatioFiles();
	}
	
	public void setNewOutputFiles(boolean newRatioFiles) {
//		if (analyzer != null)
//			 analyzer.setNewRatioFiles(newRatioFiles);
		DifficultyPredictionSettings.setNewRatioFiles(newRatioFiles);
	}
	
	public boolean preIsReplayOutputFiles() {
		return !isNewOutputFiles();
	}
	
	@Row(2)
	@Column(3)
	public boolean isReplayOutputFiles() {
		
		
		return DifficultyPredictionSettings.isReplayRatioFiles();
	}
	
	public void setReplayOutputFiles(boolean newVal) {
//		if (analyzer != null)
//			 analyzer.setNewRatioFiles(newRatioFiles);
		DifficultyPredictionSettings.setReplayRatioFiles(newVal);
	}
	
	public void setVisualizePrediction(boolean newVal) {
//		if (newVal) {
//			PredictorConfigurer.visualizePrediction();
//		}
		this.visualizePrediction = newVal;
	}
//	public static AnAnalyzerParametersSelector getInstance() {
//		if (instance == null) {
//			instance = new AnAnalyzerParametersSelector();
//		}
//		return instance;
//	}
	
//	@Column(1)
//	@Explanation("The set of problems in the selected module for which folders have been downloaded. Changing the problem automatically selects the corresponding download folder provided a valid folder has been slected for one of the problems in the module once.")
//
//	public DynamicEnum<String> getProblem() {
//		return problem;
//	}
//
//	public void setProblem(DynamicEnum<String> problem) {
//		this.problem = problem;
//	}
//	
	public static void main (String[] args) {
//		System.out.println(Common.intSuffix("Assignment2"));
//		System.out.println(Common.intSuffix("Assignment"));
		String[] participants = {"1", "2", "3"};
//		
//
//		List<String> modules = Common.arrayToArrayList(new String[] {"Comp110", "Comp401"});
//		List<String> problems = Common.arrayToArrayList(new String[] {"1", "2"});
		AnAnalyzerParametersSelector selector = new AnAnalyzerParametersSelector(participants);
		ObjectEditor.edit(selector);
		

	}
	

}
