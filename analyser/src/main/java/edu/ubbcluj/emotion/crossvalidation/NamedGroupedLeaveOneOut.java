package edu.ubbcluj.emotion.crossvalidation;

import org.openimaj.experiment.validation.cross.GroupedLeaveOneOut;

import edu.ubbcluj.emotion.util.HasName;

public class NamedGroupedLeaveOneOut<KEY, INSTANCE> extends GroupedLeaveOneOut<KEY, INSTANCE> implements HasName {

	@Override
	public String getName() {
		return "CV_LOO";
	}

}
