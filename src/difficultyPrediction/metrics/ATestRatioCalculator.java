package difficultyPrediction.metrics;

import java.util.ArrayList;
import java.util.List;

import analyzer.TimeandEventBasedPercentage;
import edu.cmu.scs.fluorite.commands.CompilationCommand;
import edu.cmu.scs.fluorite.commands.EclipseCommand;
import edu.cmu.scs.fluorite.commands.ICommand;


public class ATestRatioCalculator implements RatioCalculator {
//	public enum Features{
//		A0("leaveoneouta0/"),
//		A1("leaveoneouta1/"),
//		A2("leaveoneouta2/"),
//		A3("leaveoneouta3/");
//		
//		private String dir;
//		
//		private Features(String dir) {
//			this.dir=dir;
//			
//		}
//
//		public String getSubDir() {
//			return this.dir;
//			
//		}
//		
//	}

	public static RatioScheme CURRENT_SCHEME=RatioScheme.A1;
	public final static int DEBUG_EVENT_INDEX = 0;
	public final static int SEARCH_EVENT_INDEX = 1;
	public final static int EDIT_EVENT_INDEX = 2;
	public final static int FOCUS_EVENT_INDEX = 3;
	public final static int REMOVE_EVENT_INDEX = 4;
	static RatioCalculator instance;

	//jason's calculator, used to calculate the percentage with his calculator
	private TimeandEventBasedPercentage jasonCalculator=new TimeandEventBasedPercentage();
	private RatioCalculator r=new APercentageCalculator();

	/* (non-Javadoc)
	 * @see difficultyPrediction.metrics.FeatureCalculator#isDebugEvent(edu.cmu.scs.fluorite.commands.ICommand)
	 */
	@Override
	public boolean isDebugEvent(ICommand event) {
		boolean isDebugEvent = false;
		if ((event.getCommandType().equals("BreakPointCommand"))
				|| (event.getCommandType().equals("ExceptionCommand"))
				|| (event.getCommandType().equals("RunCommand"))) {
			isDebugEvent = true;
		}

		if(event.getCommandType().equals("CompilationCommand"))
		{
			CompilationCommand command = (CompilationCommand)event;
			//if the compilation is an error
			if(! command.getIsWarning())
			{
				isDebugEvent = true;
			}
		}

		return isDebugEvent;
	}
	// switch(event.getEventKind()) {
	// case DEBUG:
	// isDebugEvent = true;
	// break;
	// /*case BUILD:
	// isDebugEvent = true;
	// break;*/
	// }
	/* (non-Javadoc)
	 * @see difficultyPrediction.metrics.FeatureCalculator#isEditEvent(edu.cmu.scs.fluorite.commands.ICommand)
	 */
	@Override
	public boolean isEditEvent(ICommand event) {
		boolean isEditEvent = false;
		if ((event.getCommandType().equals("CopyCommand"))

				|| (event.getCommandType().equals("Insert"))
				|| (event.getCommandType().equals("InsertStringCommand"))
				|| (event.getCommandType().equals("PasteCommand"))
				|| (event.getCommandType().equals("RedoCommand"))
				|| (event.getCommandType().equals("SelectTextCommand"))
				|| (event.getCommandType().equals("CutCommand"))

				|| (event.getCommandType().equals("Replace"))



				) {
			isEditEvent = true;
		}

		if (event.getCommandType().equals("EclipseCommand")) {
			EclipseCommand eclipseCommand = (EclipseCommand) event;
			if (eclipseCommand.getCommandID().toLowerCase().contains("edit")) {
				isEditEvent = true;
			}
		}

		return isEditEvent;
	}
	// switch(event.getEventKind()) {
	// case EDIT:
	// isEditEvent=true;
	// break;
	// case COMMAND:
	// if (event.getCommandId() != null) {
	// if(event.getCommandId().toLowerCase().contains("edit")) {
	// isEditEvent = true;
	// }
	// }
	// break;
	// }
	/* (non-Javadoc)
	 * @see difficultyPrediction.metrics.FeatureCalculator#isNavigationEvent(edu.cmu.scs.fluorite.commands.ICommand)
	 */
	@Override
	public boolean isNavigationEvent(ICommand event) {
		boolean isNavigationEvent = false;
		if (event.getCommandType().equals("EclipseCommand")) {
			EclipseCommand eclipseCommand = (EclipseCommand) event;
			if (eclipseCommand.getCommandID().toLowerCase().contains("view")) {
				isNavigationEvent = true;
			}
		}

		if (event.getCommandType().equals("FileOpenCommand")
				|| (event.getCommandType().equals("FindCommand"))) {
			isNavigationEvent = true;
		}

		// switch(event.getEventKind())
		// {
		// case COMMAND:
		// if (event.getCommandId() != null)
		// {
		// if (event.getCommandId().toLowerCase().contains("view"))
		// {
		// isNavigationEvent = true;
		// }
		// }
		// break;
		// case WINDOW:
		// if (event.getWindowEventKind() == WindowEventKind.ACTIVATED)
		// {
		// if (event.getWindowTitle() != null)
		// {
		// if (event.getWindowTitle().contains(".cs") ||
		// event.getWindowTitle().contains(".cpp") ||
		// event.getWindowTitle().contains(".java"))
		// {
		// isNavigationEvent = true;
		// }
		//
		// }
		// }
		// break;
		// }
		return isNavigationEvent;
	}

	/* (non-Javadoc)
	 * @see difficultyPrediction.metrics.FeatureCalculator#isFocusEvent(edu.cmu.scs.fluorite.commands.ICommand)
	 */
	@Override
	public boolean isFocusEvent(ICommand event) {
		boolean isFocusEvent = false;

		if (event.getCommandType().equals("ShellCommand")) {
			isFocusEvent = true;
		}
		// switch(event.getEventKind())
		// {
		// case WINDOW:
		// if (event.getWindowEventKind() == WindowEventKind.ECLIPSE_LOST_FOCUS)
		// isFocusEvent = true;
		// break;
		// }

		return isFocusEvent;
	}

	/* (non-Javadoc)
	 * @see difficultyPrediction.metrics.FeatureCalculator#isAddRemoveEvent(edu.cmu.scs.fluorite.commands.ICommand)
	 */
	@Override
	public boolean isAddRemoveEvent(ICommand event) {
		boolean isAddRemoveEvent = false;

		if (event.getCommandType().equals("Delete")
				|| (event.getCommandType().equals("UndoCommand")))
			isAddRemoveEvent=true;


		return isAddRemoveEvent;
	}

	public static double computeDebugPercentage(ArrayList<Integer> eventData) {
		double numberOfDebugEvents = eventData.get(DEBUG_EVENT_INDEX);
		double numberOfSearchEvents = eventData.get(SEARCH_EVENT_INDEX);
		double numberOfEditEvents = eventData.get(EDIT_EVENT_INDEX);
		double numberOfFocusEvents = eventData.get(FOCUS_EVENT_INDEX);
		double numberOfRemoveEvents = eventData.get(REMOVE_EVENT_INDEX);

		double debugPercentage = 0;

		if (numberOfDebugEvents > 0)
			debugPercentage = (numberOfDebugEvents / (numberOfDebugEvents
					+ numberOfSearchEvents + numberOfEditEvents
					+ numberOfFocusEvents + numberOfRemoveEvents)) * 100;

		return debugPercentage;
	}

	public static double computeNavigationPercentage(
			ArrayList<Integer> eventData) {
		double numberOfDebugEvents = eventData.get(DEBUG_EVENT_INDEX);
		double numberOfSearchEvents = eventData.get(SEARCH_EVENT_INDEX);
		double numberOfEditEvents = eventData.get(EDIT_EVENT_INDEX);
		double numberOfFocusEvents = eventData.get(FOCUS_EVENT_INDEX);
		double numberOfRemoveEvents = eventData.get(REMOVE_EVENT_INDEX);

		double searchPercentage = 0;

		if (numberOfSearchEvents > 0)
			searchPercentage = (numberOfSearchEvents / (numberOfDebugEvents
					+ numberOfSearchEvents + numberOfEditEvents
					+ numberOfFocusEvents + numberOfRemoveEvents)) * 100;

		return searchPercentage;
	}

	public static double computeEditPercentage(ArrayList<Integer> eventData) {
		double numberOfDebugEvents = eventData.get(DEBUG_EVENT_INDEX);
		double numberOfSearchEvents = eventData.get(SEARCH_EVENT_INDEX);
		double numberOfEditEvents = eventData.get(EDIT_EVENT_INDEX);
		double numberOfFocusEvents = eventData.get(FOCUS_EVENT_INDEX);
		double numberOfRemoveEvents = eventData.get(REMOVE_EVENT_INDEX);

		double editPercentage = 0;

		if (numberOfEditEvents > 0)
			editPercentage = (numberOfEditEvents / (numberOfDebugEvents
					+ numberOfSearchEvents + numberOfEditEvents
					+ numberOfFocusEvents + numberOfRemoveEvents)) * 100;

		return editPercentage;
	}

	public static double computeFocusPercentage(ArrayList<Integer> eventData) {
		double numberOfDebugEvents = eventData.get(DEBUG_EVENT_INDEX);
		double numberOfSearchEvents = eventData.get(SEARCH_EVENT_INDEX);
		double numberOfEditEvents = eventData.get(EDIT_EVENT_INDEX);
		double numberOfFocusEvents = eventData.get(FOCUS_EVENT_INDEX);
		double numberOfRemoveEvents = eventData.get(REMOVE_EVENT_INDEX);

		double focusPercentage = 0;

		if (numberOfFocusEvents > 0)
			focusPercentage = (numberOfFocusEvents / (numberOfDebugEvents
					+ numberOfSearchEvents + numberOfEditEvents
					+ numberOfFocusEvents + numberOfRemoveEvents)) * 100;

		return focusPercentage;
	}

	public static double computeRemoveEventsPercentage(
			ArrayList<Integer> eventData) {
		double numberOfDebugEvents = eventData.get(DEBUG_EVENT_INDEX);
		double numberOfSearchEvents = eventData.get(SEARCH_EVENT_INDEX);
		double numberOfEditEvents = eventData.get(EDIT_EVENT_INDEX);
		double numberOfFocusEvents = eventData.get(FOCUS_EVENT_INDEX);
		double numberOfRemoveEvents = eventData.get(REMOVE_EVENT_INDEX);

		double removePercentage = 0;

		if (numberOfRemoveEvents > 0)
			removePercentage = (numberOfRemoveEvents / (numberOfDebugEvents
					+ numberOfSearchEvents + numberOfEditEvents
					+ numberOfFocusEvents + numberOfRemoveEvents)) * 100;

		return removePercentage;
	}

	/* (non-Javadoc)
	 * @see difficultyPrediction.metrics.FeatureCalculator#computeMetrics(java.util.List)
	 */
	@Override
	public ArrayList<Double> computeMetrics(List<ICommand> userActions) {
		ArrayList<Integer> metrics = getPercentageData(userActions);
		double debugPercentage = computeDebugPercentage(metrics);
		double editPercentage = computeEditPercentage(metrics);
		double navigationPercentage = computeNavigationPercentage(metrics);
		double removePercentage = computeRemoveEventsPercentage(metrics);
		double focusPercentage = computeFocusPercentage(metrics);
		// int searchPercentage, int debugPercentage, int focusPercentage , int
		// editPercentage, int removePercentage
		ArrayList<Double> percentages = new ArrayList<Double>();
		percentages.add(navigationPercentage);
		percentages.add(debugPercentage);
		percentages.add(focusPercentage);
		percentages.add(editPercentage);
		percentages.add(removePercentage);

		return percentages;
	}

	/* (non-Javadoc)
	 * @see difficultyPrediction.metrics.FeatureCalculator#getPercentageData(java.util.List)
	 */
	@Override
	public  ArrayList<Integer> getPercentageData(List<ICommand> userActions) {
		int numberOfDebugEvents = 0;
		int numberOfSearchEvents = 0;
		int numberOfEditEvents = 0;
		int numberOfFocusEvents = 0;
		int numberOfRemoveEvents = 0;

		for (int i = 0; i < userActions.size(); i++) {
			ICommand myEvent = userActions.get(i);


			switch(CURRENT_SCHEME) {
			case A0:

				//jason's edit scheme
				//System.out.println ("remove command:" + myEvent);
				if (jasonCalculator.isEditEvent(myEvent)) {
					numberOfEditEvents++;
					//System.out.println ("Edit command:" + myEvent);
				} else if (jasonCalculator.isDebugEvent(myEvent)) {
					numberOfDebugEvents++;
					//System.out.println ("Debug command:" + myEvent);

				} else if (r.isNavigationEvent(myEvent)) {
					numberOfSearchEvents++;
					//System.out.println ("navigation command:" + myEvent);

				} else if (r.isFocusEvent(myEvent)) {
					numberOfFocusEvents++;
					//System.out.println ("Focus command:" + myEvent);

				} else  if(jasonCalculator.isDeletionEvent(myEvent)){
					//numberOfRemoveEvents++;
					//System.out.println("Unclassified command: " + myEvent);
				}

				break;

			case A1:

				//jason's scheme with web
				//System.out.println ("remove command:" + myEvent);
				if (jasonCalculator.isInsertionEvent(myEvent)) {
					numberOfEditEvents++;
					//System.out.println ("Edit command:" + myEvent);
				} else if (jasonCalculator.isDebugEvent(myEvent)) {
					numberOfDebugEvents++;
					//System.out.println ("Debug command:" + myEvent);

				} else if (r.isNavigationEvent(myEvent)) {
					numberOfSearchEvents++;
					//System.out.println ("navigation command:" + myEvent);

				} else if (r.isFocusEvent(myEvent)) {
					numberOfFocusEvents++;
					//System.out.println ("Focus command:" + myEvent);

				} else  if(jasonCalculator.isDeletionEvent(myEvent)){
					numberOfRemoveEvents++;
					//System.out.println("Unclassified command: " + myEvent);
				}

				break;

			case A2:
				//kevin's scheme,mixed


				if (jasonCalculator.isInsertionEvent(myEvent)) {
					numberOfEditEvents++;
					//System.out.println ("Edit command:" + myEvent);
				} else if (isDebugEvent(myEvent)) {
					numberOfDebugEvents++;
					//System.out.println ("Debug command:" + myEvent);

				} else if (isNavigationEvent(myEvent)) {
					numberOfSearchEvents++;
					//System.out.println ("navigation command:" + myEvent);

				} else if (isFocusEvent(myEvent)) {
					numberOfFocusEvents++;
					//System.out.println ("Focus command:" + myEvent);

				} else if(jasonCalculator.isDeletionEvent(myEvent)){
					numberOfRemoveEvents++;

				} else  {
					//System.out.println("Unclassified command: " + myEvent);
				}
				break;

			case A3:

				//third ratios, original scheme
				if (isEditEvent(myEvent)) {
					numberOfEditEvents++;
					//System.out.println ("Edit command:" + myEvent);
				} else if (isDebugEvent(myEvent)) {
					numberOfDebugEvents++;
					//System.out.println ("Debug command:" + myEvent);

				} else if (isNavigationEvent(myEvent)) {
					numberOfSearchEvents++;
					//System.out.println ("navigation command:" + myEvent);

				} else if (isFocusEvent(myEvent)) {
					numberOfFocusEvents++;
					//System.out.println ("Focus command:" + myEvent);

				} else if(isAddRemoveEvent(myEvent)){
					numberOfRemoveEvents++;

				} else  {
					//System.out.println("Unclassified command: " + myEvent);
				}

			}

		}

		ArrayList<Integer> eventData = new ArrayList<Integer>();
		eventData.add(numberOfDebugEvents);
		eventData.add(numberOfSearchEvents);
		eventData.add(numberOfEditEvents);
		eventData.add(numberOfFocusEvents);
		eventData.add(numberOfRemoveEvents);

		return eventData;
	}
	/* (non-Javadoc)
	 * @see difficultyPrediction.metrics.FeatureCalculator#getFeatureName(edu.cmu.scs.fluorite.commands.ICommand)
	 */
	@Override
	public  String getFeatureName(ICommand myEvent) {

		if (isEditEvent(myEvent)) {
			return "Edit";

		} else if (isDebugEvent(myEvent)) {
			return "Debug";

		} else if (isNavigationEvent(myEvent)) {
			return "Navigation";

		} else if (isFocusEvent(myEvent)) {
			return "Focus";

		} else if (isAddRemoveEvent(myEvent)) {
			return "RemoveClass";
		} else {
			return "Unclassified";
		}



	}
	//	public static RatioCalculator getInstance() {
	//		if (instance == null)
	//			instance = new APercentageCalculator();
	//		return instance;
	//	}

}
