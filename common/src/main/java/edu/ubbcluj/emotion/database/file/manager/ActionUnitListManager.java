package edu.ubbcluj.emotion.database.file.manager;

import edu.ubbcluj.emotion.model.ActionUnitSet;

public interface ActionUnitListManager {

	public ActionUnitSet loadActionUnitList(final String folder, final String subject, final String sequence);

	public void saveActionUnitList(final String folder, final ActionUnitSet actionUnitList, final String subject, final String sequence);
}
