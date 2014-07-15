package edu.ubbcluj.emotion;

import org.fastica.FastICA;
import org.fastica.FastICAException;
import org.fastica.math.Matrix;

import edu.ubbcluj.emotion.ck.file.loader.FileResourceLoaderFactory;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoader;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoaderFactory;

public class FastICATest {
	
	private static final String database = "openimaj_folder_small";

	public static void main(String[] args) {
		ResourceLoaderFactory rlf = new FileResourceLoaderFactory();
		ResourceLoader resourceLoader = rlf.getResourceLoader(database);

		System.out.println("Creating data provider");
		DataProvider dataProvider = new DataProvider(resourceLoader);

		double[][] data = dataProvider.getMatrixData();
		double[][] transposedData = Matrix.transpose(data);
		
		try {
			System.out.println("Start FastICA");
			FastICA fica = new FastICA(transposedData, 200);
			double[][] ic = fica.getICVectors();
			System.out.println("Stop FastICA");
		} catch (FastICAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
