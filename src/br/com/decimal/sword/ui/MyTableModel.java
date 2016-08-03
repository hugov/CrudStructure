package br.com.decimal.sword.ui;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 5753014079278557112L;

	public MyTableModel() {
		super(new String[]{ "Coluna", "Atributo", "Tipo", "PK" }, 0);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Class<?> clazz = String.class;
		if(columnIndex == 3) {
			clazz = Boolean.class;
		}
		return clazz;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		
		if(column == 1 || column == 2 || column == 3) {
			return true;
		}

		return false;
	}

}
