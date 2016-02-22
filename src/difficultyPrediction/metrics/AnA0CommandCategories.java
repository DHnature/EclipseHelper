package difficultyPrediction.metrics;

import bus.uigen.ObjectEditor;

public class AnA0CommandCategories extends ACommandCategories{
	private CommandName[] editCatgory = {
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
	private CommandName[] exceptionCategory = {
			CommandName.Exception
	};
	
//	CommandName[] insertCommands = {
//			CommandName.CopyCommand,			
//			CommandName.Insert,
//			CommandName.InsertStringCommand,
//			CommandName.PasteCommand,
//			CommandName.Replace,
//			CommandName.MoveCaretCommand,
//			CommandName.SelectTextCommand,
//	};
	
//	CommandName[] removeCommands = {
////			CommandName.CutCommand,			
////			CommandName.Delete,
////			CommandName.delete,
//	};
	
	private CommandName[] debugCategory = {
			CommandName.BreakPointCommand,			
			CommandName.ExceptionCommand,
			CommandName.RunCommand,			
	};
	
	private CommandName[] navigationCategory = {
			CommandName.FileOpenCommand,			
			CommandName.FindCommand,
			CommandName.view,			
	};
	private CommandName[] focusCategory = {
			CommandName.ShellCommand,			
					
	};
	
	public AnA0CommandCategories() {
		
		this (true);
		
		
	}
     public AnA0CommandCategories(boolean aMapCategories) {
		if (aMapCategories)
		mapCategories();
		
		
	}
	private void mapCategories() {
		map (editCategory(), CommandCategory.EDIT_OR_INSERT);
//		map (removeCommands, CommandCategory.REMOVE);
		map (debugCategory(), CommandCategory.DEBUG);
		map (focusCategory(), CommandCategory.FOCUS);
		map (navigationCategory(), CommandCategory.NAVIGATION);
	}

	protected CommandName[] editCategory() {
		return editCatgory;
	}
	protected CommandName[] debugCategory() {
		return debugCategory;
	}
	protected CommandName[] navigationCategory() {
		return navigationCategory;
	}
	protected CommandName[] focusCategory() {
		return focusCategory;
	}
	public static void main (String[] args) {
		CommandCategories commandsToFeatures = new AnA0CommandCategories();
		ObjectEditor.edit(commandsToFeatures);
	}
}
