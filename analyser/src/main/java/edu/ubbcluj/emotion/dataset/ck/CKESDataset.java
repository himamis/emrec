package edu.ubbcluj.emotion.dataset.ck;

import java.util.HashSet;
import java.util.Set;

import org.openimaj.math.geometry.shape.Rectangle;

import edu.ubbcluj.emotion.database.file.loader.ImageFilter;
import edu.ubbcluj.emotion.database.file.loader.ImageFilterAdapter;
import edu.ubbcluj.emotion.dataset.DatasetInformation;
import edu.ubbcluj.emotion.dataset.FacialFeature;
import edu.ubbcluj.emotion.model.Emotion;

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
			return new Rectangle();
		case FOREHEAD:
			return new Rectangle();
		case LEFT_EYE:
			return new Rectangle();
		case MOUTH:
			return new Rectangle();
		case NOSE:
			return new Rectangle();
		case RIGHT_EYE:
			return new Rectangle();
		}
		return null;
	}

}
