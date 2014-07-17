package edu.ubbcluj.emotion;

import org.fastica.math.Matrix;
import org.openimaj.image.FImage;

import edu.ubbcluj.emotion.ck.file.loader.FileResourceLoaderFactory;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoader;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoaderFactory;
import edu.ubbcluj.emotion.fastica.FastICA;
import edu.ubbcluj.emotion.util.ImageUtil;

public class FastICATest {
	
	private static final String database = "openimaj_small3";

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
			double[][] tr = Matrix.transpose(ic);
			ImageUtil.showImage(tr[0], 50);
			ImageUtil.showImage(tr[1], 50);
			ImageUtil.showImage(tr[2], 50);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
