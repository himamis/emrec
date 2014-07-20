package edu.ubbcluj.emotion.algorithm.util;

import org.jblas.DoubleMatrix;
import org.jblas.Eigen;

public class EigenValueDecompositionSymmetric {

	private double[]	eigenValues;
	private double[][]	eigenVectors;

	public EigenValueDecompositionSymmetric(double[][] data) {
		this(new DoubleMatrix(data));

	}

	public EigenValueDecompositionSymmetric(DoubleMatrix matrix) {
		final DoubleMatrix[] a = Eigen.symmetricEigenvectors(matrix);
		eigenValues = new double[a[1].columns];

		for (int i = 0; i < a[1].columns; i++) {
			eigenValues[i] = a[1].get(i, i);
		}
		eigenVectors = a[0].toArray2();
	}

	public double[] getEigenValues() {
		return eigenValues;
	}

	public double[][] getEigenVectors() {
		return eigenVectors;
	}
}
