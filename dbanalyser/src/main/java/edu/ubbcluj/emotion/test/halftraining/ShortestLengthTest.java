package edu.ubbcluj.emotion.test.halftraining;

import edu.ubbcluj.emotion.recognition.pca.classifier.Classifier;
import edu.ubbcluj.emotion.recognition.pca.classifier.ShortestLengthClassifier;

public class ShortestLengthTest extends AbstractHFTest {

	@Override
	public Classifier getClassifier() {
		return new ShortestLengthClassifier();
	}

}
