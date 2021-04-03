package Globals.ManageVisit.ShowReport;

import java.awt.EventQueue;
import javax.swing.JFrame;
import Globals.Boundary;
import Globals.Control;
import Globals.About.AboutControl;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class ShowReportWindow implements Boundary{

	private JFrame frame;
	private ShowReportControl control;
	private Control previousControl;
	private JTextField pazienteField;
	private JTextField nreField;
	private JTextField tipoField;
	private JTextField dataField;
	private JTextField oraInizioField;
	private JTextField oraFineField;
	private JTextField ambulatorioField;
	private JTextField cfField;
	private JTextArea reportArea;
	
	/**
	 * Launch the application.
	 */
	public void open(ShowReportWindow window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					control.fillContents();
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
	public ShowReportWindow(ShowReportControl control, Control previousControl) {
		this.control = control;
		this.previousControl = previousControl;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 517, 352);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		frame.getContentPane().setLayout(null);
		
		JButton okButton = new JButton("Ok");
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				control.closeWindow();
				previousControl.openWindow();
			}
		});
		okButton.setBounds(397, 262, 96, 21);
		frame.getContentPane().add(okButton);
		
		pazienteField = new JTextField();
		pazienteField.setEditable(false);
		pazienteField.setBounds(342, 28, 108, 19);
		frame.getContentPane().add(pazienteField);
		pazienteField.setColumns(10);
		
		nreField = new JTextField();
		nreField.setEditable(false);
		nreField.setBounds(99, 57, 114, 19);
		frame.getContentPane().add(nreField);
		nreField.setColumns(10);
		
		tipoField = new JTextField();
		tipoField.setEditable(false);
		tipoField.setBounds(342, 86, 108, 19);
		frame.getContentPane().add(tipoField);
		tipoField.setColumns(10);
		
		dataField = new JTextField();
		dataField.setEditable(false);
		dataField.setBounds(342, 57, 108, 19);
		frame.getContentPane().add(dataField);
		dataField.setColumns(10);
		
		oraInizioField = new JTextField();
		oraInizioField.setEditable(false);
		oraInizioField.setBounds(99, 115, 114, 19);
		frame.getContentPane().add(oraInizioField);
		oraInizioField.setColumns(10);
		
		oraFineField = new JTextField();
		oraFineField.setEditable(false);
		oraFineField.setBounds(342, 115, 108, 19);
		frame.getContentPane().add(oraFineField);
		oraFineField.setColumns(10);
		
		reportArea = new JTextArea();
		reportArea.setEnabled(false);
		reportArea.setEditable(false);
		reportArea.setBounds(39, 182, 334, 101);
		frame.getContentPane().add(reportArea);
		
		ambulatorioField = new JTextField();
		ambulatorioField.setEditable(false);
		ambulatorioField.setBounds(99, 86, 114, 19);
		frame.getContentPane().add(ambulatorioField);
		ambulatorioField.setColumns(10);
		
		JLabel lblPaziente = new JLabel("Nome Cognome");
		lblPaziente.setBounds(258, 31, 74, 13);
		frame.getContentPane().add(lblPaziente);
		
		cfField = new JTextField();
		cfField.setEditable(false);
		cfField.setBounds(99, 28, 114, 19);
		frame.getContentPane().add(cfField);
		cfField.setColumns(10);
		
		JLabel lblCodiceFiscale = new JLabel("Codice Fiscale");
		lblCodiceFiscale.setBounds(10, 31, 79, 13);
		frame.getContentPane().add(lblCodiceFiscale);
		
		JLabel lblNre = new JLabel("NRE");
		lblNre.setBounds(43, 60, 46, 13);
		frame.getContentPane().add(lblNre);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(270, 89, 62, 13);
		frame.getContentPane().add(lblTipo);
		
		JLabel lblData = new JLabel("Data");
		lblData.setBounds(268, 60, 46, 13);
		frame.getContentPane().add(lblData);
		
		JLabel lblAmbulatorio = new JLabel("Ambulatorio");
		lblAmbulatorio.setBounds(10, 89, 79, 13);
		frame.getContentPane().add(lblAmbulatorio);
		
		JLabel lblOraInizio = new JLabel("Ora Inizio");
		lblOraInizio.setBounds(20, 118, 46, 13);
		frame.getContentPane().add(lblOraInizio);
		
		JLabel lblOraFine = new JLabel("Ora Fine");
		lblOraFine.setBounds(268, 118, 46, 13);
		frame.getContentPane().add(lblOraFine);
		
		JLabel lblRefertoVisita = new JLabel("Referto visita");
		lblRefertoVisita.setBounds(39, 159, 120, 13);
		frame.getContentPane().add(lblRefertoVisita);
	}
	
	public void closeFrame()
	{
		frame.dispose();
	}
	
	public void setReportAreaText(String text) {
		reportArea.setText(text);
	}
	
	/*
	 * CodiceFiscaleFieldText
	 */
	
	public void setCfFieldText(String text)
	{
		cfField.setText(text);
	}
	
	/*
	 * PazienteFieldText
	 */
	
	public void setPazienteFieldText(String text)
	{
		pazienteField.setText(text);
	}
	
	/*
	 * TipoFieldText
	 */
	
	public void setTipoFieldText(String text)
	{
		tipoField.setText(text);
	}
	
	/*
	 * DataFieldText
	 */
	
	public void setDataFieldText(String text)
	{
		dataField.setText(text);
	}
	
	/*
	 * OraInizioFieldText
	 */
	
	public void setOraInizioFieldText(String text)
	{
		oraInizioField.setText(text);
	}
	
	/*
	 * OraFineFieldText
	 */
	
	public void setOraFineFieldText(String text)
	{
		oraFineField.setText(text);
	}
	
	/*
	 * NreFieldText
	 */
	
	public void setNreFieldText(String text)
	{
		nreField.setText(text);
	}
	
	/*
	 * AmbulatorioFieldText
	 */
	
	public void setAmbulatorioFieldText(String text)
	{
		ambulatorioField.setText(text);
	}
}
