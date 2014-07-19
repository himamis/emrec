package edu.ubbcluj.emotion;

import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.FeatureExtractor;
import org.openimaj.image.FImage;

public class ListFeatureExtractor implements FeatureExtractor<DoubleFV, FImage> {

	private FeatureExtractor<DoubleFV, FImage>[]	extractors;

	@SafeVarargs
	public ListFeatureExtractor(FeatureExtractor<DoubleFV, FImage>... extractors) {
		this.extractors = extractors;
	}

	@Override
	public DoubleFV extractFeature(FImage object) {
		DoubleFV[] fvs = new DoubleFV[extractors.length];
		DoubleFV fv = new DoubleFV(0);
		for (int i = 0; i < extractors.length; i++) {
			fvs[i] = extractors[i].extractFeature(object);
		}
		return fv.concatenate(fvs);
	}

}
