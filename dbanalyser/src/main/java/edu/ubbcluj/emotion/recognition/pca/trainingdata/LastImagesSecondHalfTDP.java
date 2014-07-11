package edu.ubbcluj.emotion.recognition.pca.trainingdata;

import edu.ubbcluj.emotion.database.file.loader.ImageFilter;
import edu.ubbcluj.emotion.database.file.loader.ImageFilterAdapter;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoaderFactory;
import edu.ubbcluj.emotion.model.Emotion;

public class LastImagesSecondHalfTDP extends AbstractTDP {

	public LastImagesSecondHalfTDP(final ResourceLoaderFactory resourceLoaderFactory, final String database) {
		super(resourceLoaderFactory, database);
	}

	@Override
	protected ImageFilter getImageFilter(Emotion emotion) {
		return new ImageFilterAdapter(emotion) {
			@Override
			public int filter() {
				return SECOND_HALF;
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
		return super.getDescription() + "second half of the last images";
	}
}
