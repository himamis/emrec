package edu.ubbcluj.emotion.dataset;

import org.openimaj.data.dataset.ListDataset;
import org.openimaj.data.dataset.MapBackedDataset;
import org.openimaj.image.FImage;

import edu.ubbcluj.emotion.model.DatasetKey;
import edu.ubbcluj.emotion.util.HasName;

public abstract class AbstractDataset<KEY extends DatasetKey> extends MapBackedDataset<KEY, ListDataset<FImage>, FImage> implements MyGroupedDataset<KEY, FImage>, HasName {

}
