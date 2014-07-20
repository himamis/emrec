package edu.ubbcluj.emotion.algorithm;

import org.openimaj.feature.DoubleFV;
import org.openimaj.image.FImage;


public interface Algorithm extends BatchTrainer<double[]>, FeatureExtractorProvider<DoubleFV, FImage> {

}
