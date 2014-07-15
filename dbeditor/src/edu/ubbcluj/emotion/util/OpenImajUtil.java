package edu.ubbcluj.emotion.util;

import java.awt.image.BufferedImage;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.processing.face.detection.keypoints.FacialKeypoint;
import org.openimaj.image.processing.face.detection.keypoints.FacialKeypoint.FacialKeypointType;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;
import org.openimaj.math.geometry.point.Point2dImpl;
import org.openimaj.math.geometry.shape.Rectangle;

import edu.ubbcluj.emotion.model.Image;
import edu.ubbcluj.emotion.model.ImageSequence;
import edu.ubbcluj.emotion.model.Landmarks;
import edu.ubbcluj.emotion.model.LandmarksSequence;
import edu.ubbcluj.emotion.model.Point2D;
import edu.ubbcluj.emotion.model.Sequence;

public class OpenImajUtil {

	public static KEDetectedFace fromSequenceLastImage(Sequence seq) {
		ImageSequence sequences = seq.getImageSequence();
		BufferedImage image = sequences.get(sequences.size() - 1).getBufferedImage();
		LandmarksSequence landmarks = seq.getLandmarksSequence();
		Landmarks landmarksList = landmarks.get(landmarks.size() - 1);
		FacialKeypoint[] keypoints = new FacialKeypoint[FacialKeypointType.values().length];
		int i = 0;
		for (FacialKeypointType FK : FacialKeypointType.values()) {
			int[] indices = getFacialKeypointTypeIndices(FK);
			float[] coords = getMeanCoords(indices, landmarksList);
			Point2dImpl point = new Point2dImpl(coords[0], coords[1]);
			FacialKeypoint kp = new FacialKeypoint(FK, point);
			keypoints[i] = kp;
			i++;
		}
		FImage fimage = ImageUtilities.createFImage(image);
		return new KEDetectedFace(getBounds(landmarksList), fimage, keypoints, (float) 10.0);
	}

	public static KEDetectedFace fromSequenceImage(Sequence seq, int index) {
		ImageSequence sequences = seq.getImageSequence();
		BufferedImage image = sequences.get(index).getBufferedImage();
		LandmarksSequence landmarksSequence = seq.getLandmarksSequence();
		Landmarks landmarksList = landmarksSequence.get(index);
		FacialKeypoint[] keypoints = new FacialKeypoint[FacialKeypointType.values().length];
		int i = 0;
		for (FacialKeypointType FK : FacialKeypointType.values()) {
			int[] indices = getFacialKeypointTypeIndicesReverse(FK);
			float[] coords = getMeanCoords(indices, landmarksList);
			Point2dImpl point = new Point2dImpl(coords[0], coords[1]);
			FacialKeypoint kp = new FacialKeypoint(FK, point);
			keypoints[i] = kp;
			i++;
		}
		FImage fimage = ImageUtilities.createFImage(image);
		return new KEDetectedFace(getBounds(landmarksList), fimage, keypoints, (float) 10.0);
	}
	
	public static KEDetectedFace fromImageAndLandmarks(Image image, Landmarks landmarks) {
		FacialKeypoint[] keypoints = getFactialKeypoints(landmarks);
		FImage fimage = image.getFImage();
		return new KEDetectedFace(getBounds(landmarks), fimage, keypoints, (float) 10.0);
	}
	
	public static KEDetectedFace fromImageAndLandmarks(Image image, Landmarks landmarks, Rectangle bounds) {
		FacialKeypoint[] keypoints = getFactialKeypoints(landmarks);
		FImage fimage = image.getFImage();
		return new KEDetectedFace(bounds, fimage, keypoints, (float) 10.0);
	}
	
	private static FacialKeypoint[] getFactialKeypoints(Landmarks landmarks) {
		FacialKeypoint[] keypoints = new FacialKeypoint[FacialKeypointType.values().length];
		int i = 0;
		for (FacialKeypointType FK : FacialKeypointType.values()) {
			int[] indices = getFacialKeypointTypeIndicesReverse(FK);
			float[] coords = getMeanCoords(indices, landmarks);
			Point2dImpl point = new Point2dImpl(coords[0], coords[1]);
			FacialKeypoint kp = new FacialKeypoint(FK, point);
			keypoints[i] = kp;
			i++;
		}
		return keypoints;
	}

	private static int[] getFacialKeypointTypeIndices(FacialKeypointType fkt) {
		switch (fkt) {
		case EYE_LEFT_CENTER:
			return Constants.CEYELEFTCENTER;
		case EYE_LEFT_LEFT:
			return Constants.CEYELEFTLEFT;
		case EYE_LEFT_RIGHT:
			return Constants.CEYELEFTRIGHT;
		case EYE_RIGHT_CENTER:
			return Constants.CEYERIGHTCENTER;
		case EYE_RIGHT_LEFT:
			return Constants.CEYERIGHTLEFT;
		case EYE_RIGHT_RIGHT:
			return Constants.CEYERIGHTRIGHT;
		case MOUTH_CENTER:
			return Constants.MOUTHCENTER;
		case MOUTH_LEFT:
			return Constants.MOUTHLEFT;
		case MOUTH_RIGHT:
			return Constants.MOUTHRIGHT;
		case NOSE_BRIDGE:
			return Constants.CNOSEBRIDGE;
		case NOSE_LEFT:
			return Constants.CNOSELEFT;
		case NOSE_MIDDLE:
			return Constants.CNOSEMIDDLE;
		case NOSE_RIGHT:
			return Constants.CNOSERIGHT;
		}
		return null;
	}
	
	private static int[] getFacialKeypointTypeIndicesReverse(FacialKeypointType fkt) {
		switch (fkt) {
		case EYE_LEFT_CENTER:
			return Constants.CEYERIGHTCENTER;
		case EYE_LEFT_LEFT:
			return Constants.CEYERIGHTRIGHT;
		case EYE_LEFT_RIGHT:
			return Constants.CEYERIGHTLEFT;
		case EYE_RIGHT_CENTER:
			return Constants.CEYELEFTCENTER;
		case EYE_RIGHT_LEFT:
			return Constants.CEYELEFTRIGHT;
		case EYE_RIGHT_RIGHT:
			return Constants.CEYELEFTLEFT;
		case MOUTH_CENTER:
			return Constants.MOUTHCENTER;
		case MOUTH_LEFT:
			return Constants.MOUTHRIGHT;
		case MOUTH_RIGHT:
			return Constants.MOUTHLEFT;
		case NOSE_BRIDGE:
			return Constants.CNOSEBRIDGE;
		case NOSE_LEFT:
			return Constants.CNOSERIGHT;
		case NOSE_MIDDLE:
			return Constants.CNOSEMIDDLE;
		case NOSE_RIGHT:
			return Constants.CNOSELEFT;
		}
		return null;
	}

	private static float[] getMeanCoords(int[] indices, Landmarks landmarks) {
		float x = 0;
		float y = 0;
		for (int i = 0; i < indices.length; i++) {
			x += landmarks.get(indices[i]).getX();
			y += landmarks.get(indices[i]).getY();
		}
		x /= indices.length;
		y /= indices.length;
		return new float[] { x, y };
	}

	private static Rectangle getBounds(Landmarks landmarks) {
		float maxx = 0, maxy = 0;
		float minx = 500, miny = 500;

		for (Point2D fl : landmarks) {
			float x = fl.getX();
			float y = fl.getY();
			if (x > maxx) {
				maxx = x;
			}
			if (x < minx) {
				minx = x;
			}
			if (y > maxy) {
				maxy = y;
			}
			if (y < miny) {
				miny = y;
			}
		}

		miny = miny - 10;
		maxy = maxy + 10;

		minx = minx - 10;
		maxx = maxx + 10;

		return new Rectangle((int) minx, (int) miny, (int) (maxx - minx), (int) (maxy - miny));
	}
}
