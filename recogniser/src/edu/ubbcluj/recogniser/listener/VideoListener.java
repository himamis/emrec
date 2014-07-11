package edu.ubbcluj.recogniser.listener;

import java.util.List;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.algorithm.EqualisationProcessor;
import org.openimaj.image.processing.face.alignment.AffineAligner;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;

public class VideoListener implements VideoDisplayListener<MBFImage> {

	private static final String		folder			= "openimajdiff1";
	private static final int		K				= 20;

	/*
	 * private final LoaderUtil util = new LoaderUtil(folder); private final
	 * TrainingDataProvider tdp = new
	 * TrainingDataProvider.LastImagesEmotionNotNullTDP(util); private final
	 * EmotionRecogniser recogniser = new EmotionRecogniser(util, tdp, K);
	 */

	private static final int		frames			= 6;

	private FImage					last			= null;
	private int						counter			= 0;

	private FKEFaceDetector			faceDetector	= new FKEFaceDetector();
	private AffineAligner			aligner			= new AffineAligner(80, 110, 0.225f);

	private EqualisationProcessor	histeq			= new EqualisationProcessor();

	@Override
	public void afterUpdate(VideoDisplay<MBFImage> arg0) {
	}

	@Override
	public void beforeUpdate(MBFImage frame) {
		if (counter >= frames) {
			counter = 0;
			List<KEDetectedFace> faces = faceDetector.detectFaces(frame.flatten());
			if (faces.size() > 0) {
				KEDetectedFace face = faces.get(0);
				FImage alignedImage = aligner.align(face);
				if (last == null) {
					last = alignedImage;
				} else {
					//FImage diff = alignedImage.subtract(last).abs();// .abs();//.process(histeq)
					// .normalise();
					last = alignedImage.clone();
					DisplayUtilities.displayName(last, "Difference");
					/*
					 * Emotion em = recogniser.recogniseEmotion(ImageUtilities.
					 * createBufferedImage(diff)); if (em == Emotion.HAPPY) {
					 * System.out.println("I am very happy"); }
					 */
				}
			}
		} else {
			counter++;
		}

	}

}
