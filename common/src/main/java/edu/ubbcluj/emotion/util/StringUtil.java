package edu.ubbcluj.emotion.util;

import java.io.File;

public class StringUtil {

	public static String buildBaseFolderName(String folder) {
		return Constants.HOME_FOLDER.concat(folder).concat(File.separator);
	}

	public static String buildImagesFolderName(String folder) {
		return buildBaseFolderName(folder).concat(Constants.IMAGES_FOLDER);
	}

	public static String buildActionUnitsFolderName(String folder) {
		return buildBaseFolderName(folder).concat(Constants.FACS_FOLDER);
	}

	public static String buildEmotionFolderName(String folder) {
		return buildBaseFolderName(folder).concat(Constants.EMOTION_FOLDER);
	}

	public static String buildLandmarksFolderName(String folder) {
		return buildBaseFolderName(folder).concat(Constants.LANDMARKS_FOLDER);
	}

	public static String buildSubjectFolder(int subjectIndex) {
		return String.format("S%03d", subjectIndex).concat(File.separator);
	}

	public static String buildSubjectFolder(String subject) {
		return subject.concat(File.separator);
	}

	public static String buildSequenceFolder(int sequenceIndex) {
		return String.format("%03d", sequenceIndex).concat(File.separator);
	}

	public static String buildSequenceFolder(String sequence) {
		return sequence.concat(File.separator);
	}

	public static String createPath(String... strings) {
		String ret = "";
		for (String s : strings) {
			ret = ret.concat(s).concat(File.separator);
		}
		return ret;
	}

	public static String buildFileName(int subject, int sequence, int index) {
		return String.format("S%03d_%03d_%08d", subject, sequence, index);
	}

	public static String buildFileName(String subject, String sequence, int index) {
		return subject.concat("_").concat(sequence).concat(String.format("_%08d", index));
	}

	public static String buildBaseFileName(int subject, int sequence) {
		return String.format("S%03d_%03d_", subject, sequence);
	}
	
	public static String buildBaseFileName(String subject, String sequence) {
		return subject.concat("_").concat(sequence).concat("_");
	}

	public static void debug(String message) {
		System.out.println(message);
	}

	public static void error(String message) {
		System.err.println(message);
	}
}
