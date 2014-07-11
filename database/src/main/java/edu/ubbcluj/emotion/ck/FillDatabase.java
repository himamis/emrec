package edu.ubbcluj.emotion.ck;

import edu.ubbcluj.emotion.ck.file.info.FileDatabaseInformationFactory;
import edu.ubbcluj.emotion.ck.file.manager.FileResourceManagerFactory;
import edu.ubbcluj.emotion.ck.generic.JpaGenericDao;
import edu.ubbcluj.emotion.ck.generic.JpaUtil;
import edu.ubbcluj.emotion.database.file.info.DatabaseInformationFactory;
import edu.ubbcluj.emotion.database.file.info.DatabaseInformationService;
import edu.ubbcluj.emotion.database.file.manager.ResourceManager;
import edu.ubbcluj.emotion.database.file.manager.ResourceManagerFactory;
import edu.ubbcluj.emotion.database.generic.GenericDao;
import edu.ubbcluj.emotion.model.Subject;

class FillDatabase {
	
	public static final String database = "openimaj_diff";

	public static void main(String[] args) {
	//	GenericDao<Subject, Long> pdao = new JpaGenericDao<>(Subject.class);

		ResourceManagerFactory rmf = new FileResourceManagerFactory();
		DatabaseInformationFactory dif = new FileDatabaseInformationFactory();
		
		ResourceManager rm = rmf.getResourceManager(database);
		DatabaseInformationService dis = dif.getDatabaseInformationService(database);
		
		for (String subject : dis.getSubjects()) {
			Subject sub = rm.loadSubject(subject, ResourceManager.ALL);
			rm.toString();
		//	pdao.create(sub);
		}
		
		//pdao.close();
		//JpaUtil.getEntityManagerFactory().close();
	}
}
