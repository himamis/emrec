package edu.ubbcluj.emotion.recognition.pca;

import static edu.ubbcluj.emotion.util.Math.subFromRowsP;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.fastica.math.Matrix;

import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.pca.PCA;
import edu.ubbcluj.emotion.recognition.pca.classifier.Classifier;
import edu.ubbcluj.emotion.recognition.pca.trainingdata.EmotionTDP;

public class EmotionRecogniser implements Serializable {

	private static final long	serialVersionUID	= 1L;

	private EmotionTDP			trainingDataProvider;
	private Classifier			classifier;
	private int					k;

	private double[][]			kEigenVectors;
	private double[]			kEigenValues;

	private double[]			meanValues;

	private double[][][]		emotionProjections;

	/**
	 * Recognises emotion
	 * 
	 * @param util
	 * @param tdp
	 * @param K
	 */
	public EmotionRecogniser(final EmotionTDP trainingDataProvider, final Classifier classifier, final int k) {
		this.trainingDataProvider = trainingDataProvider;
		this.classifier = classifier;
		this.k = k;

		setUp();
	}

	private void setUp() {
		double[][] data = trainingDataProvider.getTrainingData();

		// debug("PCA");
		// long start = System.currentTimeMillis();
		PCA pca = new PCA(data);
		// long end = System.currentTimeMillis();
		// debug("Runtime: " + (end - start) + "ms");
		data = null;

		// debug("Selecting K best eigen vectors");
		selectKEigenVectors(pca.getEigenVectors(), pca.getEigenValues());
		meanValues = pca.getMeanValues();
		pca = null;

		// debug("Projecting database images");
		projectTrainingDataOnEigenVectors(kEigenVectors);
		classifier.setUp(emotionProjections, meanValues, kEigenVectors);

	}

	private void selectKEigenVectors(double[][] eigenVectors, double[] eigenValues) {
		kEigenVectors = new double[k][eigenVectors[0].length];
		kEigenValues = new double[k];
		for (int i = eigenVectors.length - k; i < eigenVectors.length; i++) {
			int index = i - (eigenVectors.length - k);
			kEigenVectors[index] = eigenVectors[i];
			kEigenValues[index] = eigenValues[i];
		}
	}

	private void projectTrainingDataOnEigenVectors(double[][] eigenVectors) {
		int emotionCount = Emotion.values().length;
		emotionProjections = new double[emotionCount][][];
		double[][][] emotionData = trainingDataProvider.getTrainingDataByEmotion();
		for (int i = 0; i < emotionCount; i++) {
			subFromRowsP(emotionData[i], meanValues);
			double[][] projection = Matrix.mult(eigenVectors, Matrix.transpose(emotionData[i]));
			emotionProjections[i] = projection;
		}
	}

	public Emotion recogniseEmotion(BufferedImage image) {
		return classifier.classify(image);
	}

	private void writeObject(ObjectOutputStream stream) throws IOException {
		// serialise k
		stream.writeInt(k);

		// serialise eigen values
		for (int i = 0; i < k; i++) {
			stream.writeDouble(kEigenValues[i]);
		}

		// serialise eigen vectors
		for (int i = 0; i < k; i++) {
			stream.writeInt(kEigenVectors[i].length);
			for (int j = 0; j < kEigenVectors[i].length; j++) {
				stream.writeDouble(kEigenVectors[i][j]);
			}
		}

		// serialise mean values
		stream.writeInt(meanValues.length);
		for (int i = 0; i < meanValues.length; i++) {
			stream.writeDouble(meanValues[i]);
		}

		// serialise emotion projections
		stream.writeInt(emotionProjections.length);
		for (int i = 0; i < emotionProjections.length; i++) {
			stream.writeInt(emotionProjections[i].length);
			for (int j = 0; j < emotionProjections[i].length; j++) {
				stream.writeInt(emotionProjections[i][j].length);
				for (int k = 0; k < emotionProjections[i][j].length; k++) {
					stream.writeDouble(emotionProjections[i][j][k]);
				}
			}
		}
		stream.writeObject(classifier.getClass());
	}

	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		// deserialise k
		k = stream.readInt();

		// deserialise eigen values
		kEigenValues = new double[k];
		for (int i = 0; i < k; i++) {
			kEigenValues[i] = stream.readDouble();
		}

		// deserialise eigen vectors
		kEigenVectors = new double[k][];
		for (int i = 0; i < k; i++) {
			int length = stream.readInt();
			kEigenVectors[i] = new double[length];
			for (int j = 0; j < kEigenVectors[i].length; j++) {
				kEigenVectors[i][j] = stream.readDouble();
			}
		}

		// deserialise mean values
		int length = stream.readInt();
		meanValues = new double[length];
		for (int i = 0; i < meanValues.length; i++) {
			meanValues[i] = stream.readDouble();
		}

		// deserialise emotion projections
		length = stream.readInt();
		emotionProjections = new double[length][][];
		for (int i = 0; i < emotionProjections.length; i++) {
			length = stream.readInt();
			emotionProjections[i] = new double[length][];
			for (int j = 0; j < emotionProjections[i].length; j++) {
				length = stream.readInt();
				emotionProjections[i][j] = new double[length];
				for (int k = 0; k < emotionProjections[i][j].length; k++) {
					emotionProjections[i][j][k] = stream.readDouble();
				}
			}
		}

		// deserialise classifier
		Class<?> clazz = (Class<?>) stream.readObject();
		try {
			classifier = (Classifier) clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		classifier.setUp(emotionProjections, meanValues, kEigenVectors);
	}

}
