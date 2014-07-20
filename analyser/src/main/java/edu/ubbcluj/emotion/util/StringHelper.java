package edu.ubbcluj.emotion.util;

import edu.ubbcluj.emotion.dataset.FacialFeature;

public class StringHelper {
	public static String buildFacialFeaturesString(FacialFeature[] facialFeatures) {
		StringBuilder builder = new StringBuilder();
		for (FacialFeature feature : facialFeatures) {
			builder.append(feature);
			builder.append(", ");
		}
		if (builder.length() >= 2) {
			builder.setLength(builder.length() - 2);
		}
		return builder.toString();
	}

	public static String buildExperimentName(int index, HasName[] hasNames) {
		StringBuilder builder = new StringBuilder();
		for (HasName hasName : hasNames) {
			builder.append(hasName.getName() + "-");
		}
		if (builder.length() >= 1) {
			builder.setLength(builder.length() - 1);
		}
		builder.append(String.format("%03d", index));
		builder.append(".txt");
		return builder.toString();
	}
}
