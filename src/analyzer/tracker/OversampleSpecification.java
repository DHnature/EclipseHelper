package analyzer.tracker;

public enum OversampleSpecification {
	SMOTE(new double[] {500,1000,2000,3000}),
	RESAMPLE(new double[] {0.25,0.5,0.75,1.0});

	private double[] levels;

	private OversampleSpecification(double[]  levels) {
		this.levels=levels;

	}

	public double[] getFilterLevels() {
		return this.levels;

	}
}
