package edu.ubbcluj.emotion.editor;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import edu.ubbcluj.emotion.model.Image;
import edu.ubbcluj.emotion.model.Landmarks;
import edu.ubbcluj.emotion.model.MyPoint2D;
import edu.ubbcluj.emotion.model.Sequence;
import edu.ubbcluj.emotion.model.TriangularFaceModel;

public class TriangleAligner implements SequenceEditor {

	private double	y11;
	private double	y12;
	private double	y21;
	private double	y22;
	private double	y31;
	private double	y32;

	public TriangleAligner(TriangularFaceModel faceModel) {
		Point2D.Float leftEye = faceModel.getLeftEye().toPoint2D();
		Point2D.Float rightEye = faceModel.getRightEye().toPoint2D();
		Point2D.Float mouthCenter = faceModel.getMouthCenter().toPoint2D();

		y11 = leftEye.getX();
		y12 = leftEye.getY();
		y21 = rightEye.getX();
		y22 = rightEye.getY();
		y31 = mouthCenter.getX();
		y32 = mouthCenter.getY();
	}

	@Override
	public void setUp(Sequence sequence) {
	}

	@Override
	public void doEdit(Image image, Landmarks landmarks) {
		TriangularFaceModel faceModel = new TriangularFaceModel(landmarks);
		Point2D.Float leftEye = faceModel.getLeftEye().toPoint2D();
		Point2D.Float rightEye = faceModel.getRightEye().toPoint2D();
		Point2D.Float mouthCenter = faceModel.getMouthCenter().toPoint2D();

		double x11 = leftEye.getX();
		double x12 = leftEye.getY();
		double x21 = rightEye.getX();
		double x22 = rightEye.getY();
		double x31 = mouthCenter.getX();
		double x32 = mouthCenter.getY();

		double a1 = ((y11 - y21) * (x12 - x32) - (y11 - y31) * (x12 - x22)) / ((x11 - x21) * (x12 - x32) - (x11 - x31) * (x12 - x22));
		double a2 = ((y11 - y21) * (x11 - x31) - (y11 - y31) * (x11 - x21)) / ((x12 - x22) * (x11 - x31) - (x12 - x32) * (x11 - x21));
		double a3 = y11 - a1 * x11 - a2 * x12;
		double a4 = ((y12 - y22) * (x12 - x32) - (y12 - y32) * (x12 - x22)) / ((x11 - x21) * (x12 - x32) - (x11 - x31) * (x12 - x22));
		double a5 = ((y12 - y22) * (x11 - x31) - (y12 - y32) * (x11 - x21)) / ((x12 - x22) * (x11 - x31) - (x12 - x32) * (x11 - x21));
		double a6 = y12 - a4 * x11 - a5 * x12;
		AffineTransformOp op = new AffineTransformOp(new AffineTransform(a1, a4, a2, a5, a3, a6), AffineTransformOp.TYPE_BICUBIC);

		BufferedImage bufferedImage = image.getBufferedImage();
		BufferedImage alignedImage = op.filter(bufferedImage, null);
		image.setBufferedImage(alignedImage);
		
		for (MyPoint2D point : landmarks) {
			java.awt.geom.Point2D.Float dest = new java.awt.geom.Point2D.Float();
			op.getPoint2D(point.toPoint2D(), dest);
			point.fromPoint2D(dest);
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
