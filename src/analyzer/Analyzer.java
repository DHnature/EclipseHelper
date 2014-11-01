package analyzer;

import java.util.List;

import util.annotations.Row;
import util.annotations.Visible;
import bus.uigen.models.FileSetterModel;
import difficultyPrediction.DifficultyPredictionPluginEventProcessor;
import edu.cmu.scs.fluorite.commands.ICommand;

public interface Analyzer {

	public abstract FileSetterModel getParticipantsFolder();

	public abstract String getParticipantsFolderName();

	public abstract void setParticipantsFolderName(String aName);

	public abstract boolean preLoadDirectory();

	public abstract void loadDirectory();

	public abstract boolean preLoadLogs();

	public abstract void loadLogs();

	public abstract void processParticipant(String aParticipantId);

	public abstract List<List<ICommand>> convertXMLLogToObjects(
			String aFolderName);

	public abstract AParametersSelector getParameters();

	public abstract DifficultyPredictionPluginEventProcessor getDifficultyEventProcessor();

	public abstract void setDifficultyEventProcessor(
			DifficultyPredictionPluginEventProcessor difficultyEventProcessor);

	void addAnalyzerListener(AnalyzerListener aListener);

	void removeAnalyzerListener(AnalyzerListener aListener);

	void notifyNewParticipant(String anId);

}