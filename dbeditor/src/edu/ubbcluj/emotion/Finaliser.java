package edu.ubbcluj.emotion;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import org.fastica.math.Matrix;
import org.fastica.math.Vector;

import edu.ubbcluj.emotion.util.ImageUtil;
import static edu.ubbcluj.emotion.util.Math.*;

public class Finaliser {

	public void finaliseImages(List<BufferedImage> images) {
		double[][] imagesDouble = ImageUtil.convertImageListToDoubleMatrix(images);
		double[] firstImage = Vector.clone(imagesDouble[0]);
		for (int i = 0; i < imagesDouble.length; i++) {
			for (int j = 0; j < imagesDouble[i].length; j++) {
				imagesDouble[i][j] = Math.abs(imagesDouble[i][j] - firstImage[j]);
			}
		}

		for (int i = 0; i < imagesDouble.length; i++) {
			double min = min(imagesDouble[i]);
			for (int j = 0; j < imagesDouble[i].length; j++) {
				imagesDouble[i][j] += min;
			}
			double max = max(imagesDouble[i]);
			double scale = 255.0 / max;
			for (int j = 0; j < imagesDouble[i].length; j++) {
				imagesDouble[i][j] *= scale;
			}
		}

		BufferedImage img = images.get(0);
		int h = img.getHeight();
		int w = img.getWidth();
		List<BufferedImage> ret = ImageUtil.convertDoubleMatrixToImageList(imagesDouble, w, h);
		images.clear();
		images.addAll(ret);
	}

	public static BufferedImage convertToGrayscale(BufferedImage image) {
		BufferedImage ret = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = ret.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return ret;
	}

	private static double[] calcMeanValues(double[][] inVectors) {
		int m = Matrix.getNumOfRows(inVectors);
		int n = Matrix.getNumOfColumns(inVectors);
		double[] meanValues = Vector.newVector(n);
		for (int i = 0; i < n; ++i) {
			meanValues[i] = 0.0;
			for (int j = 0; j < m; ++j)
				meanValues[i] += inVectors[j][i];
			meanValues[i] /= m;
		}
		return (meanValues);
	}
}
