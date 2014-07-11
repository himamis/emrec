package edu.ubbcluj.emotion.recognition.pca.classifier;

import static edu.ubbcluj.emotion.util.Math.columnDistance;

import java.util.Arrays;

public class KNearestNeighbour extends AbstractClassifier {

	final private int	k;

	public KNearestNeighbour(final int k) {
		this.k = k;
	}

	@Override
	protected int classify(double[][] columnVector) {
		final int[] klasses = new int[k];
		final double[] distances = new double[k];
		Arrays.fill(distances, Double.MAX_VALUE);
		for (int i = 0; i < projectionImages.length; i++) {
			for (int j = 0; j < projectionImages[i][0].length; j++) {
				double d = columnDistance(columnVector, 0, projectionImages[i], j);
				insert(distances, klasses, d, i);
			}
		}
		double[] ret = new double[getNumberOfClasses()];
		for (int i = 0; i < k; i++) {
			ret[klasses[i]] += 1 / distances[i];
		}
		int klass = 0;
		double max = ret[0];
		for (int i = 1; i < getNumberOfClasses(); i++) {
			if (ret[i] > max) {
				klass = i;
				max = ret[i];
			}
		}
		return klass;
	}

	private void insert(final double[] distances, final int[] klasses, final double distance, final int klass) {
		int i = 0;
		while (i < k && distance > distances[i]) {
			i++;
		}
		if (i == k) {
			return;
		}
		for (int j = k - 2; j >= i; j--) {
			distances[j + 1] = distances[j];
			klasses[j + 1] = klasses[j];
		}
		distances[i] = distance;
		klasses[i] = klass;
	}

	@Override
	public String getDescription() {
		return super.getDescription() + "weighted (1/d) kNN with k: " + k;
	}

}
