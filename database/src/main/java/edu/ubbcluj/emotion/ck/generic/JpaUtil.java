package edu.ubbcluj.emotion.ck.generic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaUtil {

	private static EntityManagerFactory	entityManagerFactory;
	private static Logger logger = LoggerFactory.getLogger(JpaUtil.class);

	static {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("emotion.openimajdiff");
		} catch (Throwable e) {
			logger.error("Creating EntityManagerFactory failed " + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public static EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

}
