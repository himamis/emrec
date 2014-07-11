package edu.ubbcluj.emotion.database.file.info;

public class DatabaseInformationException extends RuntimeException {

	private static final long	serialVersionUID	= 1L;

	public DatabaseInformationException() {
		super();
	}
	
	public DatabaseInformationException(String message) {
		super(message);
	}
	
	public DatabaseInformationException(String message, Throwable linkedException) {
		super(message, linkedException);
	}
}
