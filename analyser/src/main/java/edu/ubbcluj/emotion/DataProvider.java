package edu.ubbcluj.emotion;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListBackedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.data.dataset.MapBackedDataset;
import org.openimaj.experiment.annotations.DatasetDescription;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;

import edu.ubbcluj.emotion.database.file.loader.ImageFilterAdapter;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoader;
import edu.ubbcluj.emotion.model.Emotion;

@DatasetDescription(name = "CK+", creator = "Lucey, P., Cohn, J. F., Kanade, T., Saragih, J., Ambadar, Z., & Matthews, I.", url = "http://www.pitt.edu/~emotion/ck-spread.htm", description = "")
public class DataProvider {

	private ResourceLoader											resourceLoader;

	private GroupedDataset<Emotion, ListDataset<FImage>, FImage>	groupedDataset;

	private double[][]												matrixData;

	public DataProvider(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public GroupedDataset<Emotion, ListDataset<FImage>, FImage> getGroupedDataset() {
		if (groupedDataset == null) {
			loadGroupedDataset();
		}
		return groupedDataset;
	}

	private void loadGroupedDataset() {
		groupedDataset = new MapBackedDataset<Emotion, ListDataset<FImage>, FImage>();
		for (final Emotion em : Emotion.values()) {
			List<FImage> images = new ArrayList<>();
			List<BufferedImage> bufferedImages = resourceLoader.getImages(new ImageFilterAdapter() {
				@Override
				public boolean filter(Emotion emotion) {
					return emotion == em;
				}

				@Override
				public boolean filter(boolean isLastImage) {
					return isLastImage;
				}
			});
			for (BufferedImage bufferedImage : bufferedImages) {
				images.add(ImageUtilities.createFImage(bufferedImage));
			}
			groupedDataset.put(em, new ListBackedDataset<FImage>(images));
		}
	}

	public double[][] getMatrixData() {
		if (matrixData == null) {
			loadMatrixData();
		}
		return matrixData;
	}

	private void loadMatrixData() {
		if (groupedDataset == null) {
			loadGroupedDataset();
		}
		@SuppressWarnings("unchecked")
		ListDataset<FImage>[] lists = new ListDataset[Emotion.values().length];
		int n = 0;
		for (int i = 0; i < Emotion.values().length; i++) {
			Emotion em = Emotion.values()[i];
			lists[i] = groupedDataset.getInstances(em);
			n += lists[i].size();
		}
		matrixData = new double[n][];
		int k = 0;
		for (int i = 0; i < lists.length; i++) {
			ListDataset<FImage> list = lists[i];
			for (int j = 0; j < list.size(); j++) {
				matrixData[k] = list.get(j).getDoublePixelVector();
				k += 1;
			}
		}
	}

}
