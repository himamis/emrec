package edu.ubbcluj.emotion.algorithm.fastica;

import org.fastica.math.Matrix;
import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.FeatureExtractor;
import org.openimaj.image.FImage;

import edu.ubbcluj.emotion.util.MatrixUtil;

public class FeatureExtractorICA implements FeatureExtractor<DoubleFV, FImage> {

	private double[][] separatingMatrix;
	
	public FeatureExtractorICA(double[][] separatingMatrix) {
		this.separatingMatrix = separatingMatrix;
	}

	@Override
	public DoubleFV extractFeature(FImage object) {
		double[] image = object.getDoublePixelVector();
		double[][] data = MatrixUtil.convertRowVectorToColumnVector(image);
		double[][] projection = Matrix.mult(separatingMatrix, data);
		double[] rowVector = MatrixUtil.convertColumnVectorToRowVector(projection);
		return new DoubleFV(rowVector);
	}

}
