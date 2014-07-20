package edu.ubbcluj.emotion.algorithm;

public interface BatchTrainer<T> {
	void train(T[] data) throws AlgorithmException;
}
