package edu.ubbcluj.emotion.engine;

import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.FeatureExtractor;
import org.openimaj.image.FImage;
import org.openimaj.image.feature.FImage2DoubleFV;
import org.openimaj.math.geometry.shape.Rectangle;
import org.openimaj.ml.annotation.BatchAnnotator;

import edu.ubbcluj.emotion.algorithm.Algorithm;
import edu.ubbcluj.emotion.algorithm.AlgorithmException;
import edu.ubbcluj.emotion.annotator.BatchAnnotatorProvider;
import edu.ubbcluj.emotion.dataset.AbstractDataset;
import edu.ubbcluj.emotion.dataset.FacialFeature;
import edu.ubbcluj.emotion.feature.CombinedFeatureExtractor;
import edu.ubbcluj.emotion.feature.FacialFeaturesExtractor;
import edu.ubbcluj.emotion.feature.ListFeatureExtractor;
import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.util.GroupedDatasetHelper;
import edu.ubbcluj.emotion.util.HasName;
import edu.ubbcluj.emotion.util.StringHelper;

public abstract class EmotionRecogniserProvider<ALGO extends Algorithm> implements HasName {
// ...
	private FacialFeature[]				features;
	private AbstractDataset<Emotion>	dataset;

	public EmotionRecogniserProvider(AbstractDataset<Emotion> dataset, FacialFeature... features) {
		this.dataset = dataset;
		// if no facial features were specified, use the full face
		if (features == null || features.length == 0) {
			features = new FacialFeature[] { FacialFeature.FULL_FACE };
		}
		this.features = features;
	}

	public EmotionRecogniser create(GroupedDataset<Emotion, ListDataset<FImage>, FImage> trainingData,
			BatchAnnotatorProvider<Emotion> annotatorProvider) {
		CombinedFeatureExtractor[] fes = new CombinedFeatureExtractor[features.length];
		for (int i = 0; i < features.length; i++) {
			FacialFeature feature = features[i];
			// get the location of the facial feature
			Rectangle rectangle = dataset.getFacialFeatureLocation(feature);
			// extract the facial features from the dataset
			FeatureExtractor<FImage, FImage> facialFeatureExtractor = new FacialFeaturesExtractor(rectangle);
			GroupedDataset<Emotion, ListDataset<FImage>, FImage> featureDataset = GroupedDatasetHelper.extractFeature(trainingData,
					facialFeatureExtractor);
			// run the algorithm on the dataset
			double[][] data = GroupedDatasetHelper.getMatrixData(featureDataset, FImage2DoubleFV.INSTANCE);
			ALGO algorithm = getAlgorithm();
			try {
				algorithm.train(data);
			} catch (AlgorithmException ex) {
				throw new RuntimeException(ex);
			}
			// create the feature extractor
			FeatureExtractor<DoubleFV, FImage> featureExtractor = algorithm.getFeatureExtractor();
			CombinedFeatureExtractor fe = new CombinedFeatureExtractor(facialFeatureExtractor, featureExtractor);
			fes[i] = fe;
		}

		FeatureExtractor<DoubleFV, FImage> listFeatureExtractor = new ListFeatureExtractor(fes);

		BatchAnnotator<FImage, Emotion> annotator = annotatorProvider.getAnnotator(listFeatureExtractor);

		annotator.train(trainingData);
		return new EmotionRecogniser(annotator);
	}

	protected abstract ALGO getAlgorithm();

	@Override
	public String toString() {
		return "Facial features are: " + StringHelper.buildFacialFeaturesString(features) + ".";
	}
}
