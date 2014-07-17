package edu.ubbcluj.emotion.model;

import edu.ubbcluj.emotion.util.Constants;

public class TriangularFaceModel {

	private MyPoint2D	leftEye;
	private MyPoint2D	rightEye;
	private MyPoint2D	mouthCenter;

	public TriangularFaceModel(Landmarks landmarks) {
		float[] coordinates;
		coordinates = getMeanCoords(Constants.CEYELEFTCENTER, landmarks);
		leftEye = new MyPoint2D(coordinates[0], coordinates[1]);
		coordinates = getMeanCoords(Constants.CEYERIGHTCENTER, landmarks);
		rightEye = new MyPoint2D(coordinates[0], coordinates[1]);
		coordinates = getMeanCoords(Constants.MOUTHCENTER, landmarks);
		mouthCenter = new MyPoint2D(coordinates[0], coordinates[1]);
	}

	public TriangularFaceModel(MyPoint2D leftEye, MyPoint2D rightEye, MyPoint2D mouthCenter) {
		this.leftEye = leftEye;
		this.rightEye = rightEye;
		this.mouthCenter = mouthCenter;
	}

	public MyPoint2D getLeftEye() {
		return leftEye;
	}

	public void setLeftEye(MyPoint2D leftEye) {
		this.leftEye = leftEye;
	}

	public MyPoint2D getRightEye() {
		return rightEye;
	}

	public void setRightEye(MyPoint2D rightEye) {
		this.rightEye = rightEye;
	}

	public MyPoint2D getMouthCenter() {
		return mouthCenter;
	}

	public void setMouthCenter(MyPoint2D mouthCenter) {
		this.mouthCenter = mouthCenter;
	}

	private static float[] getMeanCoords(int[] indices, Landmarks landmarks) {
		float x = 0;
		float y = 0;
		for (int i = 0; i < indices.length; i++) {
			MyPoint2D point = landmarks.get(indices[i]);
			x += point.getX();
			y += point.getY();
		}
		x /= indices.length;
		y /= indices.length;
		return new float[] { x, y };
	}
}
