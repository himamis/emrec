package edu.ubbcluj.emotion.database.file.manager;

import edu.ubbcluj.emotion.model.ActionUnitList;

public interface ActionUnitListManager {

	public ActionUnitList loadActionUnitList(final String folder, final String subject, final String sequence);

	public void saveActionUnitList(final String folder, final ActionUnitList actionUnitList, final String subject, final String sequence);
}
