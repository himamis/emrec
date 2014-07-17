package edu.ubbcluj.emotion.dataset;

import java.util.Map;
import java.util.Set;

public class DatasetInformation<GROUP> {
	private int					imageWidht;
	private int					imageHeight;
	private boolean				isDifferenceImage;
	private boolean				hasLandmarks;
	private boolean				isAligned;
	private int					numberOfGroups;
	private Map<GROUP, Integer>	numberOfInstancesPerGroup;
	private Set<GROUP>			groups;

	public DatasetInformation(int imageWidht, int imageHeight, boolean isDifferenceImage, boolean hasLandmarks, boolean isAligned,
			Map<GROUP, Integer> numberOfInstancesPerGroup) {
		super();
		this.imageWidht = imageWidht;
		this.imageHeight = imageHeight;
		this.isDifferenceImage = isDifferenceImage;
		this.hasLandmarks = hasLandmarks;
		this.isAligned = isAligned;
		this.numberOfInstancesPerGroup = numberOfInstancesPerGroup;
		this.numberOfGroups = this.numberOfInstancesPerGroup.keySet().size();
		this.groups = this.numberOfInstancesPerGroup.keySet();
	}

	public int getImageWidht() {
		return imageWidht;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public boolean isDifferenceImage() {
		return isDifferenceImage;
	}

	public boolean hasLandmarks() {
		return hasLandmarks;
	}

	public boolean isAligned() {
		return isAligned;
	}

	public Map<GROUP, Integer> getNumberOfInstancesPerGroup() {
		return numberOfInstancesPerGroup;
	}

	public int getNumberOfGroups() {
		return numberOfGroups;
	}

	public Set<GROUP> getGroups() {
		return groups;
	}

}
