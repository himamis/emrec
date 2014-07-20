package edu.ubbcluj.emotion.annotator;

import java.util.List;
import java.util.Set;

import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.DoubleFVComparison;
import org.openimaj.feature.FeatureExtractor;
import org.openimaj.image.FImage;
import org.openimaj.ml.annotation.Annotated;
import org.openimaj.ml.annotation.BatchAnnotator;
import org.openimaj.ml.annotation.IncrementalAnnotator;
import org.openimaj.ml.annotation.ScoredAnnotation;
import org.openimaj.ml.annotation.basic.KNNAnnotator;

import edu.ubbcluj.emotion.model.DatasetKey;

public class KNNAnnotatorProvider<KEY extends DatasetKey> implements BatchAnnotatorProvider<KEY> {

	private DoubleFVComparison	fv;
	private int					k;

	public KNNAnnotatorProvider(DoubleFVComparison fv, int k) {
		this.fv = fv;
		this.k = k;
	}

	private class BatchKNNAnnotator extends BatchAnnotator<FImage, KEY> {

		private IncrementalAnnotator<FImage, KEY>	annotator;

		public BatchKNNAnnotator(FeatureExtractor<DoubleFV, FImage> fe, DoubleFVComparison fv, int k) {
			annotator = new KNNAnnotator<FImage, KEY, DoubleFV>(fe, fv, k);
		}

		@Override
		public void train(List<? extends Annotated<FImage, KEY>> data) {
			annotator.train(data);
		}

		@Override
		public Set<KEY> getAnnotations() {
			return annotator.getAnnotations();
		}

		@Override
		public List<ScoredAnnotation<KEY>> annotate(FImage object) {
			return annotator.annotate(object);
		}

	}

	@Override
	public BatchAnnotator<FImage, KEY> getAnnotator(FeatureExtractor<DoubleFV, FImage> featureExtractor) {
		return new BatchKNNAnnotator(featureExtractor, fv, k);
	}
	
	@Override
	public String toString() {
		return "Annotator based on a multi-class k-nearest-neighbour classifier.";
	}

}
