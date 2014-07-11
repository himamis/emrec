package edu.ubbcluj.emotion.ck.file.manager;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubbcluj.emotion.ck.file.info.FileResourceInformationService;
import edu.ubbcluj.emotion.database.file.info.ResourceInformationService;
import edu.ubbcluj.emotion.database.file.manager.ActionUnitListManager;
import edu.ubbcluj.emotion.database.file.manager.EmotionManager;
import edu.ubbcluj.emotion.database.file.manager.ImageSequenceManager;
import edu.ubbcluj.emotion.database.file.manager.LandmarksManager;
import edu.ubbcluj.emotion.database.file.manager.ResourceManager;
import edu.ubbcluj.emotion.database.file.manager.ResourceManagerFactory;

public class FileResourceManagerFactory extends ResourceManagerFactory {

	private static final Logger					logger					= LoggerFactory.getLogger(FileResourceManagerFactory.class);

	private final Map<String, ResourceManager>	resourceManagerCache	= new HashMap<>();

	private ActionUnitListManager				actionUnitListManager;
	private EmotionManager						emotionManager;
	private ImageSequenceManager				imageSequenceManager;
	private LandmarksManager					landmarksManager;
	private ResourceInformationService			resourceInformationService;

	@Override
	public ResourceManager getResourceManager(final String folder) {
		final ActionUnitListManager actionUnitListManager = getActionUnitListManager();
		final EmotionManager emotionManager = getEmotionManager();
		final ImageSequenceManager imageSequenceManager = getImageSequenceManager();
		final LandmarksManager landmarksManager = getLandmarksManager();
		final ResourceInformationService resInfoService = getResourceInformationService();
		ResourceManager resourceManager = null;
		if ((resourceManager = resourceManagerCache.get(folder)) == null) {
			logger.debug("Creating new file resource manager");
			resourceManager = new ResourceManager(folder, actionUnitListManager, emotionManager, landmarksManager, imageSequenceManager,
					resInfoService);
			resourceManagerCache.put(folder, resourceManager);
		}
		return resourceManager;
	}

	private ActionUnitListManager getActionUnitListManager() {
		if (actionUnitListManager == null) {
			actionUnitListManager = new FileActionUnitListManager();
		}
		return actionUnitListManager;
	}

	private EmotionManager getEmotionManager() {
		if (emotionManager == null) {
			emotionManager = new FileEmotionManager();
		}
		return emotionManager;
	}

	private ImageSequenceManager getImageSequenceManager() {
		if (imageSequenceManager == null) {
			imageSequenceManager = new FileImageSequenceManager();
		}
		return imageSequenceManager;
	}

	private LandmarksManager getLandmarksManager() {
		if (landmarksManager == null) {
			landmarksManager = new FileLandmarksManager();
		}
		return landmarksManager;
	}

	private ResourceInformationService getResourceInformationService() {
		if (resourceInformationService == null) {
			resourceInformationService = new FileResourceInformationService();
		}
		return resourceInformationService;
	}

}
