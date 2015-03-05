package difficultyPrediction.eventAggregation;

import java.util.ArrayList;

import edu.cmu.scs.fluorite.commands.ICommand;

public class EventAggregatorArray extends ArrayList<ICommand>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void addEvents(ICommand event) {
		this.add(event);
	}
}
