package trace.plugin;

import trace.difficultyPrediction.NewEventSegmentAggregation;
import trace.difficultyPrediction.NewExtractedFeatures;
import trace.difficultyPrediction.NewPrediction;
import trace.difficultyPrediction.NewPredictionEvent;
import trace.difficultyPrediction.PredictionValueToStatus;
import trace.difficultyPrediction.StatusAggregationStarted;
import trace.logger.LogFileCreated;
import trace.logger.MacroCommandsLogBegin;
import trace.logger.MacroCommandsLogEnd;
import trace.recorder.ExcludedCommand;
import trace.recorder.MacroRecordingStarted;
import trace.recorder.NewMacroCommand;
import trace.recorder.RecordedCommandsCleared;
import trace.view.help.HelpViewCreated;
import util.trace.ImplicitKeywordKind;
import util.trace.MessagePrefixKind;
import util.trace.Traceable;
import util.trace.TraceableInfo;
import util.trace.Tracer;


public class EclipseHelperTracerSetter {
//	public static void trace() {
//
//		Tracer.showInfo(true);
//
//
//		setTraceParameters();
//		setPrintStatus();		
//	}
	
	public static void setTraceParameters() {
		TraceableInfo.setPrintSource(true);
		Traceable.setPrintTime(false);
		Traceable.setPrintThread(true);
		Tracer.setMessagePrefixKind(MessagePrefixKind.FULL_CLASS_NAME);
		Tracer.setImplicitPrintKeywordKind(ImplicitKeywordKind.OBJECT_CLASS_NAME);
		Traceable.setDefaultInstantiate(false);
	}
	
	public static void trace() {
		Tracer.showInfo(true);

		setTraceParameters();
		setPrintStatus();		
	}
	
	public static void setPrintStatus() {

		Tracer.setKeywordPrintStatus(PluginEarlyStarted.class, true);
		Tracer.setKeywordPrintStatus(PluginStarted.class, true);
		Tracer.setKeywordPrintStatus(MacroRecordingStarted.class, true);
		Tracer.setKeywordPrintStatus(ExcludedCommand.class, true);
		Tracer.setKeywordPrintStatus(NewMacroCommand.class, true);
		Tracer.setKeywordPrintStatus(RecordedCommandsCleared.class, true);
		Tracer.setKeywordPrintStatus(HelpViewCreated.class, true);
		Tracer.setKeywordPrintStatus(NewEventSegmentAggregation.class, true);
		Tracer.setKeywordPrintStatus(NewExtractedFeatures.class, true);
		Tracer.setKeywordPrintStatus(NewPrediction.class, true);
		Tracer.setKeywordPrintStatus(NewPredictionEvent.class, true);
		Tracer.setKeywordPrintStatus(PredictionValueToStatus.class, true);
		Tracer.setKeywordPrintStatus(StatusAggregationStarted.class, true);
		Tracer.setKeywordPrintStatus(LogFileCreated.class, true);
		Tracer.setKeywordPrintStatus(MacroCommandsLogBegin.class, true);
		Tracer.setKeywordPrintStatus(MacroCommandsLogEnd.class, true);
		Tracer.setKeywordPrintStatus(HelpViewCreated.class, true);

		





	}

}
