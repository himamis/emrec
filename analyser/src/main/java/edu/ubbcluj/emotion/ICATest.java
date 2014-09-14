package edu.ubbcluj.emotion;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.openimaj.experiment.ExperimentContext;
import org.openimaj.experiment.ExperimentRunner;
import org.openimaj.image.FImage;
import org.openimaj.ml.kernel.HomogeneousKernelMap;
import org.openimaj.ml.kernel.HomogeneousKernelMap.KernelType;
import org.openimaj.ml.kernel.HomogeneousKernelMap.WindowType;
import org.slf4j.Logger;

import edu.ubbcluj.emotion.algorithm.fastica.FastICA;
import edu.ubbcluj.emotion.algorithm.pca.KPCA;
import edu.ubbcluj.emotion.annotator.BatchAnnotatorProvider;
import edu.ubbcluj.emotion.annotator.LiblinearAnnotatorProvider;
import edu.ubbcluj.emotion.crossvalidation.GroupedRandomSplitHalf;
import edu.ubbcluj.emotion.crossvalidation.NamedCrossValidator;
import edu.ubbcluj.emotion.crossvalidation.NamedGroupedLeaveOneOut;
import edu.ubbcluj.emotion.dataset.AbstractDataset;
import edu.ubbcluj.emotion.dataset.FacialFeature;
import edu.ubbcluj.emotion.dataset.ck.CKESDDataset;
import edu.ubbcluj.emotion.engine.EmotionRecogniserProvider;
import edu.ubbcluj.emotion.engine.ica.ICAEmotionRecogniserProvider;
import edu.ubbcluj.emotion.engine.pca.PCAEmotionRecogniserProvider;
import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.util.Constants;
import edu.ubbcluj.emotion.util.HasName;
import edu.ubbcluj.emotion.util.StringHelper;

public class ICATest {

	private static Logger	logger	= org.slf4j.LoggerFactory.getLogger(ICATest.class);

	public static void main(String[] args) {
		initLogger();
		logger.debug("test started");

		System.out.println("Creating grouped dataset");
		AbstractDataset<Emotion> dataset = new CKESDDataset();
		NamedCrossValidator<Emotion, FImage> crossValidator = new GroupedRandomSplitHalf<>(50);
		
		EmotionRecogniserProvider<FastICA> engine = new ICAEmotionRecogniserProvider(100, dataset, FacialFeature.EYES, FacialFeature.MOUTH);
		BatchAnnotatorProvider<Emotion> annotatorProvider = new LiblinearAnnotatorProvider<Emotion>();
		
		System.out.println("Creating benchmark");
		CrossValidationBenchmark crossValidation = new CrossValidationBenchmark(crossValidator, dataset, engine, annotatorProvider);

		System.out.println("Running experiment");
		ExperimentContext experiment = ExperimentRunner.runExperiment(crossValidation);
		String result = experiment.toString();

		writeExperimentResults(result, dataset, crossValidator, engine, annotatorProvider);

	}

	private static void initLogger() {
		ConsoleAppender console = new ConsoleAppender();

		String PATTERN = "%d [%p|%c|%C{1}] %m%n";
		console.setLayout(new PatternLayout(PATTERN));
		console.setThreshold(Level.DEBUG);
		console.activateOptions();

		org.apache.log4j.Logger.getRootLogger().addAppender(console);

		FileAppender fa = new FileAppender();
		fa.setName("FileLogger");
		fa.setFile(Constants.BASE_FOLDER + "information.log");
		fa.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
		fa.setThreshold(Level.DEBUG);
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

	public static void writeExperimentResults(String result, HasName... haveNames) {
		int i = 0;
		File file = null;
		do {
			String fileName = StringHelper.buildExperimentName(i, haveNames);
			file = new File(Constants.EXPERIMENT_FOLDER + fileName);
			i++;
		} while (file.exists());

		try {
			FileUtils.writeStringToFile(file, result);
		} catch (IOException e) {
			System.out.println(result);
		}
	}

}
