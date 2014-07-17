package edu.ubbcluj.emotion.dataset;

import org.openimaj.math.geometry.shape.Rectangle;

import edu.ubbcluj.emotion.ck.file.loader.FileResourceLoaderFactory;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoader;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoaderFactory;
import edu.ubbcluj.emotion.model.Emotion;

class CKEmotionSmallDataset extends CKDataset<Emotion> {
	
	private static final String FOLDER = "openimaj_small3";
	
	public CKEmotionSmallDataset() {
		ResourceLoaderFactory rlf = new FileResourceLoaderFactory();
		ResourceLoader resourceLoader = rlf.getResourceLoader(FOLDER);
	}

	@Override
	public Rectangle getFacialFeatureLocation(FacialFeature f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DatasetInformation<Emotion> getInformation() {
		// TODO Auto-generated method stub
		return null;
	}

}
