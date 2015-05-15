package analyzer;

import java.util.List;
import java.util.Map;
import java.util.Queue;

import util.models.PropertyListenerRegistrar;
import analyzer.extension.StuckInterval;
import analyzer.extension.StuckPoint;
import bus.uigen.models.FileSetterModel;
import difficultyPrediction.DifficultyPredictionPluginEventProcessor;
import edu.cmu.scs.fluorite.commands.ICommand;

public interface Analyzer extends PropertyListenerRegistrar{

	/**Set the output subdirectory.<p>
	 * The output subdirectory is the directory inside of the output directory<br>
	 * I.E.:<br>
	 * data/OutputData/Subdirectory/<br>
	 * Subdirectory is the one we are setting
	 * @param outputDir
	 */
	public void setOutputSubDirectory(String outputDir);
	
	public AnalyzerParameters getParameterSelector();
	
	public abstract FileSetterModel getParticipantsFolder();

	public abstract String getParticipantsFolderName();

	public abstract void setParticipantsFolderName(String aName);

	public abstract boolean preLoadDirectory();

	public abstract void loadDirectory();

	public abstract boolean preLoadLogs();

	public abstract void loadLogs(boolean createNewThread);

	public abstract void processParticipant(String aParticipantId,String outPath,String dataPath, boolean generateRatioFiles);

	public abstract List<List<ICommand>> convertXMLLogToObjects(
			String aFolderName);

	public abstract AnalyzerParameters getAnalyzerParameters();

	public abstract DifficultyPredictionPluginEventProcessor getDifficultyEventProcessor();

	public abstract void setDifficultyEventProcessor(
			DifficultyPredictionPluginEventProcessor difficultyEventProcessor);

	void addAnalyzerListener(AnalyzerListener aListener);

	void removeAnalyzerListener(AnalyzerListener aListener);

	void notifyNewParticipant(String anId, String aFolder);

	void notifyNewBrowseLine(String aLine);

	void notifyStartTimeStamp(long aStartTimeStamp);

	void notifyFinishParticipant(String anId, String aFolder);
	
	Map<String,Queue<StuckPoint>> getStuckPointMap();
	
	Map<String, Queue<StuckInterval>> getStuckIntervalMap();
	
	public String getOutputDirectory();

//	boolean isNewRatioFiles();
//
//	void setNewRatioFiles(boolean newRatioFiles);

}
