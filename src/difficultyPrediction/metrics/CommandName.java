package difficultyPrediction.metrics;

public enum CommandName {
	BreakPointCommand,
	ExceptionCommand,
	RunCommand,
	CompileWarning,
	CompileError,
	CopyCommand,
	Insert,
	InsertStringCommand,
	PasteCommand,
	RedoCommand,
	SelectTextCommand,
	CutCommand,
	Replace,
	
	FileOpenCommand,
	FindCommand,
	ShellCommand,
	Delete,
	UndoCommand,
	MoveCaretCommand,
	Exception,
	edit,// this must be after delete and maybe even view, but Jason's scheme has it wrong
	delete,
	view,

}
