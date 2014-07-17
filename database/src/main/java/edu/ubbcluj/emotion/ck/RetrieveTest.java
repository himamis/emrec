package edu.ubbcluj.emotion.ck;

import edu.ubbcluj.emotion.ck.generic.JpaGenericDao;
import edu.ubbcluj.emotion.ck.generic.JpaUtil;
import edu.ubbcluj.emotion.model.Subject;
import edu.ubbcluj.emotion.util.Constants;

public class RetrieveTest {
	
	public static final String	folder		= Constants.ORIGINAL_FOLDER;

	public static final String	database	= "emotion.original";


	public static void main(String[] args) {
		JpaGenericDao<Subject, Long> dao = new JpaGenericDao<>(database, Subject.class);
		//Subject s = dao.read((long) 4);
		
		dao.close();
		JpaUtil.getEntityManagerFactory(database).close();
		
		
	}

}
