package difficultyPrediction.statusManager;

public interface StatusListener {
	void newStatus(String aStatus);
	void newAggregatedStatus(String aStatus);
	void newStatus(int aStatus);
	void newAggregatedStatus(int aStatus);
	void newManualStatus(String aStatus);

}
