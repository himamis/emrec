package edu.ubbcluj.emotion.database.file.manager;

import edu.ubbcluj.emotion.model.LandmarksSequence;

public interface LandmarksManager {

	public LandmarksSequence loadLandmarks(final String folder, final String subject, final String sequence);

	public void saveLandmarks(final String folder, final LandmarksSequence landmarks, final String subject, final String sequence);

}
