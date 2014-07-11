package ck.database.editor;

import edu.ubbcluj.emotion.util.Constants;

public class StartApp {

	public static boolean	DEBUG				= true;

	public static int		height				= 600;
	public static int		width				= 600;

	public static String	folder				= "temporary";
	public static String	folder2				= "registeredCroppedImages";

	public static String	orig_folder			= Constants.ORIGINAL_FOLDER;
	public static String	cropped_folder		= "cropped";
	public static String	registered_folder	= "cropped_registered";
	public static String	padded_folder		= "cropped_registered_padded";
	public static String	resized_folder		= "cropped_registered_padded_resized";
	public static String	finalised_folder	= "finalised";

	public static String	openimaj_folder		= "openimaj_folder";
	public static String	openimaj_diff1		= "openimajdiff1";
	public static String	openimaj_diff2		= "openimajdiff2";

	/*
	 * public static void main(String[] args) { Lister l = new
	 * Lister(orig_folder); ResourceManager rm = ResourceManager.getInstance();
	 * 
	 * List<Integer> subjs = l.listSubjectIndices();
	 * 
	 * AffineAligner ali = new AffineAligner(80, 110, 0.225f); for (int i = 0; i
	 * < subjs.size(); i++) { int sub = subjs.get(i); Subject s =
	 * rm.loadSubject(Constants.ORIGINAL_FOLDER, sub,
	 * ResourceManager.IMAGE_SEQUENCE | ResourceManager.LANDMARKS);
	 * System.out.println("Subject loaded " + sub); for (int j = 0; j <
	 * s.getSequences().size(); j++) { Sequence a = s.getSequence(j);
	 * List<BufferedImage> images = a.getImageSequence().getImages(); for (int k
	 * = 0; k < a.getImageSequence().getSize(); k++) { KEDetectedFace face =
	 * OpenImajUtil.fromSequenceImage(a, k); FImage f = ali.align(face);
	 * BufferedImage image = ImageUtilities.createBufferedImage(f);
	 * BufferedImage gray = ImageUtil.convertToGrayscale(image); images.set(k,
	 * gray); } } rm.saveSubject("openimaj_folder", s);
	 * System.out.println("Subject saved" + sub); }
	 * 
	 * }
	 */
	/*
	 * public static void main(String[] args) { /** Calculate Mean values for
	 * left and right eyes
	 */
	/*
		*//*
		 * Lister l = new Lister(folder); ResourceManager rm =
		 * ResourceManager.getInstance();
		 * 
		 * List<Integer> subjs = l.listSubjectIndices();
		 * 
		 * ImageManipulator imp = new ImageManipulator(Constants.COORD_LEFT_EYE,
		 * Constants.COORD_RIGHT_EYE); Padder pad = new Padder(width, height);
		 * /*Cropper crop = new Cropper(CroppingMode.LANDMARKS);
		 * 
		 * 
		 * for (int i = 0; i < subjs.size(); i++) { int sub = subjs.get(i);
		 * Subject s = rm.loadSubject(Constants.ORIGINAL_FOLDER, sub,
		 * ResourceManager.IMAGE_SEQUENCE | ResourceManager.LANDMARKS);
		 * System.out.println("Subject loaded " + sub); for (int j = 0; j <
		 * s.getSequences().size(); j++) { Sequence a = s.getSequence(j);
		 * List<BufferedImage> images = a.getImageSequence().getImages();
		 * List<List<Point2D.Float>> landmarks =
		 * a.getLandmarks().getLandmarks(); crop.cropImages(images, landmarks);
		 * } rm.saveSubject("temporary", s); System.out.println("Subject saved"
		 * + sub); }
		 */
	/*
	 * MeanValueCalculator mv = new MeanValueCalculator(folder); float[][] val_l
	 * = mv.calculateMeanValuesForIndices(Constants.COORD_LEFT_EYE); float[][]
	 * val_r = mv.calculateMeanValuesForIndices(Constants.COORD_RIGHT_EYE);
	 * 
	 * float[] avgval_l = mv.calculateAveragePoint(val_l); float[] avgval_r =
	 * mv.calculateAveragePoint(val_r);
	 * 
	 * float[][] ref = new float[2][2]; ref[0][0] = avgval_l[0]; ref[0][1] =
	 * avgval_l[1]; ref[1][0] = avgval_r[0]; ref[1][1] = avgval_r[1];
	 * 
	 * for (int i = 0; i < subjs.size(); i++) { int sub = subjs.get(i); Subject
	 * s = rm.loadSubject(folder, sub, ResourceManager.IMAGE_SEQUENCE |
	 * ResourceManager.LANDMARKS); System.out.println("Subject " + sub +
	 * " loaded"); for (int j = 0; j < s.getSequences().size(); j++) { Sequence
	 * a = s.getSequence(j); List<BufferedImage> images =
	 * a.getImageSequence().getImages(); List<List<Point2D.Float>> landmarks =
	 * a.getLandmarks().getLandmarks(); //crop.cropImages(images, landmarks);
	 * imp.registerImages(ref, images, landmarks); //pad.padImages(images);
	 * imp.toString(); } rm.saveSubject(folder2, s);
	 * System.out.println("Subject " + sub + " saved"); } }
	 */
	/*
	 * public static void main(String[] args) { MeanValueCalculator mv = new
	 * MeanValueCalculator(folder); float[][] val_l =
	 * mv.calculateMeanValuesForIndices(Constants.COORD_LEFT_EYE); float[][]
	 * val_r = mv.calculateMeanValuesForIndices(Constants.COORD_RIGHT_EYE);
	 * 
	 * float[] avgval_l = mv.calculateAveragePoint(val_l); float[] avgval_r =
	 * mv.calculateAveragePoint(val_r);
	 * 
	 * float[][] ref = new float[2][2]; ref[0][0] = avgval_l[0]; ref[0][1] =
	 * avgval_l[1]; ref[1][0] = avgval_r[0]; ref[1][1] = avgval_r[1];
	 * 
	 * Lister l = new Lister(folder); ResourceManager rm =
	 * ResourceManager.getInstance();
	 * 
	 * List<Integer> subjs = l.listSubjectIndices(); ImageManipulator imp = new
	 * ImageManipulator(Constants.COORD_LEFT_EYE, Constants.COORD_RIGHT_EYE);
	 * 
	 * 
	 * for (int i = 0; i < subjs.size(); i++) { int sub = subjs.get(i); Subject
	 * s = rm.loadSubject(folder, sub, ResourceManager.IMAGE_SEQUENCE |
	 * ResourceManager.LANDMARKS); System.out.println("Subject " + sub +
	 * " loaded"); for (int j = 0; j < s.getSequences().size(); j++) { Sequence
	 * a = s.getSequence(j); List<BufferedImage> images =
	 * a.getImageSequence().getImages(); List<List<Point2D.Float>> landmarks =
	 * a.getLandmarks().getLandmarks(); //crop.cropImages(images, landmarks);
	 * imp.registerImages(ref, images, landmarks); //imp.toString(); }
	 * rm.saveSubject(folder2, s); System.out.println("Subject " + sub +
	 * " saved"); } }
	 */
	/*
	 * public static void main(String[] args) { DatabaseInfo info = new
	 * DatabaseInfo("temporary"); int[] sizes = info.getMaxWidhtHeight();
	 * System.out.println(sizes[0] + " " + sizes[1]); }
	 */
	/*
	 * public static void main(String[] args) { Lister l = new
	 * Lister(orig_folder); ResourceManager rm = ResourceManager.getInstance();
	 * 
	 * List<Integer> subjs = l.listSubjectIndices();
	 * 
	 * Cropper crop = new Cropper(CroppingMode.LANDMARKS);
	 * 
	 * for (int i = 0; i < subjs.size(); i++) { int sub = subjs.get(i); Subject
	 * s = rm.loadSubject(orig_folder, sub, ResourceManager.IMAGE_SEQUENCE |
	 * ResourceManager.LANDMARKS); System.out.println("Subject " + sub +
	 * " loaded"); for (int j = 0; j < s.getSequences().size(); j++) { Sequence
	 * a = s.getSequence(j); List<BufferedImage> images =
	 * a.getImageSequence().getImages(); List<List<Point2D.Float>> landmarks =
	 * a.getLandmarks().getLandmarks(); crop.cropImages(images, landmarks);
	 * //pad.padImages(images); } rm.saveSubject(cropped_folder, s);
	 * System.out.println("Subject " + sub + " saved"); } }
	 */
	/*
	 * public static void main(String[] args) { MeanValueCalculator mv = new
	 * MeanValueCalculator(cropped_folder); float[][] val_l =
	 * mv.calculateMeanValuesForIndices(Constants.COORD_LEFT_EYE); float[][]
	 * val_r = mv.calculateMeanValuesForIndices(Constants.COORD_RIGHT_EYE);
	 * 
	 * float[] avgval_l = mv.calculateAveragePoint(val_l); float[] avgval_r =
	 * mv.calculateAveragePoint(val_r);
	 * 
	 * float[][] ref = new float[2][2]; ref[0][0] = avgval_l[0]; ref[0][1] =
	 * avgval_l[1]; ref[1][0] = avgval_r[0]; ref[1][1] = avgval_r[1];
	 * 
	 * Lister l = new Lister(cropped_folder); ResourceManager rm =
	 * ResourceManager.getInstance();
	 * 
	 * List<Integer> subjs = l.listSubjectIndices(); ImageManipulator imp = new
	 * ImageManipulator(Constants.COORD_LEFT_EYE, Constants.COORD_RIGHT_EYE);
	 * 
	 * for (int i = 0; i < subjs.size(); i++) { int sub = subjs.get(i); Subject
	 * s = rm.loadSubject(cropped_folder, sub, ResourceManager.IMAGE_SEQUENCE |
	 * ResourceManager.LANDMARKS); System.out.println("Subject " + sub +
	 * " loaded"); for (int j = 0; j < s.getSequences().size(); j++) { Sequence
	 * a = s.getSequence(j); List<BufferedImage> images =
	 * a.getImageSequence().getImages(); List<List<Point2D.Float>> landmarks =
	 * a.getLandmarks().getLandmarks(); imp.registerImages(ref, images,
	 * landmarks); } rm.saveSubject(registered_folder, s);
	 * System.out.println("Subject " + sub + " saved"); } }
	 *//*
		 * public static void main(String[] args) { Lister l = new
		 * Lister(registered_folder); ResourceManager rm =
		 * ResourceManager.getInstance();
		 * 
		 * List<Integer> subjs = l.listSubjectIndices(); DatabaseInfo info = new
		 * DatabaseInfo(registered_folder); int[] dim =
		 * info.getMaxWidhtHeight();
		 * 
		 * 
		 * Padder p = new Padder(dim[0], dim[1]); for (int i = 0; i <
		 * subjs.size(); i++) { int sub = subjs.get(i); Subject s =
		 * rm.loadSubject(registered_folder, sub, ResourceManager.IMAGE_SEQUENCE
		 * | ResourceManager.LANDMARKS); System.out.println("Subject " + sub +
		 * " loaded"); for (int j = 0; j < s.getSequences().size(); j++) {
		 * Sequence a = s.getSequence(j); List<BufferedImage> images =
		 * a.getImageSequence().getImages(); p.padImages(images); }
		 * rm.saveSubject(padded_folder, s); System.out.println("Subject " + sub
		 * + " saved"); } }
		 */
	/*
	 * public static void main(String[] args) { Lister l = new
	 * Lister(resized_folder); ResourceManager rm =
	 * ResourceManager.getInstance();
	 * 
	 * List<Integer> subjs = l.listSubjectIndices();
	 * 
	 * Finaliser finaliser = new Finaliser();
	 * 
	 * for (int i = 0; i < subjs.size(); i++) { int sub = subjs.get(i); Subject
	 * s = rm.loadSubject(resized_folder, sub, ResourceManager.IMAGE_SEQUENCE |
	 * ResourceManager.LANDMARKS); System.out.println("Subject " + sub +
	 * " loaded"); for (int j = 0; j < s.getSequences().size(); j++) { Sequence
	 * a = s.getSequence(j); List<BufferedImage> images =
	 * a.getImageSequence().getImages(); finaliser.finaliseImages(images); }
	 * rm.saveSubject(finalised_folder, s); System.out.println("Subject " + sub
	 * + " saved"); } }
	 */

	public static void main(String[] args) {
		/*
		 * Lister l = new Lister(openimaj_folder); ResourceManager rm =
		 * ResourceManager.getInstance();
		 * 
		 * List<Integer> subjs = l.listSubjectIndices();
		 * 
		 * Finaliser finaliser = new Finaliser();
		 * 
		 * for (int i = 0; i < subjs.size(); i++) { int sub = subjs.get(i);
		 * Subject s = rm.loadSubject(openimaj_folder, sub,
		 * ResourceManager.IMAGE_SEQUENCE); System.out.println("Subject " + sub
		 * + " loaded"); for (int j = 0; j < s.getSequences().size(); j++) {
		 * Sequence a = s.get(j); List<BufferedImage> images =
		 * a.getImageSequence().getImages(); finaliser.finaliseImages(images); }
		 * rm.saveSubject(openimaj_diff2, s); System.out.println("Subject " +
		 * sub + " saved"); }
		 */
	}
}
