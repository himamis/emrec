package edu.ubbcluj.emotion.editor;

import edu.ubbcluj.emotion.model.Image;
import edu.ubbcluj.emotion.model.Landmarks;
import edu.ubbcluj.emotion.model.Sequence;

public interface SequenceEditor {

	/**
	 * This method will be called before editing a sequence.
	 * 
	 * @param sequence
	 *            the sequence to edit
	 */
	public void setUp(Sequence sequence);

	/**
	 * Applies the transformation to the given image. Landmarks can be null, if
	 * {@link SequenceEditor#inputsLandmarks() inputsLandmarks()} returns false.
	 * 
	 * @param image
	 *            the image to edit
	 * @param landmarks
	 *            the landmarks associated with the image
	 */
	public void doEdit(Image image, Landmarks landmarks);

	/**
	 * Whether the class transforms the landmarks.
	 */
	public boolean outputsLandmarks();

	/**
	 * Whether the class needs landmarks to edit the image.
	 */
	public boolean inputsLandmarks();

}
