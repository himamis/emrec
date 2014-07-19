package edu.ubbcluj.emotion.util;

import java.util.ArrayList;
import java.util.List;

import org.openimaj.data.dataset.Dataset;
import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListBackedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.data.dataset.MapBackedDataset;
import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.FeatureExtractor;

public class GroupedDatasetHelper {

	public static <KEY, INSTANCE> double[][] getMatrixData(GroupedDataset<KEY, ListDataset<INSTANCE>, INSTANCE> groupedDataset,
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
	
	public static <KEY, INSTANCE, FEATURE> GroupedDataset<KEY, ListDataset<FEATURE>, FEATURE> extractFeature(GroupedDataset<KEY, ListDataset<INSTANCE>, INSTANCE> groupedDataset,
			FeatureExtractor<FEATURE, INSTANCE> featureExtractor) {
		
		GroupedDataset<KEY, ListDataset<FEATURE>, FEATURE> groupedFeatureDataset = new MapBackedDataset<>();
		for (KEY key : groupedDataset.getGroups()) {
			List<FEATURE> featureDataset = new ArrayList<>();
			Dataset<INSTANCE> dataset = groupedDataset.get(key);
			for (INSTANCE instance : dataset) {
				FEATURE feature = featureExtractor.extractFeature(instance);
				featureDataset.add(feature);
			}
			groupedFeatureDataset.put(key, new ListBackedDataset<FEATURE>(featureDataset));
		}
		
		return groupedFeatureDataset;
	}
	
	

}
