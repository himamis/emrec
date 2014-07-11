package edu.ubbcluj.emotion.database.file.loader;

public abstract class ResourceLoaderFactory {
	
	public abstract ResourceLoader getResourceLoader(final String folder);
	
}
