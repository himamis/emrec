package edu.ubbcluj.emotion.engine;

import org.fastica.FastICAException;
import org.openimaj.image.FImage;
import org.openimaj.image.feature.FImage2DoubleFV;
import org.openimaj.ml.annotation.linear.LiblinearAnnotator;
import org.openimaj.ml.annotation.linear.LiblinearAnnotator.Mode;

import com.davidsoergel.conja.RuntimeExecutionException;

import de.bwaldvogel.liblinear.SolverType;
import edu.ubbcluj.emotion.EmotionRecogniser;
import edu.ubbcluj.emotion.FeatureExtractorICA;
import edu.ubbcluj.emotion.dataset.AbstractDataset;
import edu.ubbcluj.emotion.fastica.FastICA;
import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.util.DataUtil;

public class ICAEmotionRecogniserProvider implements EmotionRecogniserProvider {
	
	private int numICs;
	
	public ICAEmotionRecogniserProvider(int numICs) {
		this.numICs = numICs;
	}

	@Override
	public EmotionRecogniser create(AbstractDataset<Emotion> trainingData) {
		double[][] data = DataUtil.<Emotion, FImage> getMatrixData(trainingData, FImage2DoubleFV.INSTANCE);
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
