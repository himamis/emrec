package edu.ubbcluj.emotion.test;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import edu.ubbcluj.emotion.database.file.loader.ResourceLoaderFactory;
import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.recognition.pca.EmotionRecogniser;
import edu.ubbcluj.emotion.recognition.pca.classifier.Classifier;
import edu.ubbcluj.emotion.recognition.pca.trainingdata.EmotionTDP;
import edu.ubbcluj.emotion.util.Constants;

public abstract class AbstractTest implements Test {
	
	

	@Override
	public EmotionRecogniser getEmotionRecogniser(final ResourceLoaderFactory resourceLoaderFactory, final String folder, final int k) {
		EmotionRecogniser recogniser = null;
		try {
			FileInputStream file = new FileInputStream(Constants.BASE_FOLDER + this.getClass().getCanonicalName() + "_" + k + ".obj");
			ObjectInputStream in = new ObjectInputStream(file);
			recogniser = (EmotionRecogniser) in.readObject();
			in.close();
			file.close();
		} catch (IOException | ClassNotFoundException e) {
			final EmotionTDP emotionTrainingDataProvider = getTrainingData(resourceLoaderFactory, folder);
			final Classifier classifier = getClassifier();
			recogniser = new EmotionRecogniser(emotionTrainingDataProvider, classifier, k);
			ObjectOutputStream out;
			try {
				out = new ObjectOutputStream(new FileOutputStream(Constants.BASE_FOLDER + this.getClass().getCanonicalName() + "_" + k + ".obj"));
				out.writeObject(recogniser);
				out.flush();
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return recogniser;
	}
	
	@Override
	public double[] run(ResourceLoaderFactory resourceLoaderFactory, String folder, int k) {
		double[] results = new double[Emotion.values().length];
		final EmotionRecogniser recogniser = getEmotionRecogniser(resourceLoaderFactory, folder, k);
		final EmotionTDP testData = getTestData(resourceLoaderFactory, folder);
		
		for (int i = 0; i < Emotion.values().length; i++) {
			Emotion emotion = Emotion.values()[i];
			List<BufferedImage> recogniseImages = testData.getTrainingImagesByEmotion()[i];
			for (int j = 0; j < recogniseImages.size(); j++) {
				Emotion recognisedEmotion = recogniser.recogniseEmotion(recogniseImages.get(j));
				if (recognisedEmotion == emotion) {
					results[i] += 1;
				}
			}
			results[i] /= recogniseImages.size();
		}
		return results;
	}

}
