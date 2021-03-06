package edu.ubbcluj.emotion.ck.file.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import edu.ubbcluj.emotion.ck.file.info.FileResourceInformationService;
import edu.ubbcluj.emotion.database.file.info.ResourceInformationService;
import edu.ubbcluj.emotion.database.file.manager.ActionUnitListManager;
import edu.ubbcluj.emotion.database.file.manager.ResourceManagerException;
import edu.ubbcluj.emotion.model.ActionUnit;
import edu.ubbcluj.emotion.model.ActionUnitSet;
import edu.ubbcluj.emotion.util.Constants;
import edu.ubbcluj.emotion.util.StringUtil;

public class FileActionUnitListManager implements ActionUnitListManager {

	private final ResourceInformationService	resInfo	= new FileResourceInformationService();

	@Override
	public ActionUnitSet loadActionUnitList(final String folder, final String subject, final String sequence) {
		final String baseFolder = StringUtil.buildActionUnitsFolderName(Constants.ORIGINAL_FOLDER).concat(StringUtil.buildSubjectFolder(subject))
				.concat(StringUtil.buildSequenceFolder(sequence));
		String name = resInfo.getActionUnitList(folder, subject, sequence);
		if (name == null) {
			throw new ResourceManagerException("No action unit found for subject " + subject + " sequence " + sequence);
		}
		final File f = new File(baseFolder.concat(name));
		final Set<ActionUnit> aulist = new HashSet<>();

		try (Scanner scanner = new Scanner(f)) {
			while (scanner.hasNextFloat()) {
				aulist.add(new ActionUnit(((Float) scanner.nextFloat()).intValue(), ((Float) scanner.nextFloat()).intValue()));
			}
		} catch (final FileNotFoundException e) {
			throw new ResourceManagerException("Error loading action unit list for subject " + subject + " sequence " + sequence, e);
		}

		return new ActionUnitSet(aulist);
	}

	@Override
	public void saveActionUnitList(final String folder, final ActionUnitSet actionUnitList, final String subject, final String sequence) {
		throw new ResourceManagerException("Not implemented");
	}

}
