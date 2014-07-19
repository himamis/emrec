package edu.ubbcluj.emotion;

import org.openimaj.feature.FeatureExtractor;
import org.openimaj.image.FImage;
import org.openimaj.math.geometry.shape.Rectangle;


public class FacialFeaturesExtractor implements FeatureExtractor<FImage, FImage> {

	private Rectangle rectangle;
	
	public FacialFeaturesExtractor(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	
	@Override
	public FImage extractFeature(FImage object) {
		return object.extractROI(rectangle);
	}

}
