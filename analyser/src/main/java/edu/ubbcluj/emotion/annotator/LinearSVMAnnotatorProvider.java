package edu.ubbcluj.emotion.annotator;

import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.FeatureExtractor;
import org.openimaj.image.FImage;
import org.openimaj.ml.annotation.BatchAnnotator;
import org.openimaj.ml.annotation.linear.LinearSVMAnnotator;

import edu.ubbcluj.emotion.model.Emotion;

public class LinearSVMAnnotatorProvider implements BatchAnnotatorProvider {

	@Override
	public BatchAnnotator<FImage, Emotion> getAnnotator(FeatureExtractor<DoubleFV, FImage> featureExtractor) {
		return new LinearSVMAnnotator<FImage, Emotion>(featureExtractor);
	}

}
