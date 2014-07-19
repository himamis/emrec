package edu.ubbcluj.emotion.engine.pca;

import org.fastica.EigenValueFilter;

public class KEigenValueFilter implements EigenValueFilter {

	private int			k;
	private double[][]	filteredEigenVectors;
	private double[]	filteredEigenValues;

	public KEigenValueFilter(int k) {
		this.k = k;
	}

	@Override
	public void passEigenValues(double[] eigenValues, double[][] eigenVectors) {
		filteredEigenVectors = new double[k][eigenVectors[0].length];
		filteredEigenValues = new double[k];
		for (int i = eigenVectors.length - k; i < eigenVectors.length; i++) {
			int index = i - (eigenVectors.length - k);
			filteredEigenVectors[index] = eigenVectors[i];
			filteredEigenValues[index] = eigenValues[i];
		}
	}

	@Override
	public double[] getEigenValues() {
		return filteredEigenValues;
	}

	@Override
	public double[][] getEigenVectors() {
		return filteredEigenVectors;
	}

}
