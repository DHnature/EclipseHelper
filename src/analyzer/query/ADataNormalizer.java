package analyzer.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import analyzer.AParticipantTimeLine;
import analyzer.ParticipantTimeLine;
import difficultyPrediction.featureExtraction.ARatioFeatures;
import difficultyPrediction.featureExtraction.RatioFeatures;

public class ADataNormalizer implements DataNormalizer {
	//just radom keys for hashmap
	private static int names=0;
	
	/**DO NOT USE NOT YET SUPPORTED*/
	@Override
	@Deprecated
	public Map<String, ParticipantTimeLine> normalizeData(
			List<RatioFeatures> data) {
		
		Map<String,ParticipantTimeLine> map=new HashMap<>();
		ParticipantTimeLine timeline=new AParticipantTimeLine();
		
		//timeline.setNavigationList(ratio);
		
		//map.put(names+++"", data);
		
		return map;
	}


	@Override
	public Map<String, ParticipantTimeLine> normalizeData(
			Map<String, ParticipantTimeLine> data) {
		
		return data;
	}


	@Override
	public Map<String, ParticipantTimeLine> normalizeData(
			ParticipantTimeLine data, String name) {
		Map<String,ParticipantTimeLine> map=new HashMap<>();
		map.put(names+++"", data);
		
		return map;
	}
	
}
