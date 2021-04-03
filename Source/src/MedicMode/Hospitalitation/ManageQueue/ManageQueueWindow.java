package MedicMode.Hospitalitation.ManageQueue;

import java.awt.EventQueue;
import javax.swing.JFrame;
import Globals.Boundary;
import Globals.Control;
import Globals.About.AboutControl;

import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class ManageQueueWindow implements Boundary{

	private JFrame frame;
	private ManageQueueControl control;
	private Control previousControl;
	private JTable ricoveratiTable;
	private JTable inCodaTable;
	
	private JButton ricoveraButton;

	/**
	 * Launch the application.
	 */
	public void open(ManageQueueWindow window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frame.setResizable(false);			//Finestra non ridimensionabile
					window.frame.setLocationRelativeTo(null);   //Apertura al centro
					control.fillTables();
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
	public ManageQueueWindow(ManageQueueControl control, Control previousControl) {
		this.control = control;
		this.previousControl = previousControl;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 462, 458);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 34, 438, 98);
		frame.getContentPane().add(scrollPane);
		
		ricoveratiTable = new JTable();
		//ricoveratiTable.setDefaultEditor(Object.class, null);
		ricoveratiTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Codice Fiscale", "Paziente", "Ricoverato dal"
				}
			));
		scrollPane.setViewportView(ricoveratiTable);
		
		JLabel lblNewLabel = new JLabel("Pazienti ricoverati");
		lblNewLabel.setBounds(160, 6, 131, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 210, 438, 98);
		frame.getContentPane().add(scrollPane_1);
		
		inCodaTable = new JTable();
		//inCodaTable.setDefaultEditor(Object.class, null);
		inCodaTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Codice Fiscale", "Paziente", "In coda dal", "Numero"
				}
			));
		scrollPane_1.setViewportView(inCodaTable);
		
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
		backButton.setBounds(327, 360, 117, 29);
		frame.getContentPane().add(backButton);
		
		JLabel lblPazientiInCoda = new JLabel("Pazienti in coda");
		lblPazientiInCoda.setBounds(177, 185, 131, 16);
		frame.getContentPane().add(lblPazientiInCoda);
		
		JButton dismissButton = new JButton("Dimetti Paziente");
		dismissButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(dismissButton.isEnabled()) {
					String cfPaziente = (String) ricoveratiTable.getModel().getValueAt(ricoveratiTable.getSelectedRow(), 0);
					control.dismissButtonPressed(cfPaziente);
				}
			}
		});
		dismissButton.setBounds(156, 144, 135, 29);
		frame.getContentPane().add(dismissButton);
		
		ricoveraButton = new JButton("Ricovera");
		ricoveraButton.setEnabled(false);
		ricoveraButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (ricoveraButton.isEnabled())
				{
					String cfPaziente = (String) inCodaTable.getModel().getValueAt(inCodaTable.getSelectedRow(), 0);
					int oldPriorita = Integer.parseInt(inCodaTable.getModel().getValueAt(inCodaTable.getSelectedRow(), 3).toString());
					control.hospitalitationButtonPressed(cfPaziente, oldPriorita);
				}
			}
		});
		ricoveraButton.setBounds(174, 324, 117, 29);
		frame.getContentPane().add(ricoveraButton);
		
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
				AboutControl aboutControl = new AboutControl();
				aboutControl.openWindow();
			}
		});
		menu.add(mntmAbout);
	}
	
	public void closeFrame()
	{
		frame.dispose();
	}

	/*
	 * RicoveraButton
	 */
	
	public void setRicoveraButtonEnable(boolean enable)
	{
		ricoveraButton.setEnabled(enable);
	}
	
	/*
	 * RicoveratiTable
	 */
	
	public void clearRicoveratiTable()
	{
		((DefaultTableModel) ricoveratiTable.getModel()).setNumRows(0);
	}
	
	public void addRowIntoRicoveratiTable(String codiceFiscale, String paziente, String ricoveratoDal)
	{
		DefaultTableModel model = (DefaultTableModel) ricoveratiTable.getModel();
		model.addRow(new Object[] {codiceFiscale, paziente, ricoveratoDal});
	}
	
	/*
	 * InCodaTable
	 */
	
	public void addRowIntoInCodaTable(String codiceFiscale, String paziente, String inCodaDal, String priorita)
	{
		DefaultTableModel model = (DefaultTableModel) inCodaTable.getModel();
		model.addRow(new Object[] {codiceFiscale, paziente, inCodaDal, priorita});
	}
	
	public void clearInCodaTable()
	{
		((DefaultTableModel) inCodaTable.getModel()).setNumRows(0);
	}
}
