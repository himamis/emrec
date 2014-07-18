package edu.ubbcluj.emotion.dataset;

import org.openimaj.data.dataset.ListDataset;
import org.openimaj.data.dataset.MapBackedDataset;
import org.openimaj.image.FImage;

import edu.ubbcluj.emotion.model.DatasetKey;

public abstract class AbstractDataset<KEY extends DatasetKey> extends MapBackedDataset<KEY, ListDataset<FImage>, FImage> implements HasFacialFeatures {

}
