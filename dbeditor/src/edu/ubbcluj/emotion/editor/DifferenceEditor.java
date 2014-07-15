package edu.ubbcluj.emotion.editor;

import org.openimaj.image.FImage;

import edu.ubbcluj.emotion.model.Image;
import edu.ubbcluj.emotion.model.Landmarks;
import edu.ubbcluj.emotion.model.Sequence;

public class DifferenceEditor implements SequenceEditor {

	private FImage	firstImage;

	@Override
	public void setUp(Sequence sequence) {
		Image image = sequence.getImageSequence().get(0);
		firstImage = image.getFImage();
	}

	@Override
	public void doEdit(Image image, Landmarks landmarks) {
		FImage fimage = image.getFImage();
		fimage.subtractInplace(firstImage);
		image.setFImage(fimage);
	}

	@Override
	public boolean inputsLandmarks() {
		return false;
	}
	
	@Override
	public boolean outputsLandmarks() {
		return true;
	}
	

}
