package edu.ubbcluj.emotion;

import edu.ubbcluj.emotion.editor.DifferenceEditor;
import edu.ubbcluj.emotion.editor.SequenceEditor;

public class StartApp {

	public static String	source		= "openimaj_small3";

	public static String	destination	= "openimaj_small3_diff";

	public static void main(String[] args) {
		SequenceEditor editor = new DifferenceEditor();
		ImageSequenceEditorRunner.runEditor(source, destination, editor);
	}
}
