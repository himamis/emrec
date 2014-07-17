package edu.ubbcluj.emotion.dataset;

import org.openimaj.math.geometry.shape.Rectangle;

public interface HasFacialFeatures {
	
	/**
	 * @param f {@link FacialFeature}
	 * @return a {@link Rectangle} containing the facial feature location
	 */
	Rectangle getFacialFeatureLocation(FacialFeature f);
}
