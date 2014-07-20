package edu.ubbcluj.emotion;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.experiment.ExperimentContext;
import org.openimaj.experiment.ExperimentRunner;
import org.openimaj.experiment.validation.cross.CrossValidator;
import org.openimaj.experiment.validation.cross.GroupedLeaveOneOut;
import org.openimaj.image.FImage;
import org.slf4j.Logger;

import edu.ubbcluj.emotion.annotator.BatchAnnotatorProvider;
import edu.ubbcluj.emotion.annotator.LinearSVMAnnotatorProvider;
import edu.ubbcluj.emotion.dataset.AbstractDataset;
import edu.ubbcluj.emotion.dataset.FacialFeature;
import edu.ubbcluj.emotion.dataset.ck.CKESDataset;
import edu.ubbcluj.emotion.engine.EmotionRecogniserProvider;
import edu.ubbcluj.emotion.engine.pca.PCAEmotionRecogniserProvider;
import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.util.Constants;

public class Test {

	private static Logger	logger		= org.slf4j.LoggerFactory.getLogger(Test.class);

	public static void main(String[] args) {
		initLogger();
		logger.error("test started");

		System.out.println("Creating grouped dataset");
		AbstractDataset<Emotion> dataset = new CKESDataset();
		CrossValidator<GroupedDataset<Emotion, ListDataset<FImage>, FImage>> crossValidator = new GroupedLeaveOneOut<>();
		EmotionRecogniserProvider engine = new PCAEmotionRecogniserProvider(50, dataset, FacialFeature.EYES, FacialFeature.MOUTH);
		BatchAnnotatorProvider<Emotion> annotatorProvider = new LinearSVMAnnotatorProvider<Emotion>();

		System.out.println("Creating benchmark");
		CrossValidationBenchmark crossValidation = new CrossValidationBenchmark(crossValidator, dataset, engine, annotatorProvider);

		System.out.println("Running experiment");
		ExperimentContext experiment = ExperimentRunner.runExperiment(crossValidation);
		String result = experiment.toString();

		try {
			FileUtils.writeStringToFile(new File("C:\\ckeSfffff50.txt"), result);
		} catch (IOException e) {
			System.out.println(result);
		}

	}

	private static void initLogger() {
		ConsoleAppender console = new ConsoleAppender();

		String PATTERN = "%d [%p|%c|%C{1}] %m%n";
		console.setLayout(new PatternLayout(PATTERN));
		console.setThreshold(Level.INFO);
		console.activateOptions();

		org.apache.log4j.Logger.getRootLogger().addAppender(console);

		FileAppender fa = new FileAppender();
		fa.setName("FileLogger");
		fa.setFile(Constants.BASE_FOLDER + "information.log");
		fa.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
		fa.setThreshold(Level.INFO);
		fa.setAppend(true);
		fa.activateOptions();

		org.apache.log4j.Logger.getRootLogger().addAppender(fa);

		FileAppender fa2 = new FileAppender();
		fa2.setName("FileLogger2");
		fa2.setFile(Constants.BASE_FOLDER + "debug.log");
		fa2.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
		fa2.setThreshold(Level.DEBUG);
		fa2.setAppend(true);
		fa2.activateOptions();

		org.apache.log4j.Logger.getRootLogger().addAppender(fa2);

	}

}
