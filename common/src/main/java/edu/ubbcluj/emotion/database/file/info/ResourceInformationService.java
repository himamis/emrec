package edu.ubbcluj.emotion.database.file.info;

public interface ResourceInformationService {
	public String[] getSubjects(final String folder);

	public String[] getSequences(final String folder, final String subject);
	
	public String[] getImages(final String folder, final String subject, final String sequence);
	
	public String[] getLandmarks(final String folder, final String subject, final String sequence);
	
	public String getEmotion(final String folder, final String subject, final String sequence);
	
	public String getActionUnitList(final String folder, final String subject, final String sequence);

	public int getSequenceLength(final String folder, final String subject, final String sequence);

	public String[] listDatabases();
}
