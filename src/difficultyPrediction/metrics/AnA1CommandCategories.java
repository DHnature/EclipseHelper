package difficultyPrediction.metrics;

import bus.uigen.ObjectEditor;

public class AnA1CommandCategories extends AnA0CommandCategories{
	CommandName[] editOrInsertCommands = {
			CommandName.CopyCommand,
			CommandName.CutCommand,
			CommandName.Delete,
			CommandName.Insert,
			CommandName.InsertStringCommand,
			CommandName.PasteCommand,
			CommandName.RedoCommand,
			CommandName.Replace,
			CommandName.SelectTextCommand,
			CommandName.UndoCommand,
			CommandName.MoveCaretCommand,
			CommandName.edit,			
	};
	CommandName[] exceptionCommands = {
			CommandName.Exception
	};
	
	CommandName[] insertCommands = {
			CommandName.CopyCommand,			
			CommandName.Insert,
			CommandName.InsertStringCommand,
			CommandName.PasteCommand,
			CommandName.Replace,
			CommandName.MoveCaretCommand,
			CommandName.SelectTextCommand,
	};
	
	CommandName[] removeCommands = {
			CommandName.CutCommand,			
			CommandName.Delete,
			CommandName.delete,
	};
	
	CommandName[] debugCommands = {
			CommandName.BreakPointCommand,			
			CommandName.ExceptionCommand,
			CommandName.RunCommand,			
	};
	
	CommandName[] navigationCommands = {
			CommandName.FileOpenCommand,			
			CommandName.FindCommand,
			CommandName.view,			
	};
	CommandName[] focusCommands = {
			CommandName.ShellCommand,			
					
	};
	
	
	public AnA1CommandCategories() {
		map (insertCommands, CommandCategory.EDIT_OR_INSERT);
		map (removeCommands, CommandCategory.REMOVE);
		map (debugCommands, CommandCategory.DEBUG);
		map (focusCommands, CommandCategory.FOCUS);
		map (navigationCommands, CommandCategory.NAVIGATION);
		
		
		
	}
	public static void main (String[] args) {
		CommandCategories commandsToFeatures = new AnA1CommandCategories();
		ObjectEditor.edit(commandsToFeatures);
	}
}
