package edu.ubbcluj.emotion;

import org.apache.poi.hssf.record.Record;
import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.experiment.dataset.split.GroupedRandomSplitter;
import org.openimaj.image.FImage;

import edu.ubbcluj.emotion.ck.file.loader.FileResourceLoaderFactory;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoader;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoaderFactory;
import edu.ubbcluj.emotion.model.Emotion;

public class Test {

	private static String	database	= "openimaj_diff";

	public static void main(String[] args) {
		ResourceLoaderFactory rlf = new FileResourceLoaderFactory();
		ResourceLoader resourceLoader = rlf.getResourceLoader(database);

		DataProvider dataProvider = new DataProvider(resourceLoader);

		GroupedDataset<Emotion, ListDataset<FImage>, FImage> gds = dataProvider.getGroupedDataset();

		GroupedRandomSplitter<Emotion, FImage> splits = new GroupedRandomSplitter<>(gds, 15, 0, 15);

	}

}
