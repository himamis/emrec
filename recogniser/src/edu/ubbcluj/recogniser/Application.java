package edu.ubbcluj.recogniser;

import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.face.alignment.AffineAligner;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.capture.VideoCaptureException;

import edu.ubbcluj.recogniser.listener.VideoListener;

public class Application {

	private static final int				K		= 30;
	private static final String				folder	= "openimaj_folder";
	private static final FKEFaceDetector	fd		= new FKEFaceDetector();
	private static final AffineAligner		ali		= new AffineAligner(80, 110, 0.225f);

	/*
	 * private static final LoaderUtil util = new LoaderUtil(folder); private
	 * static final TrainingDataProvider tdp = new
	 * TrainingDataProvider.LastImagesEmotionNotNullTDP(util); private static
	 * final EmotionRecogniser recogniser = new EmotionRecogniser(util, tdp, K);
	 */

	private static int						x		= 0;

	public static void main(String[] args) throws VideoCaptureException {
		VideoCapture video = new VideoCapture(640, 480);

		VideoDisplay<MBFImage> vd = VideoDisplay.createVideoDisplay(video);
		video.setFPS(20);
		vd.addVideoListener(new VideoListener());

	}

}
