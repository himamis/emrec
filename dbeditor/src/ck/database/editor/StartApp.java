package ck.database.editor;

import java.awt.image.BufferedImage;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.processing.face.alignment.AffineAligner;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;

import ck.database.editor.util.OpenImajUtil;
import edu.ubbcluj.emotion.ck.file.info.FileDatabaseInformationFactory;
import edu.ubbcluj.emotion.ck.file.manager.FileImageSequenceManager;
import edu.ubbcluj.emotion.ck.file.manager.FileResourceManagerFactory;
import edu.ubbcluj.emotion.database.file.info.DatabaseInformationFactory;
import edu.ubbcluj.emotion.database.file.info.DatabaseInformationService;
import edu.ubbcluj.emotion.database.file.manager.ImageSequenceManager;
import edu.ubbcluj.emotion.database.file.manager.ResourceManager;
import edu.ubbcluj.emotion.database.file.manager.ResourceManagerFactory;
import edu.ubbcluj.emotion.model.Image;
import edu.ubbcluj.emotion.model.ImageSequence;
import edu.ubbcluj.emotion.model.Sequence;
import edu.ubbcluj.emotion.model.Subject;
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

	public static String	aligned_meshwarp	= "aligned_meshwarp";

	public static void main(String[] args) {
		ResourceManagerFactory rmf = new FileResourceManagerFactory();
		DatabaseInformationFactory dif = new FileDatabaseInformationFactory();

		ResourceManager rm = rmf.getResourceManager(orig_folder);
		ResourceManager.USE_CACHE = false; // do not use caching
		DatabaseInformationService dis = dif.getDatabaseInformationService(orig_folder);

		ImageSequenceManager ism = new FileImageSequenceManager();

		for (String subject : dis.getSubjects()) {
			Subject sub = rm.loadSubject(subject, ResourceManager.LANDMARKS | ResourceManager.IMAGE_SEQUENCE);
			for (Sequence sequence : sub) {
				ImageSequence imageSequence = sequence.getImageSequence();
				for (int i = 0; i < imageSequence.size(); i++) {
					KEDetectedFace face = OpenImajUtil.fromSequenceImage(sequence, i);
					AffineAligner aligner = new AffineAligner(80, 100, 0.225f);
					FImage image = aligner.align(face);
					BufferedImage img = ImageUtilities.createBufferedImage(image);
					imageSequence.set(i, new Image(img));
				}
				ism.saveImageSequence(aligned_meshwarp, imageSequence, subject, sequence.getSequence());
			}
		}
	}
}
