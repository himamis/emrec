package edu.ubbcluj.emotion.dataset;

import java.util.Set;

public class DatasetInformation<GROUP> {
	private int			imageWidht;
	private int			imageHeight;
	private boolean		isDifferenceImage;
	private boolean		hasLandmarks;
	private boolean		isAligned;
	private Set<GROUP>	groups;

	public DatasetInformation(int imageWidht, int imageHeight, boolean isDifferenceImage, boolean hasLandmarks, boolean isAligned, Set<GROUP> groups) {
		this.imageWidht = imageWidht;
		this.imageHeight = imageHeight;
		this.isDifferenceImage = isDifferenceImage;
		this.hasLandmarks = hasLandmarks;
		this.isAligned = isAligned;
		this.groups = groups;
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

	public int getNumberOfGroups() {
		return groups.size();
	}

	public Set<GROUP> getGroups() {
		return groups;
	}

}
