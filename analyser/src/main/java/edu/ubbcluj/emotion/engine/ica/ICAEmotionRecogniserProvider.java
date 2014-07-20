package edu.ubbcluj.emotion.engine.ica;

import org.fastica.FastICAException;
import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.FeatureExtractor;
import org.openimaj.image.FImage;
import org.openimaj.image.feature.FImage2DoubleFV;
import org.openimaj.math.geometry.shape.Rectangle;
import org.openimaj.ml.annotation.BatchAnnotator;

import edu.ubbcluj.emotion.algorithm.fastica.FastICA;
import edu.ubbcluj.emotion.annotator.BatchAnnotatorProvider;
import edu.ubbcluj.emotion.dataset.AbstractDataset;
import edu.ubbcluj.emotion.dataset.FacialFeature;
import edu.ubbcluj.emotion.engine.EmotionRecogniser;
import edu.ubbcluj.emotion.engine.EmotionRecogniserProvider;
import edu.ubbcluj.emotion.feature.CombinedFeatureExtractor;
import edu.ubbcluj.emotion.feature.FacialFeaturesExtractor;
import edu.ubbcluj.emotion.feature.ListFeatureExtractor;
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
	public EmotionRecogniser create(GroupedDataset<Emotion, ListDataset<FImage>, FImage> trainingData,
			BatchAnnotatorProvider<Emotion> annotatorProvider) {
		// if no facial features were specified, use the full face
		if (features == null || features.length == 0) {
			features = new FacialFeature[] { FacialFeature.FULL_FACE };
		}
		CombinedFeatureExtractor[] fes = new CombinedFeatureExtractor[features.length];
		for (int i = 0; i < features.length; i++) {
			FacialFeature feature = features[i];
			// get the location of the facial feature
			Rectangle rectangle = dataset.getFacialFeatureLocation(feature);
			// extract the facial features from the dataset
			FeatureExtractor<FImage, FImage> facialFeatureExtractor = new FacialFeaturesExtractor(rectangle);
			GroupedDataset<Emotion, ListDataset<FImage>, FImage> featureDataset = GroupedDatasetHelper.extractFeature(trainingData,
					facialFeatureExtractor);
			// run pca on the dataset
			double[][] data = GroupedDatasetHelper.getMatrixData(featureDataset, FImage2DoubleFV.INSTANCE);
			double[][] transposedData = org.fastica.math.Matrix.transpose(data);
			try {
				FastICA fastICA = new FastICA(transposedData, numICs);

				FeatureExtractorICA featureExtractor = new FeatureExtractorICA(fastICA.getSeparatingMatrix());
				CombinedFeatureExtractor fe = new CombinedFeatureExtractor(facialFeatureExtractor, featureExtractor);
				fes[i] = fe;
			} catch (FastICAException ex) {
				throw new RuntimeException("FastICA throw an unexpected exception.", ex);
			}
		}
		FeatureExtractor<DoubleFV, FImage> listFeatureExtractor = new ListFeatureExtractor(fes);
		BatchAnnotator<FImage, Emotion> annotator = annotatorProvider.getAnnotator(listFeatureExtractor);
		annotator.train(trainingData);
		return new EmotionRecogniser(annotator);
	}

	@Override
	public String toString() {
		return "Emotion Recogniser using ICA (Independent Component Analisys) with " + numICs + " independent components.";
	}

}
