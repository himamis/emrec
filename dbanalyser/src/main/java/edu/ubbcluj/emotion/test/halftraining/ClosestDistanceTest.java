package edu.ubbcluj.emotion.test.halftraining;

import edu.ubbcluj.emotion.recognition.pca.classifier.Classifier;
import edu.ubbcluj.emotion.recognition.pca.classifier.NearestNeighbourClassifier;

public class ClosestDistanceTest extends AbstractHFTest {

	@Override
	public Classifier getClassifier() {
		return new NearestNeighbourClassifier();
	}
	
}
