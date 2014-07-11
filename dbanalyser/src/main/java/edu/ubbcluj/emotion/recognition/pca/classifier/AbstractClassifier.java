package edu.ubbcluj.emotion.recognition.pca.classifier;

import static edu.ubbcluj.emotion.util.Math.subVectorP;

import java.awt.image.BufferedImage;

import org.fastica.math.Matrix;

import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.util.HasDescription;
import edu.ubbcluj.emotion.util.ImageUtil;

abstract class AbstractClassifier implements Classifier, HasDescription {

	protected double[][][]	projectionImages;
	protected double[]		meanValues;
	protected double[][]	eigenVectors;

	public AbstractClassifier() {
	}

	@Override
	public void setUp(double[][][] projectionImages, double[] meanValues, double[][] eigenVectors) {
		this.projectionImages = projectionImages;
		this.meanValues = meanValues;
		this.eigenVectors = eigenVectors;
	}

	@Override
	public Emotion classify(BufferedImage image) {
		final double[][] data = ImageUtil.convertImageToColumnVector(image);
		subVectorP(data, meanValues);
		double[][] projection = Matrix.mult(eigenVectors, data);
		int emotionIndex = classify(projection);
		return Emotion.values()[emotionIndex];
	}

	@Override
	public String getDescription() {
		return "Classifier: ";
	}

	protected int getNumberOfClasses() {
		return projectionImages.length;
	}

	protected abstract int classify(final double columnVector[][]);
}
