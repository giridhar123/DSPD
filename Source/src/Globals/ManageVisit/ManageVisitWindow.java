package Globals.ManageVisit;

import java.awt.EventQueue;
import Globals.Globals;
import Globals.VisitTable;
import Globals.About.AboutControl;
import Globals.Entities.Prenotazione;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.table.DefaultTableModel;
import Globals.Boundary;
import Globals.Control;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import javax.swing.JScrollPane;

public class ManageVisitWindow implements Boundary{

	private JFrame frame;
	private ManageVisitControl control;
	private Control previousControl;
	private VisitTable table;

	/**
	 * Launch the application.
	 */
	public void open(ManageVisitWindow window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					control.fillTable();
					window.frame.setResizable(false);			//Finestra non ridimensionabile
					window.frame.setLocationRelativeTo(null);   //Apertura al centro
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @param previousControl2 
	 * @param printHistoryControl 
	 */
	public ManageVisitWindow(ManageVisitControl control, Control previousControl) {
		this.control = control;
		this.previousControl = previousControl;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 753, 371);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton printButton = new JButton("Stampa");
		printButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(printButton.isEnabled())
					print();
			}
		});
		printButton.setBounds(544, 224, 119, 25);
		frame.getContentPane().add(printButton);
		
		JButton backButton = new JButton("Indietro");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(backButton.isEnabled()) {
					control.closeWindow();
					previousControl.openWindow();
				}
			}
		});
		backButton.setBounds(6, 222, 117, 29);
		frame.getContentPane().add(backButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 723, 203);
		frame.getContentPane().add(scrollPane);
		
		table = new VisitTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"NRE", "Tipo Visita", "Data", "Ora Inizio", "Ora Fine", "Ambulatorio", "Reparto", "Medico"
			}
		));
		
		scrollPane.setViewportView(table);		
		
		JLabel lblNewLabel = new JLabel("Le visite in rosso non sono state ancora effettuate ed \u00E8 possibile modificarle facendo doppio click.");
		lblNewLabel.setBounds(6, 250, 639, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblLeVisiteIn = new JLabel("Le visite in verde sono state concluse e facendo doppio click \u00E8 possibile visualizzarne il referto.");
		lblLeVisiteIn.setBounds(6, 278, 639, 16);
		frame.getContentPane().add(lblLeVisiteIn);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2)
					control.onVisitClicked();
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmEsci = new JMenuItem("Esci");
		mntmEsci.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			System.exit(0);
			}
		});
		mnFile.add(mntmEsci);
		
		JMenu menu = new JMenu("?");
		menu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(menu);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				AboutControl aboutControl=new AboutControl();
				aboutControl.openWindow();
			}
		});
		menu.add(mntmAbout);
	}
	
	public void closeFrame()
	{
		frame.dispose();
	}
	
	public void addRowToTable(Prenotazione prenotazione)
	{
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.addRow(new Object[] {prenotazione.getNre(), prenotazione.getNomeTipo(), prenotazione.getData(), prenotazione.getOraInizio(), prenotazione.getOraFine(), prenotazione.getNomeAmbulatorio(), prenotazione.getNomeReparto(), prenotazione.getNomeMedico() + " " + prenotazione.getCognomeMedico()});
	}
	
	public int getSelectedRow()
	{
		return table.getSelectedRow();
	}
	
	public String getValueOfTableAt(int riga, int colonna)
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
