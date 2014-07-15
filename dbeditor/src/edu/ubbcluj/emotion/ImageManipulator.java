package edu.ubbcluj.emotion;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;

public class ImageManipulator {

	/*
	 * This class rotates, translates and then stretches the images
	 * based on two reference point
	 * Image registration
	 */
	
	private float[][] reference_points; // two 2D coordinates (reference points)
	private List<BufferedImage> images;	// the image sequence to be touched
	private List<List<Point2D.Float>> landmarks;
	
	private AffineTransform atx;
	
	private Point2D.Float p_left = new Point2D.Float();
	private Point2D.Float p_right = new Point2D.Float();
	
	private Point2D.Float o_left = new Point2D.Float();
	private Point2D.Float o_right = new Point2D.Float();
		
	private int[] left_indices;
	private int[] right_indices;
	
	public ImageManipulator(int[] left_indices, int[] right_indices) {
		this.left_indices = left_indices;
		this.right_indices = right_indices;
	}
	
	public void registerImages(float[][] ref, List<BufferedImage> images, List<List<Point2D.Float>> landmarks) {
		this.images = images;
		this.landmarks = landmarks;
		this.reference_points = ref;
		
		prepare();
		
		rotate();
		translate();
		stretch();

		transform();
	}
	
	private void prepare() {
		atx = new AffineTransform();
		calcLeftRightPoints(left_indices, right_indices);
	}
	
	private void calcLeftRightPoints(int[] left_indices, int[] right_indices) {
		List<Point2D.Float> list = landmarks.get(0);

		float left_x = 0;
		float left_y = 0;
		
		for (int i = 0; i < left_indices.length; i++) {
			left_x += list.get(left_indices[i]).getX();
			left_y += list.get(left_indices[i]).getY();
		}
		
		left_x /= left_indices.length;
		left_y /= left_indices.length;
		
		float right_x = 0;
		float right_y = 0;
		
		for (int i = 0; i < right_indices.length; i++) {
			right_x += list.get(right_indices[i]).getX();
			right_y += list.get(right_indices[i]).getY();
		}
		
		right_x /= right_indices.length;
		right_y /= right_indices.length;
		
		p_left.setLocation(left_x, left_y);
		p_right.setLocation(right_x, right_y);
		
		o_left.setLocation(left_x, left_y);
		o_right.setLocation(right_x, right_y);
		
		if (StartApp.DEBUG) {
			
			System.out.println("Left: " + p_left);
			System.out.println("Right: " + p_right);

			System.out.println("RefLeft: " + reference_points[0][0] + " "
					+ reference_points[0][1]);
			System.out.println("RefRight: " + reference_points[1][0] + " "
					+ reference_points[1][1]);
		}
	}
	
	private void rotate() {
		// translate vectors to base (0, 0)
		float x = p_right.x - p_left.x;
		float y = p_right.y - p_left.y;
		
		float refx = reference_points[1][0] - reference_points[0][0];
		float refy = reference_points[1][1] - reference_points[0][1];
				
		float reft = (float) Math.atan(refy/refx);
		float t = (float) Math.atan(y/x);
		
		float theta = reft - t;
		
		atx.rotate(theta, o_left.getX(), o_left.getY());
		atx.transform(o_left, p_left);
		atx.transform(o_right, p_right);
		
		if (StartApp.DEBUG) {
			System.out.println("Rotate (RAD): " + theta);
		}
	}
	
	private void translate() {
		float cent_x = (p_left.x + p_right.x) / (float)2.0;
		float cent_y = (p_left.y + p_right.y) / (float)2.0;
		
		float refcent_x = (reference_points[0][0] + reference_points[1][0]) / (float) 2.0;
		float refcent_y = (reference_points[0][1] + reference_points[1][1]) / (float) 2.0;
		
		float tx = refcent_x - cent_x;
		float ty = refcent_y - cent_y;
		
		atx.translate(tx, ty);
		atx.transform(o_left, p_left);
		atx.transform(o_right, p_right);
		
		if (StartApp.DEBUG) {
			System.out.println("Translate: (" +tx+","+ty+")");
		}
	}
	
	private void stretch() {
		float refcent_x = (reference_points[0][0] + reference_points[1][0]) / (float) 2.0;
		
		float cent_x = (p_left.x + p_right.x) / (float) 2.0;
		
		float v = refcent_x / cent_x;
		
		atx.scale(v, 1);
		atx.transform(o_left, p_left);
		atx.transform(o_right, p_right);
		
		if (StartApp.DEBUG) {
			System.out.println("Stretch: " + v);
		}
	}
	
	private void transform() {
		AffineTransformOp op = new AffineTransformOp(atx, AffineTransformOp.TYPE_BICUBIC);
		for (int i = 0; i < images.size(); i++) {
			BufferedImage bf = op.filter(images.get(i), null);
			images.set(i, bf);
			List<Point2D.Float> points = landmarks.get(i);
			for (int j = 0; j < points.size(); j++) {
				Point2D p = points.get(j);
				op.getPoint2D(p, p);
			}
		}
	}

}
