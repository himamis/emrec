package edu.ubbcluj.emotion.recognition.pca.trainingdata;

import edu.ubbcluj.emotion.database.file.loader.ImageFilter;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoaderFactory;
import edu.ubbcluj.emotion.model.Emotion;

public class LastImagesExceptTDP extends AbstractTDP {

	// private int leaveOutIndex;
	// private Emotion leaveOutEmotion;

	public LastImagesExceptTDP(final ResourceLoaderFactory resourceLoaderFactory, final String database) {
		super(resourceLoaderFactory, database);
	}

	@Override
	protected ImageFilter getImageFilter(Emotion emotion) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getDescription() {
		return super.getDescription() + "last images except the given index";
	}
}
