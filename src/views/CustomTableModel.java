package views;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class CustomTableModel extends DefaultTableModel {
	
	@Override
	public boolean isCellEditable(int row, int column) {
		if (row == 0) {
			return false;
		}
		if (column == 0) {
			return false;
		}
		if (column == Busqueda.tbHuespedes.getColumnCount() - 1) {
			return false;
		}
		return super.isCellEditable(row, column);
	}
}
