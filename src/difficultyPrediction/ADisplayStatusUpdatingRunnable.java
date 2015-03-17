package difficultyPrediction;

public class ADisplayStatusUpdatingRunnable implements Runnable{
	String status;
	DifficultyPredictionRunnable difficultyPredictionRunnable;
	public ADisplayStatusUpdatingRunnable(DifficultyPredictionRunnable aDifficultyPredictionRunnable, String aStatus) {
		System.out.println ("ADisplayStatusUpdatingRunnable created");
		status = aStatus;
		difficultyPredictionRunnable = aDifficultyPredictionRunnable;
	}
	@Override
	public void run() {
		difficultyPredictionRunnable.changeStatusInHelpView(status);

	}
	 

}
