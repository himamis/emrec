package edu.ubbcluj.emotion.crossvalidation;

import org.openimaj.experiment.validation.cross.GroupedLeaveOneOut;

import edu.ubbcluj.emotion.model.DatasetKey;

public class NamedGroupedLeaveOneOut<KEY extends DatasetKey, INSTANCE> extends GroupedLeaveOneOut<KEY, INSTANCE> implements NamedCrossValidator<KEY, INSTANCE> {

	@Override
	public String getName() {
		return "CV_LOO";
	}

}
