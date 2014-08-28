package edu.ubbcluj.emotion.database.file.loader;

import edu.ubbcluj.emotion.model.ActionUnitSet;
import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.model.LandmarksSequence;

/**
 * Class used to filter out specific sequences.
 * All of the filter methods should return true,
 * the sequence should be inside the result set.
 * For this, use {@link ImageFilter#filter(Emotion, LandmarksSequence, ActionUnitSet, boolean, int) this}
 * filter method.
 * @author bencze
 * 
 */
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

	/**
	 * Should this method return true, the image sequnce
	 * should be inside the result set.
	 * @param emotion
	 * @param landmarks
	 * @param actionUnitList
	 * @param isLastImage
	 * @param index
	 * @return true if the sequence should be in the result set
	 */
	boolean filter(final Emotion emotion, final LandmarksSequence landmarks, final ActionUnitSet actionUnitList, final boolean isLastImage,
			final int index);

}
