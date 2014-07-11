package edu.ubbcluj.emotion.database.file.manager;

import edu.ubbcluj.emotion.model.ImageSequence;

public interface ImageSequenceManager {

	public ImageSequence loadImageSequence(final String folder, final String subject, final String sequence);

	public void saveImageSequence(final String folder, final ImageSequence imageSequence, final String subject, final String sequence);
}
