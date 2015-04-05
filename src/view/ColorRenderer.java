package view;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
  @author Colum Foskin version 1.0
 * 26/02/15 
 * This class is used so I can change the default render to a colour renderer.
 * This class was taken from oracle and edited to suit my needs
 */
public class ColorRenderer extends JLabel implements TableCellRenderer {
	private static final long serialVersionUID = 1L;

	public ColorRenderer() {
		setOpaque(true); //MUST do this for background to show up.
	}

	public Component getTableCellRendererComponent(
			JTable table, Object color,
			boolean isSelected, boolean hasFocus,
			int row, int column) {
		setBackground((Color) color);
		return this;
	}

}

