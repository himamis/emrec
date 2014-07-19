package edu.ubbcluj.emotion.swing;

import javax.swing.table.AbstractTableModel;

import edu.ubbcluj.emotion.model.ActionUnit;
import edu.ubbcluj.emotion.model.ActionUnitSet;

public class FacsTableModel extends AbstractTableModel {

	private static final long	serialVersionUID	= 1L;
	private Object[]		auArray;

	public int getColumnCount() {
		return 3;
	}

	public int getRowCount() {
		if (auArray == null) {
			return 0;
		}
		return auArray.length;
	}

	public Object getValueAt(int row, int col) {
		if (auArray == null) {
			return null;
		}
		ActionUnit au = (ActionUnit) auArray[row];
		if (col == 0) {
			return au.getCode();
		} else if (col == 1) {
			return au.getIntensity();
		} else {
			return au.getDescription();
		}
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

	@Override
	public String getColumnName(int column) {
		if (column == 0) {
			return "AU CODE";
		} else if (column == 1) {
			return "INTENSITY";
		} else {
			return "DESCRIPTION";
		}
	}

	public void setAuList(ActionUnitSet aulist) {
		this.auArray = aulist.getActionUnits().toArray();
		fireTableDataChanged();
	}

}
