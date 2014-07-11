package edu.ubbcluj.emotion.ck.file.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubbcluj.emotion.ck.file.info.FileDatabaseInformationFactory;
import edu.ubbcluj.emotion.ck.file.manager.FileResourceManagerFactory;
import edu.ubbcluj.emotion.database.file.info.DatabaseInformationFactory;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoader;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoaderFactory;
import edu.ubbcluj.emotion.database.file.manager.ResourceManagerFactory;

public class FileResourceLoaderFactory extends ResourceLoaderFactory {

	private static final Logger logger = LoggerFactory.getLogger(FileResourceLoaderFactory.class);
	
	@Override
	public ResourceLoader getResourceLoader(final String folder) {
		logger.debug("Creating a new file resource loader");
		DatabaseInformationFactory databaseInformationFactory = new FileDatabaseInformationFactory();
		ResourceManagerFactory resourceManagerFactory = new FileResourceManagerFactory();
		return new FileResourceLoader(folder, databaseInformationFactory, resourceManagerFactory);
	}

}
