package edu.ubbcluj.emotion.ck;

import edu.ubbcluj.emotion.ck.generic.JpaGenericDao;
import edu.ubbcluj.emotion.ck.generic.JpaUtil;
import edu.ubbcluj.emotion.model.Landmarks;
import edu.ubbcluj.emotion.model.LandmarksSequence;
import edu.ubbcluj.emotion.model.Sequence;
import edu.ubbcluj.emotion.model.Subject;

public class RetrieveTest {

	public static void main(String[] args) {
		JpaGenericDao<Subject, Long> dao = new JpaGenericDao<>(Subject.class);
		Subject s = dao.read((long) 4);
		Sequence seq = s.getSequences().get(0);
		LandmarksSequence ls = seq.getLandmarksSequence();
		Landmarks landm = ls.get(0);
		
		JpaUtil.getEntityManager().close();
		JpaUtil.getEntityManagerFactory().close();
	}

}
