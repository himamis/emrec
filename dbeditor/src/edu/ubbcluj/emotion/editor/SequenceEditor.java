package edu.ubbcluj.emotion.editor;

import edu.ubbcluj.emotion.model.Image;
import edu.ubbcluj.emotion.model.Landmarks;
import edu.ubbcluj.emotion.model.Sequence;

public interface SequenceEditor {
	
	public void setUp(Sequence sequence);

	public void doEdit(Image image, Landmarks landmarks);

	public boolean outputsLandmarks();
	
	public boolean inputsLandmarks();

}
