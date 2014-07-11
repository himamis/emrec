package edu.ubbcluj.emotion.pca;

import static edu.ubbcluj.emotion.util.Math.calculateRowMeanValues;
import static edu.ubbcluj.emotion.util.Math.normalizeVector;
import static edu.ubbcluj.emotion.util.Math.subFromRowsP;

import org.fastica.math.Matrix;
import org.jblas.DoubleMatrix;
import org.jblas.Eigen;

import edu.ubbcluj.emotion.pca.util.QuickSortDualPivot;
import edu.ubbcluj.emotion.util.HasDescription;

public class PCA implements HasDescription {

	private double[]	meanValues;
	private double[]	eigenValues;
	private double[][]	eigenVectors;

	private double[][]	vectorsZeroMean;

	/**
	 * Calculates, sorts and normalizes eigenvectors calculated from the input
	 * vectors
	 * 
	 * @param inVectors
	 *            contains the data in rows
	 */
	public PCA(double[][] inVectors) {
		double[][] copyOfInVectors = Matrix.clone(inVectors);
		meanValues = calculateRowMeanValues(copyOfInVectors);
		subFromRowsP(copyOfInVectors, meanValues);
		vectorsZeroMean = copyOfInVectors;

		DoubleMatrix A = new DoubleMatrix(copyOfInVectors);
		DoubleMatrix I = A.transpose();

		DoubleMatrix AT = I;

		DoubleMatrix C = A.mmul(AT).muli(1.0 / copyOfInVectors[0].length);

		final DoubleMatrix[] a = Eigen.symmetricEigenvectors(C);
		eigenValues = new double[a[1].columns];

		for (int i = 0; i < a[1].columns; i++) {
			eigenValues[i] = a[1].get(i, i);
		}
		final double[][] v = a[0].toArray2();
		eigenVectors = new double[v.length][];

		final double[][] ATd = AT.toArray2();

		for (int i = 0; i < v.length; i++) {
			eigenVectors[i] = Matrix.mult(ATd, v[i]);
		}

		QuickSortDualPivot qs = new QuickSortDualPivot();
		qs.sort(eigenVectors, eigenValues);

		for (int i = 0; i < eigenVectors.length; i++) {
			normalizeVector(eigenVectors[i]);
		}
	}

	/**
	 * Returns the eigenvalues of the computed covariance matrix.
	 * 
	 * @return the eigenvalues of the covariance matrix
	 */
	public double[] getEigenValues() {
		return (eigenValues);
	}

	/**
	 * Returns the eigenvectors of the computed covariance matrix.
	 * 
	 * @return the eigenvectors of the covariance matrix
	 */
	public double[][] getEigenVectors() {
		return (eigenVectors);
	}

	/**
	 * Returns the computed mean vector for the set of given vectors.
	 * 
	 * @return the mean vector
	 */
	public double[] getMeanValues() {
		return (meanValues);
	}

	public double[][] getVectorsZeroMean() {
		return vectorsZeroMean;
	}

	@Override
	public String getDescription() {
		return "Algorithm: PCA (Principal Component Analisys)";
	}
}