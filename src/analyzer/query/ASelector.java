package analyzer.query;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import analyzer.ParticipantTimeLine;

import com.sun.org.apache.bcel.internal.generic.Select;

/**
 * Class for use with selection operations<p>
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
public class ASelector implements Selector{

	//SELECTOR

	/**Selector Method<p>
	 * See {@link Select}'s description
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> select(ParticipantTimeLine timeline, SelectAttr attr, Comparator comp, double criteria) {
		List<Integer> pass = null;
		String getterName=attr.getGetterName();

		//nothing will pass if the attribute method is not defined
		if((getterName!=null && !getterName.trim().equals(""))) {
			//Get the name of the getter for the select attribute
			//ie: EditRatio's getter in Participant timeline is .getEditList();
			try {
				Object result=ParticipantTimeLine.class.getMethod(attr.getGetterName()).invoke(timeline);

				//If the array is an array of doubles, use the compare method to compare each
				//element of the returned value of getter to the constant
				if(result instanceof List) {
					@SuppressWarnings({ "unchecked", "rawtypes" })
					List l=(List) result;

					//integer
					if(l.get(0) instanceof Double) {
						pass=compare((List<Double>) l,comp,criteria);

						//integer	
					} else {

						pass=compare((List<Integer>) l,comp,(int) criteria);

					}

				}

			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				System.out.println("Oops look like selection failed");
				e.printStackTrace();
			}

		} else {
			pass=new ArrayList<>();

		}

		return pass;

	}

	/**Helper method that gets all index in the list of double that fufills the comparison with the criteria<br>
	 * Please refer to {@link Comparator}, {@link LessThanComparator}, {@link GreaterThanComparator}, or {@link EqualToComparator}
	 * for more info
	 * 
	 * @param l
	 * @param comp
	 * @param criteria
	 * @return
	 */
	private List<Integer> compare(List<Double> l, Comparator comp, double criteria) {
		List<Integer> result=new ArrayList<>();

		for(int i=0; i<l.size();i++) {
			//true if the comparator's criteria is fufilled
			if(comp.compareTo(l.get(i), criteria)) {
				result.add(i);

			}

		}

		return result;

	}
	//Integer version
	private List<Integer> compare(List<Integer> l, Comparator comp, int criteria) {
		List<Integer> result=new ArrayList<>();

		for(int i=0; i<l.size();i++) {


			//true if the comparator's criteria is fufilled
			if(comp.compareTo(l.get(i), criteria)) {
				result.add(i);

			}

		}

		return result;

	}

	//TODO MAKE A FUNCTION THAT TAKES IN A LIST OF FEATURES
	//AND ONE THAT IS THE ONE TO BE COMPARED TO



	//DOMINANCE

	/**Select where the feature was dominant, return a list of indexes where it is 
	 * <p>
	 * 
	 * See {@link Selector}'s definition
	 *  */
	public List<Integer> selectDominantFeature(SelectAttr feature, ParticipantTimeLine timeline, SelectAttr...ignoredFeatures) {
		List<Integer> dominantLocation=new ArrayList<Integer>(); 

		switch(feature) {
		case DEBUG:
			for(int i=0;i<timeline.getDebugList().size();i++) {
				double debug=timeline.getDebugList().get(i);

				//see if the debug value is the greatest of them all
				if(isDominant(debug, 
						isNotIgnored(SelectAttr.NAVIGATION, ignoredFeatures)? timeline.getNavigationList().get(i):Integer.MIN_VALUE,
								isNotIgnored(SelectAttr.FOCUS, ignoredFeatures)? timeline.getFocusList().get(i):Integer.MIN_VALUE,
										isNotIgnored(SelectAttr.EDIT, ignoredFeatures)? timeline.getDeletionList().get(i)+timeline.getInsertionList().get(i):Integer.MIN_VALUE,
												isNotIgnored(SelectAttr.REMOVE, ignoredFeatures)? timeline.getRemoveList().get(i):Integer.MIN_VALUE)) {

					//if dominant then add to index
					dominantLocation.add(i);
				}

			}
			break;
			//edit is insert plus remove
		case EDIT:
			for(int i=0;i<timeline.getInsertionList().size();i++) {
				double edit=timeline.getInsertionList().get(i)+timeline.getDeletionList().get(i);

				//see if the debug value is the greatest of them all
				if(isDominant(edit, 
						isNotIgnored(SelectAttr.NAVIGATION, ignoredFeatures)? timeline.getNavigationList().get(i):Integer.MIN_VALUE,
								isNotIgnored(SelectAttr.FOCUS, ignoredFeatures)? timeline.getFocusList().get(i):Integer.MIN_VALUE,
										isNotIgnored(SelectAttr.DEBUG, ignoredFeatures)? timeline.getDebugList().get(i):Integer.MIN_VALUE,
												isNotIgnored(SelectAttr.REMOVE, ignoredFeatures)? timeline.getRemoveList().get(i):Integer.MIN_VALUE)) {

					//if dominant then add to index
					dominantLocation.add(i);
				}

			}

			break;
		case NAVIGATION:
			for(int i=0;i<timeline.getNavigationList().size();i++) {
				double nav=timeline.getNavigationList().get(i);

				//see if the debug value is the greatest of them all
				if(isDominant(nav, 
						isNotIgnored(SelectAttr.EDIT, ignoredFeatures)? timeline.getDeletionList().get(i)+timeline.getInsertionList().get(i):Integer.MIN_VALUE,
								isNotIgnored(SelectAttr.FOCUS, ignoredFeatures)?timeline.getFocusList().get(i):Integer.MIN_VALUE,
										isNotIgnored(SelectAttr.DEBUG, ignoredFeatures)? timeline.getDebugList().get(i):Integer.MIN_VALUE,
												isNotIgnored(SelectAttr.REMOVE, ignoredFeatures)? timeline.getRemoveList().get(i):Integer.MIN_VALUE)) {

					//if dominant then add to index
					dominantLocation.add(i);
				}


			}

			break;
		case FOCUS:
			for(int i=0;i<timeline.getDebugList().size();i++) {
				double focus=timeline.getFocusList().get(i);

				//see if the debug value is the greatest of them all
				if(isDominant(focus, 
						isNotIgnored(SelectAttr.EDIT, ignoredFeatures)? timeline.getDeletionList().get(i)+timeline.getInsertionList().get(i):Integer.MIN_VALUE,
								isNotIgnored(SelectAttr.NAVIGATION, ignoredFeatures)? timeline.getNavigationList().get(i):Integer.MIN_VALUE,
										isNotIgnored(SelectAttr.DEBUG, ignoredFeatures)? timeline.getDebugList().get(i):Integer.MIN_VALUE,
												isNotIgnored(SelectAttr.REMOVE, ignoredFeatures)? timeline.getRemoveList().get(i):Integer.MIN_VALUE)) {

					//if dominant then add to index
					dominantLocation.add(i);
				}


			}

			break;	
		case REMOVE:
			for(int i=0;i<timeline.getDebugList().size();i++) {
				double remove=timeline.getRemoveList().get(i);

				//see if the debug value is the greatest of them all
				if(isDominant(remove, 
						isNotIgnored(SelectAttr.EDIT, ignoredFeatures)? timeline.getDeletionList().get(i)+timeline.getInsertionList().get(i):Integer.MIN_VALUE,
								isNotIgnored(SelectAttr.NAVIGATION, ignoredFeatures)? timeline.getNavigationList().get(i):Integer.MIN_VALUE,
										isNotIgnored(SelectAttr.DEBUG, ignoredFeatures)? timeline.getDebugList().get(i):Integer.MIN_VALUE,
												isNotIgnored(SelectAttr.FOCUS, ignoredFeatures)? timeline.getFocusList().get(i):Integer.MIN_VALUE)) {

					//if dominant then add to index
					dominantLocation.add(i);
				}


			}


		default:
			break;
		}


		return dominantLocation;
	}

	//SIGNIFICANCE

	/**Select where the feature was significant, aka it is greater than the edit ratio 
	 * See {@link Selector}'s definition
	 * */
	public List<Integer> selectSignificantFeature(SelectAttr feature, ParticipantTimeLine timeline) {
		List<Integer> significantLocation=new ArrayList<Integer>(); 

		switch(feature) {
		case DEBUG:
			for(int i=0;i<timeline.getDebugList().size();i++) {
				double debug=timeline.getDebugList().get(i);

				//see if the debug value is the greater than edit
				if(isDominant(debug, 
						timeline.getDeletionList().get(i)+timeline.getInsertionList().get(i)
						)) {

					//if dominant then add to index
					significantLocation.add(i);
				}

			}

			break;
		case NAVIGATION:
			for(int i=0;i<timeline.getNavigationList().size();i++) {
				double nav=timeline.getNavigationList().get(i);


				if(isDominant(nav, 
						timeline.getDeletionList().get(i)+timeline.getInsertionList().get(i)
						)) {

					//if dominant then add to index
					significantLocation.add(i);
				}


			}

			break;
		case FOCUS:
			for(int i=0;i<timeline.getDebugList().size();i++) {
				double focus=timeline.getFocusList().get(i);


				if(isDominant(focus, 
						timeline.getDeletionList().get(i)+timeline.getInsertionList().get(i)
						)) {

					//if dominant then add to index
					significantLocation.add(i);
				}


			}

			break;
		case REMOVE:
			for(int i=0;i<timeline.getDebugList().size();i++) {
				double remove=timeline.getRemoveList().get(i);


				if(isDominant(remove, 
						timeline.getDeletionList().get(i)+timeline.getInsertionList().get(i)
						)) {

					//if dominant then add to index
					significantLocation.add(i);
				}


			}

			break;
		case EDIT:
		default:

		}


		return significantLocation;
	}

	public Object[] getDominance(ParticipantTimeLine timeline) {
		List<Long> timestamps=new ArrayList<Long>();
		List<String> dominantFeature=new ArrayList<>();

		List<SelectAttr> attr=new ArrayList<>();
		attr.add(SelectAttr.DEBUG);
		attr.add(SelectAttr.FOCUS);
		attr.add(SelectAttr.EDIT);
		attr.add(SelectAttr.NAVIGATION);
		attr.add(SelectAttr.REMOVE);


		for(int i=0;i<timeline.getFocusList().size();i++) {

			

			if(timeline.getPredictions().get(i)==1) {
				dominantFeature.add(getDominantAttribute(
						attr,
						timeline.getDebugList().get(i),
						timeline.getFocusList().get(i),
						timeline.getDeletionList().get(i)+
						timeline.getInsertionList().get(i),
						timeline.getNavigationList().get(i),
						timeline.getRemoveList().get(i)

						).toString());
			
				timestamps.add(timeline.getTimeStampList().get(i));
			}
			
		}


		return new Object[] {timestamps,dominantFeature};
	}

	private SelectAttr getDominantAttribute(List<SelectAttr> attr, double...values) {
		double value=0;
		SelectAttr dominantAttr=null;


		for(int i=0;i<attr.size();i++) {
			if(values[i]>value) {
				value=values[i];
				dominantAttr=attr.get(i);

			}

		}

		return dominantAttr;

	}

	/**See if the value is dominant among the array of other ratios, aka it is the greatest of them all*/
	private boolean isDominant(double value, double...otherratios) {

		for(int i=0;i<otherratios.length;i++) {
			if(value<otherratios[i]) {
				return false;

			}


		}

		return true;
	}


	//Checks if feature is NOT in a list of ignored features
	private boolean isNotIgnored(SelectAttr feature, SelectAttr ...ignoredFeatures) {
		return !Arrays.asList(ignoredFeatures).contains(feature);

	}



}



