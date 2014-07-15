package edu.ubbcluj.emotion;

import java.util.List;

import org.openimaj.image.FImage;

public abstract class Face {
	
	private FImage face;
	
	public Face(FImage face) {
		this.face = face;
	}
	
	public abstract List<FImage> getFacialFeatures();

}
