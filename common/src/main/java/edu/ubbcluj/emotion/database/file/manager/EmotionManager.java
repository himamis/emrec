package edu.ubbcluj.emotion.database.file.manager;

import edu.ubbcluj.emotion.model.Emotion;

public interface EmotionManager {
	
	public Emotion loadEmotion(final String folder, final String subject, final String sequence);
	
	public void saveEmotion(final String folder, final Emotion emotion, final String subject, final String sequence);

}
