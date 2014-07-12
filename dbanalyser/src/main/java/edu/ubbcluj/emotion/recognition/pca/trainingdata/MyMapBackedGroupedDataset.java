package edu.ubbcluj.emotion.recognition.pca.trainingdata;

import org.openimaj.data.dataset.ListBackedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.data.dataset.MapBackedDataset;
import org.openimaj.image.FImage;

import edu.ubbcluj.emotion.model.Emotion;

public class MyMapBackedGroupedDataset extends MapBackedDataset<Emotion, ListDataset<FImage>, FImage> {
	
	public ListDataset<FImage> getAllInstances() {
		ListDataset<FImage> allInstances = new ListBackedDataset<FImage>();
		for (Emotion emotion : Emotion.values()) {
			ListDataset<FImage> instances = getInstances(emotion);
			allInstances.addAll(instances);
		}
		return allInstances;
	}
}
