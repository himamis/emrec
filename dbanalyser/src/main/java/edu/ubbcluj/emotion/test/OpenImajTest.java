package edu.ubbcluj.emotion.test;

import java.util.Map;

import org.apache.poi.hssf.record.Record;
import org.openimaj.experiment.evaluation.classification.ClassificationEvaluator;
import org.openimaj.experiment.evaluation.classification.ClassificationResult;
import org.openimaj.experiment.evaluation.classification.analysers.confusionmatrix.CMAnalyser;
import org.openimaj.experiment.evaluation.classification.analysers.confusionmatrix.CMResult;
import org.openimaj.image.FImage;
import org.openimaj.ml.annotation.BatchAnnotator;

import edu.ubbcluj.emotion.database.file.loader.ResourceLoaderFactory;
import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.recognition.pca.EmotionRecogniser;
import edu.ubbcluj.emotion.recognition.pca.EmotionRecogniserOI;
import edu.ubbcluj.emotion.recognition.pca.classifier.Classifier;
import edu.ubbcluj.emotion.recognition.pca.trainingdata.EmotionTDP;
import edu.ubbcluj.emotion.recognition.pca.trainingdata.LastImagesFirstHalfTDP;
import edu.ubbcluj.emotion.recognition.pca.trainingdata.LastImagesSecondHalfTDP;

public class OpenImajTest implements Test {

	@Override
	public EmotionTDP getTrainingData(ResourceLoaderFactory resourceLoaderFactory, String folder) {
		return new LastImagesFirstHalfTDP(resourceLoaderFactory, folder);
	}

	@Override
	public EmotionTDP getTestData(ResourceLoaderFactory resourceLoaderFactory, String folder) {
		return new LastImagesSecondHalfTDP(resourceLoaderFactory, folder);
	}

	@Override
	public Classifier getClassifier() {
		return null;
	}

	@Override
	public EmotionRecogniser getEmotionRecogniser(ResourceLoaderFactory resourceLoaderFactory, String folder, int k) {
		return null;
	}

	@Override
	public double[] run(ResourceLoaderFactory resourceLoaderFactory, String folder, int k) {
		EmotionRecogniserOI eroi = new EmotionRecogniserOI(getTrainingData(resourceLoaderFactory, folder), null, k);
		BatchAnnotator<double[], Emotion> annotator = eroi.getAnnotator();
		ClassificationEvaluator<CMResult<Emotion>, Emotion, double[]> eval = new ClassificationEvaluator<CMResult<Emotion>, Emotion, double[]>(
				annotator, getTestData(resourceLoaderFactory, folder).getGroupedDataset(), new CMAnalyser<double[], Emotion>(
						CMAnalyser.Strategy.SINGLE));
		Map<double[], ClassificationResult<Emotion>> guesses = eval.evaluate();
		CMResult<Emotion> result = eval.analyse(guesses);
		System.out.println(result);
		return null;
	}

}
