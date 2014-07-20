package edu.ubbcluj.emotion.algorithm.fastica;

import org.fastica.BelowEVFilter;
import org.fastica.ContrastFunction;
import org.fastica.EigenValueFilter;
import org.fastica.FastICAConfig;
import org.fastica.FastICAException;
import org.fastica.TanhCFunction;
import org.fastica.math.Matrix;
import org.fastica.math.Vector;
import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.FeatureExtractor;
import org.openimaj.image.FImage;

import edu.ubbcluj.emotion.algorithm.Algorithm;
import edu.ubbcluj.emotion.algorithm.AlgorithmException;
import edu.ubbcluj.emotion.algorithm.pca.PCA;
import edu.ubbcluj.emotion.algorithm.util.EigenValueDecompositionSymmetric;

/**
 * The <code>FastICA<code> class contains the main
 * FastICA algorithm.
 * 
 * @author Michael Lambertz
 */
public class FastICA implements Algorithm {
	// asssume the data is a sequence of column vectors

	private double[][]	inVectors;
	// private double[] meanValues;
	private double[][]	vectorsZeroMean;
	private double[][]	whiteningMatrix;
	private double[][]	dewhiteningMatrix;
	private double[][]	whitenedVectors;
	private double[][]	weightMatrix;
	private double[][]	mixingMatrix;
	private double[][]	separatingMatrix;
	private double[][]	icVectors;
	
	private FastICAConfig config;
	private ContrastFunction conFunction; 
	private EigenValueFilter evFilter;

	/**
	 * This constructor calls the FastICA algorithm. Note that the constructor
	 * expects the data, which should be analysed, to be an array of signals
	 * from different sources. These signalvectors must have equal length.
	 * 
	 * @param inVectors
	 *            the set of signals to analyse
	 * @param numICs
	 *            the number of independent components
	 * @throws FastICAException
	 *             if a computation error occurs
	 */
	public FastICA(int numICs) {
		this.config = new FastICAConfig(numICs);
		this.conFunction = new TanhCFunction(1.0);
		this.evFilter = new BelowEVFilter(1.0e-12, false);
	}

	/**
	 * This constructor calls the FastICA algorithm. Note that the constructor
	 * expects the data, which should be analysed, to be an array of signals
	 * from different sources. These signalvectors must have equal length.
	 * 
	 * @param inVectors
	 *            the set of signals to analyse
	 * @param config
	 *            the FastICA configuration
	 * @param conFunction
	 *            the contrast function
	 * @param evFilter
	 *            the eigenvalue filter
	 * @param listener
	 *            the progress listener
	 * @throws FastICAException
	 *             if a computation error occurs
	 */
	public FastICA(FastICAConfig config, ContrastFunction conFunction, EigenValueFilter evFilter) {
		this.config = config;
		this.conFunction = conFunction;
		this.evFilter = evFilter;
	}

	/**
	 * Calculates a difference measure of two matrices relative to their size.
	 * 
	 * @param mat1
	 *            the first matrix
	 * @param mat2
	 *            the second matrix
	 * @return the difference measure
	 */
	private static double deltaMatrices(double[][] mat1, double[][] mat2) {
		double[][] test = Matrix.sub(mat1, mat2);
		double delta = 0.0;
		int m = Matrix.getNumOfRows(mat1);
		int n = Matrix.getNumOfColumns(mat1);
		for (int i = 0; i < m; ++i) {
			for (int j = 0; j < n; ++j) {
				delta += Math.abs(test[i][j]);
			}
		}
		return (delta / (m * n));
	}

	/**
	 * Calculates a difference measure of two vectors relative to their size.
	 * 
	 * @param vec1
	 *            the first vector
	 * @param vec2
	 *            the second vector
	 * @return the difference measure
	 */
	private static double deltaVectors(double[] vec1, double[] vec2) {
		double[] test = Vector.sub(vec1, vec2);
		double delta = 0.0;
		int m = vec1.length;
		for (int i = 0; i < m; ++i) {
			delta += Math.abs(test[i]);
		}
		return delta / m;
	}

	/**
	 * Calculates the power of a symmetric matrix.
	 * 
	 * @param inMatrix
	 *            the symmetric matrix
	 * @param power
	 *            the power
	 * @return the resulting matrix
	 */
	private static double[][] powerSymmMatrix(double[][] inMatrix, double power) {
		EigenValueDecompositionSymmetric eig = new EigenValueDecompositionSymmetric(inMatrix);
		int m = Matrix.getNumOfRows(inMatrix);
		double[][] eigenVectors = eig.getEigenVectors();
		double[] eigenValues = eig.getEigenValues();
		for (int i = 0; i < m; ++i) {
			eigenValues[i] = Math.pow(eigenValues[i], power);
		}
		return (Matrix.mult(Matrix.mult(eigenVectors, Matrix.diag(eigenValues)), Matrix.transpose(eigenVectors)));
	}

	/**
	 * Inverts every element of the vector.
	 * 
	 * @param inVector
	 *            the vector
	 * @return the resulting vctor
	 */
	private static double[] invVector(double[] inVector) {
		int m = inVector.length;
		double[] outVector = new double[m];
		for (int i = 0; i < m; ++i) {
			outVector[i] = 1 / inVector[i];
		}
		return outVector;
	}

	/**
	 * Square roots every element of the vector.
	 * 
	 * @param inVector
	 *            the vector
	 * @return the resulting vector
	 */
	private static double[] sqrtVector(double[] inVector) {
		int m = inVector.length;
		double[] outVector = new double[m];
		for (int i = 0; i < m; ++i) {
			outVector[i] = Math.sqrt(inVector[i]);
		}
		return outVector;
	}

	/**
	 * Returns the resulting set of vectors containing the independent
	 * components.
	 * 
	 * @return the resulting set of vectors
	 */
	public synchronized double[][] getICVectors() {
		if (icVectors == null) {
			// calculate independent component vectors and readd the mean
			icVectors = Matrix.mult(separatingMatrix, inVectors);
		}
		return icVectors;
	}

	/**
	 * Returns the assumed mixing matrix.
	 * 
	 * @return the assumed mixing matrix
	 */
	public double[][] getMixingMatrix() {
		return mixingMatrix;
	}

	/**
	 * Returns the assumed seperating matrix.
	 * 
	 * @return the assumed seperating matrix
	 */
	public double[][] getSeparatingMatrix() {
		return separatingMatrix;
	}

	@Override
	public void train(double[][] data) throws AlgorithmException{
		double[][] inVectors = Matrix.transpose(data);
		this.inVectors = inVectors;
		this.icVectors = null;
		PCA pca = new PCA(inVectors);
		vectorsZeroMean = pca.getVectorsZeroMean();
		double[] eigenValues = pca.getEigenValues();
		double[][] eigenVectors = pca.getEigenVectors();
		evFilter.passEigenValues(eigenValues, eigenVectors);
		eigenValues = evFilter.getEigenValues();
		if ((eigenValues == null) || (eigenValues.length == 0)) {
			mixingMatrix = null;
			separatingMatrix = null;
			icVectors = null;
			throw new AlgorithmException("No more eigenvalues");
		}
		eigenVectors = evFilter.getEigenVectors();
		whiteningMatrix = Matrix.mult(Matrix.diag(invVector(sqrtVector(eigenValues))), Matrix.transpose(eigenVectors));
		dewhiteningMatrix = Matrix.mult(eigenVectors, Matrix.diag(sqrtVector(eigenValues)));
		// the whitened vectors' correlation matrix equals unit matrix
		// which is demanded to perform the FastICA algorithm
		whitenedVectors = Matrix.mult(whiteningMatrix, vectorsZeroMean);

		// initialize the weight matrix and some other variables
		int m = Matrix.getNumOfRows(whitenedVectors);
		int n = Matrix.getNumOfColumns(whitenedVectors);
		int numICs = config.getNumICs();
		if (m < numICs) {
			numICs = m;
		}
		if (config.getInitialMixingMatrix() == null) {
			weightMatrix = Matrix.random(numICs, m);
		} else {
			if ((Matrix.getNumOfColumns(config.getInitialMixingMatrix()) == numICs)
					&& (Matrix.getNumOfRows(config.getInitialMixingMatrix()) == Matrix.getNumOfRows(vectorsZeroMean))) {
				weightMatrix = Matrix.transpose(Matrix.mult(whiteningMatrix, config.getInitialMixingMatrix()));
			} else {
				weightMatrix = Matrix.random(numICs, m);
			}
		}
		weightMatrix = Matrix.mult(powerSymmMatrix(Matrix.square(weightMatrix), -0.5), weightMatrix);
		int iter;
		boolean ready;
		int maxIter = config.getMaxIterations();

		// TODO: NaN problem, exception
		// perform FastICA algorithm
		switch (config.getApproach()) {
		case SYMMETRIC:
			double[][] weightMatrixOld;
			iter = 0;
			ready = false;
			while ((iter < maxIter) && (!ready)) {

				weightMatrixOld = Matrix.clone(weightMatrix);

				for (int i = 0; i < numICs; ++i) {
					double[] v1 = Matrix.getVecOfRow(weightMatrix, i);
					double[] v2;
					double beta = 0.0;
					double[] exgf = Vector.newVector(m, 0.0);
					double egfd = 0.0;
					for (int j = 0; j < n; ++j) {
						double[] actualXVector = Matrix.getVecOfCol(whitenedVectors, j);
						double weightedX = Vector.dot(v1, actualXVector);
						double gff = conFunction.function(weightedX);
						double gfd = conFunction.derivative(weightedX);
						beta += weightedX * gff;
						egfd += gfd;
						exgf = Vector.add(exgf, Vector.scale(gff, actualXVector));
					}
					beta /= n;
					egfd /= n;
					exgf = Vector.scale(1.0 / n, exgf);
					v2 = Vector.sub(v1, Vector.scale(1.0 / (egfd - beta), Vector.sub(exgf, Vector.scale(beta, v1))));
					// write new vector to the matrix
					for (int j = 0; j < m; ++j) {
						weightMatrix[i][j] = v2[j];
					}
				}

				// symmetric decorrelation by orthonormalisation
				weightMatrix = Matrix.mult(powerSymmMatrix(Matrix.square(weightMatrix), -0.5), weightMatrix);

				// test if good approximation
				if (deltaMatrices(weightMatrix, weightMatrixOld) < config.getEpsilon()) {
					ready = true;
				}

				iter++;
			}
			break;
		case DEFLATION:
			for (int i = 0; i < numICs; ++i) {
				double[] v2 = Matrix.getVecOfRow(weightMatrix, i);
				iter = 0;
				ready = false;
				while ((iter < maxIter) && (!ready)) {

					double[] v1 = Vector.clone(v2);
					double beta = 0.0;
					double[] exgf = Vector.newVector(m, 0.0);
					double egfd = 0.0;
					for (int j = 0; j < n; ++j) {
						double[] actualXVector = Matrix.getVecOfCol(whitenedVectors, j);
						double weightedX = Vector.dot(v1, actualXVector);
						double gff = conFunction.function(weightedX);
						double gfd = conFunction.derivative(weightedX);
						beta += weightedX * gff;
						egfd += gfd;
						exgf = Vector.add(exgf, Vector.scale(gff, actualXVector));
					}
					beta /= n;
					egfd /= n;
					exgf = Vector.scale(1.0 / n, exgf);
					v2 = Vector.sub(v1, Vector.scale(1.0 / (egfd - beta), Vector.sub(exgf, Vector.scale(beta, v1))));

					// orthogonalisation of the vector
					for (int j = 0; j < i; ++j) {
						v2 = Vector.sub(v2, Vector.scale(Vector.dot(v2, weightMatrix[j]), weightMatrix[j]));
					}
					// orthonormalisation of the vector
					v2 = Vector.scale(1 / Math.sqrt(Vector.dot(v2, v2)), v2);

					// write new vector to the matrix
					for (int j = 0; j < m; ++j) {
						weightMatrix[i][j] = v2[j];
					}

					// test if good approximation
					if (deltaVectors(v2, v1) < config.getEpsilon()) {
						ready = true;
					}

					iter++;
				}
			}
			break;
		}

		mixingMatrix = Matrix.mult(dewhiteningMatrix, Matrix.transpose(weightMatrix));
		separatingMatrix = Matrix.mult(weightMatrix, whiteningMatrix);
	}

	@Override
	public FeatureExtractor<DoubleFV, FImage> getFeatureExtractor() {
		return new FeatureExtractorICA(separatingMatrix);
	}
}
