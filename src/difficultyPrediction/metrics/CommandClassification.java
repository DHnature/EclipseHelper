package difficultyPrediction.metrics;

public interface CommandClassification {

	public abstract CommandName getCommand();


	public abstract CommandCategoryName getFeature();

	public abstract void setFeature(CommandCategoryName feature);

}