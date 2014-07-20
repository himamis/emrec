package edu.ubbcluj.emotion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.davidsoergel.conja.Function;
import com.davidsoergel.conja.Parallel;

import edu.ubbcluj.emotion.ck.file.info.FileDatabaseInformationFactory;
import edu.ubbcluj.emotion.ck.file.manager.FileImageSequenceManager;
import edu.ubbcluj.emotion.ck.file.manager.FileLandmarksManager;
import edu.ubbcluj.emotion.ck.file.manager.FileResourceManagerFactory;
import edu.ubbcluj.emotion.database.file.info.DatabaseInformationFactory;
import edu.ubbcluj.emotion.database.file.info.DatabaseInformationService;
import edu.ubbcluj.emotion.database.file.manager.ImageSequenceManager;
import edu.ubbcluj.emotion.database.file.manager.LandmarksManager;
import edu.ubbcluj.emotion.database.file.manager.ResourceManager;
import edu.ubbcluj.emotion.database.file.manager.ResourceManagerFactory;
import edu.ubbcluj.emotion.editor.SequenceEditor;
import edu.ubbcluj.emotion.model.Image;
import edu.ubbcluj.emotion.model.ImageSequence;
import edu.ubbcluj.emotion.model.Landmarks;
import edu.ubbcluj.emotion.model.LandmarksSequence;
import edu.ubbcluj.emotion.model.Sequence;
import edu.ubbcluj.emotion.model.Subject;

public class ImageSequenceEditorRunner {

	private static final Logger	logger	= LoggerFactory.getLogger(ImageSequenceEditorRunner.class);

	public static void runEditor(String sourceFolder, String destinationFolder, final SequenceEditor editor) {
		ResourceManagerFactory rmf = new FileResourceManagerFactory();
		DatabaseInformationFactory dif = new FileDatabaseInformationFactory();

		ResourceManager rm = rmf.getResourceManager(sourceFolder);
		ResourceManager.USE_CACHE = false; // do not use caching
		DatabaseInformationService dis = dif.getDatabaseInformationService(sourceFolder);

		if (editor.inputsLandmarks() && !dis.hasLandmarks()) {
			throw new RuntimeException(sourceFolder + " database lacks landmarks");
		}

		boolean saveLandmarks = (dis.hasLandmarks() && editor.outputsLandmarks());
		boolean loadLandmarks = editor.inputsLandmarks() || saveLandmarks;

		ImageSequenceManager ism = new FileImageSequenceManager();
		LandmarksManager lmm = new FileLandmarksManager();

		for (String subject : dis.getSubjects()) {
			int flags = ResourceManager.IMAGE_SEQUENCE;
			if (loadLandmarks) {
				flags = flags | ResourceManager.LANDMARKS;
			}
			Subject sub = rm.loadSubject(subject, flags);
			for (final Sequence sequence : sub) {
				editor.setUp(sequence);
				final ImageSequence imageSequence = sequence.getImageSequence();
				final LandmarksSequence landmarksSequence = sequence.getLandmarksSequence();
				Parallel.forEach(imageSequence.size(), new Function<Integer, Void>() {

					@Override
					public Void apply(Integer i) {
						Image image = imageSequence.get(i);
						Landmarks landmarks = null;
						if (landmarksSequence != null) {
							landmarks = landmarksSequence.get(i);
						}
						editor.doEdit(image, landmarks);
						return null;
					}
				});

				ism.saveImageSequence(destinationFolder, imageSequence, subject, sequence.getSequence());
				if (saveLandmarks) {
					lmm.saveLandmarks(destinationFolder, landmarksSequence, subject, sequence.getSequence());
				}
			}
			logger.info("Subject " + subject + " saved");
		}
	}

}
