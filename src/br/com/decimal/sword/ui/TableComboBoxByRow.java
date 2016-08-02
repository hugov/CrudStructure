/**
 * 
 */
package br.com.decimal.sword.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

/**
 * @author Administrador
 *
 */
public class TableComboBoxByRow extends JFrame {
	
	List<TableCellEditor> editors = new ArrayList<TableCellEditor>(3);

    public TableComboBoxByRow()
    {
        // Create the editors to be used for each row

        String[] items1 = { "Red", "Blue", "Green" };
        JComboBox comboBox1 = new JComboBox( items1 );
        DefaultCellEditor dce1 = new DefaultCellEditor( comboBox1 );
        editors.add( dce1 );

//        String[] items2 = { "Circle", "Square", "Triangle" };
//        JComboBox comboBox2 = new JComboBox( items2 );
//        DefaultCellEditor dce2 = new DefaultCellEditor( comboBox2 );
//        editors.add( dce2 );
//
//        String[] items3 = { "Apple", "Orange", "Banana" };
//        JComboBox comboBox3 = new JComboBox( items3 );
//        DefaultCellEditor dce3 = new DefaultCellEditor( comboBox3 );
//        editors.add( dce3 );

        //  Create the table with default data

        Object[][] data =
        {
            {"Color", "Red"},
            {"Shape", "Square"},
            {"Fruit", "Banana"},
            {"Plain", "Text"}
        };
        String[] columnNames = {"Type","Value"};
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        
        JTable table = new JTable(model)
        {
        	
            //  Determine editor to be used by row
//            public TableCellEditor getCellEditor(int row, int column)
//            {
//                int modelColumn = convertColumnIndexToModel( column );
//
//                if (modelColumn == 1 && row < 3)
//                    return editors.get(row);
//                else
//                    return super.getCellEditor(row, column);
//            }
        };
        
        TableColumn col = table.getColumnModel().getColumn(1);
        col.setCellEditor( dce1 );

        JScrollPane scrollPane = new JScrollPane( table );
        getContentPane().add( scrollPane );
    }

    public static void main(String[] args)
    {
        TableComboBoxByRow frame = new TableComboBoxByRow();
        frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
        frame.pack();
        frame.setVisible(true);
    }

}
