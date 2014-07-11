package edu.ubbcluj.emotion.util;

import static edu.ubbcluj.emotion.util.Math.max;
import static edu.ubbcluj.emotion.util.Math.min;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;

public class ImageUtil {

	private static double[] convertByteToDoulbe(byte[] array) {
		double[] doubleArray = new double[array.length];
		for (int i = 0; i < array.length; i++) {
			doubleArray[i] = array[i];
		}
		return doubleArray;
	}

	private static float[] convertDoubleVectorToFloatVector(double[] array) {
		float[] floatArray = new float[array.length];
		for (int i = 0; i < array.length; i++) {
			floatArray[i] = (float) array[i];
		}
		return floatArray;
	}

	public static double[] convertImageToRowVector(BufferedImage image) {
		BufferedImage gray = convertImageToGrayscale(image);
		FImage img = ImageUtilities.createFImage(gray);
		byte[] byteArray = img.toByteImage();
		double[] doubleArray = convertByteToDoulbe(byteArray);
		return doubleArray;
	}

	public static double[][] convertImageToColumnVector(BufferedImage image) {
		BufferedImage gray = convertImageToGrayscale(image);
		FImage img = ImageUtilities.createFImage(gray);
		byte[] byteArray = img.toByteImage();

		double[][] matrix = new double[byteArray.length][1];
		for (int i = 0; i < byteArray.length; i++) {
			matrix[i][0] = byteArray[i];
		}
		return matrix;
	}

	public static double[][] convertImageListToDoubleMatrix(List<BufferedImage> images) {
		double[][] matrix = new double[images.size()][];
		for (int i = 0; i < images.size(); i++) {
			BufferedImage image = images.get(i);
			double[] ret = convertImageToRowVector(image);
			matrix[i] = ret;
		}
		return matrix;
	}

	public static BufferedImage convertRowVectorToImage(double[] array, int w, int h) {
		float[] floatArray = convertDoubleVectorToFloatVector(array);
		FImage image = new FImage(floatArray, w, h);
		return ImageUtilities.createBufferedImage(image);
	}

	public static BufferedImage convertImageToGrayscale(BufferedImage image) {
		BufferedImage ret = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = ret.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return ret;
	}

	public static List<BufferedImage> convertDoubleMatrixToImageList(double[][] images, int w, int h) {
		List<BufferedImage> ret = new LinkedList<>();
		for (int i = 0; i < images.length; i++) {
			double[] image = images[i];
			BufferedImage bimage = convertRowVectorToImage(image, w, h);
			ret.add(bimage);
		}
		return ret;
	}

	public static void showImage(double[] v, int w) {
		double[] vector = v.clone();
		int h = vector.length / w;
		double min = min(vector);
		for (int i = 0; i < vector.length; i++) {
			vector[i] -= min;
		}
		double max = max(vector);
		double scale = 256.0 / max;
		for (int i = 0; i < vector.length; i++) {
			vector[i] *= scale;
		}
		BufferedImage image = convertRowVectorToImage(vector, w, h);
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(image)));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void showImage(BufferedImage image) {
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(image)));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
