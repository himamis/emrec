package ck.database.editor.util;

import java.util.ArrayList;
import java.util.List;

import edu.ubbcluj.emotion.model.Landmarks;
import edu.ubbcluj.emotion.model.LandmarksSequence;
import edu.ubbcluj.emotion.model.Sequence;
import edu.ubbcluj.emotion.model.Subject;

public class MeanValueCalculator {

	private List<Subject>	anim	= new ArrayList<Subject>();

	public MeanValueCalculator(String folder) {
		/*
		 * String dir = StringUtil .buildBaseFolderName(folder); File f = new
		 * File(dir.concat(str))
		 */
		/*
		 * ResourceManager man = ResourceManager.getInstance(); Lister l = new
		 * Lister(folder); for (Integer i : l.listSubjectIndices()) {
		 * anim.add(man.loadSubject(folder, i, ResourceManager.LANDMARKS)); }
		 */
	}

	/**
	 * Calculates the mean values of the landmark points specified by the
	 * indices taking into consideration only the first image
	 * 
	 * @param indices
	 *            the indices to calculate mean values
	 * @return mean values
	 */
	public float[][] calculateMeanValuesForIndices(int[] indices) {
		// create return value and fill with 0's
		float[][] result = new float[indices.length][2];
		for (int i = 0; i < result.length; i++) {
			result[i][0] = 0;
			result[i][1] = 0;
		}
		float n = 0;

		for (int i = 0; i < anim.size(); i++) {
			Subject subject = anim.get(i);
			for (int k = 0; k < subject.size(); k++) {
				Sequence a = subject.get(k);
				LandmarksSequence l = a.getLandmarksSequence();
				Landmarks f = l.get(0);
				n++;
				for (int j = 0; j < indices.length; j++) {
					result[j][0] += f.get(indices[j]).getX();
					result[j][1] += f.get(indices[j]).getY();
				}
			}
		}

		for (int j = 0; j < result.length; j++) {
			result[j][0] /= n;
			result[j][1] /= n;
		}

		return result;
	}

	public float[] calculateAveragePoint(float[][] values) {
		float[] ret = new float[2];
		ret[0] = ret[1] = 0;
		for (int i = 0; i < values.length; i++) {
			ret[0] += values[i][0];
			ret[1] += values[i][1];
		}
		ret[0] /= values.length;
		ret[1] /= values.length;
		return ret;
	}

}
