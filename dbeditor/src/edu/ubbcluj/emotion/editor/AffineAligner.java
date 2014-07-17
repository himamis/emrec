package edu.ubbcluj.emotion.editor;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;

import edu.ubbcluj.emotion.model.Image;
import edu.ubbcluj.emotion.model.Landmarks;
import edu.ubbcluj.emotion.model.Sequence;
import edu.ubbcluj.emotion.util.OpenImajUtil;

public class AffineAligner implements SequenceEditor {
	
	private org.openimaj.image.processing.face.alignment.AffineAligner aligner;
	
	public AffineAligner(int width, int height, float patchBorderPercentage) {
		aligner = new org.openimaj.image.processing.face.alignment.AffineAligner(width, height, patchBorderPercentage);
	}

	@Override
	public void setUp(Sequence sequence) {
	}

	@Override
	public void doEdit(Image image, Landmarks landmarks) {
		KEDetectedFace face = OpenImajUtil.fromImageAndLandmarks(image, landmarks);
		FImage alignedImage = aligner.align(face);
		alignedImage.normalise();
		image.setBufferedImage(ImageUtilities.createBufferedImage(alignedImage));
	}

	@Override
	public boolean outputsLandmarks() {
		return false;
	}

	@Override
	public boolean inputsLandmarks() {
		return true;
	}


}
