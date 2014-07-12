package edu.ubbcluj.emotion;

import static edu.ubbcluj.emotion.util.StringUtil.debug;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubbcluj.emotion.ck.file.loader.FileResourceLoaderFactory;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoaderFactory;
import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.test.OpenImajTest;
import edu.ubbcluj.emotion.test.Test;
import edu.ubbcluj.emotion.test.halftraining.ShortestLengthTest;
import edu.ubbcluj.emotion.util.Constants;

public class AnalyserApplication {

	private static final Logger	logger	= LoggerFactory.getLogger(AnalyserApplication.class);

	/*
	 * private static String resized_folder = "openimajdiff2";
	 * 
	 * private static final int K = 30; private static final Emotion findEmotion
	 * = Emotion.HAPPY; private static final int leaveIndex = 0;
	 */

	public static void main(String[] args) {
		initLogger();

		final ResourceLoaderFactory resourceLoaderFactory = new FileResourceLoaderFactory();

		Test test = new OpenImajTest();
		double[] result = test.run(resourceLoaderFactory, "openimaj_folder", 5);

		/*for (int i = 0; i < result.length; i++) {
			
			debug("Emotion " + Emotion.values()[i] + " recognised in " + result[i] * 100 + "%");
		}*/
	}

	private static void initLogger() {
		ConsoleAppender console = new ConsoleAppender();

		String PATTERN = "%d [%p|%c|%C{1}] %m%n";
		console.setLayout(new PatternLayout(PATTERN));
		console.setThreshold(Level.FATAL);
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
