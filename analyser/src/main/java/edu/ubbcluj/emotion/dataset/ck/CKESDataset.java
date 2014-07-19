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
, description = "Cohn-Kanade database version 2, with cropped and aligned faces and resized to 50*60")
public class CKESDataset extends AbstractCKDataset<Emotion> {
	
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
		return "openimaj_small3";
	}

	@Override
	protected DatasetInformation<Emotion> constructDatasetInformationObject() {
		Set<Emotion> set = new HashSet<>();
		for (Emotion emotion : Emotion.values()) {
			set.add(emotion);
		}
		
		return new DatasetInformation<Emotion>(50, 60, false, false, true, set);
	}

	@Override
	protected Rectangle constructFacialFeatureLocationObject(FacialFeature f) {
		switch (f) {
		case EYES:
			return new Rectangle(0, 12, 50, 16);
		case FOREHEAD:
			return new Rectangle();
		case LEFT_EYE:
			return new Rectangle();
		case MOUTH:
			return new Rectangle(5, 40, 40, 20);
		case NOSE:
			return new Rectangle();
		case RIGHT_EYE:
			return new Rectangle();
		case FULL_FACE:
			return new Rectangle(0, 0, 50, 60);
		}
		return null;
	}

}
