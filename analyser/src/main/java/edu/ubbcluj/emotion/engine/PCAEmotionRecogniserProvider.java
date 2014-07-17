package edu.ubbcluj.emotion.engine;

import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.image.FImage;
import org.openimaj.image.feature.FImage2DoubleFV;
import org.openimaj.ml.annotation.linear.LiblinearAnnotator;
import org.openimaj.ml.annotation.linear.LiblinearAnnotator.Mode;

import de.bwaldvogel.liblinear.SolverType;
import edu.ubbcluj.emotion.EmotionRecogniser;
import edu.ubbcluj.emotion.FeatureExtractorPCA;
import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.pca.PCA;
import edu.ubbcluj.emotion.util.DataUtil;

public class PCAEmotionRecogniserProvider implements EmotionRecogniserProvider{

	private int	k;

	public PCAEmotionRecogniserProvider(int k) {
		this.k = k;
	}

	@Override
	public EmotionRecogniser create(GroupedDataset<Emotion, ListDataset<FImage>, FImage> trainingData) {
		double[][] data = DataUtil.<Emotion, FImage> getMatrixData(trainingData, FImage2DoubleFV.INSTANCE);
		PCA pca = new PCA(data);

		// Select k best eigenvectors
		double[][] eigenVectors = pca.getEigenVectors();
		double[][] kEigenVectors = new double[k][eigenVectors[0].length];
		for (int i = eigenVectors.length - k; i < eigenVectors.length; i++) {
			int index = i - (eigenVectors.length - k);
			kEigenVectors[index] = eigenVectors[i];
		}
		double[] meanValues = pca.getMeanValues();

		FeatureExtractorPCA featureExtractor = new FeatureExtractorPCA(meanValues, kEigenVectors);

		LiblinearAnnotator<FImage, Emotion> annotator = new LiblinearAnnotator<FImage, Emotion>(featureExtractor, Mode.MULTICLASS,
				SolverType.L2R_L2LOSS_SVC, 1.0, 0.00001);
		annotator.train(trainingData);

		return new EmotionRecogniser(annotator);
	}

	
	@Override
	public String toString() {
		return "Emotion Recogniser using PCA (Principal Component Analisys) with " + k + " eigenvectors.";
	}
}
