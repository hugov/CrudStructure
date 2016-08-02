/**
 * 
 */
package br.com.decimal.sword.ui;

import javax.swing.table.DefaultTableModel;

/**
 * @author Administrador
 *
 */
public class MyTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 5753014079278557112L;

	public MyTableModel() {
		super(new String[]{ "Coluna", "Atributo", "Tipo", "PK" }, 0);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Class clazz = String.class;
		switch (columnIndex) {
		case 3:
			clazz = Boolean.class;
			break;
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

//	@Override
//	public void setValueAt(Object aValue, int row, int column) {
//		if (aValue instanceof Boolean && column == 3) {
//			System.out.println(aValue);
//			Vector rowData = (Vector)getDataVector().get(row);
//			rowData.set(3, Boolean.valueOf(aValue.toString()));
//			fireTableCellUpdated(row, column);
//		} else {
//			System.out.println(aValue);
//			fireTableCellUpdated(row, column);
//		}
//		
//	}

}
