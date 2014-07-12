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
import edu.ubbcluj.emotion.util.Constants;

class FillDatabase {

	public static final String	folder		= Constants.ORIGINAL_FOLDER;

	public static final String	database	= "emotion.original";

	public static void main(String[] args) {
		GenericDao<Subject, Long> pdao = new JpaGenericDao<>(database, Subject.class);

		ResourceManagerFactory rmf = new FileResourceManagerFactory();
		DatabaseInformationFactory dif = new FileDatabaseInformationFactory();

		ResourceManager rm = rmf.getResourceManager(folder);
		DatabaseInformationService dis = dif.getDatabaseInformationService(folder);

		String[] subjects = dis.getSubjects();

		for (int i = 0; i < 10; i++) {
			String subject = subjects[i];
			Subject sub = rm.loadSubject(subject, ResourceManager.ALL);
			System.out.println("Subject + " + subject + " loaded");
			pdao.create(sub);
		}

		pdao.close();
		JpaUtil.getEntityManagerFactory(database).close();
	}
}
