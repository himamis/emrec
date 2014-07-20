package edu.ubbcluj.emotion.annotator;

import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.FeatureExtractor;
import org.openimaj.image.FImage;
import org.openimaj.ml.annotation.BatchAnnotator;

import edu.ubbcluj.emotion.model.DatasetKey;

public interface BatchAnnotatorProvider<KEY extends DatasetKey> {
	public BatchAnnotator<FImage, KEY> getAnnotator(FeatureExtractor<DoubleFV, FImage> featureExtractor);
}
