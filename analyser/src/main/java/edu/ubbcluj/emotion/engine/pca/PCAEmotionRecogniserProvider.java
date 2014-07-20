package edu.ubbcluj.emotion.engine.pca;

import org.fastica.EigenValueFilter;

import edu.ubbcluj.emotion.algorithm.pca.KPCA;
import edu.ubbcluj.emotion.dataset.AbstractDataset;
import edu.ubbcluj.emotion.dataset.FacialFeature;
import edu.ubbcluj.emotion.engine.EmotionRecogniserProvider;
import edu.ubbcluj.emotion.model.Emotion;

public class PCAEmotionRecogniserProvider extends EmotionRecogniserProvider<KPCA> {

	private int					k;
	private EigenValueFilter	evFilter;

	public PCAEmotionRecogniserProvider(int k, AbstractDataset<Emotion> dataset, FacialFeature... features) {
		super(dataset, features);
		this.k = k;
		this.evFilter = new KEigenValueFilter(k);
	}

	@Override
	protected KPCA getAlgorithm() {
		return new KPCA(evFilter);
	}

	@Override
	public String toString() {
		return "Emotion Recogniser using PCA (Principal Component Analisys) with " + k + " eigenvectors. Facial features are: "
				+ super.toString();
	}

	@Override
	public String getName() {
		return "A_PCA" + k;
	}
}
