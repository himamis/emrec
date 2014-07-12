package edu.ubbcluj.emotion;

import static edu.ubbcluj.emotion.util.Math.subVectorP;

import org.fastica.math.Matrix;
import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.FeatureExtractor;
import org.openimaj.image.FImage;

import edu.ubbcluj.emotion.util.MatrixUtil;

public class FeatureExtractorPCA implements FeatureExtractor<DoubleFV, FImage> {

	private double[]	meanValues;
	private double[][]	eigenVectors;

	public FeatureExtractorPCA(double[] meanValues, double[][] eigenVectors) {
		this.meanValues = meanValues;
		this.eigenVectors = eigenVectors;
	}

	@Override
	public DoubleFV extractFeature(FImage object) {
		double[] image = object.getDoublePixelVector();
		double[][] data = MatrixUtil.convertRowVectorToColumnVector(image);
		subVectorP(data, meanValues);
		double[][] projection = Matrix.mult(eigenVectors, data);
		double[] rowVector = MatrixUtil.convertColumnVectorToRowVector(projection);
		return new DoubleFV(rowVector);
	}

}
