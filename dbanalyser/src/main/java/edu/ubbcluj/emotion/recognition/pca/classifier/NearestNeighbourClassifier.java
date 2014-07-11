package edu.ubbcluj.emotion.recognition.pca.classifier;

import static edu.ubbcluj.emotion.util.Math.columnDistance;

public class NearestNeighbourClassifier extends AbstractClassifier {

	@Override
	protected int classify(double[][] columnVector) {
		double distance = Double.MAX_VALUE;
		int emotionIndex = -1;

		for (int i = 0; i < projectionImages.length; i++) {
			for (int j = 0; j < projectionImages[i][0].length; j++) {
				double d = columnDistance(columnVector, 0, projectionImages[i], j);
				if (d < distance) {
					distance = d;
					emotionIndex = i;
				}
			}
		}
		return emotionIndex;
	}

	@Override
	public String getDescription() {
		return super.getDescription() + "Nearest Neighbour Classifier";
	}

}
