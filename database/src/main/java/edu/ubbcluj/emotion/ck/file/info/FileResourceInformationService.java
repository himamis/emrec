package edu.ubbcluj.emotion.ck.file.info;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

import edu.ubbcluj.emotion.database.file.info.ResourceInformationService;
import edu.ubbcluj.emotion.util.Constants;
import edu.ubbcluj.emotion.util.StringUtil;

public class FileResourceInformationService implements ResourceInformationService {

	@Override
	public String[] getSubjects(final String folder) {
		final String imagesFolder = StringUtil.buildImagesFolderName(folder);
		final String[] directories = listFileNames(imagesFolder);
		return directories;
	}

	@Override
	public String[] getSequences(final String folder, final String subject) {
		final String subjectImageFolder = StringUtil.buildImagesFolderName(folder).concat(StringUtil.buildSubjectFolder(subject));
		final String[] directories = listFileNames(subjectImageFolder);
		return directories;
	}

	@Override
	public String[] getImages(final String folder, final String subject, final String sequence) {
		final String sequenceImageFolder = StringUtil.buildImagesFolderName(folder).concat(StringUtil.buildSubjectFolder(subject))
				.concat(StringUtil.buildSequenceFolder(sequence));
		final String[] files = listFileNames(sequenceImageFolder);
		return files;
	}

	@Override
	public String[] getLandmarks(String folder, String subject, String sequence) {
		final String sequenceLandmarksFolder = StringUtil.buildLandmarksFolderName(folder).concat(StringUtil.buildSubjectFolder(subject))
				.concat(StringUtil.buildSequenceFolder(sequence));
		final String[] files = listFileNames(sequenceLandmarksFolder);
		return files;
	}

	@Override
	public String getEmotion(String folder, String subject, String sequence) {
		final String emotionFolder = StringUtil.buildEmotionFolderName(Constants.ORIGINAL_FOLDER).concat(StringUtil.buildSubjectFolder(subject))
				.concat(StringUtil.buildSequenceFolder(sequence));
		final String[] files = listFileNames(emotionFolder);
		return files != null && files.length > 0 ? files[0] : null;
	}

	@Override
	public String getActionUnitList(String folder, String subject, String sequence) {
		final String actionUnitFolder = StringUtil.buildActionUnitsFolderName(Constants.ORIGINAL_FOLDER).concat(StringUtil.buildSubjectFolder(subject))
				.concat(StringUtil.buildSequenceFolder(sequence));
		final String[] files = listFileNames(actionUnitFolder);
		return  files != null && files.length > 0 ? files[0] : null;
	}

	@Override
	public int getSequenceLength(final String folder, final String subject, final String sequence) {
		final String sequenceImageFolder = StringUtil.buildImagesFolderName(folder).concat(StringUtil.buildSubjectFolder(subject))
				.concat(StringUtil.buildSequenceFolder(sequence));
		final String[] files = listFileNames(sequenceImageFolder);
		if (files == null) {

		}
		return files.length;
	}

	@Override
	public String[] listDatabases() {
		String[] directories = listFileNames(Constants.HOME_FOLDER, new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
		return directories;
	}

	private String[] listFileNames(final String folder) {
		return listFileNames(folder, null);
	}

	private String[] listFileNames(final String folder, final FilenameFilter filter) {
		final File file = new File(folder);
		final String[] directories = file.list(filter);
		if (directories != null) {
			Arrays.sort(directories);
		}
		return directories;
	}

}
