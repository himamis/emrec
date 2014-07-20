package edu.ubbcluj.emotion.dataset.ck;

import java.util.HashSet;
import java.util.Set;

import org.openimaj.experiment.annotations.DatasetDescription;
import org.openimaj.math.geometry.shape.Rectangle;

import edu.ubbcluj.emotion.database.file.loader.ImageFilter;
import edu.ubbcluj.emotion.database.file.loader.ImageFilterAdapter;
import edu.ubbcluj.emotion.dataset.DatasetInformation;
import edu.ubbcluj.emotion.dataset.FacialFeature;
import edu.ubbcluj.emotion.model.Emotion;

@DatasetDescription(name = "CK+", creator = "Lucey, P., Cohn, J. F., Kanade, T., Saragih, J., Ambadar, Z., & Matthews, I.", url = "http://www.pitt.edu/~emotion/ck-spread.htm"
, description = "Cohn-Kanade database version 2, with cropped and aligned faces, resized to 80*110 and with the first images subtracted from each image in a sequence.")
public class CKEDDataset extends AbstractCKDataset<Emotion> {
	
	@Override
	protected ImageFilter getImageFilter(final Emotion key) {
		return new ImageFilterAdapter() {
			@Override
			public boolean filter(Emotion emotion) {
				return key == emotion;
			}

			@Override
			public boolean filter(boolean isLastImage) {
				return isLastImage;
			}
		};
	}

	@Override
	protected String getFolderName() {
		return "openimaj_diff";
	}

	@Override
	protected DatasetInformation<Emotion> constructDatasetInformationObject() {
		Set<Emotion> set = new HashSet<>();
		for (Emotion emotion : Emotion.values()) {
			set.add(emotion);
		}
		
		return new DatasetInformation<Emotion>(80, 110, true, false, true, set);
	}

	@Override
	protected Rectangle constructFacialFeatureLocationObject(FacialFeature f) {
		switch (f) {
		case EYES:
			throw new UnsupportedOperationException();
		case FOREHEAD:
			throw new UnsupportedOperationException();
		case LEFT_EYE:
			throw new UnsupportedOperationException();
		case MOUTH:
			throw new UnsupportedOperationException();
		case NOSE:
			throw new UnsupportedOperationException();
		case RIGHT_EYE:
			throw new UnsupportedOperationException();
		case FULL_FACE:
			return new Rectangle(0, 0, 80, 110);
		}
		return null;
	}

	@Override
	public String getName() {
		return "D_CKED";
	}

}