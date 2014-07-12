package edu.ubbcluj.emotion.recognition.pca.trainingdata;

import java.awt.image.BufferedImage;
import java.util.List;

import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListDataset;

import edu.ubbcluj.emotion.model.Emotion;

public interface EmotionTDP extends TrainingDataProvider {

	public double[][][] getTrainingDataByEmotion();

	public List<BufferedImage>[] getTrainingImagesByEmotion();

	public GroupedDataset<Emotion, ListDataset<double[]>, double[]> getGroupedDataset();

}
