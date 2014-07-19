package edu.ubbcluj.emotion.annotator;

import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.FeatureExtractor;
import org.openimaj.image.FImage;
import org.openimaj.ml.annotation.Annotator;

import edu.ubbcluj.emotion.model.Emotion;

public interface BatchAnnotatorProvider {
	public Annotator<FImage, Emotion> getAnnotator(FeatureExtractor<DoubleFV, FImage> featureExtractor);
}
