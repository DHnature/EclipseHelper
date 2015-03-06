package analyzer.extension;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import weka.classifiers.trees.J48;
import weka.core.Instances;

/**Prediction tracer for Weka data mining tool<p>
 *Input parameters:<br> 
 *-train <i>filename</i>: Train with the arfffile file 
 *-test <i>filename</i>: Test with the 
 *
 *
 * 
 * */
public class APredictionTracker {
	private static String trainFile="data/OutputData/all.arff";
	private static String testFile="data/OutputData/16/16.arff";
	
	public static void main(String[] args) {
		
		try(BufferedReader trainStream=new BufferedReader(new InputStreamReader(new FileInputStream(trainFile)));
			BufferedReader testStream=new BufferedReader(new InputStreamReader(new FileInputStream(testFile)))
				) {
			Instances training=new Instances(trainStream);
			training.setClassIndex(training.numAttributes()-1);
			Instances testing=new Instances(testStream);
			testing.setClassIndex(testing.numAttributes()-1);
			
			J48 tree=new J48();
			tree.buildClassifier(training);
			
			
			for(int i=0;i<testing.numInstances();i++) {
				double pred=tree.classifyInstance(testing.instance(i));
				double actual=testing.instance(i).classValue();
				if(actual==pred)
					System.out.printf("Predicted: %s; Actual: %s%n", testing.classAttribute().value((int)pred),testing.classAttribute().value((int) testing.instance(i).classValue()));
				else
					System.err.printf("Predicted: %s; Actual: %s%n", testing.classAttribute().value((int)pred),testing.classAttribute().value((int) testing.instance(i).classValue()));
			}
			
		} catch(IOException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}

