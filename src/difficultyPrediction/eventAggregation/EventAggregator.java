package difficultyPrediction.eventAggregation;

import java.util.List;

import edu.cmu.scs.fluorite.commands.ICommand;

public interface EventAggregator {
	public long getStartTimestamp();
	
	public void setStartTimeStamp(long startTimeStamp);
	
	
	public void setEventAggregationStrategy(EventAggregationStrategy strat) ;
	
	public EventAggregationStrategy getEventAggregationStrategy() ;
	
	public void onEventsHandOff(List<ICommand> genericActions) ;

}
