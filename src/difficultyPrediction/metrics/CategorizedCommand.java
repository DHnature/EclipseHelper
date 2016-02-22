package difficultyPrediction.metrics;

public interface CategorizedCommand {

	public abstract CommandName getCommand();


	public abstract CommandCategory getCategory();

	public abstract void setFeature(CommandCategory feature);

}