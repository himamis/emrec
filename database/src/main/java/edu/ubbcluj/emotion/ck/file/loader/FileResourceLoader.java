package edu.ubbcluj.emotion.ck.file.loader;

import java.util.ArrayList;
import java.util.List;

import org.openimaj.image.FImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubbcluj.emotion.database.file.info.DatabaseInformationFactory;
import edu.ubbcluj.emotion.database.file.info.DatabaseInformationService;
import edu.ubbcluj.emotion.database.file.loader.ImageFilter;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoader;
import edu.ubbcluj.emotion.database.file.manager.ResourceManager;
import edu.ubbcluj.emotion.database.file.manager.ResourceManagerFactory;
import edu.ubbcluj.emotion.model.ActionUnitSet;
import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.model.ImageSequence;
import edu.ubbcluj.emotion.model.LandmarksSequence;

public class FileResourceLoader implements ResourceLoader {

	private static final Logger					logger	= LoggerFactory.getLogger(FileResourceLoader.class);

	private final ResourceManager				resourceManager;
	private final DatabaseInformationService	resourceInfo;

	public FileResourceLoader(final String folder, final DatabaseInformationFactory resInfoFactory, final ResourceManagerFactory resManagerFactory) {
		this.resourceManager = resManagerFactory.getResourceManager(folder);
		this.resourceInfo = resInfoFactory.getDatabaseInformationService(folder);
	}

	@Override
	public List<FImage> getImages(ImageFilter filter) {

		List<FImage> filteredImages = new ArrayList<>();

		String[] subjects = resourceInfo.getSubjects();
		for (String subject : subjects) {
			String[] sequences = resourceInfo.getSequences(subject);
			for (String sequence : sequences) {
				ImageSequence imageSequence = resourceManager.loadImageSequence(subject, sequence);
				Emotion emotion = resourceManager.loadEmotion(subject, sequence);
				LandmarksSequence landmarks = resourceManager.loadLandmarks(subject, sequence);
				ActionUnitSet actionUnitList = resourceManager.loadActionUnitList(subject, sequence);
				for (int i = 0; i < imageSequence.size(); i++) {
					boolean isLastImage = i == imageSequence.size() - 1;
					if (filter.filter(emotion, landmarks, actionUnitList, isLastImage, i)) {
						logger.debug(subject + " " + sequence + " added");
						FImage image = imageSequence.get(i).getImage();
						filteredImages.add(image);
					}
				}
			}
		}
		if (filter.filter() == ImageFilter.FIRST_HALF) {
			logger.debug("first half filtered");
			filteredImages = filteredImages.subList(0, filteredImages.size() / 2);
		} else if (filter.filter() == ImageFilter.SECOND_HALF) {
			logger.debug("second half filtered");
			filteredImages = filteredImages.subList(filteredImages.size() / 2, filteredImages.size());
		}
		return filteredImages;
	}
}
