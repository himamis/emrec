package edu.ubbcluj.emotion.database.file.info;

import edu.ubbcluj.emotion.util.Constants;

public abstract class DatabaseInformationFactory {
	/**
	 * It will return a {@code DatabaseInformationService} object,
	 * injencting the necessary dependencies.
	 * 
	 * @param folder the folder to load the images
	 * @return a DatabaseInformationService implementation
	 */
	public abstract DatabaseInformationService getDatabaseInformationService(final String folder);
	
	/**
	 * Returns an array containing the available databases.
	 * These will be folder names relative to
	 * {@link Constants#BASE_FOLDER}.
	 * 
	 * @return the array containing the folder names
	 */
	public abstract String[] listDatabases();
	
}
