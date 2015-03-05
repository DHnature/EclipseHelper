package analyzer.query;

import java.util.List;

import analyzer.ParticipantTimeLine;

/**
 * Interface for use with selection operations<p>
 * Every method here will return an integer list of the entries in participanttimeline that fufills the criteria
 * 
 * Operations:<br>
 * 1. Select dominant features<br>
 * 2. Select significant(greater than edit)<br>
 * 3. Select features/other criteria(Like number of correction) greater/less than or equal to a threshold
 * 
 * @author wangk1
 *
 */
public interface Selector{

	//SELECTOR
	
	/**Selector Method<p>
	 * Choose all index of the attribute(Features, etc.) that fufills the criteria parameter
	 * <br>
	 * The {@link Comparator} will choose whether to deny or accept a element of an index.
	 * <br>
	 * The comparator is basically a single method class that will return wether a is great than, less than, or equal to b, two of its inputs
	 *
	 * @param timeline , the data
	 * @param attr , the attribute(Feature, or some other thing like 
	 * @param comp , the comparator. This can be less than, greater, or the equals.
	 * @param criteria , the criteria to be compared to
	 * @return
	 */
	public List<Integer> select(ParticipantTimeLine timeline, SelectAttr attr, Comparator comp, double criteria);

	//DOMINANCE

	/**Select where the feature was dominant, return a list of indexes where it is 
	 * 
	 * @param feature , the feature to check for dominance
	 * @param timeline , the timeline to check
	 * @param ignoredFeatures, the list of features to ignore while checking for dominance
	 * @return	An integer list of the index in the timeline where the feature param was dominant
	 * */
	public List<Integer> selectDominantFeature(SelectAttr feature, ParticipantTimeLine timeline, SelectAttr...ignoredFeatures);

	//SIGNIFICANCE

	/**Select where the feature was significant, aka it is greater than the edit ratio 
	 * 
	 * @param feature , the feature to check for significance
	 * @param timeline , the timeline to check
	 * @param ignoredFeatures, the list of features to ignore while checking for dominance
	 * @return	An integer list of the index in the timeline where the feature param was significant
	 * */
	public List<Integer> selectSignificantFeature(SelectAttr feature, ParticipantTimeLine timeline);

	

}


