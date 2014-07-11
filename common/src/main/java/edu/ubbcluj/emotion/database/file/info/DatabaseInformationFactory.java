package edu.ubbcluj.emotion.database.file.info;

public abstract class DatabaseInformationFactory {
	
	public abstract DatabaseInformationService getDatabaseInformationService(final String folder);
	
	public abstract String[] listDatabases();
	
}
