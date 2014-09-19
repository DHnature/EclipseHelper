package difficultyPrediction.metrics;

import java.util.ArrayList;
import java.util.List;

public class HoldPredictions {
	public int numberOfYes;
	public int numberOfNo;
	public List<String> predictions;
	
	public HoldPredictions() {
		this.predictions = new ArrayList<String>();
	}

}
