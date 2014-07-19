package edu.ubbcluj.emotion.feature;

import org.openimaj.feature.FeatureExtractor;
import org.openimaj.image.FImage;
import org.openimaj.math.geometry.shape.Rectangle;

/**
 * Extracts a rectangle from an image.
 * @author bencze
 *
 */
public class FacialFeaturesExtractor implements FeatureExtractor<FImage, FImage> {

	private Rectangle rectangle;
	
	/**
	 * Constructs a feature extractos
	 * @param rectangle the ROI to be extracted from the image
	 */
	public FacialFeaturesExtractor(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	
	@Override
	public FImage extractFeature(FImage object) {
		return object.extractROI(rectangle);
	}

}
