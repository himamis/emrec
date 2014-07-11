package edu.ubbcluj.emotion.recognition.pca.classifier;

import java.awt.image.BufferedImage;

import edu.ubbcluj.emotion.model.Emotion;

public interface Classifier {

	public void setUp(final double[][][] projectionImages, final double[] meanValues, final double[][] eigenVectors);

	public Emotion classify(final BufferedImage image);
}
