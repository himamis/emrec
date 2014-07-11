package edu.ubbcluj.emotion.database.generic;

import java.io.Serializable;

public interface GenericDao<T, PK extends Serializable> {

	T create(T newInstance);

	T read(PK id);

	void update(T transientObject);

	void delete(T persistentObject);
	
	void close();
}
