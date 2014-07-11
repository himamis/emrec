package edu.ubbcluj.emotion.database.file.loader;

import edu.ubbcluj.emotion.model.ActionUnitList;
import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.model.LandmarksSequence;

public class ImageFilterAdapter implements ImageFilter {
	
	private final Emotion filterEmotion;
	
	public ImageFilterAdapter() {
		this.filterEmotion = null;
	}
	
	public ImageFilterAdapter(final Emotion filterEmotion) {
		this.filterEmotion = filterEmotion;
	}

	@Override
	public boolean filter(final Emotion emotion) {
		if (filterEmotion != null) {
			return emotion == filterEmotion;
		}
		return true;
	}

	@Override
	public boolean filter(final LandmarksSequence landmarks) {
		return true;
	}

	@Override
	public boolean filter(final ActionUnitList actionUnitList) {
		return true;
	}

	@Override
	public boolean filter(final boolean isLastImage) {
		return true;
	}
	
	@Override
	public boolean filter(int index) {
		return true;
	}
	
	@Override
	public int filter() {
		return ALL;
	}

	@Override
	public boolean filter(final Emotion emotion, final LandmarksSequence landmarks, final ActionUnitList actionUnitList, final boolean isLastImage, final int index) {
		return filter(emotion) && filter(landmarks) && filter(actionUnitList) && filter(isLastImage) && filter(index);
	}

}
