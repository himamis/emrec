package ck.database.editor;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;

public class Resizer {
	
	private AffineTransformOp op;

	public Resizer(int origwidth, int origheight, int width, int height) {
		AffineTransform atx = new AffineTransform();
		double x = (double) width / (double) origwidth;
		double y = (double) height / (double) origheight;
		atx.scale(x, y);
		op = new AffineTransformOp(atx, AffineTransformOp.TYPE_BICUBIC);
	}
	
	public void resizeImages(List<BufferedImage> images, List<List<Point2D.Float>> landmarks) {
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
