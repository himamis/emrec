package edu.ubbcluj.emotion.test;

import edu.ubbcluj.emotion.database.file.loader.ResourceLoaderFactory;
import edu.ubbcluj.emotion.recognition.pca.EmotionRecogniser;
import edu.ubbcluj.emotion.recognition.pca.classifier.Classifier;
import edu.ubbcluj.emotion.recognition.pca.trainingdata.EmotionTDP;

public interface Test {

	public EmotionTDP getTrainingData(final ResourceLoaderFactory resourceLoaderFactory, final String folder);

	public EmotionTDP getTestData(final ResourceLoaderFactory resourceLoaderFactory, final String folder);

	public Classifier getClassifier();

	public EmotionRecogniser getEmotionRecogniser(final ResourceLoaderFactory resourceLoaderFactory, final String folder, final int k);

	public double[] run(final ResourceLoaderFactory resourceLoaderFactory, final String folder, final int k);
}
