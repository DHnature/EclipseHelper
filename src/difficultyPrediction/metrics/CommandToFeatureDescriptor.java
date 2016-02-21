package difficultyPrediction.metrics;

public interface CommandToFeatureDescriptor {

	public abstract CommandName getCommand();


	public abstract FeatureName getFeature();

	public abstract void setFeature(FeatureName feature);

}