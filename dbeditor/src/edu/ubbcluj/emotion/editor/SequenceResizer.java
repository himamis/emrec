package edu.ubbcluj.emotion.editor;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;

import edu.ubbcluj.emotion.model.Image;
import edu.ubbcluj.emotion.model.Landmarks;
import edu.ubbcluj.emotion.model.Point2D;
import edu.ubbcluj.emotion.model.Sequence;

public class SequenceResizer implements SequenceEditor {

	private int	width;
	private int	height;

	public SequenceResizer(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void setUp(Sequence sequence) {
	}

	@Override
	public void doEdit(Image image, Landmarks landmarks) {
		double originalWidth = (double) image.getWidth();
		double originalHeight = (double) image.getHeight();
		AffineTransform atx = new AffineTransform();
		double x = (double) width / originalWidth;
		double y = (double) height / originalHeight;
		atx.scale(x, y);
		AffineTransformOp op = new AffineTransformOp(atx, AffineTransformOp.TYPE_BICUBIC);
		BufferedImage bufferedImage = image.getBufferedImage();
		BufferedImage resizedImage = op.filter(bufferedImage, null);
		image.setBufferedImage(resizedImage);
		if (landmarks != null) {
			for (Point2D point : landmarks) {
				java.awt.geom.Point2D.Float dest = new java.awt.geom.Point2D.Float();
				op.getPoint2D(point.toPoint2D(), dest);
				point.fromPoint2D(dest);
			}
		}
	}

	@Override
	public boolean outputsLandmarks() {
		return true;
	}

	@Override
	public boolean inputsLandmarks() {
		return false;
	}
}
