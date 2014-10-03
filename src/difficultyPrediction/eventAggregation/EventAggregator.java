package difficultyPrediction.eventAggregation;

import java.util.List;

import trace.difficultyPrediction.NewEventSegmentAggregation;
import difficultyPrediction.Mediator;
import edu.cmu.scs.fluorite.commands.ICommand;


public class EventAggregator {
	
	private long startTimestamp = 0;
	public long getStartTimestamp()
	{
		return startTimestamp;
	}
	
	public void setStartTimeStamp(long startTimeStamp)
	{
		this.startTimestamp = startTimeStamp;
	}
	
	public EventAggregator(Mediator mediator) {
		this.mediator = mediator;
	}
	
	Mediator mediator;
	public EventAggregationStrategy eventAggregationStrategy;
	
	
	public void setEventAggregationStrategy(EventAggregationStrategy strat) {
		this.eventAggregationStrategy = strat;
	}
	
	public EventAggregationStrategy getEventAggregationStrategy() {
		return eventAggregationStrategy;
	}
	
	public void onEventsHandOff(List<ICommand> genericActions) {
		if(mediator != null) {
			EventAggregatorDetails args = new EventAggregatorDetails(genericActions);
			args.startTimeStamp = this.startTimestamp;
			NewEventSegmentAggregation.newCase(args, this);
			mediator.eventAggregator_HandOffEvents(this, args);
		}
	}
}
