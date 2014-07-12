package edu.ubbcluj.emotion.ck.generic;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaUtil {

	private static Logger								logger					= LoggerFactory.getLogger(JpaUtil.class);
	private static Map<String, EntityManagerFactory>	entityManagerFactories	= new HashMap<>();

	public static EntityManagerFactory getEntityManagerFactory(String database) {
		EntityManagerFactory entityManagerFactory;
		if ((entityManagerFactory = entityManagerFactories.get(database)) == null) {
			try {
				entityManagerFactory = Persistence.createEntityManagerFactory(database);
				entityManagerFactories.put(database, entityManagerFactory);
			} catch (Throwable e) {
				logger.error("Creating EntityManagerFactory failed " + e);
				throw new ExceptionInInitializerError(e);
			}
		}
		return entityManagerFactory;
	}

	public static EntityManager getEntityManager(String database) {
		return getEntityManagerFactory(database).createEntityManager();
	}

}
