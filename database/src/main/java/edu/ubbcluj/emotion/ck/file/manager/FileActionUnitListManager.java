package edu.ubbcluj.emotion.ck.file.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.ubbcluj.emotion.ck.file.info.FileResourceInformationService;
import edu.ubbcluj.emotion.database.file.info.ResourceInformationService;
import edu.ubbcluj.emotion.database.file.manager.ActionUnitListManager;
import edu.ubbcluj.emotion.database.file.manager.ResourceManagerException;
import edu.ubbcluj.emotion.model.ActionUnit;
import edu.ubbcluj.emotion.model.ActionUnitList;
import edu.ubbcluj.emotion.util.Constants;
import edu.ubbcluj.emotion.util.StringUtil;

public class FileActionUnitListManager implements ActionUnitListManager {

	private final ResourceInformationService	resInfo	= new FileResourceInformationService();

	@Override
	public ActionUnitList loadActionUnitList(final String folder, final String subject, final String sequence) {
		final int lastIndex = resInfo.getSequenceLength(Constants.ORIGINAL_FOLDER, subject, sequence);
		final String name = StringUtil.buildActionUnitsFolderName(Constants.ORIGINAL_FOLDER).concat(StringUtil.buildSubjectFolder(subject))
				.concat(StringUtil.buildSequenceFolder(sequence)).concat(StringUtil.buildFileName(subject, sequence, lastIndex))
				.concat(Constants.FACS_FILE);
		final File f = new File(name);
		final List<ActionUnit> aulist = new ArrayList<>();

		try (Scanner scanner = new Scanner(f)) {
			while (scanner.hasNextFloat()) {
				aulist.add(new ActionUnit(((Float) scanner.nextFloat()).intValue(), ((Float) scanner.nextFloat()).intValue()));
			}
		} catch (final FileNotFoundException e) {
			throw new ResourceManagerException("Error loading action unit list for subject " + subject + " sequence " + sequence, e);
		}

		return new ActionUnitList(aulist);
	}

	@Override
	public void saveActionUnitList(final String folder, final ActionUnitList actionUnitList, final String subject, final String sequence) {
		throw new ResourceManagerException("Not implemented");
	}

}
