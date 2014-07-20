package edu.ubbcluj.emotion.algorithm;

import org.openimaj.feature.FeatureExtractor;

public interface FeatureExtractorProvider<FEATURE, OBJECT> {
	public FeatureExtractor<FEATURE, OBJECT> getFeatureExtractor();
}
