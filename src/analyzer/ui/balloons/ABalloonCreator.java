package analyzer.ui.balloons;

import org.eclipse.ui.PlatformUI;

import util.annotations.Column;
import util.annotations.ComponentWidth;
import util.annotations.Row;
import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import difficultyPrediction.ADifficultyPredictionRunnable;
import edu.cmu.scs.fluorite.commands.PredictionCommand;

public class ABalloonCreator {
	String status = "";
	@Row(1)
	@ComponentWidth(325)
	public String getStatus() {
		return status;
	}

	public void setStatus(String newVal) {
		ADifficultyPredictionRunnable.getInstance().showStatusInBallonTip(newVal);		
		
	}
	
	
	@Row(0)
	@Column(0)
	public void localDifficulty() {
		setStatus("Facing Difficulty");
	}
	@Row(0)
	@Column(1)
	public void remoteDifficulty() {
		setStatus("Alice is facing difficulty");
	}
	@Row(0)
	@Column(2)
	public void difficultyCommunicated() {
		setStatus("Difficulty communicated to collaborators");
	}
	public static void createUI() {
		OEFrame frame = ObjectEditor.edit(new ABalloonCreator());
		frame.setSize(400, 120);

	}
	public static void main (String[] args) {
		createUI();
		
	}
	

	

}
