package edu.ubbcluj.emotion.recognition.pca.classifier;

import static edu.ubbcluj.emotion.util.Math.*;

public class ShortestLengthClassifier extends AbstractClassifier {

	@Override
	protected int classify(double[][] columnVector) {
		double shortestLength = Double.MAX_VALUE;
		int emotionIndex = -1;

		for (int i = 0; i < projectionImages.length; i++) {
			double length = 0;
			for (int j = 0; j < projectionImages[i][0].length; j++) {
				double d = columnDistance(columnVector, 0, projectionImages[i], j);
				length += d * d;
			}
			length /= Math.sqrt(length);
			if (length < shortestLength) {
				shortestLength = length;
				emotionIndex = i;
			}
		}
		return emotionIndex;
	}

	@Override
	public String getDescription() {
		return super.getDescription() + "Shortest Length Classifier";
	}

}
