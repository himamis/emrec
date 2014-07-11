package edu.ubbcluj.emotion;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.ubbcluj.emotion.ck.file.info.FileDatabaseInformationFactory;
import edu.ubbcluj.emotion.database.file.info.DatabaseInformationFactory;
import edu.ubbcluj.emotion.database.file.info.DatabaseInformationService;

public class FileDatabaseInformationServiceTest {

	private DatabaseInformationFactory	factory;

	@Before
	public void setUp() {
		factory = new FileDatabaseInformationFactory();
	}

	@Test
	public void testDatabaseListing() {
		String[] databases = factory.listDatabases();
		Assert.assertNotNull(databases);
		Assert.assertTrue(databases.length > 0);
	}

	@Test
	public void testSubjectListing() {
		String[] databases = factory.listDatabases();
		Assert.assertNotNull(databases);
		Assert.assertTrue(databases.length > 0);
		String[] subjects = factory.getDatabaseInformationService(databases[0]).getSubjects();
		Assert.assertNotNull(subjects);
		Assert.assertTrue(subjects.length > 0);
	}

	@Test
	public void testSubjectListingFalseDatabaseName() {
		String[] subjects = factory.getDatabaseInformationService("non-existing-folder").getSubjects();
		Assert.assertNull(subjects);
	}

	@Test
	public void testSequenceListing() {
		String[] databases = factory.listDatabases();
		Assert.assertNotNull(databases);
		Assert.assertTrue(databases.length > 0);
		DatabaseInformationService dis = factory.getDatabaseInformationService(databases[0]);
		String[] subjects = dis.getSubjects();
		Assert.assertNotNull(subjects);
		Assert.assertTrue(subjects.length > 0);
		String[] sequences = dis.getSequences(subjects[0]);
		Assert.assertNotNull(sequences);
		Assert.assertTrue(sequences.length > 0);
	}

	@Test
	public void testSequenceListingFalseSubjectName() {
		String[] databases = factory.listDatabases();
		Assert.assertNotNull(databases);
		Assert.assertTrue(databases.length > 0);
		DatabaseInformationService dis = factory.getDatabaseInformationService(databases[0]);
		String[] sequences = dis.getSequences("non-existing-subject");
		Assert.assertNull(sequences);
	}

	@Test
	public void testSequenceLength() {
		String[] databases = factory.listDatabases();
		Assert.assertNotNull(databases);
		Assert.assertTrue(databases.length > 0);
		DatabaseInformationService dis = factory.getDatabaseInformationService(databases[0]);
		String[] subjects = dis.getSubjects();
		Assert.assertNotNull(subjects);
		Assert.assertTrue(subjects.length > 0);
		String[] sequences = dis.getSequences(subjects[0]);
		Assert.assertNotNull(sequences);
		Assert.assertTrue(sequences.length > 0);
		int length = dis.getSequenceLength(subjects[0], sequences[0]);
		Assert.assertEquals(length, 11);
	}
	
	@Test
	public void testSequenceLengthFalseSequenceName() {
		String[] databases = factory.listDatabases();
		Assert.assertNotNull(databases);
		Assert.assertTrue(databases.length > 0);
		DatabaseInformationService dis = factory.getDatabaseInformationService(databases[0]);
		String[] subjects = dis.getSubjects();
		Assert.assertNotNull(subjects);
		Assert.assertTrue(subjects.length > 0);
		String[] sequences = dis.getSequences(subjects[0]);
		Assert.assertNotNull(sequences);
		Assert.assertTrue(sequences.length > 0);
		int length = dis.getSequenceLength(subjects[0], sequences[0]);
		Assert.assertEquals(length, 11);
	}

}
