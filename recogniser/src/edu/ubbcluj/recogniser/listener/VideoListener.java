package edu.ubbcluj.recogniser.listener;

import java.util.List;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.face.alignment.AffineAligner;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;

public class VideoListener implements VideoDisplayListener<MBFImage> {

	private FKEFaceDetector		faceDetector	= new FKEFaceDetector();
	private AffineAligner		aligner			= new AffineAligner(50, 60, 0.400f);

	@Override
	public void afterUpdate(VideoDisplay<MBFImage> arg0) {
	}

	@Override
	public void beforeUpdate(MBFImage frame) {
		List<KEDetectedFace> faces = faceDetector.detectFaces(frame.flatten());
		if (faces.size() > 0) {
			KEDetectedFace face = faces.get(0);
			FImage alignedImage = aligner.align(face);
			alignedImage.normalise();
			DisplayUtilities.createNamedWindow("aligned");
			DisplayUtilities.displayName(alignedImage, "aligned");
		}
	}

}
