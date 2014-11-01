package analyzer;

import java.util.List;

import bus.uigen.ObjectEditor;
import util.annotations.Column;
import util.annotations.Explanation;
import util.annotations.Row;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.misc.Common;
import util.models.ADynamicEnum;
import util.models.DynamicEnum;
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
public class AParametersSelector  {
	DynamicEnum<String> participants;
	Analyzer analyzer;
	
	public AParametersSelector(List<String> aParticipants) {
		participants = new ADynamicEnum(aParticipants);
	}
	public AParametersSelector(String[] aParticipants) {
		participants = new ADynamicEnum(Common.arrayToArrayList(aParticipants));
	}
	public AParametersSelector(Analyzer anAnalyzer) {
		participants = new ADynamicEnum();
		analyzer = anAnalyzer;
	}
	int segmentLength = 50;
	@Row(0)
	@Column(0)
	public int getSegmentLength() {
		return segmentLength;
	}
	public void setSegmentLength(int newVal) {
		this.segmentLength = newVal;
	}
	@Row(0)
	@Column(1)
//	@Explanation("The set of modules that can be graded. Usually you will work on a single module.")
	public DynamicEnum<String> getParticipants() {
		return participants;
	}

	public void setParticipants(DynamicEnum<String> aNewVal) {
		participants = aNewVal;
	}
	@Row(1)
	@Column(0)
	public void loadDirectory() {
		if (analyzer != null)
			analyzer.loadDirectory();
	}
	public boolean preLoadLogs() {
		if (analyzer != null)
		return analyzer.preLoadLogs();
		else return false;
	}
	public boolean preLoadDirectory() {
		if (analyzer != null)
		return analyzer.preLoadDirectory();
		else return false;
	}
	@Row(1)
	@Column(1)
    public void loadLogs() {
		if (analyzer != null)
			analyzer.loadLogs();
		
	}
//	@Column(1)
//	@Explanation("The set of problems in the selected module for which folders have been downloaded. Changing the problem automatically selects the corresponding download folder provided a valid folder has been slected for one of the problems in the module once.")
//
//	public DynamicEnum<String> getProblem() {
//		return problem;
//	}
//
//	public void setProblem(DynamicEnum<String> problem) {
//		this.problem = problem;
//	}
//	
	public static void main (String[] args) {
//		System.out.println(Common.intSuffix("Assignment2"));
//		System.out.println(Common.intSuffix("Assignment"));
		String[] participants = {"1", "2", "3"};
//		
//
//		List<String> modules = Common.arrayToArrayList(new String[] {"Comp110", "Comp401"});
//		List<String> problems = Common.arrayToArrayList(new String[] {"1", "2"});
		AParametersSelector selector = new AParametersSelector(participants);
		ObjectEditor.edit(selector);
		

	}
	

}
