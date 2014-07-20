package edu.ubbcluj.recogniser;

import org.openimaj.image.MBFImage;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.capture.VideoCaptureException;

import edu.ubbcluj.recogniser.listener.VideoListener;

public class Application {

	public static void main(String[] args) throws VideoCaptureException {
		VideoCapture video = new VideoCapture(640, 480);

		VideoDisplay<MBFImage> vd = VideoDisplay.createVideoDisplay(video);
		vd.displayMode(false);
		vd.addVideoListener(new VideoListener());

	}

}
