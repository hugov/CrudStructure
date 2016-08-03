/**
 * 
 */
package br.com.decimal.sword.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import br.com.decimal.sword.entity.Field;
import br.com.decimal.sword.service.SwordService;

/**
 * @author Vitor Hugo Oliveira
 *
 */
public class Principal extends JFrame {
	
	private static final long serialVersionUID = 7399173442135140747L;
	
	private static final int COLUNA_BD = 0;
	private static final int NOME_ATRIBUTO = 1;
	private static final int TIPO_ATRIBUTO = 2;
	private static final int PK_BD = 3;
	
	private List<TableCellEditor> editors = new ArrayList<TableCellEditor>(4);
	
	private JPanel selecioneTabelaPanel;
	private JComboBox<String> selecioneTabelaComboBox;
	private JPanel definaAtributosObjetoPanel;
	private JLabel nomeAtributoLabel;
	private JTextField nomeAtributoTextField;
	private JScrollPane scrollPane;
	private JTable definaAtributosObjetoTable;
	private JButton runButton;
	private JButton clearButton;
	
	private SwordService swordService;

	public Principal() {
		setSize(800, 500);
		setLayout(null);
		setTitle("Decimal LTDA - Code Builder 0.0.1");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.swordService = new SwordService();

		initComponents();
		
	}
	
	public void initComponents() {
		
		final MyTableModel tableModel = new MyTableModel();
		
		selecioneTabelaPanel = new JPanel(null);
		selecioneTabelaPanel.setBorder(new TitledBorder("Selecione a tabela"));
		selecioneTabelaPanel.setBounds(20, 20, 750, 100);
		
		selecioneTabelaComboBox = new JComboBox<>();
		selecioneTabelaComboBox.setBounds(40, 40, 450, 25);
		
		fillComboBox(selecioneTabelaComboBox , swordService.listTableName());
		
		selecioneTabelaComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				
				String item = (String)e.getItem();
				
				if(!item.equals("Selecione ...")) {
					clearTable(tableModel);
					String[] columns = swordService.listColumnName(item);
					fillTable(tableModel, columns);
				} else {
					screenClear(tableModel);
				}
				
			}
		});
		
		selecioneTabelaPanel.add(selecioneTabelaComboBox);
		
		definaAtributosObjetoPanel = new JPanel(null);
		definaAtributosObjetoPanel.setBorder(new TitledBorder("Defina os atributos do objeto"));
		definaAtributosObjetoPanel.setBounds(20, 140, 750, 250);
		
		nomeAtributoLabel = new JLabel("Nome do Objeto");
		nomeAtributoLabel.setBounds(40, 40, 150, 25);
		
		nomeAtributoTextField = new JTextField();
		nomeAtributoTextField.setBounds(150, 40, 340, 25);
		
		definaAtributosObjetoPanel.add(nomeAtributoLabel);
		definaAtributosObjetoPanel.add(nomeAtributoTextField);
		
		definaAtributosObjetoTable = new JTable();
		definaAtributosObjetoTable.setBounds(40, 80, 670, 150);
		
		definaAtributosObjetoTable.setModel(tableModel);
		
		TableColumn col = definaAtributosObjetoTable.getColumnModel().getColumn( TIPO_ATRIBUTO );
		
		String[] dataType = { "java.lang.Integer", "java.lang.Long", "java.lang.String", "java.util.Date" };
        JComboBox<String> dataTypeComboBox = new JComboBox<String>( dataType );
        DefaultCellEditor dataTypeCellEditor = new DefaultCellEditor( dataTypeComboBox );
        editors.add( dataTypeCellEditor );
		
        col.setCellEditor( dataTypeCellEditor );
		
		scrollPane = new JScrollPane(definaAtributosObjetoTable);
		scrollPane.setBounds(40, 80, 670, 150);
		//scrollPane.setSize(300, 100);
		
		definaAtributosObjetoPanel.add(scrollPane);
		
		runButton = new JButton("Executar");
		runButton.setBounds(550, 420, 100, 30);
		//executarButton.setVisible(true);
		
		runButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(!validateForm( tableModel )) {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos do formulário.", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				defineTableModelEditable(tableModel);
				
				Field [] fields = captureField(tableModel);
				swordService.create(fields, "C:\\TEMP\\");

				JOptionPane.showMessageDialog(null, "Arquivos gerados com sucesso !!!");
				
			}
		});
		
		clearButton = new JButton("Limpar");
		clearButton.setBounds(670, 420, 100, 30);
		//executarButton.setVisible(true);
		
		clearButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				screenClear(tableModel);
			}
			
		});
		
		add(selecioneTabelaPanel);
		add(definaAtributosObjetoPanel);
		add(runButton);
		add(clearButton);
	}
	
	private boolean validateForm(DefaultTableModel tableModel) {
		
		if(selecioneTabelaComboBox.getSelectedIndex() == 0)
			return false;
		
		if("".equals(nomeAtributoTextField.getText()))
			return false;
		
		int rowCount = tableModel.getRowCount();
		int columnCount = tableModel.getColumnCount();
		
		boolean isPKSelected = false;
		for(int row = 0; row < rowCount; row++) {
			Object value = tableModel.getValueAt(row, PK_BD);
			isPKSelected = Boolean.valueOf(value.toString());
			
			if(isPKSelected) {
				break;
			}
		}
		
		if(!isPKSelected) {
			return false;
		}
		
		for(int row = 0; row < rowCount; row++) {
			// Ignora a coluna de PK
			for(int column = 0; column < columnCount - 1; column++) {
				Object value = tableModel.getValueAt(row, column);
				
				if(value == null || "".equals(value)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Principal principal = new Principal();                
                principal.setVisible(true);
            }
        });
	}
	
	private Field [] captureField(DefaultTableModel tableModel) {
		String table = (String)selecioneTabelaComboBox.getSelectedItem();
		String objectName = nomeAtributoTextField.getText();
		
		int rowCount = tableModel.getRowCount();
		int columnCount = tableModel.getColumnCount();
		
		Field [] fields = new Field[ rowCount ];
		
		for(int row = 0; row < rowCount; row++) {
			
			Field field = new Field();
			field.setClassName( objectName );
			field.setTableName( table );
			
			for(int column = 0; column < columnCount; column++) {
				Object value = tableModel.getValueAt(row, column);
				
				if(column == COLUNA_BD) {
					field.setColumnName( (String)value );
				} else if(column == NOME_ATRIBUTO) {
					field.setFieldName( (String)value );
				} else if(column == TIPO_ATRIBUTO) {
					field.setFieldType( (String)value );
				} else if(column == PK_BD) {
					field.setPK( Boolean.valueOf(value.toString()) );
				}
				
			}
			
			fields[ row ] = field;
			
		}
		
		return fields;
	}
	
	private void defineTableModelEditable(final DefaultTableModel tableModel) {
		int rowCount = tableModel.getRowCount();
		int columnCount = tableModel.getColumnCount();
		
		for(int row = 0; row < rowCount; row++) {
			for(int column = 0; column < columnCount; column++) {
				tableModel.isCellEditable(row, column);
			}
		}
	}
	
	private void screenClear(final DefaultTableModel tableModel) {
		selecioneTabelaComboBox.setSelectedIndex(0);
		clearTable(tableModel);
		nomeAtributoTextField.setText("");
	}
	
	private void fillComboBox(JComboBox<String> comboBox, String [] tables) {
		for(String tableName : tables) {
			comboBox.addItem(tableName);
		}
	}
	
	private void clearTable(DefaultTableModel tableModel) {
		int rowCount = tableModel.getRowCount();
		for(int row = 0; row < rowCount; row++) {
			tableModel.removeRow( 0 ); 
		}
		
		definaAtributosObjetoTable.setModel(tableModel);
		
	}
	
	private void fillTable( DefaultTableModel tableModel, String [] columns ) {
		for(String column : columns) {
			Object[] linha = new Object[] { column, "", "", false };
		    tableModel.addRow(linha);
		}
	}
	
}
