package edu.ubbcluj.emotion.util;

public class MatrixUtil {

	public static double[] convertColumnVectorToRowVector(double[][] columnVector) {
		double[] vector = new double[columnVector.length];
		for (int i = 0; i < vector.length; i++) {
			vector[i] = columnVector[i][0];
		}
		return vector;
	}

	public static double[][] convertRowVectorToColumnVector(double[] vector) {
		double[][] columnVector = new double[vector.length][1];
		for (int i = 0; i < columnVector.length; i++) {
			columnVector[i][0] = vector[i];
		}
		return columnVector;
	}
}
