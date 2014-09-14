package edu.ubbcluj.recogniser.listener;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.face.alignment.AffineAligner;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;

public class VideoListener implements VideoDisplayListener<MBFImage> {
	
	int i = 0;
	int j = 0;
		
	public VideoListener() {
	}

	private FKEFaceDetector		faceDetector	= new FKEFaceDetector();
	private AffineAligner		aligner			= new AffineAligner(80, 110, 0.225f);

	@Override
	public void afterUpdate(VideoDisplay<MBFImage> arg0) {
	}

	@Override
	public void beforeUpdate(MBFImage frame) {
		if (i < 20) {
			i++;
			return;
		}
		i = 0;
		List<KEDetectedFace> faces = faceDetector.detectFaces(frame.flatten());
		if (faces.size() > 0) {
			KEDetectedFace face = faces.get(0);
			FImage alignedImage = aligner.align(face);
			alignedImage.normalise();
			try {
				ImageUtilities.write(alignedImage, new File("c:\\Users\\himamis\\git\\emrec" + j + ".png"));
				DisplayUtilities.createNamedWindow("aligned");
				DisplayUtilities.displayName(alignedImage, "aligned");
				j++;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DisplayUtilities.createNamedWindow("aligned");
			DisplayUtilities.displayName(alignedImage, "aligned");
		}
	}

}
