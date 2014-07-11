package edu.ubbcluj.emotion.recognition.pca.trainingdata;

import java.awt.image.BufferedImage;
import java.util.List;

public interface EmotionTDP extends TrainingDataProvider {
	
	public double[][][] getTrainingDataByEmotion();
	
	public List<BufferedImage>[] getTrainingImagesByEmotion();
	
	

}
