package MedicMode.VisiteAmbulatorio;

import java.awt.EventQueue;
import Globals.Entities.Prenotazione;

import javax.swing.JFrame;
import Globals.Boundary;
import Globals.Control;
import Globals.Globals;

import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;

public class VisiteAmbulatorioWindow implements Boundary{

	private JFrame frame;
	private VisiteAmbulatorioControl control;
	private Control previousControl;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public void open(VisiteAmbulatorioWindow window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frame.setResizable(false);			//Finestra non ridimensionabile
					window.frame.setLocationRelativeTo(null);   //Apertura al centro
					control.fillTable();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VisiteAmbulatorioWindow(VisiteAmbulatorioControl control, Control previousControl) {
		this.control = control;
		this.previousControl = previousControl;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 468, 319);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		frame.getContentPane().setLayout(null);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(indietroButton.isEnabled()) {
					previousControl.openWindow();
					control.closeWindow();
				}
			}
		});
		indietroButton.setBounds(327, 221, 117, 29);
		frame.getContentPane().add(indietroButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 438, 209);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					control.onRowClicked();
				}
			}
		});
		
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"NRE", "CF Paziente", "Tipo Visita", "Data", "Ora Inizio", "Ora Fine"
				}
			)
			{
				@Override
				public boolean isCellEditable(int row, int column)
				{
					return false;
				}
			}
		);
		scrollPane.setViewportView(table);
		
		JButton stampaButton = new JButton("Stampa");
		stampaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(stampaButton.isEnabled())
					print();
			}
		});
		stampaButton.setBounds(211, 221, 117, 29);
		frame.getContentPane().add(stampaButton);
	}

	@Override
	public void closeFrame() {
		frame.dispose();		
	}
	
	public void addRowToTable(Prenotazione prenotazione)
	{
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.addRow(new Object[] {prenotazione.getNre(), prenotazione.getCfPaziente(), prenotazione.getNomeTipo(), prenotazione.getData(), prenotazione.getOraInizio(), prenotazione.getOraFine()});
		
		table.setEditingRow(table.getRowCount() - 1);
	}
	
	public int getSelectedRow()
	{
		return table.getSelectedRow();
	}
	
	public String getValueFromTable(int riga, int colonna)
	{
		return (String) table.getValueAt(riga, colonna);
	}
	
	private void print() {
		try {
			table.print();
		} catch (PrinterException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
	  }
}
