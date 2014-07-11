package edu.ubbcluj.emotion.recognition.pca.trainingdata;

import java.awt.image.BufferedImage;
import java.util.List;

public interface TrainingDataProvider {

	double[][] getTrainingData();

	List<BufferedImage> getTrainingImages();

}
