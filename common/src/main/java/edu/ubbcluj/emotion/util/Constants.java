package edu.ubbcluj.emotion.util;

import java.io.File;

public abstract class Constants {

	public static final String	EXPERIMENT_FOLDER	= "c:\\Users\\himamis\\git\\emrec\\experiments\\";

	private static final String	SEPARATOR			= File.separator;
	
	public static final String	HOME_FOLDER			= "h:" + SEPARATOR + "workspace" + SEPARATOR + "databases" + SEPARATOR;
	public static final String	ORIGINAL_FOLDER		= "ck+";
	public static final String	BASE_FOLDER			= HOME_FOLDER + ORIGINAL_FOLDER + SEPARATOR;
	public static final String	IMAGES_FOLDER		= "cohn-kanade-images" + SEPARATOR;
	public static final String	LANDMARKS_FOLDER	= "Landmarks" + SEPARATOR;
	public static final String	FACS_FOLDER			= "FACS" + SEPARATOR;
	public static final String	EMOTION_FOLDER		= "Emotion" + SEPARATOR;
	public static final String	DATA_FOLDER			= "data" + SEPARATOR;

	public static final int[]	COORD_LEFT_EYE		= { 36, 37, 38, 39, 40, 41 };
	public static final int[]	COORD_RIGHT_EYE		= { 42, 43, 44, 45, 46, 47 };
	public static final int[]	COORD_MOUTH			= { 48, 54 };

	public static final String	FACS_FILE			= "_facs.txt";
	public static final String	EMOTION_FILE		= "_emotion.txt";
	public static final String	LANDMARKS_FILE		= "_landmarks.txt";
	public static final String	SEQUENCE_FILE		= ".png";

	public static final int[]	CNOSEBRIDGE			= { 27 };
	public static final int[]	CNOSEMIDDLE			= { 33 };
	public static final int[]	CNOSERIGHT			= { 31 };
	public static final int[]	CNOSELEFT			= { 35 };

	public static final int[]	MOUTHRIGHT			= { 48 };
	public static final int[]	MOUTHLEFT			= { 54 };
	public static final int[]	MOUTHCENTER			= { 62, 66 };

	public static final int[]	CEYELEFTCENTER		= { 42, 43, 44, 45, 46, 47 };
	public static final int[]	CEYERIGHTCENTER		= { 36, 37, 38, 39, 40, 41 };
	public static final int[]	CEYELEFTLEFT		= { 45 };
	public static final int[]	CEYELEFTRIGHT		= { 42 };
	public static final int[]	CEYERIGHTRIGHT		= { 36 };
	public static final int[]	CEYERIGHTLEFT		= { 39 };

}
