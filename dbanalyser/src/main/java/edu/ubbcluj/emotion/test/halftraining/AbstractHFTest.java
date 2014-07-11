package edu.ubbcluj.emotion.test.halftraining;

import edu.ubbcluj.emotion.database.file.loader.ResourceLoaderFactory;
import edu.ubbcluj.emotion.recognition.pca.trainingdata.EmotionTDP;
import edu.ubbcluj.emotion.recognition.pca.trainingdata.LastImagesFirstHalfTDP;
import edu.ubbcluj.emotion.recognition.pca.trainingdata.LastImagesSecondHalfTDP;
import edu.ubbcluj.emotion.test.AbstractTest;

abstract class AbstractHFTest extends AbstractTest {

	@Override
	public EmotionTDP getTrainingData(ResourceLoaderFactory resourceLoaderFactory, String folder) {
		return new LastImagesFirstHalfTDP(resourceLoaderFactory, folder);
	}

	@Override
	public EmotionTDP getTestData(ResourceLoaderFactory resourceLoaderFactory, String folder) {
		return new LastImagesSecondHalfTDP(resourceLoaderFactory, folder);
	}

}
