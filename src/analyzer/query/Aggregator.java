package analyzer.query;

import java.util.List;

import analyzer.AParticipantTimeLine;
import analyzer.ParticipantTimeLine;
import difficultyPrediction.featureExtraction.RatioFeatures;

/**Class used for aggregate operations<br>
 * Everything will return a ratio feature with the exception of eliminateOthers and eliminate<p>
 * 
 * Operations:<br>
 * 1.Sum each feature in the participanttimeline<br>
 * 2.Average each feature in the pariticipanttimeline<br>
 * 3. Eliminate will take in a participanttimeline and a List<Integer>.
 * It will eliminate the elements at the index in the List, return a new participanttimeline<br>
 * 4. EliminateOthers will take take in a participanttimeline and a List<Integer>.
 * It will keep all the element at the indexes in the integer and eliminate others.<br>
 * 5. Copy will make a deep copy of the participanttimeline
 * */
public interface Aggregator {

	/**Make a deepcopy*/

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ParticipantTimeLine deepCopy(ParticipantTimeLine timelineFrom);

	public void eliminate(ParticipantTimeLine timeline, List<Integer> eliminate);

	public void eliminateOther(ParticipantTimeLine timeline, List<Integer> keep);

	public RatioFeatures sumRatios(ParticipantTimeLine timeline);

	public RatioFeatures avgRatios(ParticipantTimeLine timeline);

}
