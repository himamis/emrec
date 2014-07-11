package ck.database.editor;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class Cropper {
	
	public enum CroppingMode {
		LANDMARKS
	}
		
	private CroppingMode mode;
	
	private List<BufferedImage> images;
	private List<List<Point2D.Float>> landmarks;
	
	public Cropper(CroppingMode mode) {
		this.mode = mode;
	}
	
	public void cropImages(List<BufferedImage> images, List<List<Point2D.Float>> landmarks) {
		this.images = images;
		this.landmarks = landmarks;
		
		switch (mode) {
		case LANDMARKS:
			cropUsingLandmarks();
		}
	}
	
	

	private void cropUsingLandmarks() {
		float maxx = 0, maxy = 0;
		float minx = 500, miny = 500;
		
		for (List<Point2D.Float> ls : landmarks) {
			for (Point2D.Float fl : ls) {
				float x = fl.x;
				float y = fl.y;
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
		
		for (int i = 0; i < images.size(); i++) {
			BufferedImage image = images.get(i);
			
			minx = minx < 0 ? 0 : minx;
			miny = miny < 0 ? 0 : miny;
			maxx = maxx > image.getWidth() ? image.getWidth() : maxx;
			maxy = maxy > image.getHeight() ? image.getHeight() : maxy;
			
			BufferedImage croppedImage = image.getSubimage((int)minx, (int)miny, (int)(maxx-minx), (int)(maxy-miny));
			images.set(i, croppedImage);
			List<Point2D.Float> landmarksForFrameI = landmarks.get(i);
			for (int j = 0; j < landmarksForFrameI.size(); j++) {
				Point2D.Float p = landmarksForFrameI.get(j);
				p.setLocation(p.x - minx, p.y - miny);
			}
		}
	}
}
