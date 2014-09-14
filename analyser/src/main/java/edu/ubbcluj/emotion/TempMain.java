package edu.ubbcluj.emotion;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import org.openimaj.experiment.evaluation.classification.ClassificationResult;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;

import edu.ubbcluj.emotion.algorithm.pca.KPCA;
import edu.ubbcluj.emotion.annotator.BatchAnnotatorProvider;
import edu.ubbcluj.emotion.annotator.LiblinearAnnotatorProvider;
import edu.ubbcluj.emotion.dataset.AbstractDataset;
import edu.ubbcluj.emotion.dataset.ck.CKEDataset;
import edu.ubbcluj.emotion.engine.EmotionRecogniser;
import edu.ubbcluj.emotion.engine.EmotionRecogniserProvider;
import edu.ubbcluj.emotion.engine.pca.PCAEmotionRecogniserProvider;
import edu.ubbcluj.emotion.model.Emotion;

public class TempMain {

	private static String	fileLoc		= "c:\\Users\\himamis\\git\\";
	private static String	fileFormat	= ".png";
	
	private static String	orom		= "orom" + fileFormat;
	private static String	duh			= "duh" + fileFormat;
	private static String	megvetes	= "megvetes" + fileFormat;
	private static String	undor		= "undor" + fileFormat;

	public static void main(String[] args) {
		System.out.println("Creating dataset");
		AbstractDataset<Emotion> dataset = new CKEDataset();

		BatchAnnotatorProvider<Emotion> annotatorProvider = new LiblinearAnnotatorProvider<Emotion>();

		System.out.println("Creating emotion recogniser");
		EmotionRecogniserProvider<KPCA> emrec = new PCAEmotionRecogniserProvider(30, dataset);
		EmotionRecogniser rec = emrec.create(dataset, annotatorProvider);

		FImage forom;
		try {
			forom = ImageUtilities.readF(new File(fileLoc + orom));
			ClassificationResult<Emotion> res = rec.classify(forom);
			DecimalFormat df = new DecimalFormat("#.###");
			for (Emotion emotion : Emotion.values()) {
				double hit = res.getConfidence(emotion);
				System.out.println(emotion + " confidence + " + df.format(hit));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
