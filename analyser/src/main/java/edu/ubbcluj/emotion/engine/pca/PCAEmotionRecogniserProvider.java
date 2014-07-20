package edu.ubbcluj.emotion.engine.pca;

import org.fastica.EigenValueFilter;
import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.FeatureExtractor;
import org.openimaj.image.FImage;
import org.openimaj.image.feature.FImage2DoubleFV;
import org.openimaj.math.geometry.shape.Rectangle;
import org.openimaj.ml.annotation.BatchAnnotator;

import edu.ubbcluj.emotion.annotator.BatchAnnotatorProvider;
import edu.ubbcluj.emotion.dataset.AbstractDataset;
import edu.ubbcluj.emotion.dataset.FacialFeature;
import edu.ubbcluj.emotion.engine.EmotionRecogniser;
import edu.ubbcluj.emotion.engine.EmotionRecogniserProvider;
import edu.ubbcluj.emotion.feature.CombinedFeatureExtractor;
import edu.ubbcluj.emotion.feature.FacialFeaturesExtractor;
import edu.ubbcluj.emotion.feature.ListFeatureExtractor;
import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.pca.PCA;
import edu.ubbcluj.emotion.util.GroupedDatasetHelper;

public class PCAEmotionRecogniserProvider implements EmotionRecogniserProvider {

	private int							k;
	private FacialFeature[]				features;
	private AbstractDataset<Emotion>	dataset;

	public PCAEmotionRecogniserProvider(int k, AbstractDataset<Emotion> dataset, FacialFeature... features) {
		this.k = k;
		this.dataset = dataset;
		this.features = features;
	}

	@Override
	public EmotionRecogniser create(GroupedDataset<Emotion, ListDataset<FImage>, FImage> trainingData, BatchAnnotatorProvider<Emotion> annotatorProvider) {
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
			PCA pca = new PCA(data);
			// use only the best k eigenvectors
			EigenValueFilter evFilter = new KEigenValueFilter(k);
			evFilter.passEigenValues(pca.getEigenValues(), pca.getEigenVectors());
			double[][] eigenVectors = evFilter.getEigenVectors();
			double[] meanValues = pca.getMeanValues();
			// create the feature extractors and train the annotators
			FeatureExtractorPCA featureExtractor = new FeatureExtractorPCA(meanValues, eigenVectors);
			CombinedFeatureExtractor fe = new CombinedFeatureExtractor(facialFeatureExtractor, featureExtractor);
			fes[i] = fe;
		}
		
		FeatureExtractor<DoubleFV, FImage> listFeatureExtractor = new ListFeatureExtractor(fes);
		
		BatchAnnotator<FImage, Emotion> annotator = annotatorProvider.getAnnotator(listFeatureExtractor);
		
		annotator.train(trainingData);
		return new EmotionRecogniser(annotator);
	}

	@Override
	public String toString() {
		return "Emotion Recogniser using PCA (Principal Component Analisys) with " + k + " eigenvectors.";
	}
}
