package analyzer.query;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import analyzer.AParticipantTimeLine;
import analyzer.ParticipantTimeLine;
import difficultyPrediction.featureExtraction.ARatioFeatures;
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
public class AnAggregator implements Aggregator{
	
	/**Make a deepcopy*/

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ParticipantTimeLine deepCopy(ParticipantTimeLine timelineFrom) {
		ParticipantTimeLine newT=new AParticipantTimeLine();
		
		for(Method setMethod:ParticipantTimeLine.class.getMethods()) {
			//get all the setter methods in the newTimeline
			if(setMethod.getName().matches("set.+")) {
				
			
				//find the matching setter
				for(Method getMethod:ParticipantTimeLine.class.getMethods()) {
					if(getMethod.getName().matches("get.+") && (setMethod.getName().split("set")[1]).equals(getMethod.getName().split("get")[1])) {
						try {
							//invoke the set on the new timeline and grabbing the output from the get
							setMethod.invoke(newT, new ArrayList((List) getMethod.invoke(timelineFrom)));
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException 
								| ClassCastException e) {
						
							e.printStackTrace();
						}
						
					}
					
				}
			
			}
		}
	
		
		
		return newT;
	}
	
	public void eliminate(ParticipantTimeLine timeline, List<Integer> eliminate) {
		List<Double> debugList=new ArrayList<>(timeline.getDebugList());
		List<Double> insertionList=new ArrayList<>(timeline.getInsertionList());
		List<Double> deletionList=new ArrayList<>(timeline.getDeletionList());
		List<Double> removeList=new ArrayList<>(timeline.getRemoveList());
		List<Double> navigationList=new ArrayList<>(timeline.getNavigationList());
		List<Double> focusList=new ArrayList<>(timeline.getFocusList());
		
		//set all the elements at the location to null
		for(int i:eliminate) {
			debugList.set(i, null);
			insertionList.set(i, null);
			deletionList.set(i, null);
			removeList.set(i, null);
			navigationList.set(i, null);
			focusList.set(i, null);
			timeline.getPredictionCorrections().set(i, null);
			timeline.getPredictions().set(i, null);
	
			
		}
		Set<?> c=Collections.singleton(null);
		debugList.removeAll(c);
		insertionList.removeAll(c);
		deletionList.removeAll(c);
		removeList.removeAll(c);
		navigationList.removeAll(c);
		focusList.removeAll(c);
		timeline.getPredictions().removeAll(c);
		timeline.getPredictionCorrections().removeAll(c);
		timeline.getWebLinks().removeAll(c);
		
		timeline.setDebugList(debugList);
		timeline.setDeletionList(deletionList);
		timeline.setFocusList(focusList);
		timeline.setInsertionList(insertionList);
		timeline.setNavigationList(navigationList);
		timeline.setRemoveList(removeList);
	
	}
	
	public void eliminateOther(ParticipantTimeLine timeline, List<Integer> keep) {
		List<Double> debugList=new ArrayList<>();
		List<Double> insertionList=new ArrayList<>();
		List<Double> deletionList=new ArrayList<>();
		List<Double> removeList=new ArrayList<>();
		List<Double> navigationList=new ArrayList<>();
		List<Double> focusList=new ArrayList<>();
		List<Integer> predictions=new ArrayList<>();
		List<Integer> predictionsCorrections=new ArrayList<>();
		
		//set all the elements at the location to null
		for(int i:keep) {
			debugList.add(timeline.getDebugList().get(i));
			insertionList.add(timeline.getInsertionList().get(i));
			deletionList.add(timeline.getDeletionList().get(i));
			removeList.add(timeline.getRemoveList().get(i));
			navigationList.add(timeline.getNavigationList().get(i));
			focusList.add(timeline.getFocusList().get(i));
			predictions.add(timeline.getPredictions().get(i));
			predictionsCorrections.add(timeline.getPredictionCorrections().get(i));
			
		}
		
	
		timeline.setDebugList(debugList);
		timeline.setDeletionList(deletionList);
		timeline.setFocusList(focusList);
		timeline.setInsertionList(insertionList);
		timeline.setNavigationList(navigationList);
		timeline.setRemoveList(removeList);
		timeline.setPredictionCorrections(predictionsCorrections);
		timeline.setPredictions(predictions);
	
	}
	
	public RatioFeatures sumRatios(ParticipantTimeLine timeline) {
		RatioFeatures features=new ARatioFeatures();
		
		features.setDebugRatio(sumList(timeline.getDebugList()));
		features.setEditRatio(sumList(timeline.getDeletionList())+sumList(timeline.getInsertionList()));
		features.setNavigationRatio(sumList(timeline.getNavigationList()));
		features.setDeletionTimeRatio(sumList(timeline.getDeletionList()));
		features.setInsertionRatio(sumList(timeline.getInsertionList()));
		features.setFocusRatio(sumList(timeline.getFocusList()));
		features.setRemoveRatio(sumList(timeline.getRemoveList()));
		
		return features;
	}
	
	public RatioFeatures avgRatios(ParticipantTimeLine timeline) {
		RatioFeatures features=new ARatioFeatures();
		int length=timeline.getDebugList().size();
		
		features=sumRatios(timeline);
		
		features.setDebugRatio(features.getDebugRatio()/((double) length));
		features.setInsertionRatio(features.getInsertionRatio()/((double)length));
		features.setDeletionRatio(features.getDeletionRatio()/((double)length));
		features.setRemoveRatio(features.getRemoveRatio()/((double)length));
		features.setEditRatio(features.getEditRatio()/((double)length));
		features.setNavigationRatio(features.getNavigationRatio()/((double)length));
		features.setFocusRatio(features.getFocusRatio()/((double)length));
		
		return features;
	}
	
	private double sumList(List<Double> l) {
		double sum=0;
		
		for(double d:l) {
			sum+=d;
			
		}
		
		return sum;
		
	}
	
	public static void main(String[] args) {
		
		String getter=AParticipantTimeLine.class.getMethods()[1].getName();
		getter=getter.split("get")[1];
		
		System.out.println(getter);
		
		
		
	}
	
}
