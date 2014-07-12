package edu.ubbcluj.emotion;

import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.experiment.ExperimentContext;
import org.openimaj.experiment.RunnableExperiment;
import org.openimaj.experiment.annotations.DependentVariable;
import org.openimaj.experiment.annotations.IndependentVariable;
import org.openimaj.experiment.evaluation.classification.analysers.confusionmatrix.AggregatedCMResult;
import org.openimaj.experiment.validation.cross.CrossValidator;
import org.openimaj.image.FImage;

import edu.ubbcluj.emotion.model.Emotion;

public class CrossValidationBenchmark implements RunnableExperiment {
	
	@IndependentVariable
	protected CrossValidator<GroupedDataset<Emotion, ListDataset<FImage>, FImage>> crossValidator;

	@IndependentVariable
	protected GroupedDataset<PERSON, ? extends ListDataset<IMAGE>, IMAGE> dataset;

	@IndependentVariable
	protected FaceDetector<FACE, IMAGE> faceDetector;

	@IndependentVariable
	protected FaceRecogniserProvider<FACE, PERSON> engine;

	@DependentVariable
	protected AggregatedCMResult<PERSON> result;

	@Override
	public void setup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void perform() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finish(ExperimentContext context) {
		// TODO Auto-generated method stub
		
	}

}
