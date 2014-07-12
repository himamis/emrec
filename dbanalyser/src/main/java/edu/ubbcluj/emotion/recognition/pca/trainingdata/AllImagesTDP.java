package edu.ubbcluj.emotion.recognition.pca.trainingdata;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.openimaj.data.dataset.ListBackedDataset;

import edu.ubbcluj.emotion.database.file.loader.ImageFilter;
import edu.ubbcluj.emotion.database.file.loader.ImageFilterAdapter;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoaderFactory;
import edu.ubbcluj.emotion.model.Emotion;

public class AllImagesTDP extends AbstractTDP {

	public AllImagesTDP(final ResourceLoaderFactory resourceLoaderFactory, final String database) {
		super(resourceLoaderFactory, database);
	}

	@Override
	protected ImageFilter getImageFilter(final Emotion emotion) {
		return new ImageFilterAdapter(emotion);
	}

	@Override
	public String getDescription() {
		return super.getDescription() + " all images";
	}

}
