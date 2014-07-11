package edu.ubbcluj.emotion.test.leaveoneout;

import edu.ubbcluj.emotion.database.file.loader.ResourceLoaderFactory;
import edu.ubbcluj.emotion.recognition.pca.trainingdata.EmotionTDP;
import edu.ubbcluj.emotion.test.AbstractTest;

abstract class AbstractLOUTest extends AbstractTest {

	@Override
	public EmotionTDP getTrainingData(ResourceLoaderFactory resourceLoaderFactory, String folder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmotionTDP getTestData(ResourceLoaderFactory resourceLoaderFactory, String folder) {
		// TODO Auto-generated method stub
		return null;
	}

}
