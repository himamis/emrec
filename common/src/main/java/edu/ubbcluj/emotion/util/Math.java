package edu.ubbcluj.emotion.util;

public abstract class Math {

	public static double min(double[] vec) {
		double min = vec[0];
		for (int i = 1; i < vec.length; i++) {
			if (min > vec[i]) {
				min = vec[i];
			}
		}
		return min;
	}

	public static double max(double[] vec) {
		double max = vec[0];
		for (int i = 1; i < vec.length; i++) {
			if (max < vec[i]) {
				max = vec[i];
			}
		}
		return max;
	}

	public static double[] calculateRowMeanValues(double[][] data) {
		int rows = data.length;
		int columns = data[0].length;

		double[] meanValues = new double[columns];

		for (int j = 0; j < columns; j++) {
			for (int i = 0; i < rows; i++) {
				meanValues[j] += data[i][j];
			}
			meanValues[j] /= rows;
		}
		return meanValues;
	}

	public static double[] calculateColumnMeanValues(double[][] data, double[] weights) {
		int rows = data.length;
		int columns = data[0].length;

		double weightSum = sum(weights);
		double[] meanValues = new double[rows];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				meanValues[i] += weights[i] * data[i][j];
			}
			meanValues[i] /= weightSum;
		}
		return meanValues;
	}

	public static double[] calculateColumnMeanValues(double[][] data) {
		int rows = data.length;
		int columns = data[0].length;

		double[] meanValues = new double[rows];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				meanValues[i] += data[i][j];
			}
			meanValues[i] /= columns;
		}
		return meanValues;
	}

	public static double sum(double[] array) {
		double sum = 0;
		for (int i = 0; i < array.length; i++) {
			sum += array[i];
		}
		return sum;
	}

	public static double distance(double[] vector1, double[] vector2) {
		double sum = 0.0;
		for (int i = 0; i < vector1.length; i++) {
			sum += java.lang.Math.pow(vector1[i] - vector2[i], 2.0);
		}
		return java.lang.Math.sqrt(sum);
	}

	public static double columnDistance(double[][] matrix1, int index1, double[][] matrix2, int index2) {
		double sum = 0.0;
		for (int i = 0; i < matrix1.length; i++) {
			sum += java.lang.Math.pow(matrix1[i][index1] - matrix2[i][index2], 2.0);
		}
		return java.lang.Math.sqrt(sum);
	}

	public static double[] addVectorP(double[] vector1, double[] vector2) {
		for (int i = 0; i < vector1.length; i++) {
			vector1[i] += vector2[i];
		}
		return vector1;
	}

	public static double[] subVectorP(double[] vector1, double[] vector2) {
		for (int i = 0; i < vector1.length; i++) {
			vector1[i] -= vector2[i];
		}
		return vector1;
	}
	
	public static double[][] subVectorP(double[][] columnVector, double[] vector) {
		for (int i = 0; i < vector.length; i++) {
			columnVector[i][0] -= vector[i];
		}
		return columnVector;
	}

	public static double[][] addToRowsP(double[][] matrix, double[] vector) {
		for (int i = 0; i < matrix.length; i++) {
			addVectorP(matrix[i], vector);
		}
		return matrix;
	}

	public static double[][] subFromRowsP(double[][] matrix, double[] vector) {
		for (int i = 0; i < matrix.length; i++) {
			subVectorP(matrix[i], vector);
		}
		return matrix;
	}

	public static void normalizeVector(double[] vector) {
		double sum = 0;
		for (int i = 0; i < vector.length; i++) {
			sum += vector[i] * vector[i];
		}
		sum = Math.sqrt(sum);
		for (int i = 0; i < vector.length; i++) {
			vector[i] /= sum;
		}
	}

	public static double sqrt(double a) {
		return java.lang.Math.sqrt(a);
	}
}
