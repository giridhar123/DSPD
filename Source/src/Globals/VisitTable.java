package Globals;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class VisitTable extends JTable{
	
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
	{
	    Component comp = super.prepareRenderer(renderer, row, column);
	    int modelRow = convertRowIndexToModel(row);
	    String medico  = (String) this.getModel().getValueAt(modelRow, 7);

	    if(medico.equals(" ")){
	        comp.setBackground(Color.RED);
	    }
	    else comp.setBackground(Color.GREEN);
	    
	    return comp;
	}

}
