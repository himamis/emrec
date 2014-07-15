package edu.ubbcluj.emotion;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.processing.face.alignment.AffineAligner;
import org.openimaj.image.processing.face.detection.FaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;

import com.davidsoergel.conja.Function;
import com.davidsoergel.conja.Parallel;

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
import edu.ubbcluj.emotion.util.OpenImajUtil;

public class StartApp {

	public static boolean	DEBUG					= true;

	public static String	original_folder			= Constants.ORIGINAL_FOLDER;

	public static String	openimaj_folder			= "openimaj_folder";
	public static String	openimaj_diff1			= "openimajdiff1";
	public static String	openimaj_diff2			= "openimajdiff2";

	public static String	openimaj_folder_small	= "openimaj_folder_detect";

	public static void main(String[] args) {
		ResourceManagerFactory rmf = new FileResourceManagerFactory();
		DatabaseInformationFactory dif = new FileDatabaseInformationFactory();

		ResourceManager rm = rmf.getResourceManager(original_folder);
		ResourceManager.USE_CACHE = false; // do not use caching
		DatabaseInformationService dis = dif.getDatabaseInformationService(original_folder);

		ImageSequenceManager ism = new FileImageSequenceManager();
		
		final AffineAligner aligner = new AffineAligner(50, 70, 0.225f);
		
		final FaceDetector<KEDetectedFace, FImage> d = new FKEFaceDetector();

		for (String subject : dis.getSubjects()) {
			Subject sub = rm.loadSubject(subject, ResourceManager.IMAGE_SEQUENCE | ResourceManager.LANDMARKS);
			for (final Sequence sequence : sub) {
				final ImageSequence imageSequence = sequence.getImageSequence();
				Parallel.forEach(imageSequence.size(), new Function<Integer, Void>() {
					
					@Override
					public Void apply(Integer i) {
						FImage faceImage = imageSequence.get(i).getFImage();
						
						KEDetectedFace face = d.detectFaces(faceImage)
								.get(0);
						FImage alignedFace = aligner.align(face);
						Image image = imageSequence.get(i);
						image.setBufferedImage(ImageUtilities.createBufferedImage(alignedFace));
						return null;
					}
				});

				ism.saveImageSequence(openimaj_folder_small, imageSequence, subject, sequence.getSequence());
			}
			System.out.println("Subject " + subject + " saved");
		}
	}
}
