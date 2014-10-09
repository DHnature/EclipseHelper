package difficultyPrediction.eventAggregation;

import java.util.List;

import trace.difficultyPrediction.NewEventSegmentAggregation;
import difficultyPrediction.Mediator;
import edu.cmu.scs.fluorite.commands.ICommand;

public interface EventAggeragator {
	public long getStartTimestamp();
	
	public void setStartTimeStamp(long startTimeStamp);
	
	
	public void setEventAggregationStrategy(EventAggregationStrategy strat) ;
	
	public EventAggregationStrategy getEventAggregationStrategy() ;
	
	public void onEventsHandOff(List<ICommand> genericActions) ;

}
