package edu.ubbcluj.emotion.swing;

import javax.swing.table.AbstractTableModel;

import edu.ubbcluj.emotion.model.ActionUnit;
import edu.ubbcluj.emotion.model.ActionUnitList;

public class FacsTableModel extends AbstractTableModel {

	private static final long	serialVersionUID	= 1L;
	private ActionUnitList		aulist;

	public int getColumnCount() {
		return 3;
	}

	public int getRowCount() {
		if (aulist == null) {
			return 0;
		}
		return aulist.size();
	}

	public Object getValueAt(int row, int col) {
		if (aulist == null) {
			return null;
		}
		ActionUnit au = aulist.get(row);
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

	public void setAuList(ActionUnitList aulist) {
		this.aulist = aulist;
		fireTableDataChanged();
	}

}
