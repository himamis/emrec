package edu.ubbcluj.emotion.recognition.pca;

import static edu.ubbcluj.emotion.util.Math.subFromRowsP;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.record.Record;
import org.fastica.math.Matrix;
import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListBackedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.data.dataset.MapBackedDataset;
import org.openimaj.experiment.evaluation.classification.ClassificationEvaluator;
import org.openimaj.experiment.evaluation.classification.analysers.confusionmatrix.CMAnalyser;
import org.openimaj.experiment.evaluation.classification.analysers.confusionmatrix.CMResult;
import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.FeatureExtractor;
import org.openimaj.image.FImage;
import org.openimaj.ml.annotation.BatchAnnotator;
import org.openimaj.ml.annotation.linear.LiblinearAnnotator;
import org.openimaj.ml.annotation.linear.LiblinearAnnotator.Mode;

import de.bwaldvogel.liblinear.SolverType;
import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.pca.PCA;
import edu.ubbcluj.emotion.recognition.pca.classifier.Classifier;
import edu.ubbcluj.emotion.recognition.pca.trainingdata.EmotionTDP;
import edu.ubbcluj.emotion.util.ImageUtil;

public class EmotionRecogniserOI implements Serializable {

	private static final long						serialVersionUID	= 1L;

	private EmotionTDP								trainingDataProvider;
	private Classifier								classifier;
	private int										k;

	private double[][]								kEigenVectors;
	private double[]								kEigenValues;

	private double[]								meanValues;

	private double[][][]							emotionProjections;

	private BatchAnnotator<double[], Emotion>		annotator;
	private FeatureExtractor<DoubleFV, double[]>	fe;

	/**
	 * Recognises emotion
	 * 
	 * @param util
	 * @param tdp
	 * @param K
	 */
	public EmotionRecogniserOI(final EmotionTDP trainingDataProvider, final Classifier classifier, final int k) {
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
		fe = pca;
		// long end = System.currentTimeMillis();
		// debug("Runtime: " + (end - start) + "ms");
		data = null;

		// debug("Selecting K best eigen vectors");
		selectKEigenVectors(pca.getEigenVectors(), pca.getEigenValues());
		meanValues = pca.getMeanValues();

		// debug("Projecting database images");
		projectTrainingDataOnEigenVectors(kEigenVectors);

		annotator = new LiblinearAnnotator<double[], Emotion>(pca, Mode.MULTICLASS, SolverType.L2R_L2LOSS_SVC, 1.0, 0.00001);
		annotator.train(trainingDataProvider.getGroupedDataset());
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

	public BatchAnnotator<double[], Emotion> getAnnotator() {
		return annotator;
	}

	public Emotion recogniseEmotion(BufferedImage image) {

		return classifier.classify(image);
	}

	private GroupedDataset<Emotion, ListDataset<double[]>, double[]> getGroupedDataset() {
		Map<Emotion, ListDataset<double[]>> map = new HashMap<Emotion, ListDataset<double[]>>();
		for (int i = 0; i < emotionProjections.length; i++) {
			ListBackedDataset<double[]> lbd = new ListBackedDataset<double[]>();
			// iterate through emotions
			for (int k = 0; k < emotionProjections[i][0].length; k++) {
				double[] d = new double[emotionProjections[i].length];
				for (int j = 0; j < emotionProjections[i].length; j++) {
					d[j] = emotionProjections[i][j][k];
				}
				lbd.add(d);
			}
			map.put(Emotion.values()[i], lbd);
		}
		return new MapBackedDataset<Emotion, ListDataset<double[]>, double[]>(map);
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
