package edu.ubbcluj.emotion.engine;

import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.image.FImage;

import edu.ubbcluj.emotion.annotator.BatchAnnotatorProvider;
import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.util.HasName;

public interface EmotionRecogniserProvider extends HasName {
	public EmotionRecogniser create(GroupedDataset<Emotion, ListDataset<FImage>, FImage> trainingData, BatchAnnotatorProvider<Emotion> annotatorProvider);
}
