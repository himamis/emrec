package edu.ubbcluj.emotion.recognition.pca.trainingdata;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import edu.ubbcluj.emotion.database.file.loader.ImageFilter;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoader;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoaderFactory;
import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.util.HasDescription;
import edu.ubbcluj.emotion.util.ImageUtil;

public abstract class AbstractTDP implements EmotionTDP, HasDescription {

	final protected ResourceLoader	loader;
	private double[][][]			trainingDataByEmotion;
	private double[][]				trainingData;
	private List<BufferedImage>		trainingImages;
	private List<BufferedImage>[]	trainingImagesByEmotion;

	public AbstractTDP() {
		throw new RuntimeException("Implicit constructor not allowed");
	}

	public AbstractTDP(final ResourceLoaderFactory resourceLoaderFactory, final String database) {
		this.loader = resourceLoaderFactory.getResourceLoader(database);
	}

	@Override
	public double[][] getTrainingData() {
		if (trainingData == null) {
			double[][][] trainingDataByEmotion = getTrainingDataByEmotion();
			int n = 0;
			for (int i = 0; i < trainingDataByEmotion.length; i++) {
				n += trainingDataByEmotion[i].length;
			}
			trainingData = new double[n][];
			int k = 0;
			for (int i = 0; i < trainingDataByEmotion.length; i++) {
				for (int j = 0; j < trainingDataByEmotion[i].length; j++) {
					trainingData[k] = trainingDataByEmotion[i][j];
					++k;
				}
			}
		}
		return trainingData;
	}

	@Override
	public List<BufferedImage> getTrainingImages() {
		if (trainingImages == null) {
			trainingImages = new ArrayList<>();
			List<BufferedImage>[] trainingImagesByEmotion = getTrainingImagesByEmotion();
			for (List<BufferedImage> images : trainingImagesByEmotion) {
				trainingImages.addAll(images);
			}
		}
		return trainingImages;
	}

	@Override
	public double[][][] getTrainingDataByEmotion() {
		if (trainingDataByEmotion == null) {
			trainingDataByEmotion = new double[Emotion.values().length][][];
			List<BufferedImage>[] trainingImagesByEmotion = getTrainingImagesByEmotion();
			for (int i = 0; i < trainingImagesByEmotion.length; i++) {
				trainingDataByEmotion[i] = ImageUtil.convertImageListToDoubleMatrix(trainingImagesByEmotion[i]);
			}
		}
		return trainingDataByEmotion;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BufferedImage>[] getTrainingImagesByEmotion() {
		if (trainingImagesByEmotion == null) {
			trainingImagesByEmotion = (List<BufferedImage>[]) new List[Emotion.values().length];
			for (int i = 0; i < Emotion.values().length; i++) {
				Emotion emotion = Emotion.values()[i];
				trainingImagesByEmotion[i] = loader.getImages(getImageFilter(emotion));
			}
		}
		return trainingImagesByEmotion;
	}
	
	@Override
	public String getDescription() {
		return "Data Provider: ";
	}

	protected abstract ImageFilter getImageFilter(Emotion emotion);
}
