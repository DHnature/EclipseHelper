package analyzer.extension;

import analyzer.ParticipantTimeLine;

public class ALiveAnalyzerProcessor extends AnAnalyzerProcessor implements LiveAnalyzerProcessor {
    public static final String LIVE_USER_NAME = "Live User";
    public static final String DUMMY_FOLDER_NAME = "Live Dummy Folder";
    public ALiveAnalyzerProcessor() {
    	newParticipant(LIVE_USER_NAME, DUMMY_FOLDER_NAME);
    }
	@Override
	public ParticipantTimeLine getParticipantTimeLine() {
		return participantTimeLine;
	}

}
