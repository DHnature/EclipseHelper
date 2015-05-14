package analyzer.tracker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.Bagging;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.supervised.instance.Resample;
import weka.filters.supervised.instance.SMOTE;
import weka.filters.unsupervised.attribute.Remove;
import analyzer.extension.ArffFileGeneratorFactory;
import difficultyPrediction.DifficultyPredictionSettings;
import difficultyPrediction.metrics.ATestRatioCalculator;
import difficultyPrediction.metrics.ATestRatioCalculator.Scheme;

/**For leave one out analysis
 * 
 * @author wangk1
 *
 */
public class LeaveOneOutAnalysis {
	private static BufferedWriter out=null;

	static{

		try {
			out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("leaveoneout.txt"))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


	}

	enum Classifier{
		J48,
		ADABOOST,
		BAGGING

	}

	enum Oversample{
		SMOTE(new double[] {500,1000,2000,3000}),
		RESAMPLE(new double[] {0.25,0.5,0.75,1.0});

		private double[] levels;

		private Oversample(double[]  levels) {
			this.levels=levels;

		}

		public double[] getFilterLevels() {
			return this.levels;

		}
	}

	private static String trainingDir;
	private static String testingDir;
	//private static String outputAndTestSubDir;

	public static void generateNeccessaryArffFiles(String participantSubDir) {
		ArffFileGeneratorFactory f=new ArffFileGeneratorFactory("");

		f.setOutputSubDirectory(participantSubDir);
		//gen leave one out all.arff
		generateLeaveParticpantOut(f);
		//gen participant.arff
		generateParticipantArff(f);
	}


	public static void generateLeaveParticpantOut(ArffFileGeneratorFactory f) {
		DifficultyPredictionSettings.setNewRatioFiles(false);

		for(int i=1;i<18;i++) {	
			f.insertCommand("All ignore "+String.valueOf(i));
			f.createArffs();

		}

	}

	public static void generateParticipantArff(ArffFileGeneratorFactory f) {
		DifficultyPredictionSettings.setNewRatioFiles(true);
		f.setOutputSubDirectory("");

		for(int i=1;i<18;i++) {	
			f.insertCommand(String.valueOf(i));
			f.createArffs();

		}


		DifficultyPredictionSettings.setNewRatioFiles(false);


	}

	public static void leaveOneOutAnalysis(Classifier c, Oversample s,double percentage,boolean ignoreWeblinks) throws Exception {
		//the tracker
		PredictionTracker t=new APredictionTracker();

		//filter
		weka.filters.Filter f=null;

		if(s==Oversample.SMOTE)
			f=new SMOTE();
		else {
			Resample r=new Resample();
			r.setBiasToUniformClass(percentage==Double.MIN_VALUE? 0.0:percentage);
			f=r;
		}

		//outputter we want, wrong predictions that is either predicted as stuck or not stuck
		//PredictionOutputter wrongStuckOutputter=new ClassFilterOutputter(new WrongPredictionOutputter("data/wrongpredictstuck.csv"),"NO");
		//PredictionOutputter wrongNotStuckOutputter=new ClassFilterOutputter(new WrongPredictionOutputter("data/wrongpredictnostuck.csv"),"YES");

		PredictionOutputter rightStuckOutputter=new ClassFilterOutputter("YES",new ResamplingOutputter(10,400,new CorrectPredictionOutputter("data/rightpredictstuck.csv")));
		//PredictionOutputter rightNotStuckOutputter=new ClassFilterOutputter(new CorrectPredictionOutputter("data/rightpredictnostuck.csv"),"NO");
		PredictionOutputter matrixOutputter=new ResultOutputter("YES", "NO",out);

		PredictionOutputter combinedOutputter=new CombinedOutputter(matrixOutputter,rightStuckOutputter);

		double p=0;
		int offSet=0;
		for(int i=16;i<33;i++) {
			//don't delete, just used to compensate for the fact there is no participant 25
			if(i==25) {
				offSet++;

			}

			// //Participant that is an outlier with regards to number of stuck segments
			//			if(i==28) {
			//				continue;
			//
			//			}

			String training=trainingDir+(i-15)+"/all.arff";
			String testing=testingDir+(i+offSet)+"/"+(i+offSet)+".arff";

			//			out("Training: "+training);
			//			out("Testing: "+testing);

			t.setTrainingFile(training);
			t.setTestingFile(testing);

			t.loadInstances();

			if(ignoreWeblinks) {
				//out("Ignoring weblink");
				Instances train=t.getTrainingInstances();
				int index=train.attribute("webLinkTimes").index()+1;
				//out(index);
				Remove r=new Remove();
				r.setAttributeIndices(index+"");
				r.setInputFormat(train);
				t.setTrainingInstance(weka.filters.Filter.useFilter(train, r));

			}


			//try to smote training to 30% of testing
			Instances t_instance=t.getTrainingInstances();
			p=smote30Percent(t_instance);
			if(s==Oversample.SMOTE)
				((SMOTE) f).setPercentage(percentage==Double.MIN_VALUE? p:percentage);


			t.filterTraining(f);


			weka.classifiers.Classifier b=null;

			if(c==Classifier.ADABOOST) {
				b=new AdaBoostM1();
				((AdaBoostM1) b).setClassifier(new DecisionStump());
			} else if(c==Classifier.BAGGING) {
				b=new Bagging();
				((Bagging) b).setClassifier(new DecisionStump());
			} else {
				b=new J48();

			}

			t.buildClassifier(b);

			t.evaluateTesting();

			t.outputResults(combinedOutputter, true);
		}

		try {
			out.write("Testing: "+c+" Filter: "+s+" Boost: "+p);
			out.newLine();
			out.flush();
		} catch (IOException e1) {

			e1.printStackTrace();
		}


		combinedOutputter.closeStream();
		try {
			out.write("Analysis completed");
			out.newLine();
			out.flush();

		} catch (IOException e1) {

			e1.printStackTrace();
		}
	}




	/**SMOTE minority class and majority class to be equal
	 * 
	 * @param i
	 * @return
	 */
	private static double smoteEven(Instances i) {
		double c1count=0;
		double c2count=0;

		for(int index=0;index<i.numInstances();index++) {
			Instance instance=i.instance(index);

			if(instance.classValue()==0.0) {
				c1count++;

			} else {
				c2count++;

			}

		}

		return c1count<c2count? c2count/c1count*100:c1count/c2count*100;
	}

	/**SMOTE minority class and majority class to be equal
	 * 
	 * @param i
	 * @return
	 */
	private static double smote30Percent(Instances i) {
		double c1count=0;
		double c2count=0;

		for(int index=0;index<i.numInstances();index++) {
			Instance instance=i.instance(index);

			if(instance.classValue()==0.0) {
				c1count++;

			} else {
				c2count++;

			}

		}

		return c1count<c2count? c2count*0.3/c1count*100:c1count*0.3/c2count*100;
	}


	public static void main(String[] args) {
		Scheme[] vals=new Scheme[] {Scheme.A3};

		//go through each scheme, can't do this for now because of memory leak preventing entire run
		for(Scheme s:vals) {
			ATestRatioCalculator.CURRENT_SCHEME=s;

			String outputAndTestSubDir=s.getSubDir();

			trainingDir="data/OutputData/"+outputAndTestSubDir+"All ignore ";
			testingDir="data/OutputData/";
			generateNeccessaryArffFiles(outputAndTestSubDir);

			try {
				out.write(String.format("**Results for scheme %s**%n",ATestRatioCalculator.CURRENT_SCHEME));
				out.newLine();
				out.write("Training dir: "+trainingDir);
				out.newLine();
				out.flush();
			} catch (IOException e2) {
				e2.printStackTrace();
			}

			double[] filteringAmount=new double[] {Double.MIN_VALUE};

			//classifier
			for (Classifier c: Classifier.values()) {

				//for (Oversample p:Oversample.values()) {
				Oversample p=Oversample.SMOTE;
				//different levels
				for (double l:filteringAmount) {


					//args: classifier, filter, and filter %(only for smote)
					try {
						leaveOneOutAnalysis(c, p, l,outputAndTestSubDir.equalsIgnoreCase("leaveoneoutjason/"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				//}

			}

		}
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//can't figure out why the timer thread won't terminate on its own, has something to do with the prediction thread in difficultyPredictor
		System.exit(0);
	}

}
