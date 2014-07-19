package edu.ubbcluj.emotion.dataset;

import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListDataset;

import edu.ubbcluj.emotion.model.DatasetKey;

public interface MyGroupedDataset<KEY extends DatasetKey, FImage> extends GroupedDataset<KEY, ListDataset<FImage>, FImage>, 
		HasFacialFeatures,
		HasInformation<KEY> {

}
