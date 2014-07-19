package edu.ubbcluj.emotion.engine;

import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.image.FImage;

import edu.ubbcluj.emotion.model.Emotion;

public interface EmotionRecogniserProvider {
	public EmotionRecogniser create(GroupedDataset<Emotion, ListDataset<FImage>, FImage> trainingData);
}
