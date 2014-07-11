package edu.ubbcluj.emotion.ck.file.info;

import java.util.HashMap;
import java.util.Map;

import edu.ubbcluj.emotion.database.file.info.DatabaseInformationFactory;
import edu.ubbcluj.emotion.database.file.info.DatabaseInformationService;
import edu.ubbcluj.emotion.database.file.info.ResourceInformationService;

public class FileDatabaseInformationFactory extends DatabaseInformationFactory {

	private ResourceInformationService						resourceInformationService;

	private final Map<String, DatabaseInformationService>	databaseInformationServiceCache	= new HashMap<>();

	@Override
	public DatabaseInformationService getDatabaseInformationService(final String folder) {
		return getDatabaseInformationServiceCache(folder);
	}

	@Override
	public String[] listDatabases() {
		final ResourceInformationService resInfo = getResourceInformationService();
		return resInfo.listDatabases();
	}

	private ResourceInformationService getResourceInformationService() {
		if (resourceInformationService == null) {
			resourceInformationService = new FileResourceInformationService();
		}
		return resourceInformationService;
	}

	private DatabaseInformationService getDatabaseInformationServiceCache(final String folder) {
		DatabaseInformationService databaseInformationService = null;
		if ((databaseInformationService = databaseInformationServiceCache.get(folder)) == null) {
			final ResourceInformationService resourceInformationService = getResourceInformationService();
			databaseInformationService = new DatabaseInformationService(folder, resourceInformationService);
			databaseInformationServiceCache.put(folder, databaseInformationService);
		}
		return databaseInformationService;
	}
}
