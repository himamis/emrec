package edu.ubbcluj.emotion.annotator;

import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.FeatureExtractor;
import org.openimaj.image.FImage;
import org.openimaj.ml.annotation.BatchAnnotator;
import org.openimaj.ml.annotation.linear.LiblinearAnnotator;
import org.openimaj.ml.annotation.linear.LiblinearAnnotator.Mode;

import de.bwaldvogel.liblinear.SolverType;
import edu.ubbcluj.emotion.model.DatasetKey;

public class LiblinearAnnotatorProvider<KEY extends DatasetKey> implements BatchAnnotatorProvider<KEY> {

	private Mode		mode;
	private SolverType	solver;
	private double		C;
	private double		eps;
	private double		bias;
	private boolean		dense;

	public LiblinearAnnotatorProvider(Mode mode, SolverType solver, double C, double eps, double bias, boolean dense) {
		this.mode = mode;
		this.solver = solver;
		this.C = C;
		this.eps = eps;
		this.bias = bias;
		this.dense = dense;
	}

	public LiblinearAnnotatorProvider(Mode mode, SolverType solver, double C, double eps) {
		this(mode, solver, C, eps, -1, false);
	}
	
	public LiblinearAnnotatorProvider() {
		this(Mode.MULTICLASS, SolverType.L2R_L2LOSS_SVC, 1.0, 0.00001);
	}

	@Override
	public BatchAnnotator<FImage, KEY> getAnnotator(FeatureExtractor<DoubleFV, FImage> featureExtractor) {
		return new LiblinearAnnotator<FImage, KEY>(featureExtractor, mode, solver, C, eps, bias, dense);
	}
	
	@Override
	public String toString() {
		return "Annotator based on linear classifiers learned using Liblinear.";
	}

	@Override
	public String getName() {
		return "AN_LL";
	}
}
