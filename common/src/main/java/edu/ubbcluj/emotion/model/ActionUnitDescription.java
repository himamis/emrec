package edu.ubbcluj.emotion.model;

import java.util.HashMap;

abstract class ActionUnitDescription {
	private static HashMap<Integer, String>	m;

	public static String getAUDescription(int key) {
		if (m == null) {
			m = new HashMap<>();
			setUpMap();
		}
		return m.get(key);
	}

	private static void setUpMap() {
		m.put(1, "Inner Brow Raiser");
		m.put(2, "Outer Brow Raiser");
		m.put(4, "Brow Lowerer");
		m.put(5, "Upper Lid Raiser");
		m.put(6, "Cheek Raiser");
		m.put(7, "Lid Tightener");
		m.put(9, "Nose Wrinkler");
		m.put(10, "Upper Lip Raiser");
		m.put(11, "Nasolabial Deepener");
		m.put(12, "Lip Corner Puller");
		m.put(13, "Cheek Puffer");
		m.put(14, "Dimpler");
		m.put(15, "Lip Corner Depressor");
		m.put(16, "Lower Lip Depressor");
		m.put(17, "Chin Raiser");
		m.put(18, "Lip Puckerer");
		m.put(20, "Lip stretcher");
		m.put(22, "Lip Funneler");
		m.put(23, "Lip Tightener");
		m.put(24, "Lip Pressor");
		m.put(25, "Lips part");
		m.put(26, "Jaw Drop");
		m.put(27, "Mouth Stretch");
		m.put(28, "Lip Suck");
		m.put(41, "Lid droop");
		m.put(42, "Slit");
		m.put(43, "Eyes Closed");
		m.put(44, "Squint");
		m.put(51, "Head turn left");
		m.put(52, "Head turn right");
		m.put(53, "Head up");
		m.put(54, "Head down");
		m.put(55, "Head tilt left");
		m.put(56, "Head tilt right");
		m.put(57, "Head forward");
		m.put(58, "Head back");
		m.put(61, "Eyes turn left");
		m.put(62, "Eyes turn right");
		m.put(63, "Eyes up");
		m.put(64, "Eyes down");
	}
}
