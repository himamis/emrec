package edu.ubbcluj.emotion.util;

import java.util.ArrayList;
import java.util.List;

import org.openimaj.data.dataset.Dataset;
import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.FeatureExtractor;

public class DataUtil {

	public static <KEY, INSTANCE> double[][] getMatrixData(GroupedDataset<KEY, Dataset<INSTANCE>, INSTANCE> groupedDataset,
			FeatureExtractor<DoubleFV, INSTANCE> featureExtractor) {
		List<Dataset<INSTANCE>> datasetList = new ArrayList<>();
		int n = 0;
		for (KEY key : groupedDataset.getGroups()) {
			Dataset<INSTANCE> dataset = groupedDataset.getInstances(key);
			n += dataset.numInstances();
			datasetList.add(dataset);
		}
		double[][] matrixData = new double[n][];
		int k = 0;
		for (Dataset<INSTANCE> dataset : datasetList) {
			for (INSTANCE instance : dataset) {
				DoubleFV fv = featureExtractor.extractFeature(instance);
				matrixData[k] = fv.values;
				k += 1;
			}
		}
		return matrixData;
	}

}
