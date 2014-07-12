package edu.ubbcluj.emotion.ck.generic;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;

import edu.ubbcluj.emotion.database.generic.GenericDao;

public class JpaGenericDao<T, PK extends Serializable> implements GenericDao<T, PK> {

	protected Class<T>		entityClass;
	protected EntityManager	entityManager;

	@SuppressWarnings("unchecked")
	public JpaGenericDao(String database) {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
		entityManager = JpaUtil.getEntityManager(database);
	}

	public JpaGenericDao(String database, Class<T> clazz) {
		this.entityClass = clazz;
		entityManager = JpaUtil.getEntityManager(database);
	}

	@Override
	public T create(T newInstance) {
		entityManager.getTransaction().begin();
		entityManager.persist(newInstance);
		entityManager.getTransaction().commit();
		return newInstance;
	}

	@Override
	public T read(PK id) {
		return entityManager.find(entityClass, id);
	}

	@Override
	public void update(T transientObject) {
		entityManager.merge(transientObject);

	}

	@Override
	public void delete(T persistentObject) {
		T object = entityManager.merge(persistentObject);
		entityManager.remove(object);
	}

	public void close() {
		entityManager.close();
	}

}
