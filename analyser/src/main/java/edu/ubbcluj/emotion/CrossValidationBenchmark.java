package edu.ubbcluj.emotion;

import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.experiment.ExperimentContext;
import org.openimaj.experiment.RunnableExperiment;
import org.openimaj.experiment.annotations.DependentVariable;
import org.openimaj.experiment.annotations.Experiment;
import org.openimaj.experiment.annotations.IndependentVariable;
import org.openimaj.experiment.annotations.Time;
import org.openimaj.experiment.evaluation.classification.ClassificationEvaluator;
import org.openimaj.experiment.evaluation.classification.analysers.confusionmatrix.AggregatedCMResult;
import org.openimaj.experiment.evaluation.classification.analysers.confusionmatrix.CMAggregator;
import org.openimaj.experiment.evaluation.classification.analysers.confusionmatrix.CMAnalyser;
import org.openimaj.experiment.evaluation.classification.analysers.confusionmatrix.CMResult;
import org.openimaj.experiment.validation.ValidationOperation;
import org.openimaj.experiment.validation.ValidationRunner;
import org.openimaj.experiment.validation.cross.CrossValidator;
import org.openimaj.image.FImage;

import edu.ubbcluj.emotion.engine.EmotionRecogniser;
import edu.ubbcluj.emotion.engine.EmotionRecogniserProvider;
import edu.ubbcluj.emotion.model.Emotion;

@Experiment(author = "Bencze Balazs", dateCreated = "2014-07-12", description = "Emotion recognition cross validation experiment")
public class CrossValidationBenchmark implements RunnableExperiment {

	@IndependentVariable
	protected CrossValidator<GroupedDataset<Emotion, ListDataset<FImage>, FImage>>	crossValidator;

	@IndependentVariable
	protected EmotionRecogniserProvider												engine;

	@IndependentVariable
	protected GroupedDataset<Emotion, ListDataset<FImage>, FImage>					dataset;

	@DependentVariable
	protected AggregatedCMResult<Emotion>											result;

	public CrossValidationBenchmark(CrossValidator<GroupedDataset<Emotion, ListDataset<FImage>, FImage>> crossValidator,
			GroupedDataset<Emotion, ListDataset<FImage>, FImage> dataset, EmotionRecogniserProvider engine) {
		this.crossValidator = crossValidator;
		this.dataset = dataset;
		this.engine = engine;
	}

	@Override
	public void setup() {
	}

	@Override
	public void perform() {
		final CMAggregator<Emotion> aggregator = new CMAggregator<Emotion>();
		System.out.println("Performing experiment");
		result = ValidationRunner.run(aggregator, dataset, crossValidator,
				new ValidationOperation<GroupedDataset<Emotion, ListDataset<FImage>, FImage>, CMResult<Emotion>>() {

					@Time(identifier = "Train and Evaluate recogniser")
					@Override
					public CMResult<Emotion> evaluate(GroupedDataset<Emotion, ListDataset<FImage>, FImage> training,
							GroupedDataset<Emotion, ListDataset<FImage>, FImage> validation) {
						final EmotionRecogniser recogniser = engine.create(training);

						final ClassificationEvaluator<CMResult<Emotion>, Emotion, FImage> eval = new ClassificationEvaluator<>(recogniser,
								validation, new CMAnalyser<FImage, Emotion>(CMAnalyser.Strategy.SINGLE));

						return eval.analyse(eval.evaluate());
					}

				});

	}

	@Override
	public void finish(ExperimentContext context) {
	}

}
