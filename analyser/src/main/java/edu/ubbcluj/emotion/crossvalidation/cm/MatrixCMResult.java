package edu.ubbcluj.emotion.crossvalidation.cm;

import gov.sandia.cognition.learning.performance.categorization.ConfusionMatrix;
import gov.sandia.cognition.learning.performance.categorization.DefaultConfusionMatrix;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.openimaj.experiment.evaluation.classification.analysers.confusionmatrix.CMResult;

import com.bethecoder.ascii_table.ASCIITable;

public class MatrixCMResult<CLASS>  {
	
	private List<CMResult<CLASS>> matrices;
	
	public MatrixCMResult(List<CMResult<CLASS>> matrices) {
		this.matrices = matrices;
	}

	public ConfusionMatrix<CLASS> getAggregatedCM() {
		ConfusionMatrix<CLASS> matrix = new DefaultConfusionMatrix<>();
		for (CMResult<CLASS> cmResult : matrices) {
			ConfusionMatrix<CLASS> cm = cmResult.getMatrix();
			matrix.addAll(cm);
		}
		return matrix;
	}

	private String getConfusionMatrix() {
		ConfusionMatrix<CLASS> matrix = getAggregatedCM();
		List<CLASS> categories = new ArrayList<>(matrix.getCategories());
		int n = categories.size();
		String[] header = new String[n + 1];
		String data[][] = new String[n][n + 1];
		header[0] = "";
		int k = 1;
		for (CLASS category : categories) {
			String categoryHeader = category.toString();
			header[k] = categoryHeader;
			data[k - 1][0] = categoryHeader;
			k++;
		}
		DecimalFormat df = new DecimalFormat("#.##");
		for (int i = 0; i < n; i++) {
			CLASS actual = categories.get(i);
			double actualCount = matrix.getActualCount(actual);
			for (int j = 0; j < n; j++) {
				CLASS predicted = categories.get(j);
				double count = matrix.getCount(actual, predicted);
				data[i][j + 1] = df.format((count / actualCount));
			}
		}
		return ASCIITable.getInstance().getTable(header, data);
	}
	
	@Override
	public String toString() {
		return getSummaryReport();
	}
	
	public String getSummaryReport() {
		return getConfusionMatrix();
	}

}
