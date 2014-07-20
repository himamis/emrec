package edu.ubbcluj.emotion.algorithm;

public class AlgorithmException extends Exception {

	private static final long	serialVersionUID	= 1L;

	public AlgorithmException() {
	}

	public AlgorithmException(String message) {
		super(message);
	}

	public AlgorithmException(Throwable cause) {
		super(cause);
	}

	public AlgorithmException(String message, Throwable cause) {
		super(message, cause);
	}
}
