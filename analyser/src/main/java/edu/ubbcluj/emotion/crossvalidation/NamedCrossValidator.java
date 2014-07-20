package edu.ubbcluj.emotion.crossvalidation;

import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.experiment.validation.cross.CrossValidator;

import edu.ubbcluj.emotion.model.DatasetKey;
import edu.ubbcluj.emotion.util.HasName;

public interface NamedCrossValidator<KEY extends DatasetKey, INSTANCE> extends CrossValidator<GroupedDataset<KEY, ListDataset<INSTANCE>, INSTANCE>>, HasName {

}
