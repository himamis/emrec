package edu.ubbcluj.emotion.annotator;

import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.FeatureExtractor;
import org.openimaj.image.FImage;
import org.openimaj.ml.annotation.BatchAnnotator;
import org.openimaj.ml.annotation.linear.LinearSVMAnnotator;

import edu.ubbcluj.emotion.model.DatasetKey;

public class LinearSVMAnnotatorProvider<KEY extends DatasetKey> implements BatchAnnotatorProvider<KEY> {

	@Override
	public BatchAnnotator<FImage, KEY> getAnnotator(FeatureExtractor<DoubleFV, FImage> featureExtractor) {
		return new LinearSVMAnnotator<FImage, KEY>(featureExtractor);
	}

	@Override
	public String toString() {
		return "Annotator based on a set of linear SVMs (one per annotation). The SVMs use the PEGASOS algorithm.";
	}

	@Override
	public String getName() {
		return "AN_SVM";
	}
}
