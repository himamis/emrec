package edu.ubbcluj.emotion.util;

/**
 * Qicksort with dual pivot Implementation modified from source
 * https://github.com
 * /arunma/DataStructuresAlgorithms/blob/master/src/basics/sorting
 * /quick/QuickSortDualPivot.java
 */
public class QuickSortDualPivot {

	public void sort(double[][] input, double[] values) {
		sort(input, values, 0, input.length - 1);
	}

	private void sort(double[][] input, double[] values, int lowIndex, int highIndex) {

		if (highIndex <= lowIndex)
			return;

		double pivot1 = values[lowIndex];
		double pivot2 = values[highIndex];

		if (pivot1 > pivot2) {
			exchange(input, values, lowIndex, highIndex);
			pivot1 = values[lowIndex];
			pivot2 = values[highIndex];
		} else if (pivot1 == pivot2) {
			while (pivot1 == pivot2 && lowIndex < highIndex) {
				lowIndex++;
				pivot1 = values[lowIndex];
			}
		}

		int i = lowIndex + 1;
		int lt = lowIndex + 1;
		int gt = highIndex - 1;

		while (i <= gt) {

			if (less(values[i], pivot1)) {
				exchange(input, values, i++, lt++);
			} else if (less(pivot2, values[i])) {
				exchange(input, values, i, gt--);
			} else {
				i++;
			}

		}

		exchange(input, values, lowIndex, --lt);
		exchange(input, values, highIndex, ++gt);

		sort(input, values, lowIndex, lt - 1);
		sort(input, values, lt + 1, gt - 1);
		sort(input, values, gt + 1, highIndex);
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
