package analyzer.extension;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import analyzer.AnAnalyzer;
import analyzer.Analyzer;
import difficultyPrediction.extension.APrintingDifficultyPredictionListener;
import difficultyPrediction.featureExtraction.RatioFeatures;
import edu.cmu.scs.fluorite.commands.ICommand;
import edu.cmu.scs.fluorite.commands.PredictionCommand;

/**Class that generates Arff Files from the input ratios via difficulty listener event callbacks and
 * new predictions.
 * <p>
 * Instructions:
 * 1. To ins
 * 
 * @author wangk1
 *
 */
public class AnArffGeneratorOld extends APrintingDifficultyPredictionListener implements ArffGenerator{
	public static final String DEFAULT_ARFF_PATH="data/userStudy";

	//name of relation to be printed as @relation tag
	public static final String RELATION="programmer-weka.filters.supervised.instance.SMOTE-C0-K5-P100.0-S1-weka.filters.supervised.instance.SMOTE-C0-K5-P100.0-S1-weka.filters.supervised.instance.SMOTE-C0-K5-P100.0-S1-weka.filters.supervised.instance.SMOTE-C0-K5-P100.0-S1";

	//Insert More features here
	public static final String[] FEATURES={"searchPercentage","numeric",
		"debugPercentage","numeric",
		"focusPercentage","numeric",
		"editPercentage","numeric",
		"removePercentage","numeric",
		"stuck","{YES,NO}"
	};

	//path to save the arff file to. Include the .arff extension
	private String path;
	//Buffered writer for writing out to the arff file
	private ArffWriter arffWriter;

	//Queue of ratios, when a new prediction is made. Print all the ratios from the queue.
	private Queue<RatioFeatures> ratios;


	//set to keep
	//Is the user currently stuck
	private boolean isStuck;

	private boolean all;

	private Analyzer analyzer;

	//set the path of the arff file
	public AnArffGeneratorOld(Analyzer analyzer) {
		this.analyzer=analyzer;
		this.isStuck=false;
		this.ratios=new LinkedList<RatioFeatures>();
		
		arffWriter=new AnArffGeneratorOld.ArffWriter();

	}


	/**METHODS SECTION*/

	/**Prep arff file method, called by newParticipant Method*/
	private void prep() {
		//create a new bufferedwriter, with a different path
		

		//if no file name exist generate one that does not override other files
		if(path==null) {
			path=findRightFileName();

		}

		Path p=Paths.get(path);

		//create file if not exists
		if(!Files.exists(p) && Files.notExists(p)) {
			try {
				Files.createFile(p);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {


		}

		if(this.all) {
			arffWriter.start(path, true);
			//now write headers out to file
			generateArffHeader();
			//stop is important. Flushes header since the newUser is going to create a new writer
			//this forces the bytes out or the header is not going to be written
			arffWriter.stop();
		} else {
			arffWriter.start(path,false);
			//now write headers out to file
			generateArffHeader();
			
		}

	}

	/**Find the right name for the file if no name is specified by {@link #setArffFilePath(String)}*/
	private String findRightFileName() {
		int i=2010;

		//find the right file to write out to
		while(Files.exists(Paths.get(DEFAULT_ARFF_PATH+i+".arff")) && !Files.notExists(Paths.get(DEFAULT_ARFF_PATH+i+".arff"))){
			i++;

		}

		return DEFAULT_ARFF_PATH+i+".arff";
	}

	/**Generate the @relation, @attribute header for arff files*/
	private void generateArffHeader() {
		arffWriter.writeRelation(RELATION).writeNewLine();

		//write all features out
		for(int i=0;i<FEATURES.length-1;i=i+2) {
			arffWriter.writeAttribute(FEATURES[i], FEATURES[i+1]);

		}

		arffWriter.writeNewLine();

	}

	@Override
	public void newParticipant(String anId, String aFolder) {
		System.out.println("***new participant: "+ aFolder);
		
		//set path
		if(aFolder==null && anId.equals("All") && !all) {
			this.all=true;
			path=AnAnalyzer.PARTICIPANT_OUTPUT_DIRECTORY+"/all.arff";
			
			prep();
			
		} else if(!all){
			path=AnAnalyzer.PARTICIPANT_OUTPUT_DIRECTORY+"/"+aFolder+"/"+aFolder+".arff";
			
			prep();
			
		} else {
			arffWriter.restart();
			
		}
		
		//start the output output writer
		if(!this.all) {
			analyzer.getDifficultyEventProcessor().addDifficultyPredictionEventListener(this);		
			
		} else {
			
			if(analyzer.getDifficultyEventProcessor()!=null) {
				analyzer.getDifficultyEventProcessor().addDifficultyPredictionEventListener(this);		
				
			}
			
			
		}
	}

	@Override
	public void recordCommand(ICommand newCommand) {
		if(newCommand.getCommandType().equals("PredictionCommand")) {
			PredictionCommand prediction=(PredictionCommand) newCommand;

			if(prediction.getPredictionType()==PredictionCommand.PredictionType.HavingDifficulty) {
				this.isStuck=true;	


			} else {
				this.isStuck=false;

			}

			while(!ratios.isEmpty()) {
				RatioFeatures r=ratios.remove();

				//now output it all
				arffWriter.writeData(isStuck? "YES":"NO", 
						r.getNavigationRatio(), 
						r.getDebugRatio(),
						r.getFocusRatio(),
						r.getEditRatio(),
						r.getRemoveRatio()	
						);
			}

		}

		//System.out.println("Extension**New User/Prediction Command:" + newCommand);	


	}
	@Override
	public void start() {
		System.out.println("Extension**Difficulty Prediction Started");	


	}
	@Override
	public void stop() {
		System.out.println("Extension**Difficulty Prediction Stopped");	
		//stop file stream here
		arffWriter.stop();
	}
	@Override
	public void newRatios(RatioFeatures newVal) {
		ratios.add(newVal);

		System.out.println("Extension**New Ratios:" + newVal.getNavigationRatio()+newVal.getDebugRatio()+
				newVal.getFocusRatio()+
				newVal.getEditRatio()+
				newVal.getRemoveRatio());		
	}





	/**Inner static class that encapsulate a buffered stream<br>*/
	/**Allows chaining by returning this<br>
	 * 
	 * Swallows errors*/
	public static class ArffWriter{
		private BufferedWriter writer;
		//turns to true when output to the data section started
		boolean datastarted;
		private String path;

		public ArffWriter() {
			this.datastarted=false;

		}

		/**Must be called before any form of writing<br>
		 * Can be called repeatidly to set to a new path
		 * */
		public void start(String path, boolean append) {
			this.datastarted=false;
			this.path=path;

			try {
				//truncate first
				writer=Files.newBufferedWriter(Paths.get(path), Charset.defaultCharset(), StandardOpenOption.WRITE,StandardOpenOption.TRUNCATE_EXISTING);
				
				//if append, start in append mode
				if(append) {
					writer.close();
					
					writer=Files.newBufferedWriter(Paths.get(path), Charset.defaultCharset(), StandardOpenOption.APPEND);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		/**Start the same writer with the same path**/
		public void restart() {
			try {
				writer=Files.newBufferedWriter(Paths.get(path), Charset.defaultCharset(), StandardOpenOption.APPEND);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		/**Set another file to write to. If append, write to end of the filef*/
		public void resetPath(String path,boolean append) {
			start(path,append);

		}

		/**Stop the stream*/
		public void stop() {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		/**Write out a @Relation and its content. Auto append a newline at the end*/
		public ArffWriter writeRelation(String name) {
			this.writeToArffFile("@Relation ")
			.writeToArffFile(name).writeNewLine();


			return this;
		}

		/**Write out @attribute, its name, and its type*/
		public ArffWriter writeAttribute(String attrname,String type) {
			this.writeToArffFile("@Attribute ").writeToArffFile(attrname+" ")
			.writeToArffFile(type).writeNewLine();

			return this;
		}

		/**Writers the ratios in data along with the prediction*/
		public ArffWriter writeData(String prediction, double ... data) {
			if(!this.datastarted) {
				datastarted=true;
				writeToArffFile("@data");
				writeNewLine();

			}

			//write each ratio out
			for(double d:data) {
				writeToArffFile(d+",");

			}

			//now write the prediction
			writeToArffFile(prediction);
			writeNewLine();


			return this;
		}

		/**Output methods*/

		/**Writes the ratios in the current stack buffer out to the arff file*/
		public ArffWriter writeToArffFile(String output) {

			//try to write out
			try {
				writer.append(output);
			} catch (IOException e) {
				e.getCause();

			}

			return this;
		}

		/***Writer new line to arff file*/
		public ArffWriter writeNewLine() {
			try {
				writer.newLine();
			} catch (IOException e) {
				e.getCause();

			}

			return this;
		}



	}



	@Override
	public void newBrowseLine(String aLine) {
		// TODO Auto-generated method stub

	}


	@Override
	public void newBrowseEntries(Date aDate, String aSearchString, String aURL) {
		// TODO Auto-generated method stub

	}


	@Override
	public void finishedBrowserLines() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		Analyzer analyzer = new AnAnalyzer();
		ArffGenerator arffGenerator = new AnArffGeneratorOld(analyzer);
		analyzer.addAnalyzerListener(arffGenerator);
		OEFrame frame = ObjectEditor.edit(analyzer);
		frame.setSize(550, 200);

	}


	@Override
	public void finishParticipant(String anId, String aFolder) {
		// TODO Auto-generated method stub
		
	}


}