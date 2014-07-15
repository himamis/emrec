package edu.ubbcluj.emotion;

import java.util.List;
import java.util.Set;

import org.openimaj.experiment.evaluation.classification.ClassificationResult;
import org.openimaj.image.FImage;
import org.openimaj.ml.annotation.Annotator;
import org.openimaj.ml.annotation.ScoredAnnotation;

import edu.ubbcluj.emotion.model.Emotion;

public class EmotionRecogniser implements Annotator<FImage, Emotion> {

	private Annotator<FImage, Emotion>	internalAnnotator;

	public EmotionRecogniser(Annotator<FImage, Emotion> internalAnnotator) {
		this.internalAnnotator = internalAnnotator;
	}

	@Override
	public ClassificationResult<Emotion> classify(FImage object) {
		return internalAnnotator.classify(object);
	}

	@Override
	public Set<Emotion> getAnnotations() {
		return internalAnnotator.getAnnotations();
	}

	@Override
	public List<ScoredAnnotation<Emotion>> annotate(FImage object) {
		return internalAnnotator.annotate(object);
	}

}
