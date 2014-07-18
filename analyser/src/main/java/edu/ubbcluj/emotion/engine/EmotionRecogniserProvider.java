package edu.ubbcluj.emotion.engine;

import edu.ubbcluj.emotion.EmotionRecogniser;
import edu.ubbcluj.emotion.dataset.AbstractDataset;
import edu.ubbcluj.emotion.model.Emotion;

public interface EmotionRecogniserProvider {
	public EmotionRecogniser create(AbstractDataset<Emotion> trainingData);
}
