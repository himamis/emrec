package edu.ubbcluj.emotion.editor;

import org.openimaj.image.FImage;

import edu.ubbcluj.emotion.model.Image;
import edu.ubbcluj.emotion.model.Landmarks;
import edu.ubbcluj.emotion.model.LandmarksSequence;
import edu.ubbcluj.emotion.model.MyPoint2D;
import edu.ubbcluj.emotion.model.Sequence;

public class ImageCropper implements SequenceEditor {

	float	minx, miny, maxx, maxy;

	public ImageCropper() {
	}

	@Override
	public void setUp(Sequence sequence) {
		LandmarksSequence landmarksSequence = sequence.getLandmarksSequence();
		maxx = 0;
		maxy = 0;
		minx = 500;
		miny = 500;

		for (Landmarks ls : landmarksSequence) {
			for (MyPoint2D fl : ls) {
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
		}
		miny = miny - 60;
		maxy = maxy + 20;

		minx = minx - 30;
		maxx = maxx + 30;
	}

	@Override
	public void doEdit(Image image, Landmarks landmarks) {
		float minx = this.minx < 0 ? 0 : this.minx;
		float miny = this.miny < 0 ? 0 : this.miny;
		float maxx = this.maxx > image.getWidth() ? image.getWidth() : this.maxx;
		float maxy = this.maxy > image.getHeight() ? image.getHeight() : this.maxy;
		FImage croppedImage = image.getImage().extractROI((int) minx, (int) miny, (int) (maxx - minx), (int) (maxy - miny));
		image.setImage(croppedImage);
		for (MyPoint2D point : landmarks) {
			point.setX(point.getX() - minx);
			point.setY(point.getY() - miny);
		}

	}

	@Override
	public boolean outputsLandmarks() {
		return true;
	}

	@Override
	public boolean inputsLandmarks() {
		return true;
	}
}
