package analyzer.query;

import java.util.List;
import java.util.Map;

import analyzer.ParticipantTimeLine;
import difficultyPrediction.featureExtraction.RatioFeatures;

public interface DataNormalizer {

	public Map<String, ParticipantTimeLine> normalizeData(List<RatioFeatures> data);
	
	public Map<String, ParticipantTimeLine> normalizeData(ParticipantTimeLine data, String name);
	
	public Map<String, ParticipantTimeLine> normalizeData(Map<String,ParticipantTimeLine> data);
	
}
