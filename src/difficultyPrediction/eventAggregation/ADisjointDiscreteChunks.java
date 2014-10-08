package difficultyPrediction.eventAggregation;

import edu.cmu.scs.fluorite.commands.ICommand;

public class ADisjointDiscreteChunks implements EventAggregationStrategy {

	public static final int DEFAULT_MAX = 25;
	// dfaiult ignore number is 50
	public static final int DEFAULT_IGNORE_NUM = 50;
	public static final boolean DEFAULT_IGNORE = true;

	public int maxNumberOfEvents;
	public int numberOfEventsToIgnore;
	public boolean ignoreEvents;

	public ADisjointDiscreteChunks() {
		maxNumberOfEvents = DEFAULT_MAX;
		numberOfEventsToIgnore = DEFAULT_IGNORE_NUM;
		ignoreEvents = DEFAULT_IGNORE;
	}

	public void performAggregation(ICommand event,
			AnEventAggregator eventAggregator) {

		actions.addEvents(event);

		// Ignore the first few events a user sends then begin tracking, to
		// avoid
		// dealing with startup lag
		if (ignoreEvents) {
			System.out
					.println("(discrete chunks.performAggregation) Ignored events left = "
							+ (numberOfEventsToIgnore - actions.size()));
			if (actions.size() >= numberOfEventsToIgnore) {
				actions.clear();
				ignoreEvents = false;
			}
		} else {
			if (actions.size() % maxNumberOfEvents == 0) {
				eventAggregator.onEventsHandOff(actions);
				actions.clear();
			}
		}

	}

	private EventAggregatorArray actions = new EventAggregatorArray();

}
