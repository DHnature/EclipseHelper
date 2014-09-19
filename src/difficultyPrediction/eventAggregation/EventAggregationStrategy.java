package difficultyPrediction.eventAggregation;

import edu.cmu.scs.fluorite.commands.ICommand;


public interface EventAggregationStrategy {
	public void performAggregation(ICommand event, EventAggregator eventAggregator);
}
