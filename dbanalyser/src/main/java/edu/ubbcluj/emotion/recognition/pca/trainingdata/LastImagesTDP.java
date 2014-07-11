package edu.ubbcluj.emotion.recognition.pca.trainingdata;

import edu.ubbcluj.emotion.database.file.loader.ImageFilter;
import edu.ubbcluj.emotion.database.file.loader.ImageFilterAdapter;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoaderFactory;
import edu.ubbcluj.emotion.model.Emotion;

public class LastImagesTDP extends AbstractTDP {

	public LastImagesTDP(final ResourceLoaderFactory resourceLoaderFactory, final String database) {
		super(resourceLoaderFactory, database);
	}

	@Override
	protected ImageFilter getImageFilter(Emotion emotion) {
		return new ImageFilterAdapter(emotion) {
			@Override
			public boolean filter(boolean isLastImage) {
				return isLastImage;
			}
		};
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return super.getDescription() + "last images";
	}
}
