package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
class TableHeaderStyle extends DefaultTableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (row == 0) {
			label.setOpaque(true);
			label.setBackground(new Color(12, 128, 189));
			label.setForeground(new Color(250,250,250));
			label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(50,50,50)));
			label.setFont(new Font("Tahoma",Font.BOLD,13));
			label.setPreferredSize(new Dimension(label.getWidth(), 40));
		}else {
			label.setOpaque(true);
			if (isSelected) {
				label.setBackground(table.getSelectionBackground());
				label.setForeground(table.getSelectionForeground());
			} else {
				label.setBackground(Color.WHITE);
				label.setForeground(Color.BLACK);
			}
			label.setBorder(null);
			label.setFont(table.getFont());
			label.setPreferredSize(new Dimension(label.getWidth(), table.getRowHeight()));
		}
		return label;
	}
}