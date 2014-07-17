package edu.ubbcluj.emotion.model;

import java.io.Serializable;

public enum Emotion implements Serializable, DatasetKey {
	// NEUTRAL(0),
	ANGER(1), CONTEMPT(2), DISGUST(3), FEAR(4), HAPPY(5), SADNESS(6), SUPRISE(7);

	private final int	code;

	private Emotion(int code) {
		this.code = code;
	}

	public int toInteger() {
		return code;
	}

	public static Emotion fromInteger(int code) {
		switch (code) {
		// case 0:
		// return NEUTRAL;
		case 1:
			return ANGER;
		case 2:
			return CONTEMPT;
		case 3:
			return DISGUST;
		case 4:
			return FEAR;
		case 5:
			return HAPPY;
		case 6:
			return SADNESS;
		case 7:
			return SUPRISE;
		default:
			return null;
		}
	}

}
