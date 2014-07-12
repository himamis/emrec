package edu.ubbcluj.emotion.recognition.pca.trainingdata;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListBackedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.data.dataset.MapBackedDataset;

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
	private List<List<double[]>>	trainingDataByEmotionList;
	private List<double[]>			trainingDataList;

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

	private List<List<double[]>> getTrainingDataByEmotionList() {
		if (trainingDataByEmotionList == null) {
			double[][][] data = getTrainingDataByEmotion();
			trainingDataByEmotionList = new ArrayList<List<double[]>>();
			for (int i = 0; i < data.length; i++) {
				List<double[]> td = new ArrayList<double[]>();
				for (int j = 0; j < data[i].length; j++) {
					td.add(data[i][j]);
				}
				trainingDataByEmotionList.add(td);
			}
		}
		return trainingDataByEmotionList;
	}

	private List<double[]> getTrainingDataList() {
		if (trainingDataList == null) {
			trainingDataList = new ArrayList<double[]>();
			double[][] data = getTrainingData();
			for (int i = 0; i < data.length; i++) {
				trainingDataList.add(data[i]);
			}
		}
		return trainingDataList;
	}

	@Override
	public GroupedDataset<Emotion, ListDataset<double[]>, double[]> getGroupedDataset() {
		GroupedDataset<Emotion, ListDataset<double[]>, double[]> gds = new MapBackedDataset<Emotion, ListDataset<double[]>, double[]>();
		List<List<double[]>> trainingDataList = getTrainingDataByEmotionList();
		for (int i = 0; i < trainingDataList.size(); i++) {
			gds.put(Emotion.values()[i], new ListBackedDataset<double[]>(trainingDataList.get(i)));
		}
		return gds;
	}

	protected abstract ImageFilter getImageFilter(Emotion emotion);
}
