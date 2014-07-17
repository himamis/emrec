package edu.ubbcluj.emotion;

import edu.ubbcluj.emotion.editor.AffineAligner;
import edu.ubbcluj.emotion.editor.SequenceEditor;
import edu.ubbcluj.emotion.util.Constants;

public class StartApp {

	public static boolean	DEBUG					= true;

	public static String	original_folder			= Constants.ORIGINAL_FOLDER;

	public static String	openimaj_folder_small	= "openimaj_small3";

	public static void main(String[] args) {
		SequenceEditor editor = new AffineAligner(50, 60, 0.400f);
		ImageSequenceEditorRunner.runEditor(original_folder, openimaj_folder_small, editor);
	}
}
