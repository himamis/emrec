package edu.ubbcluj.emotion.engine.ica;

import edu.ubbcluj.emotion.algorithm.fastica.FastICA;
import edu.ubbcluj.emotion.dataset.AbstractDataset;
import edu.ubbcluj.emotion.dataset.FacialFeature;
import edu.ubbcluj.emotion.engine.EmotionRecogniserProvider;
import edu.ubbcluj.emotion.model.Emotion;

public class ICAEmotionRecogniserProvider extends EmotionRecogniserProvider<FastICA> {

	private int	numICs;

	public ICAEmotionRecogniserProvider(int numICs, AbstractDataset<Emotion> dataset, FacialFeature... features) {
		super(dataset, features);
		this.numICs = numICs;
	}

	@Override
	public String toString() {
		return "Emotion Recogniser using ICA (Independent Component Analisys) with " + numICs + " independent components. Facial features are: "
				+ super.toString();
	}

	@Override
	public String getName() {
		return "A_ICA" + numICs;
	}

	@Override
	protected FastICA getAlgorithm() {
		return new FastICA(numICs);
	}
}
