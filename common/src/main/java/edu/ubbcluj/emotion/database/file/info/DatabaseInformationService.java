package edu.ubbcluj.emotion.database.file.info;

import java.util.HashMap;
import java.util.Map;

public class DatabaseInformationService {

	private final ResourceInformationService	resourceInfo;
	private final String						folder;

	private String[]							subjects;
	private final Map<String, String[]>			sequenceCache		= new HashMap<>();
	private final Map<String, Integer>			sequenceLengthCache	= new HashMap<>();

	public DatabaseInformationService(final String folder, final ResourceInformationService resourceInfo) {
		this.folder = folder;
		this.resourceInfo = resourceInfo;
	}

	public String[] getSubjects() {
		if (subjects == null) {
			subjects = resourceInfo.getSubjects(folder);
		}
		return subjects;

	}

	public String[] getSequences(final String subject) {
		String[] sequences = null;
		if ((sequences = sequenceCache.get(subject)) == null) {
			sequences = resourceInfo.getSequences(folder, subject);
			sequenceCache.put(subject, sequences);
		}
		return sequences;
	}

	public int getSequenceLength(final String subject, final String sequence) {
		Integer sequenceLength = null;
		final String key = subject.concat(sequence);
		if ((sequenceLength = sequenceLengthCache.get(key)) == null) {
			sequenceLength = resourceInfo.getSequenceLength(folder, subject, sequence);
			sequenceLengthCache.put(key, sequenceLength);
		}
		return sequenceLength;
	}

	public boolean hasEmotion(final String subject, final String sequence) {
		String emotion = resourceInfo.getEmotion(folder, subject, sequence);
		return emotion != null;
	}

	public boolean hasLandmarks(final String subject, final String sequence) {
		String[] landmarks = resourceInfo.getLandmarks(folder, subject, sequence);
		return landmarks != null && landmarks.length > 0;
	}

	public boolean hasLandmarks() {
		String[] landmarks = resourceInfo.getLandmarks(folder, "S005", "001");
		return landmarks != null && landmarks.length > 0;
	}

}
