package edu.ubbcluj.emotion.database.file.manager;

public class ResourceManagerException extends RuntimeException {

	private static final long	serialVersionUID	= 1L;

	public ResourceManagerException() {
	}
	
	public ResourceManagerException(String message) {
		super(message);
	}
	
	public ResourceManagerException(String message, Throwable linkedException) {
		super(message, linkedException);
	}

}
