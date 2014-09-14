package edu.ubbcluj.emotion.dataset.ck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openimaj.data.dataset.ListBackedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.experiment.annotations.DatasetDescription;
import org.openimaj.image.FImage;
import org.openimaj.math.geometry.shape.Rectangle;

import edu.ubbcluj.emotion.ck.file.loader.FileResourceLoaderFactory;
import edu.ubbcluj.emotion.database.file.loader.ImageFilter;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoader;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoaderFactory;
import edu.ubbcluj.emotion.dataset.AbstractDataset;
import edu.ubbcluj.emotion.dataset.DatasetInformation;
import edu.ubbcluj.emotion.dataset.FacialFeature;
import edu.ubbcluj.emotion.dataset.HasFacialFeatures;
import edu.ubbcluj.emotion.dataset.HasInformation;
import edu.ubbcluj.emotion.model.DatasetKey;

@DatasetDescription(name = "CK+", creator = "Lucey, P., Cohn, J. F., Kanade, T., Saragih, J., Ambadar, Z., & Matthews, I.", url = "http://www.pitt.edu/~emotion/ck-spread.htm", description = "Version 2, referred to as CK+, includes both posed and non-posed (spontaneous) expressions "
		+ "and additional types of metadata. For posed expressions, the number of sequences is increased"
		+ " from the initial release by 22% and the number of subjects by 27%. As with the initial release,"
		+ " the target expression for each sequence is fully FACS coded. In addition validated emotion labels "
		+ "have been added to the metadata. Thus, sequences may be analyzed for both action units and prototypic"
		+ " emotions. The non-posed expressions are from Ambadar, Cohn, & Reed (2009). Additionally, CK+ provides "
		+ "protocols and baseline results for facial feature tracking and action unit and emotion recognition. "
		+ "Tracking results for shape and appearance are via the approach of Matthews & Baker (2004). For action"
		+ " unit and expression recognition, a linear support vector machine (SVM) classifier with leave-one-out subject"
		+ " cross-validation was used. Both sets of results are included with the metadata. For a full description of CK+,"
		+ " please see P. Lucey et al. (2010).")
public abstract class AbstractCKDataset<KEY extends DatasetKey> extends AbstractDataset<KEY> implements
		HasFacialFeatures, HasInformation<KEY> {

	private double[][]						matrixData;

	private DatasetInformation<KEY>			datasetInformation;

	private Map<FacialFeature, Rectangle>	facialFeaturesLocation	= new HashMap<>();

	public AbstractCKDataset() {
		DatasetInformation<KEY> information = getInformation();
		ResourceLoaderFactory rlf = new FileResourceLoaderFactory();
		ResourceLoader resourceLoader = rlf.getResourceLoader(getFolderName());

		for (KEY key : information.getGroups()) {
			List<FImage> images= resourceLoader.getImages(getImageFilter(key));
			put(key, new ListBackedDataset<FImage>(images));
		}
	}

	/**
	 * Transform the dataset to a matrix containing the images. The information
	 * about the groups is lost.
	 * 
	 * @return A double matrix where each column contains an image
	 */
	public double[][] getMatrixData() {
		if (matrixData == null) {
			loadMatrixData();
		}
		return matrixData;
	}

	private void loadMatrixData() {
		DatasetInformation<KEY> information = getInformation();
		int numberOfGroups = information.getNumberOfGroups();

		@SuppressWarnings("unchecked")
		ListDataset<FImage>[] lists = new ListDataset[numberOfGroups];
		int n = 0;
		int index = 0;
		for (KEY key : information.getGroups()) {
			lists[index] = getInstances(key);
			n += lists[index].size();
			index++;
		}

		int imageSize = information.getImageHeight() * information.getImageWidht();

		matrixData = new double[imageSize][n];
		int j = 0;
		for (int k = 0; k < lists.length; k++) {
			ListDataset<FImage> list = lists[k];
			for (int l = 0; l < list.size(); l++) {
				double[] vector = list.get(l).getDoublePixelVector();
				for (int i = 0; i < vector.length; i++) {
					matrixData[i][j] = vector[i];
				}
				j += 1;
			}
		}
	}
	
	@Override
	public Rectangle getFacialFeatureLocation(FacialFeature f) {
		Rectangle rectangle;
		if ((rectangle = facialFeaturesLocation.get(f)) == null) {
			rectangle = constructFacialFeatureLocationObject(f);
			facialFeaturesLocation.put(f, rectangle);
		}
		return rectangle;
	}
	
	@Override
	public DatasetInformation<KEY> getInformation() {
		if (datasetInformation == null) {
			datasetInformation = constructDatasetInformationObject();
		}
		return datasetInformation;
	}

	protected abstract ImageFilter getImageFilter(KEY key);

	protected abstract String getFolderName();
	
	protected abstract DatasetInformation<KEY> constructDatasetInformationObject();
	
	protected abstract Rectangle constructFacialFeatureLocationObject(FacialFeature f);
}
