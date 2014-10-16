package analyzer;

import difficultyPrediction.eventAggregation.EventAggeragator;
import difficultyPrediction.eventAggregation.EventAggregationStrategy;
import difficultyPrediction.eventAggregation.AnEventAggregator;
import difficultyPrediction.eventAggregation.EventAggregatorArray;
import edu.cmu.scs.fluorite.commands.ICommand;

public class DiscreteChunksAnalyzer implements EventAggregationStrategy {

	public DiscreteChunksAnalyzer() {

	}

	int m_numberOfEvents;
	private boolean ignoreEvents;
	private int numberOfEventsToIgnore = 50;
	private EventAggregatorArray actions = new EventAggregatorArray();
	
	public DiscreteChunksAnalyzer(String numberOfEvents) {
		m_numberOfEvents = Integer.parseInt(numberOfEvents);
		ignoreEvents = false;
	}

	@Override
	public void performAggregation(ICommand event,
			EventAggeragator eventAggregator) {
		actions.addEvents(event);
		if (ignoreEvents) {
			System.out
					.println("(discrete chunks.performAggregation) Ignored events left = "
							+ (numberOfEventsToIgnore - actions.size()));
			if (actions.size() >= numberOfEventsToIgnore) {
				actions.clear();
				ignoreEvents = false;
			}
		} else {
			if (actions.size() % m_numberOfEvents == 0) {
//				NewEventSegment.newCase(this);
				
				eventAggregator.onEventsHandOff(actions);
				//do something with events here
				actions.clear();
			}
		}

	}

}
