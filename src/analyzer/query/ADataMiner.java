package analyzer.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import analyzer.AParticipantTimeLine;
import analyzer.ParticipantTimeLine;



/**
 * Class that serves as the mediator for mining the information from various sources
 * 
 * <p>
 * Ability:<br>
 * -Can use console to listen or query or pass query via<p>
 * 
 * Use:<p>
 * 
 * 1. Call the static method set data source method to set a data source<br>
 * 	-Must be one of the preadded data sources<br>
 *  -USE THE {@link DataNormalizer} and the {@link ADataNormalizer} to add in more data sources<p>
 * 2.
 * 
 * */
public class ADataMiner {
	private final static Selector selector=new ASelector();
	private final static Aggregator aggregator=new AnAggregator();

	private static Map<String,ParticipantTimeLine> data;



	//DATA SOURCE METHODS



	/**Parse the string query as query on the data source set via the setDataSource Method
	 * 
	 * @param query , the query itself
	 * @return true/false, whether the parse was successful
	 */
	public static boolean parseQuery(String query) {

		return true;
	}

	/**Class containing static methods that */
	public static class HelperMethods{

		/**
		 * Returns where attribute is the most dominant out of other attributes in data
		 * */
		public static Map<String, ParticipantTimeLine> selectWhereDominant(String attribute
				, Map<String, ParticipantTimeLine> data, String ... ignoreAttribute) {

			Map<String, ParticipantTimeLine> map=new HashMap<>();

			List<SelectAttr> ignoredAttr=convertToSelectAttrList(ignoreAttribute);

			List<Integer> results=null;
			for(String key:data.keySet()) {
				results=selector.selectDominantFeature(SelectAttr.getAttributeFromString(attribute)
						, data.get(key),ignoredAttr.toArray(new SelectAttr[ignoredAttr.size()]));

				//deep copy of the participanttimeline
				ParticipantTimeLine copy=aggregator.deepCopy(data.get(key));

				//eliminate the other results not in result list
				aggregator.eliminateOther(copy, results);

				map.put(key,copy);
			}

			System.out.println(results);

			return map;
		}

		/***/
		public static Map<String, ParticipantTimeLine> selectWhereSignificant(String attribute
				, Map<String, ParticipantTimeLine> data) {

			Map<String, ParticipantTimeLine> map=new HashMap<>();

			List<Integer> results=null;
			for(String key:data.keySet()) {
				results=selector.selectSignificantFeature(SelectAttr.getAttributeFromString(attribute)
						, data.get(key));

				//deep copy of the participanttimeline
				ParticipantTimeLine copy=aggregator.deepCopy(data.get(key));

				//eliminate the other results not in result list
				aggregator.eliminateOther(copy, results);

				map.put(key,copy);
			}

			System.out.println(results);

			return map;
		}

		public static Map<String,ParticipantTimeLine> selectWhereCorrectionMade(Map<String, ParticipantTimeLine> data) {
			Map<String, ParticipantTimeLine> map=new HashMap<>();

			List<Integer> results=null;
			for(String key:data.keySet()) {
				results=selector.select(data.get(key), SelectAttr.CORRECTION,
						new NotEqualToComparator(),-1);

				//deep copy of the participanttimeline
				ParticipantTimeLine copy=aggregator.deepCopy(data.get(key));

				//eliminate the other results not in result list
				aggregator.eliminateOther(copy, results);

				map.put(key,copy);
			}

			System.out.println(results);

			return map;


		}

		public static  Map<String,ParticipantTimeLine> selectWhereNonEditHighAndNoPrediction(Map<String, ParticipantTimeLine> data) {

			Map<String, ParticipantTimeLine> map=new HashMap<>();

			List<Integer> results=null;
			for(String key:data.keySet()) {
				//find where edit is dominant,
				results=selector.selectDominantFeature(SelectAttr.getAttributeFromString("edit")
						, data.get(key));

				//deep copy of the participanttimeline
				ParticipantTimeLine copy=aggregator.deepCopy(data.get(key));

				//eliminate where edit is the highest
				aggregator.eliminate(copy, results);

				//now choose where there is not any difficulty
				results=selector.select(copy, SelectAttr.getAttributeFromString("prediction"), new EqualToComparator(), 0);

				//only choose where there is no difficulty
				aggregator.eliminateOther(copy, results);

				map.put(key,copy);
			}

			System.out.println(results);

			return map;


		}	
		
		public static  Map<String,ParticipantTimeLine> selectWhereEditHighAndPrediction(Map<String, ParticipantTimeLine> data) {

			Map<String, ParticipantTimeLine> map=new HashMap<>();

			List<Integer> results=null;
			for(String key:data.keySet()) {
				//find where edit is dominant,
				results=selector.selectDominantFeature(SelectAttr.getAttributeFromString("edit")
						, data.get(key));

				//deep copy of the participanttimeline
				ParticipantTimeLine copy=aggregator.deepCopy(data.get(key));

				//keep where edit is the highest
				aggregator.eliminateOther(copy, results);

				//now choose where there is not any difficulty
				results=selector.select(copy, SelectAttr.getAttributeFromString("prediction"), new EqualToComparator(), 1);

				//only choose where there is difficulty
				aggregator.eliminateOther(copy, results);

				System.out.println(results);
				
				map.put(key,copy);
			}

			System.out.println(results);

			return map;


		}	
		


		private static List<SelectAttr> convertToSelectAttrList(String[] attrs) {
			List<SelectAttr> attrList=new ArrayList<>();

			for(String a:attrs) {
				attrList.add(SelectAttr.getAttributeFromString(a));

			}

			attrList.removeAll(Collections.singleton(null));

			return attrList;
		}
	}

	public static class ConsoleListener{
		private Scanner console;

		public ConsoleListener() {
			console=new Scanner(System.in);

		}


	}

	public static void main(String[] args) {
		ParticipantTimeLine t=new AParticipantTimeLine();

		t.setDebugList(new ArrayList<>(Arrays.asList(new Double[] {
				105d,20d,60d,90d,30d, 20d

		})));

		t.setInsertionList(new ArrayList<>(Arrays.asList(new Double[] {
				30d, 20d, 20d,100d, 10d, 30d

		})));


		t.setDeletionList(new ArrayList<>(Arrays.asList(new Double[] {
				20d, 10d, 20d, 0d, 20d, 10d

		})));


		t.setFocusList(new ArrayList<>(Arrays.asList(new Double[] {
				20d, 21d, 5d, 0d, 10d, 20d

		})));

		t.setRemoveList(
				new ArrayList<>(Arrays.asList(new Double[] {
						5d, 75d, 5d, 0d, 10d, 40d

				}))	);


		t.setNavigationList(new ArrayList<>(Arrays.asList(new Double[] {
				15d, 15d, 0d, 0d, 20d, 20d

		})));
		
		t.setPredictions(new ArrayList<>(Arrays.asList(new Integer[] {
				0, 0, 1, 1, 0, 0

		})));;

		t.setPredictionCorrections(new ArrayList<>(Arrays.asList(new Integer[] {
				-1, 1, -1, 0, -1, 1

		})));;

		Map<String, ParticipantTimeLine> data=new HashMap<>();
		data.put("1", t);
		
		ADataMiner.HelperMethods.selectWhereDominant("debug", data);
		
		//		System.out.println(s.selectSignificantFeature(SelectAttr.getAttributeFromString("debug"), t));
		//		
		//		ParticipantTimeLine t1=a.deepCopy(t);
		//		a.eliminateOther(t1,s.selectSignificantFeature(SelectAttr.getAttributeFromString("debug"), t));



		//Comparator comp=new NotEqualToComparator();
		//System.out.println(s.select(t, SelectAttr.CORRECTION, comp, -1));

		//data=ADataMiner.HelperMethods.selectWhereSignificant("edit", data);

		data=ADataMiner.HelperMethods.selectWhereEditHighAndPrediction(data);

		System.out.println("Done!");
	}


}
