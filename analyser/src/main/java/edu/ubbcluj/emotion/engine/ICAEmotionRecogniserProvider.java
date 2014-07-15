package edu.ubbcluj.emotion.engine;

import org.fastica.FastICA;
import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.image.FImage;
import org.openimaj.image.feature.FImage2DoubleFV;
import org.openimaj.ml.annotation.linear.LiblinearAnnotator;
import org.openimaj.ml.annotation.linear.LiblinearAnnotator.Mode;

import ch.akuhn.matrix.Matrix;
import de.bwaldvogel.liblinear.SolverType;
import edu.ubbcluj.emotion.EmotionRecogniser;
import edu.ubbcluj.emotion.FeatureExtractorPCA;
import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.pca.PCA;
import edu.ubbcluj.emotion.util.DataUtil;

public class ICAEmotionRecogniserProvider implements EmotionRecogniserProvider {
	
	private int numberOfIndependentComponents;
	
	public ICAEmotionRecogniserProvider(int numberOfIndependentComponents) {
		this.numberOfIndependentComponents = numberOfIndependentComponents;
	}

	@Override
	public EmotionRecogniser create(GroupedDataset<Emotion, ListDataset<FImage>, FImage> trainingData) {
		double[][] data = DataUtil.<Emotion, FImage> getMatrixData(trainingData, FImage2DoubleFV.INSTANCE);
		double[][] transposedData = org.fastica.math.Matrix.transpose(data);
		
		//FastICA ica = new FastICA(data, numberOfIndependentComponents);
		/*ica.get
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

		return new EmotionRecogniser(annotator);*/
		return null;
	}

}
