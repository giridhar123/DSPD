package MedicMode.ManageVisitDoctor;

import java.awt.EventQueue;
import javax.swing.JFrame;
import Globals.Boundary;
import Globals.Control;
import Globals.Globals;
import Globals.About.AboutControl;
import Globals.Entities.Prenotazione;
import Globals.ManageVisit.ShowReport.ShowReportControl;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class ManageVisitDoctorWindow implements Boundary{

	private JFrame frame;
	private ManageVisitDoctorControl control;
	private Control previousControl;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public void open(ManageVisitDoctorWindow window) {
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
	 */
	public ManageVisitDoctorWindow(ManageVisitDoctorControl control, Control previousControl) {
		this.control = control;
		this.previousControl = previousControl;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 712, 341);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 10, 688, 213);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Codice Fiscale", "Paziente", "NRE", "Tipo Ricetta", "Data", "Ora Inzio", "Ora Fine", "Ambulatorio"
			}
		));
		scrollPane.setViewportView(table);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int riga = table.getSelectedRow();
					String nre = (String) table.getModel().getValueAt(riga, 2);
					Prenotazione prenotazione = new Prenotazione(nre);
					ShowReportControl reportControl = new ShowReportControl(control, prenotazione);
					reportControl.openWindow();
					control.closeWindow();
				}
			}
		});
		
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
		backButton.setBounds(603, 251, 85, 21);
		frame.getContentPane().add(backButton);
		
		JButton stampaButton = new JButton("Stampa");
		stampaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(stampaButton.isEnabled())
					print();
			}
		});
		stampaButton.setBounds(463, 247, 117, 29);
		frame.getContentPane().add(stampaButton);
		
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
		model.addRow(new Object[] {prenotazione.getCfPaziente(), prenotazione.getNomePaziente() + " " + prenotazione.getCognomePaziente(), prenotazione.getNre(), prenotazione.getNomeTipo(), prenotazione.getData(), prenotazione.getOraInizio(), prenotazione.getOraFine(), prenotazione.getNomeAmbulatorio()});
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
