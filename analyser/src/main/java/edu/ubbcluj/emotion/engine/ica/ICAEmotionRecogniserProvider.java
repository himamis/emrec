package edu.ubbcluj.emotion.engine.ica;

import org.fastica.FastICAException;
import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.image.FImage;
import org.openimaj.image.feature.FImage2DoubleFV;
import org.openimaj.ml.annotation.linear.LiblinearAnnotator;
import org.openimaj.ml.annotation.linear.LiblinearAnnotator.Mode;

import com.davidsoergel.conja.RuntimeExecutionException;

import de.bwaldvogel.liblinear.SolverType;
import edu.ubbcluj.emotion.annotator.BatchAnnotatorProvider;
import edu.ubbcluj.emotion.dataset.AbstractDataset;
import edu.ubbcluj.emotion.dataset.FacialFeature;
import edu.ubbcluj.emotion.engine.EmotionRecogniser;
import edu.ubbcluj.emotion.engine.EmotionRecogniserProvider;
import edu.ubbcluj.emotion.fastica.FastICA;
import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.util.GroupedDatasetHelper;

public class ICAEmotionRecogniserProvider implements EmotionRecogniserProvider {

	private int							numICs;
	private FacialFeature[]				features;
	private AbstractDataset<Emotion>	dataset;

	public ICAEmotionRecogniserProvider(int numICs, AbstractDataset<Emotion> dataset, FacialFeature... features) {
		this.numICs = numICs;
		this.features = features;
	}

	@Override
	public EmotionRecogniser create(GroupedDataset<Emotion, ListDataset<FImage>, FImage> trainingData, BatchAnnotatorProvider<Emotion> annotatorProvider) {
		double[][] data = GroupedDatasetHelper.<Emotion, FImage> getMatrixData(trainingData, FImage2DoubleFV.INSTANCE);
		double[][] transposedData = org.fastica.math.Matrix.transpose(data);
		try {
			FastICA fastICA = new FastICA(transposedData, numICs);

			FeatureExtractorICA featureExtractor = new FeatureExtractorICA(fastICA.getSeparatingMatrix());

			LiblinearAnnotator<FImage, Emotion> annotator = new LiblinearAnnotator<FImage, Emotion>(featureExtractor, Mode.MULTICLASS,
					SolverType.L2R_L2LOSS_SVC, 1.0, 0.00001);
			annotator.train(trainingData);

			return new EmotionRecogniser(annotator);

		} catch (FastICAException e) {
			throw new RuntimeExecutionException(e);
		}
	}

	@Override
	public String toString() {
		return "Emotion Recogniser using ICA (Independent Component Analisys) with " + numICs + " independent components.";
	}

}
