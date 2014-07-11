package edu.ubbcluj.emotion.fastica;

import org.fastica.ContrastFunction;
import org.fastica.EigenValueFilter;
import org.fastica.FastICAConfig;
import org.fastica.FastICAException;
import org.fastica.ProgressListener;

public class FastICA extends org.fastica.FastICA {

	public FastICA(double[][] inVectors, FastICAConfig config, ContrastFunction conFunction, EigenValueFilter evFilter, ProgressListener listener)
			throws FastICAException {
		super(inVectors, config, conFunction, evFilter, listener);
	}

}
