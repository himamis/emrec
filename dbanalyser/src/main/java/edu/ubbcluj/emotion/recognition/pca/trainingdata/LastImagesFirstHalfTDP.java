package edu.ubbcluj.emotion.recognition.pca.trainingdata;

import edu.ubbcluj.emotion.database.file.loader.ImageFilter;
import edu.ubbcluj.emotion.database.file.loader.ImageFilterAdapter;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoaderFactory;
import edu.ubbcluj.emotion.model.Emotion;

public class LastImagesFirstHalfTDP extends AbstractTDP {

	public LastImagesFirstHalfTDP(final ResourceLoaderFactory resourceLoaderFactory, final String database) {
		super(resourceLoaderFactory, database);
	}

	@Override
	protected ImageFilter getImageFilter(Emotion emotion) {
		return new ImageFilterAdapter(emotion) {
			@Override
			public int filter() {
				return FIRST_HALF;
			}
			
			@Override
			public boolean filter(boolean isLastImage) {
				return isLastImage;
			}
		};
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return super.getDescription() + "first half of the last images";
	}
}
