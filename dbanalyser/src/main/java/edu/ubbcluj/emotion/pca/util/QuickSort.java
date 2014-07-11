package edu.ubbcluj.emotion.pca.util;

public class QuickSort {

	public void sort(double[][] input, double[] values) {
		sort(input, values, 0, input.length - 1);
	}

	private void sort(double[][] input, double[] values, int lowIndex, int highIndex) {

		if (highIndex <= lowIndex) {
			return;
		}

		int partIndex = partition(input, values, lowIndex, highIndex);

		sort(input, values, lowIndex, partIndex - 1);
		sort(input, values, partIndex + 1, highIndex);
	}

	private int partition(double[][] input, double[] values, int lowIndex, int highIndex) {

		int i = lowIndex;
		int pivotIndex = lowIndex;
		int j = highIndex + 1;

		while (true) {

			while (less(values[++i], values[pivotIndex])) {
				if (i == highIndex)
					break;
			}

			while (less(values[pivotIndex], values[--j])) {
				if (j == lowIndex)
					break;
			}

			if (i >= j)
				break;

			exchange(input, values, i, j);

		}

		exchange(input, values, pivotIndex, j);

		return j;
	}

	private static boolean less(double a, double b) {
		return a < b;
	}

	private static void exchange(double[][] input, double[] values, int i, int r) {
		double[] temp = input[i];
		input[i] = input[r];
		input[r] = temp;
		double t = values[i];
		values[i] = values[r];
		values[r] = t;
	}
}
