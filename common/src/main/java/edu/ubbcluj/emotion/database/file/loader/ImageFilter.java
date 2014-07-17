package edu.ubbcluj.emotion.database.file.loader;

import edu.ubbcluj.emotion.model.ActionUnitSet;
import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.model.LandmarksSequence;

public interface ImageFilter {
	public static final int	FIRST_HALF	= 1;
	public static final int	SECOND_HALF	= 2;
	public static final int	ALL			= 3;

	boolean filter(final Emotion emotion);

	boolean filter(final LandmarksSequence landmarks);

	boolean filter(final ActionUnitSet actionUnitList);

	boolean filter(final boolean isLastImage);
	
	boolean filter(final int index); 

	int filter();

	boolean filter(final Emotion emotion, final LandmarksSequence landmarks, final ActionUnitSet actionUnitList, final boolean isLastImage, final int index);

}
